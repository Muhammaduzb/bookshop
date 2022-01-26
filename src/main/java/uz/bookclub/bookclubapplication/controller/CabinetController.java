package uz.bookclub.bookclubapplication.controller;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import uz.bookclub.bookclubapplication.DAO.BookDAO;
import uz.bookclub.bookclubapplication.DAO.BookDaoDefault;
import uz.bookclub.bookclubapplication.entity.Book;
import uz.bookclub.bookclubapplication.entity.SeeCount;
import uz.bookclub.bookclubapplication.entity.User;
import uz.bookclub.bookclubapplication.repository.*;
import uz.bookclub.bookclubapplication.security.SignedUser;
import uz.bookclub.bookclubapplication.service.AddBookService;
import uz.bookclub.bookclubapplication.service.CheckSeeCountService;
import uz.bookclub.bookclubapplication.service.DeleteBookService;
import uz.bookclub.bookclubapplication.service.GetBookService;

import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Controller
public class CabinetController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    GetBookService bookService;

    @Autowired
    DeleteBookService deleteBookService;

    @Autowired
    AddBookService addBookService;

    @Autowired
    UserAddressRepository userAddressRepository;

    @GetMapping("/cabinet/about")
    public String getDeveloper(Model model, @SignedUser User user) {
//        User user = (User) SecurityContextHolder
//                .getContext()
//                .getAuthentication()
//                .getPrincipal();
        model.addAttribute("user", user);
        return "cabinet/about";
    }

    @GetMapping("/cabinet/users/list")
    @ResponseBody
    public List<User> getUsers() {
       return userRepository.findAll();
    }

    @GetMapping("/cabinet/userlist")
    public String getUserList(Model model, @SignedUser User user) {
        model.addAttribute("user", user);
        return "/cabinet/userlist";
    }

