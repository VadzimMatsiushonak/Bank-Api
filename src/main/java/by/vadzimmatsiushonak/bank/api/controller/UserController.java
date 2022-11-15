package by.vadzimmatsiushonak.bank.api.controller;

import by.vadzimmatsiushonak.bank.api.model.User;
import by.vadzimmatsiushonak.bank.api.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public User findById(@PathVariable Long id) {
        return userService.findById(id).orElse(null);
    }

    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

}
