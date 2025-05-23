package org.example.oauthclientdemo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    // for google
    /*
    @GetMapping
    public String hello(@AuthenticationPrincipal OidcUser oidcUser) {
        return """
                Hello %s\s
                Your email is %s\s
                Thank you""".formatted(oidcUser.getFamilyName(), oidcUser.getEmail());
    }
    */

    // for github* and google
    @GetMapping
    public String hello(@AuthenticationPrincipal OAuth2User oAuth2User) {
        return """
                Hello %s\s
                Thank you""".formatted(oAuth2User.getName());  // the id will be here
    }
}
