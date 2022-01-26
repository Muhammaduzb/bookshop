package uz.bookclub.bookclubapplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.bookclub.bookclubapplication.entity.District;
import uz.bookclub.bookclubapplication.entity.Region;
import uz.bookclub.bookclubapplication.entity.UserAddress;
import uz.bookclub.bookclubapplication.repository.DistrictRepo;
import uz.bookclub.bookclubapplication.repository.RegionRepo;
import uz.bookclub.bookclubapplication.repository.UserAddressRepository;

import java.util.List;

/**
 * created by Muhammad
 * on 13.08.2020
 */

@Service
public class RegionDistrictService {

    @Autowired
    RegionRepo regionRepo;

    @Autowired
    DistrictRepo districtRepo;

    @Autowired
    UserAddressRepository userAddressRepository;

    public List<Region> getAllRegions(){
        return regionRepo.findAll();
    }

    public List<District> getAllDistricts(Integer id){
        return districtRepo.getByRegionId(id);
    }

    public UserAddress userAddress(Integer id){

        return userAddressRepository.findById(id).get();
    }
}
