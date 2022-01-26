package uz.bookclub.bookclubapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.bookclub.bookclubapplication.entity.District;

import java.util.List;

/**
 * created by Muhammad
 * on 12.08.2020
 */

@Repository
public interface DistrictRepo extends JpaRepository<District, Integer> {


    List<District> getByRegionId(Integer id);

    @Query(nativeQuery = true, value = "select * from district o where id=:id")
    District getMyDistrictName(@Param("id") Integer id);

}
