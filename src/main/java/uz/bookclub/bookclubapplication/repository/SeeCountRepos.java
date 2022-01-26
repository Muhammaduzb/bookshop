package uz.bookclub.bookclubapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.bookclub.bookclubapplication.entity.Book;
import uz.bookclub.bookclubapplication.entity.SeeCount;

import java.util.List;
import java.util.Optional;

/**
 * created by Muhammad
 * on 21.10.2020
 */

@Repository
public interface SeeCountRepos extends JpaRepository<SeeCount, Integer> {
    @Query(nativeQuery = true, value = "select * from see_count o where user_id=:user_id")
    Optional<SeeCount> findByUser_id(Integer user_id);


}
