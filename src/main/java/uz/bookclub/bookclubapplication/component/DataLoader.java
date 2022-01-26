package uz.bookclub.bookclubapplication.component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.bookclub.bookclubapplication.entity.User;
import uz.bookclub.bookclubapplication.repository.RoleRepository;
import uz.bookclub.bookclubapplication.repository.UserRepository;

@Component
public class DataLoader implements CommandLineRunner {
    @Value("${spring.datasource.initialization-mode}")
    String initialMode;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    public DataLoader(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
//        this.userRepository = userRepository;
//        this.roleRepository = roleRepository;
//        this.passwordEncoder = passwordEncoder;
//    }

    @Override
    public void run(String... args) throws Exception {
//        if (initialMode.equals("always")) {
//            userRepository.save(new User("998009990099", passwordEncoder.encode("19301930"),true,
//                    roleRepository.findAll()));
//
//        }
    }
}
