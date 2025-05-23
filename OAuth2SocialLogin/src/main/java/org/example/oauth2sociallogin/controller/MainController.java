package org.example.oauth2sociallogin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class MainController {

    @RequestMapping("/")
    public String home() {
        return "Welcome to this tutorial";
    }

    @RequestMapping("/user")
    public Principal user (Principal user) {  // we'll return the details of a logged in user here
        return user;
    }
}
