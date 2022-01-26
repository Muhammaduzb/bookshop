package uz.bookclub.bookclubapplication.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * created by Muhammad
 * on 14.08.2020
 */


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserAddress {
    @Id
    private Integer userId;
    private Integer regionId;
    private Integer districtId;
}
