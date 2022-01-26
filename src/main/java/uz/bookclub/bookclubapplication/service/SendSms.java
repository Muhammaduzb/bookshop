package uz.bookclub.bookclubapplication.service;

/**
 * created by Muhammad
 * on 26.08.2020
 */
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SendSms {
    public static final String ACCOUNT_SID = "ACcbfd8af8fe2ab50f226ec9a91bb0d0d6";
    public static final String AUTH_TOKEN = "36b5df23638530a61ed85dc3015d1018";

    public static void sendSms(String number,String activeCode){
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(new PhoneNumber("+" + number),
                new PhoneNumber("+18886758114"),
                "Sizning bookclub.uz sayti uchun tasdiqlash parolingiz: " + activeCode).create();

//        System.out.println(message.getSid());
    }
}
