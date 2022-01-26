package uz.bookclub.bookclubapplication.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.bookclub.bookclubapplication.entity.Book;

import java.util.List;

/**
 * created by Muhammad
 * on 13.08.2020
 */
@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {

    @Query(nativeQuery = true, value = "select * from book o where user_id=:id")
    Page<Book> getMyBooks(@Param("id") Integer id,Pageable pageable);

    @Query(nativeQuery = true, value = "select * from book o where id=:id")
    Book getMyBook(@Param("id") Integer id);

    @Query("SELECT b FROM Book b WHERE b.name LIKE CONCAT('%',:word1,'%') or " +
            "b.author LIKE CONCAT('%',:word1,'%') or b.comment LIKE CONCAT('%',:word1,'%') or " +
            "b.name LIKE CONCAT('%',:word2,'%') or " +
            "b.author LIKE CONCAT('%',:word2,'%') or b.comment LIKE CONCAT('%',:word2,'%')")
    List<Book> findBooksWithPartOfName(@Param("word1") String word1,@Param("word2") String word2);

    Page<Book> findAll(Pageable pageable);

    @Query(nativeQuery = true, value = "select * from book order by timestamp desc")
    Page<Book> findAllByTimestamp(Pageable pageable);


}