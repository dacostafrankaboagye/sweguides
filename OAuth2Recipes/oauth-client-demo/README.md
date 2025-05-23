- reference:
  - [https://docs.spring.io/spring-security/reference/servlet/oauth2/login/index.html](https://docs.spring.io/spring-security/reference/servlet/oauth2/login/index.html)

## Using Google
- set the logging
```yml
logging:
  level:
    org.springframework.security: TRACE
```
- check the dependencies
  - by add the oauth2 client we ge this in the console when we run

```text
Will secure any request with filters: 
- DisableEncodeUrlFilter, WebAsyncManagerIntegrationFilter, SecurityContextHolderFilter, 
- HeaderWriterFilter, CsrfFilter, LogoutFilter, UsernamePasswordAuthenticationFilter, 
- DefaultResourcesFilter, DefaultLoginPageGeneratingFilter, DefaultLogoutPageGeneratingFilter, 
- BasicAuthenticationFilter, RequestCacheAwareFilter, SecurityContextHolderAwareRequestFilter, 
- AnonymousAuthenticationFilter, ExceptionTranslationFilter, AuthorizationFilter


```
- at this `localhost:8081` 
  - we are directed to `localhost:8081/login`
    - we see a login form
      - e.g. `DefaultLoginPageGeneratingFilter` is responsible

- now we configure the application to use the OAuth Client for the authentication flow

```yml
spring:
  application:
    name: oauth-client-demo
  security:
    oauth2:
      client:
        registration:
          google:
            client-id: ${google-client-id}
            client-secret: ${google-client-secret}
logging:
  level:
    org.springframework.security: TRACE
server:
  port: 8081

```
- when we run the application this time,
  - we see in the console
    - there are more filters added
      - there are the OAuth2 stuffs

```text
Will secure any request with filters: 
- DisableEncodeUrlFilter, WebAsyncManagerIntegrationFilter, SecurityContextHolderFilter, 
- HeaderWriterFilter, CsrfFilter, LogoutFilter, OAuth2AuthorizationRequestRedirectFilter, 
- OAuth2AuthorizationRequestRedirectFilter, OAuth2LoginAuthenticationFilter, DefaultResourcesFilter, 
- DefaultLoginPageGeneratingFilter, DefaultLogoutPageGeneratingFilter, RequestCacheAwareFilter, 
- SecurityContextHolderAwareRequestFilter, AnonymousAuthenticationFilter, OAuth2AuthorizationCodeGrantFilter, 
- ExceptionTranslationFilter, AuthorizationFilter
```

- so now when we go to `localhost:8081`
  - it automatically redirects us to google
  - note at this point, we dont have any security config

- let add a controller 
```java
@RestController public class HelloController {
    @GetMapping public String hello(@AuthenticationPrincipal OidcUser oidcUser) {
        return """
                Hello %s\s
                Your email is %s\s
                Thank you""".formatted(oidcUser.getFamilyName(), oidcUser.getEmail());
    }
}
```
- we can use open id connect user `OidcUser` because
  - we know the google supports open id connect
  - spring by default send scopes that include open id

- authenticate again, and check the cosole
  - you would see a lot of information
- you can also preserve the log in the network tab of the browser
- take a look at the this  :: consent
![./images/consent.png](./images/consent.png)

## Using Github
- create a new oauth app in the developer settings of github
```yml
spring:
  application:
    name: oauth-client-demo
  security:
    oauth2:
      client:
        registration:
          github:
            client-id: ${github-client-id}
            client-secret: ${github-client-secret}
logging:
  level:
    org.springframework.security: TRACE
server:
  port: 8081
```

- the controller
```java
@RestController public class HelloController {
  @GetMapping public String hello(@AuthenticationPrincipal OAuth2User oAuth2User) {
    return """
            Hello %s\s
            Thank you""".formatted(oAuth2User.getAttributes());
  }
}
```

- note that we are using `OAuth2User`
  - you can highlight it and do 'ctrl+h'
    - to see the subtypes / hierarchy
      - github uses the traditional auth flow
- now when you start the application, 
  - you will be redirected to github

- take a look at the redirection
```text

https://github.com/login/oauth/authorize
?response_type=code
&client_id=Ov23lie9kPwEigjmdIhM
&scope=read:user
&state=P6yI3-ImisbMgU_-_waaXAybe3BAiY8HUjiniJRNKQ0%3D
&redirect_uri=http://localhost:8081/login/oauth2/code/github
```

- notice the `scope`: it is only `read:user`
  - no open id like for the google
    - so no id token
  - it's not that we are not sending the scope
    - github doesn't support it
- see the output
```
Hello {
login=xxx, 
id=xxxxx, 
node_id=xxxxxxx, 
avatar_url=xxxxxxxxxx, 
gravatar_id=, url=xxxxxxx, 
html_url=https://github.com/frank-jnr-aboagye, 
followers_url=xxxxxxx/followers, 
following_url=xxxxxxx/following{/other_user}, 
gists_url=xxxxxxx/gists{/gist_id}, 
starred_url=xxxxxxx/starred{/owner}{/repo}, 
subscriptions_url=xxxxxxx/subscriptions, 
organizations_url=xxxxxxx/orgs, 
repos_url=xxxxxxx/repos, 
events_url=xxxxxxx/events{/privacy}, 
received_events_url=xxxxxxx/received_events, 
type=User, user_view_type=private, site_admin=false, 
name=xxxxxxx, 
company=null, 
blog=, location=null, email=null, hireable=null, bio=null, twitter_username=null, 
notification_email=null, public_repos=0, public_gists=0, followers=0, following=0, 
created_at=2025-02-11T11:50:50Z, updated_at=2025-05-07T09:28:25Z, private_gists=0, 
total_private_repos=1, owned_private_repos=1, disk_usage=58, collaborators=0, 
two_factor_authentication=false, 
plan={name=free, space=xxxx, collaborators=0, private_repos=10000}
} Thank you
```
## For Both
- we can enable for both
```yml
spring:
  application:
    name: oauth-client-demo
  security:
    oauth2:
      client:
        registration:
          google:
            client-id: ${google-client-id}
            client-secret: ${google-client-secret}
          github:
            client-id: ${github-client-id}
            client-secret: ${github-client-secret}
logging:
  level:
    org.springframework.security: TRACE
server:
  port: 8081
```

- spring supports both

![./images/both.png](./images/both.png)