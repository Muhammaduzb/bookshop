package uz.bookclub.bookclubapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.bookclub.bookclubapplication.entity.Role;
@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    Role getByName(String name);
}
