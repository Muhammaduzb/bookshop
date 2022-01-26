package uz.bookclub.bookclubapplication.service;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import uz.bookclub.bookclubapplication.DAO.BookDAO;
import uz.bookclub.bookclubapplication.DAO.BookDaoDefault;
import uz.bookclub.bookclubapplication.entity.Book;
import uz.bookclub.bookclubapplication.entity.UserAddress;
import uz.bookclub.bookclubapplication.repository.*;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * created by Muhammad
 * on 21.08.2020
 */

@Service
public class GetBookService {
    @Autowired
    UserAddressRepository userAddressRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RegionRepo regionRepo;

    @Autowired
    DistrictRepo districtRepo;

//    public List<BookDAO> getMyBooks(Integer id){
//        List<Book> bookList = bookRepository.getMyBooks(id);
//            return getBooks(bookList);
//    }

    public BookDAO getMyBook(Integer id){
            Book book = bookRepository.getMyBook(id);
                return getMyBook(book);
        }

    public List<BookDAO> getAllBooks(){
        List<Book> bookList = bookRepository.findAll();
            return getBooks(bookList);
    }

    public List<BookDAO> getSearchedBooks(String word1){
        Transcriptor transcriptor = new Transcriptor();
        String word2 = transcriptor.getRu(word1);
            List<Book> bookList = bookRepository.findBooksWithPartOfName(word1,word2);
                return getBooks(bookList);
        }

    public List<BookDAO> getBooks(List<Book> bookList) {
        List<BookDAO> bookDAOList = new ArrayList<>();
        String language = null;
        for (int i = 0; i < bookList.size(); i++) {
            Integer id = bookList.get(i).getUserId();
            Integer bookId= bookList.get(i).getId();
            byte[] picture = bookList.get(i).getPhoto();
            String username = userRepository.findById(id).get().getUsername();
            String name = bookList.get(i).getName();
            String author = bookList.get(i).getAuthor();
            if (bookList.get(i).getLanguage() == 0) {
                language = "O'zbek";
            } else if (bookList.get(i).getLanguage() == 1) {
                language = "Rus";
            } else if (bookList.get(i).getLanguage() == 2) {
                language = "Kiril";
            } else if (bookList.get(i).getLanguage() == 3) {
                language = "English";
            } else if (bookList.get(i).getLanguage() == 4) {
                language = "Boshqa";
            }
            String comment = bookList.get(i).getComment();
            userAddressRepository.getMyRegionId(id);
            Integer regionId = userAddressRepository.getMyRegionId(id).getRegionId();
//            System.out.println("region id: " + regionId);
            String region = regionRepo.getMyRegionName(regionId).getName();
            Integer districtId = userAddressRepository.getMyRegionId(id).getDistrictId();
            String district = districtRepo.getMyDistrictName(districtId).getName();
            bookDAOList.add(i, new BookDAO(bookId,picture, username, name, author, language, comment, region, district));
        }
            return bookDAOList;
    }

    public BookDAO getMyBook(Book book) {
        BookDAO bookDAO;
        String language = null;

            Integer id = book.getUserId();
            Integer bookId= book.getId();
            byte[] picture = book.getPhoto();
            String username = userRepository.findById(id).get().getUsername();
            String name = book.getName();
            String author = book.getAuthor();
            if (book.getLanguage() == 0) {
                language = "O'zbek";
            } else if (book.getLanguage() == 1) {
                language = "Rus";
            } else if (book.getLanguage() == 2) {
                language = "Kiril";
            } else if (book.getLanguage() == 3) {
                language = "English";
            } else if (book.getLanguage() == 4) {
                language = "Boshqa";
            }
            String comment = book.getComment();
            userAddressRepository.getMyRegionId(id);
            Integer regionId = userAddressRepository.getMyRegionId(id).getRegionId();
//            System.out.println("region id: " + regionId);
            String region = regionRepo.getMyRegionName(regionId).getName();
            Integer districtId = userAddressRepository.getMyRegionId(id).getDistrictId();
            String district = districtRepo.getMyDistrictName(districtId).getName();
            bookDAO = new BookDAO(bookId,picture, username, name, author, language, comment, region, district);

            return bookDAO;
    }

    public Page<BookDaoDefault> bookDaoDefaultPage(Page<Book> pageBook){
        List<Book> list = pageBook.getContent();
        List<BookDaoDefault> bookDAOList = new ArrayList<>();
        String language = null;
        for (int i = 0; i < list.size(); i++) {
            Integer id =  list.get(i).getUserId();
            String username = userRepository.findById(id).get().getUsername();
            Integer regionId = userAddressRepository.getMyRegionId(id).getRegionId();
            String region = regionRepo.getMyRegionName(regionId).getName();
            Integer districtId = userAddressRepository.getMyRegionId(id).getDistrictId();
            String district = districtRepo.getMyDistrictName(districtId).getName();
            byte[] encodeBase64 = Base64.encodeBase64(list.get(i).getPhoto());
            String picture = null;
            try {
                picture = new String(encodeBase64, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
//            System.out.println("list size " + i + " " + list.get(i));
            if (list.get(i).getLanguage() == 0) {
                language = "O'zbek";
            } else if (list.get(i).getLanguage() == 1) {
                language = "Rus";
            } else if (list.get(i).getLanguage() == 2) {
                language = "Kiril";
            } else if (list.get(i).getLanguage() == 3) {
                language = "English";
            } else if (list.get(i).getLanguage() == 4) {
                language = "Boshqa";
            }

            bookDAOList.add(i,new BookDaoDefault(list.get(i).getId(),picture,username,
                    list.get(i).getName(),list.get(i).getAuthor(),language,list.get(i).getComment(),district,region));
        }
        final Page<BookDaoDefault> bookDAOPage = new PageImpl<>(bookDAOList);
        return bookDAOPage;
    }

}
