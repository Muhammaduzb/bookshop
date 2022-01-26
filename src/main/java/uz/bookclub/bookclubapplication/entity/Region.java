package uz.bookclub.bookclubapplication.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * created by Muhammad
 * on 10.08.2020
 */
//@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Region {
    @Id
    private Integer id;
    private String name;

}