//     @GetMapping("/actionBook/editBook/{id}")
//        public String editBook(Model model,@PathVariable("id")Integer id) {
//         BookDAO bookDAO = bookService.getMyBook(id);
//         model.addAttribute("forEditBook", bookDAO);
//            return "editBook";
//        }

    @Autowired
    BookRepository bookRepository;

    @Autowired
    RegionRepo regionRepo;
    @Autowired
    DistrictRepo districtRepo;

    @Autowired
    GetBookService getBookService;

    @Autowired
    CheckSeeCountService checkSeeCountService;

    @GetMapping("/")
    public String listAllBookIndex(Model model,@RequestParam(value = "page",
      required = false,defaultValue = "0") Integer page){
        Page<Book> pageBook = bookRepository.findAll(
                  PageRequest.of(page,12));
        final Page<BookDaoDefault> bookDAOPage = getBookService.bookDaoDefaultPage(pageBook);

        Page<Book> pageBookLastAdded = bookRepository.findAllByTimestamp(
                          PageRequest.of(page,12));
                final Page<BookDaoDefault> bookDAOPageLastAdded = getBookService.bookDaoDefaultPage(pageBookLastAdded);
          model.addAttribute("bookPage",bookDAOPage);
          model.addAttribute("bookPageLastAdded",bookDAOPageLastAdded);
          model.addAttribute("numbers", IntStream.range(0,pageBook.getTotalPages()).toArray());
        return "cabinet";
      };

    @GetMapping("/cabinet")
        public String getCabinetPage(Model model,@RequestParam(value = "page",
          required = false,defaultValue = "0") Integer page){
            User user = (User) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();

            Boolean seeCount = checkSeeCountService.getSeeCount(user.getId());
            checkSeeCountService.setSeeCount(user.getId());

            Page<Book> pageBook = bookRepository.findAll(
                      PageRequest.of(page,12));
            final Page<BookDaoDefault> bookDAOPage = getBookService.bookDaoDefaultPage(pageBook);

            Page<Book> pageBookLastAdded = bookRepository.findAllByTimestamp(
                              PageRequest.of(page,12));
                    final Page<BookDaoDefault> bookDAOPageLastAdded = getBookService.bookDaoDefaultPage(pageBookLastAdded);
              model.addAttribute("seeCount",seeCount);
              model.addAttribute("bookPage",bookDAOPage);
              model.addAttribute("bookPageLastAdded",bookDAOPageLastAdded);
              model.addAttribute("numbers", IntStream.range(0,pageBook.getTotalPages()).toArray());
            return "cabinet";
          };

    @GetMapping("/actionBook/allBooksPage")
    public String listAllBookPage(Model model,@RequestParam(value = "page",
            required = false,defaultValue = "0") Integer page){
        Page<Book> pageBook = bookRepository.findAll(
                PageRequest.of(page,18));
        final Page<BookDaoDefault> bookDAOPage = getBookService.bookDaoDefaultPage(pageBook);

        model.addAttribute("bookPage",bookDAOPage);
//          model.addAttribute("userAddress",userAddresses);
        model.addAttribute("numbers", IntStream.range(0,pageBook.getTotalPages()).toArray());
        return "allBooksPage";
    };

    @GetMapping("/actionBook/allBooksLastAddedPage")
    public String listallBooksLastAddedPage(Model model,@RequestParam(value = "page",
            required = false,defaultValue = "0") Integer page){
        Page<Book> pageBook = bookRepository.findAllByTimestamp(
                PageRequest.of(page,18));
        final Page<BookDaoDefault> bookDAOPage = getBookService.bookDaoDefaultPage(pageBook);

        model.addAttribute("bookPage",bookDAOPage);
//          model.addAttribute("userAddress",userAddresses);
        model.addAttribute("numbers", IntStream.range(0,pageBook.getTotalPages()).toArray());
        return "allBooksLastAddedPage";
    }

     @GetMapping("/actionBook/getAllMyBooks")
        public String getAllMyBooks(Model model,@RequestParam(value = "page",
                required = false,defaultValue = "0") Integer page){

         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

         if (authentication instanceof AnonymousAuthenticationToken)
         {
             return "login";
         }else {

//         return "dashboard";
             User user = (User) SecurityContextHolder
                     .getContext()
                     .getAuthentication()
                     .getPrincipal();

             Page<Book> pageBook = bookRepository.getMyBooks(user.getId(),
                     PageRequest.of(page, 18));
             final Page<BookDaoDefault> bookDAOPage = getBookService.bookDaoDefaultPage(pageBook);

             model.addAttribute("bookPage", bookDAOPage);
             //          model.addAttribute("userAddress",userAddresses);
             model.addAttribute("numbers", IntStream.range(0, pageBook.getTotalPages()).toArray());
             return "allmybooks";
         }
        }

    @GetMapping("/actionBook/deleteMyBook/{id}")
    public String deleteMyBook(@PathVariable("id")Integer id,Model model,@RequestParam(value = "page",
            required = false,defaultValue = "0")Integer page){
        User user = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        Book book = new Book();

        if (bookRepository.findById(id).isPresent()){
            book = bookRepository.findById(id).get();
                Integer userId = book.getUserId();
                    if (user.getId().equals(userId)){
                        deleteBookService.deleteBook(id);
            }
        }else
        {
            Page<Book> pageBook = bookRepository.getMyBooks(user.getId(),
                    PageRequest.of(page,18));
            final Page<BookDaoDefault> bookDAOPage = getBookService.bookDaoDefaultPage(pageBook);

            model.addAttribute("bookPage",bookDAOPage);
            //          model.addAttribute("userAddress",userAddresses);
            model.addAttribute("numbers", IntStream.range(0,pageBook.getTotalPages()).toArray());

            return "redirect:/actionBook/getAllMyBooks";
        }

        Page<Book> pageBook = bookRepository.getMyBooks(user.getId(),
                PageRequest.of(page,18));
        final Page<BookDaoDefault> bookDAOPage = getBookService.bookDaoDefaultPage(pageBook);

        model.addAttribute("bookPage",bookDAOPage);
        //          model.addAttribute("userAddress",userAddresses);
        model.addAttribute("numbers", IntStream.range(0,pageBook.getTotalPages()).toArray());

        return "redirect:/actionBook/getAllMyBooks";
    }

    @PostMapping("/actionBook/editMyBook/{id}")
    public String editMyBook(@PathVariable("id")Integer id, @RequestParam("picture") MultipartFile multipartFile, Model model,
                             @RequestParam(value = "page",
            required = false,defaultValue = "0")Integer page, @Valid BookDaoDefault bookDaoDefault, BindingResult bindingResult){
        User user = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        Book book = new Book();
        Date date= new Date();
        long time = date.getTime();
//        Integer language = null;
        if (bookRepository.findById(id).isPresent()){
            book = bookRepository.findById(id).get();
            Integer userId = book.getUserId();
            if (user.getId().equals(userId)){
//                if (bookDaoDefault.getLanguage().equals("O'zbek")) {
//                    language = 0;
//                } else if (bookDaoDefault.getLanguage().equals("Rus")) {
//                    language = 1;
//                } else if (bookDaoDefault.getLanguage().equals("Kiril")) {
//                    language = 2;
//                } else if (bookDaoDefault.getLanguage().equals("English")) {
//                    language = 3;
//                } else if (bookDaoDefault.getLanguage().equals("Boshqa")) {
//                    language = 4;
//                }
                try {

                    addBookService.addBook(multipartFile.getBytes(),bookDaoDefault.getName(),bookDaoDefault.getAuthor(),
                            Integer.valueOf(bookDaoDefault.getLanguage()),bookDaoDefault.getComment(),userId);
                    deleteBookService.deleteBook(id);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else
        {
            Page<Book> pageBook = bookRepository.getMyBooks(user.getId(),
                    PageRequest.of(page,18));
            final Page<BookDaoDefault> bookDAOPage = getBookService.bookDaoDefaultPage(pageBook);

            model.addAttribute("bookPage",bookDAOPage);
            //          model.addAttribute("userAddress",userAddresses);
            model.addAttribute("numbers", IntStream.range(0,pageBook.getTotalPages()).toArray());

            return "redirect:/actionBook/getAllMyBooks";
        }

        Page<Book> pageBook = bookRepository.getMyBooks(user.getId(),
                PageRequest.of(page,18));
        final Page<BookDaoDefault> bookDAOPage = getBookService.bookDaoDefaultPage(pageBook);

        model.addAttribute("bookPage",bookDAOPage);
        //          model.addAttribute("userAddress",userAddresses);
        model.addAttribute("numbers", IntStream.range(0,pageBook.getTotalPages()).toArray());

        return "redirect:/actionBook/getAllMyBooks";
    }


    @PostMapping("/actionBook/addBook")
    public String addBook(@RequestParam("picture") MultipartFile multipartFile,@RequestParam("name") String name,
                          @RequestParam("author") String author,@RequestParam("language") String language,
                          @RequestParam("comment") String comment,Model model,
                          @RequestParam(value = "page",required = false,defaultValue = "0")Integer page,
                          @Valid BookDaoDefault bookDaoDefault, BindingResult bindingResult){
        User user = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        Book book = new Book();
        Date date= new Date();
        long time = date.getTime();
                try {
                    addBookService.addBook(multipartFile.getBytes(),name,author,
                            Integer.valueOf(language),comment,user.getId());
                } catch (IOException e) {
                    e.printStackTrace();
                }


        Page<Book> pageBook = bookRepository.getMyBooks(user.getId(),
                PageRequest.of(page,18));
        final Page<BookDaoDefault> bookDAOPage = getBookService.bookDaoDefaultPage(pageBook);

        model.addAttribute("bookPage",bookDAOPage);
        model.addAttribute("numbers", IntStream.range(0,pageBook.getTotalPages()).toArray());

        return "redirect:/actionBook/getAllMyBooks";
    }
//    @PostMapping("/actionBook/addBook")
//    public String addBook(final @RequestParam("file") MultipartFile file,final @RequestParam("name") String name,
//                        final @RequestParam("author") String author,final @RequestParam("language") Integer language,
//                        final @RequestParam("comment") String comment) throws IOException {
//        User user =(User) SecurityContextHolder
//                .getContext()
//                .getAuthentication()
//                .getPrincipal();
//        Integer id = user.getId();
////        System.out.println(file);
////        System.out.println(file.getBytes());
////        System.out.println(file.getBytes()+ "--------------  " + name + author + language + comment +id);
//        addBookService.addBook(file.getBytes(),name.toLowerCase(),author.toLowerCase(),language,comment,id);
//        return "redirect:/actionBook/getAllMyBooks";
//    }

}
