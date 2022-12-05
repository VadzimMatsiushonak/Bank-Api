package by.vadzimmatsiushonak.bank.api.controller;

import static org.springframework.http.HttpStatus.OK;

import by.vadzimmatsiushonak.bank.api.model.entity.User;
import by.vadzimmatsiushonak.bank.api.service.UserService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        return ResponseEntity.status(OK)
            .body(userService.findById(id).orElse(null));
    }

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.status(OK)
            .body(userService.findAll());
    }

}
