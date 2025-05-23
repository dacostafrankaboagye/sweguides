package org.example.oauth2sociallogin.controller;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller  // this is just controller
public class ViewController {

    @GetMapping("/profile")
    public String getProfile(OAuth2AuthenticationToken  token, Model model) {
        Map<String, Object> attributes = token.getPrincipal().getAttributes();
        model.addAttribute("name", attributes.get("name"));
        model.addAttribute("email", attributes.get("email"));
        model.addAttribute("photo", attributes.get("picture")); // For Google
        return "user-profile";
    }

    @GetMapping("/login")
    public String login() {
        return "custom-login";
    }

}
