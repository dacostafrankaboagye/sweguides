package org.example.openidconnect.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AppController {

    @GetMapping("/tokens")
    public String getTokens(
            @RegisteredOAuth2AuthorizedClient("google") OAuth2AuthorizedClient authorizedClient,
            @AuthenticationPrincipal OidcUser  oidcUser
            ){

        StringBuilder sb = new StringBuilder();

        if (authorizedClient != null) {
            sb.append("<h2>Access Token:</h2>");
            sb.append("<p>Value: ").append(authorizedClient.getAccessToken().getTokenValue()).append("</p>");
            sb.append("<p>Issued At: ").append(authorizedClient.getAccessToken().getIssuedAt()).append("</p>");
            sb.append("<p>Expires At: ").append(authorizedClient.getAccessToken().getExpiresAt()).append("</p>");
            sb.append("<p>Scopes: ").append(authorizedClient.getAccessToken().getScopes()).append("</p>");
            sb.append("<hr>");

            if (authorizedClient.getRefreshToken() != null) {
                sb.append("<h2>Refresh Token:</h2>");
                sb.append("<p>Value: ").append(authorizedClient.getRefreshToken().getTokenValue()).append("</p>");
                sb.append("<p>Issued At: ").append(authorizedClient.getRefreshToken().getIssuedAt()).append("</p>");
                sb.append("<hr>");
            }
        }

        if (oidcUser != null && oidcUser.getIdToken() != null) {
            sb.append("<h2>ID Token:</h2>");
            sb.append("<p>Value: ").append(oidcUser.getIdToken().getTokenValue()).append("</p>");
            sb.append("<p>Issued At: ").append(oidcUser.getIdToken().getIssuedAt()).append("</p>");
            sb.append("<p>Expires At: ").append(oidcUser.getIdToken().getExpiresAt()).append("</p>");
            sb.append("<p>Claims: ").append(oidcUser.getIdToken().getClaims()).append("</p>");
            sb.append("<hr>");
        } else {
            sb.append("<p>No ID Token found (ensure 'openid' scope is requested and user is an OIDC user).</p>");
        }

        return sb.toString();

    }

    /*
{
  "at_hash": "fkF_N3nVgPHcQdqUiZGFYg",
  "sub": "102257476467084329917",
  "email_verified": true,
  "iss": "https://accounts.google.com",
  "given_name": "frank",
  "nonce": "YKhRYEfTrP7gyjKxPPDX1dcHiCbdWS02TQAwJhDW7mM",
  "picture": "https://lh3.googleusercontent.com/a/ACg8ocJe9ZWt2M3I7mfbffmw_s5TtFiJTJ5t1X1txbhHGuOYhYw39-4=s96-c",
  "aud": [
    "283894779173-af9l6d7m949b3fh0nveb8o1hcbpsam5r.apps.googleusercontent.com"
  ],
  "azp": "283894779173-af9l6d7m949b3fh0nveb8o1hcbpsam5r.apps.googleusercontent.com",
  "name": "frank kwabena-aboagye",
  "exp": "2025-05-27T20:35:00Z",
  "family_name": "kwabena-aboagye",
  "iat": "2025-05-27T19:35:00Z",
  "email": "kwabenaaboagyefrank@gmail.com"
}

    */

    @GetMapping  // the default - as homepage
    public Map<String, Object> home(@AuthenticationPrincipal OAuth2User oauth2User) {
        // It's useful for inspecting the full set of data received from Google.
        return oauth2User.getAttributes();
    }

    @GetMapping("user-info")
    public String userInfo(@AuthenticationPrincipal OAuth2User oauth2User) {
        // getting the username and the email
        return "User Name: " + oauth2User.getName() +
                ", Email: " + oauth2User.getAttributes().get("email");
    }



    @GetMapping("/get-provider")
    public String provider(OAuth2AuthenticationToken token) {
        return "Logged in via: " + token.getAuthorizedClientRegistrationId();
    }


}
