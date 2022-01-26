package uz.bookclub.bookclubapplication.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterReq {
    @NotEmpty(message = "Iltimos telefon raqamingizni 12 ta raqamli formatda kiriting")
    @Pattern(regexp = "[[0-9 ()+-]+$]{17}", message = "Iltimos raqamingizni to'liq kiriting!")
    private String username;

//    @NotBlank(message = "Enter email address")
//    @Email(message = "Enter valid email address")
//    private String email;

//    @Size(min = 3, message = "Full name must be minimum 3 characters.")
//    private String fullName;

    @NotEmpty(message = "Iltimos parolni kiriting!")
    @Size(min = 6 ,max = 16 , message = "Eng kamida 6 ta belgidan foydalaning!")
    @Pattern(regexp = "[A-Za-z0-9]{6,16}", message = "Faqat lotin alifbosida katta,kichik harflar va raqamlardan foydalaning!")
    private String password;

//    private String prePassword;
//
//    @AssertTrue(message="Password is not confirmed.")
//    private boolean isValid() {
//        return this.password.equals(this.prePassword);
//    }

}
