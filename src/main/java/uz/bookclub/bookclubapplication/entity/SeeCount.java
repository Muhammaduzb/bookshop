package uz.bookclub.bookclubapplication.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * created by Muhammad
 * on 21.10.2020
 */

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SeeCount {
    // bu userni id si bn bir xil boladi
    @Id
    private Integer user_id;
    //kirgan vaqt bir boladi va wu bn owib borayveradi
    private Integer count;
    private Boolean trueOrFalse;
}
