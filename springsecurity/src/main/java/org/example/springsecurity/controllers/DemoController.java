package org.example.springsecurity.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class DemoController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    // see everything of the user making the request
    @GetMapping("/user")
    public String user(Principal principal) {
        System.out.println(principal);
        return "Hello User";
    }
}
