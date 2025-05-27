package org.example.securingapiwithoauth2.controller;


import lombok.RequiredArgsConstructor;
import org.example.securingapiwithoauth2.dto.MyTokenResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AppController {

    private final OAuth2AuthorizedClientService  authorizedClientService;

    @GetMapping("/") // 🔓
    public String home() {
        return "Hello, World!";
    }

    @GetMapping("/public") // 🔓
    public String publicEndpoint() {
        return "This is a public endpoint";
    }

    @GetMapping("/public/logout-success")
    public String logoutSuccess() {
        return "Logout successful";
    }

    @GetMapping("/secure/hello") // test this in postman
    public String privateEndpoint() {
        return "This is a private endpoint";
    }
    @GetMapping("/secure/user")
    public String securedHello(@AuthenticationPrincipal OidcUser user) {
        return "Hello, " + user.getFullName();
    }


    @GetMapping("/secure/token")
    public String getToken(OAuth2AuthenticationToken authToken){
        OAuth2AuthorizedClient client = authorizedClient(authToken);
        return "Access Token: " + client.getAccessToken().getTokenValue();
    }

    @GetMapping("/secure/token-response")
    public MyTokenResponse tokenResponse(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient client, @AuthenticationPrincipal OidcUser user){
        String accessToken = client.getAccessToken().getTokenValue();
        String idToken = user.getIdToken().getTokenValue();
        return new MyTokenResponse(accessToken, idToken);

    }

    @GetMapping("/secure/me")
    public Map<String, Object> me(@AuthenticationPrincipal Jwt  jwt) {
        return jwt.getClaims();
    }

    @GetMapping("/secure/userinfo")
    public Map<String, Object> userInfo(@AuthenticationPrincipal OidcUser oidcUser) {
        return oidcUser.getClaims(); // All user claims like email, name, etc.
    }

    private OAuth2AuthorizedClient authorizedClient(OAuth2AuthenticationToken authentication){
        return authorizedClientService.loadAuthorizedClient(
                authentication.getAuthorizedClientRegistrationId(),
                authentication.getName()
        );
    }
}
