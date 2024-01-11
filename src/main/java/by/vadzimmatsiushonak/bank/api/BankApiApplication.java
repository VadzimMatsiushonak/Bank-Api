package by.vadzimmatsiushonak.bank.api;

import by.vadzimmatsiushonak.bank.api.model.entity.User;
import by.vadzimmatsiushonak.bank.api.model.entity.auth.Role;
import by.vadzimmatsiushonak.bank.api.model.entity.base.ModelStatus;
import by.vadzimmatsiushonak.bank.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@SpringBootApplication
public class BankApiApplication {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(BankApiApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void startup() {
        System.err.println("Application started");
//        User root = new User();
//        root.setLogin("root");
//        root.setPassword(passwordEncoder.encode("pass"));
//        root.setRole(Role.ADMIN);
//        root.setPermissions(Role.ADMIN.permissions);
//        root.setStatus(ModelStatus.ACTIVE);
//        userRepository.save(root);
//        System.err.println("Root user successfully created");
    }

}
