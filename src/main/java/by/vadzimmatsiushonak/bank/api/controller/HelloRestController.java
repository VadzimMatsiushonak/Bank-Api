package by.vadzimmatsiushonak.bank.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloRestController {

    @GetMapping("/api/hello")
    public String hello() {
        return "Hello World!";
    }

}
