package uz.bookclub.bookclubapplication.DAO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * created by Muhammad
 * on 21.08.2020
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDAO {
    private Integer bookid;
    private byte[] picture;
    private String username;
    private String name;
    private String author;
    private String language;
    private String comment;
    private String district;
    private String region;
}
