package uz.bookclub.bookclubapplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.bookclub.bookclubapplication.entity.SeeCount;
import uz.bookclub.bookclubapplication.repository.SeeCountRepos;

import javax.swing.text.html.Option;
import java.util.Optional;

/**
 * created by Muhammad
 * on 26.10.2020
 */

@Service
public class CheckSeeCountService {
    @Autowired
    SeeCountRepos seeCountRepos;

    public Boolean getSeeCount(Integer id){
        Optional<SeeCount> seeCount = null;
        if (seeCountRepos.findByUser_id(id).isPresent()) {
             seeCount = Optional.of(seeCountRepos.findByUser_id(id).get());
             return seeCount.get().getTrueOrFalse();
        }
        return true;
    }

    public Optional<SeeCount> setSeeCount(Integer id){
        Optional<SeeCount> seeCountObj = null;
        if (seeCountRepos.findByUser_id(id).isPresent()){
        seeCountObj = Optional.of(seeCountRepos.findByUser_id(id).get());
        Integer seeCountNumber = seeCountObj.get().getCount();
//        Integer column_id = seeCountObj.getId();
        seeCountNumber++;
        seeCountRepos.delete(seeCountObj.get());
//        seeCountObj.setId(column_id);
        seeCountObj.get().setUser_id(id);
        seeCountObj.get().setCount(seeCountNumber);
        seeCountObj.get().setTrueOrFalse(false);
        seeCountRepos.save(seeCountObj.get());
        }else {
            Optional<SeeCount> seeCount = Optional.of(new SeeCount());
            seeCount.get().setUser_id(id);
            seeCount.get().setCount(1);
            seeCount.get().setTrueOrFalse(true);
            seeCountRepos.save(seeCount.get());
        }

        return seeCountObj;
    }
}
