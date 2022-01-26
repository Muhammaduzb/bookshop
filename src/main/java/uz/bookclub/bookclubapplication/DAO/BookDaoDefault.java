package uz.bookclub.bookclubapplication.DAO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * created by Muhammad
 * on 10.09.2020
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDaoDefault{
    private Integer bookid;
    private String picture;
    private String username;
    private String name;
    private String author;
    private String language;
    private String comment;
    private String district;
    private String region;
}