package uz.bookclub.bookclubapplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.bookclub.bookclubapplication.entity.Book;
import uz.bookclub.bookclubapplication.entity.User;
import uz.bookclub.bookclubapplication.entity.UserAddress;
import uz.bookclub.bookclubapplication.repository.BookRepository;
import uz.bookclub.bookclubapplication.repository.UserAddressRepository;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;

/**
 * created by Muhammad
 * on 13.08.2020
 */

@Service
public class AddBookService {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserAddressRepository addressRepository;

    public void addBook(byte[] file,String name, String author, Integer language,String comment,Integer id){

        Date date= new Date();
        long time = date.getTime();

        Timestamp ts = new Timestamp(time);

        Book book = new Book();
        Integer districtId = addressRepository.getMyDistrictId(id).getDistrictId();
        try {
            book.setName(name);

            book.setComment(comment);
            System.out.println(file);
            if (file.length==0){
                URL url = new URL("https://i.mycdn.me/i?r=AyH4iRPQ2q0otWIFepML2LxR8ctda1innMwrUPV52KOF5g");;
//                BufferedImage image = null;

//                File fnew=new File("https://images.unsplash.com/photo-1529590003495-b2646e2718bf?ixlib=rb-1.2.1&auto=format&fit=crop&w=410&q=80");
                BufferedImage originalImage=ImageIO.read(url);
                ByteArrayOutputStream baos=new ByteArrayOutputStream();
                ImageIO.write(originalImage, "png", baos );
                byte[] imageInByte=baos.toByteArray();
                book.setPhoto(imageInByte);
//                book.setPhoto(file);
            }
            else
                {
            book.setPhoto(file);}
            book.setTimestamp(ts);
            book.setAuthor(author);
            book.setLanguage(language);
            book.setUserId(id);
            book.setDistrictId(districtId);
//            System.out.println("book:" + book);
            bookRepository.save(book);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addUserAddress(Integer region,Integer district){
        User user =(User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        Integer id = user.getId();
//        System.out.println("Id =" + id);
        try {
            if (addressRepository.findById(id).isPresent()){
                addressRepository.deleteById(id);
                addressRepository.save(new UserAddress(id,region,district));
            }else {
                addressRepository.save(new UserAddress(id, region, district));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

