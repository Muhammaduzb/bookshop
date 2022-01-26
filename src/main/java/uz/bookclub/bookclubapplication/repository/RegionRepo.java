package uz.bookclub.bookclubapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.bookclub.bookclubapplication.entity.Region;

/**
 * created by Muhammad
 * on 10.08.2020
 */

@Repository
public interface RegionRepo extends JpaRepository<Region, Integer> {
    Region findByName(String name);

    @Query(nativeQuery = true, value = "select * from region o where id=:id")
    Region getMyRegionName(@Param("id") Integer id);

}
