# Spring Security Fundamentals 
- by: Laur Spilca


## Lesson 1 - First steps

- Spring Security is about Application Level security
  - Application level security is about
    - Authentication
    - Authorisation

- Authentication
  - who's the user?
- Authorisation
  - is the user allowed to do something?

- Authority
  - something you have
  - I have the authority to perform certain actions
- Role
  - something you are
  - sort of a badge
- Authority and Roles can be somewhat similar..
  - they have the same contract behind the scenes - GrantedAuthority

- Ways – Authentication
    - HTTP Basic Authentication – Simple apps, internal tools
    - X.509 Certificate Authentication – High-security enterprise systems
    - JWT (with OAuth2 Resource Server or Custom) – REST APIs, SPA backends
        - Using Spring Security OAuth2 Resource Server
        - Custom JWT Authentication Filter

- Ways – Authorization
    - In Web Apps
        - Filter-Based Authorization – Role-based page access
        - Annotation-Based Authorization – e.g., @PreAuthorize, @Secured, @RolesAllowed
  - In Non-Web Apps
      - Aspect-Based Authorization (AOP) – Used in service layers, background jobs (can also be used in web apps)
- Note
```text
- Just by adding Spring Security to the pom, we get

=================================================================================
Using generated security password: a51f9a5a-16c6-4f51-b92b-63f7c7694291   //<< this is a sample output

This generated password is for development use only. Your security configuration must be updated before running your application in production.
==================================================================================
```

- from a simple controller

```java
    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }
    
 /*
 - accessing this on postman, /hello
 - without any credentials , we get 401 Unauthorized
 - because of spring security

            
 */
```

- By Default,
  - spring security authentication is : http basic
  - so we use the Authorisation Header, to send the credentials
  - it is base64 encoded

- So In postman
  - go to authorisation tab
  - select `Basic Auth`
    - username: `user`  <- always this
    - password: `<the randomly generated password in the console>` a51f9a5a-16c6-4f51-b92b-63f7c7694291  <- using this as an example
- now sending the request should work
- now as the request is being sent
  - check the Request Headers
  - you will see something like this
```text
Authorization: Basic dXNlcjphNTFmOWE1YS0xNmM2LTRmNTEtYjkyYi02M2Y3Yzc2OTQyOTE=

```

- so the random text after in this `Basic dXNlcjphNTFmOWE1YS0xNmM2LTRmNTEtYjkyYi02M2Y3Yzc2OTQyOTE=`
    - is base64 encoding
- so we can decode it with any base64 decoder
  - e.g. `https://www.base64decode.org/`
- you will find out that 

```text
this: dXNlcjphNTFmOWE1YS0xNmM2LTRmNTEtYjkyYi02M2Y3Yzc2OTQyOTE=

will decode to

user:a51f9a5a-16c6-4f51-b92b-63f7c7694291

=============

so it is the username and the password we provided earlier

so like

Basic<space><encoded version of the <username><colon><password> >

```

- note that Base64 is an encoding
- there is a difference btn,
  - encoding: just a transformation; just know the rule; 
  - encryption: you always need a secret to go back from output to input
  - hash functions: you cannot go from output to input; you can tell if an input corresponds to an output

| Feature                  | Encoding                                             | Encryption                               | Hash Functions                          |
| ------------------------ | ---------------------------------------------------- | ---------------------------------------- | --------------------------------------- |
| **Purpose**              | Data transformation for readability or compatibility | Secure data from unauthorized access     | Ensure data integrity / verify data     |
| **Reversibility**        | ✅ Reversible                                         | ✅ Reversible (with key)                  | ❌ Not reversible (one-way)              |
| **Key Needed?**          | ❌ No                                                 | ✅ Yes (secret key or public/private key) | ❌ No                                    |
| **Example Use**          | URL encoding, Base64 encoding                        | Secure communications (HTTPS, AES, RSA)  | Password storage, file integrity checks |
| **Examples**             | Base64, URL Encoding                                 | AES, RSA, DES                            | SHA-256, SHA-1, MD5                     |
| **Output**               | Human-readable (usually)                             | Ciphertext (not readable)                | Fixed-length hash (e.g., 256-bit)       |
| **Security**             | ❌ Not secure                                         | ✅ Secure (if implemented correctly)      | ✅ Secure (for strong algorithms)        |
| **Same input → output?** | ✅ Always same                                        | ✅ Same with same key                     | ✅ Same input → same hash                |


### how do you create your own set of credentials?
- creating your own `UserDetailsService`
  - UserDetailsServices - manages the user details
    - it tells spring where to get the credentials from
```text
- you add that bean to the context
- it is interface (contract manager) for user details

```

```java
- the UserDetailsService has a method
    - to get the unique identifier of the user :: here represented by username
        - it is presented by a different contract called UserDetails

// this is the raw interface from spring itself
public interface UserDetailsService {
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}

// user details as provided by spring
public interface UserDetails extends Serializable {
    Collection<? extends GrantedAuthority> getAuthorities();
    String getPassword();
    String getUsername();
    default boolean isAccountNonExpired() {return true;}
    default boolean isAccountNonLocked() {return true;}
    default boolean isCredentialsNonExpired() {return true;}
    default boolean isEnabled() {return true;}
}


```

- since the `UserDetailsService` is contract
  - you can choose an implementation of it

![./images/udsImplementation.png](./images/udsImplementation.png)


- to provide our own credentials, lets use the simplest one - `InMemoryUserDetailsManager`

- this is how the `InMemoryUserDetailsManager` looks like

```java

public class InMemoryUserDetailsManager implements UserDetailsManager, UserDetailsPasswordService {
    // ...
    public InMemoryUserDetailsManager() {}
    public InMemoryUserDetailsManager(Collection<UserDetails> users) {         //..     }
    public InMemoryUserDetailsManager(UserDetails... users) {         //..     }
    public InMemoryUserDetailsManager(Properties users) {         //..     }
    private User createUserDetails(String name, UserAttribute attr) {         //..     }
    public void createUser(UserDetails user) {          // ..           }
    public void deleteUser(String username) {         // ..     }
    public void updateUser(UserDetails user) {         // ..     }
    public boolean userExists(String username) {  // ..    }
    public void changePassword(String oldPassword, String newPassword) {         // ..     }
    public UserDetails updatePassword(UserDetails user, String newPassword) {         // ..     }
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {         // ..     }
    public void setSecurityContextHolderStrategy(SecurityContextHolderStrategy securityContextHolderStrategy) {         // ...     }
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {         // ..    }
}

```


- so lets set it up
```text
- it mandatory that, the have an authority (role, action)
- Spring Security requires roles to be prefixed with "ROLE_" when using SimpleGrantedAuthority.

```
- the `GrantedAuthority` is a contract so here are some implementation

![./images/grantedAuthImpl.png](./images/grantedAuthImpl.png)

```text
For the Authorities

✅ You want to allow duplicates and preserve order  ==== 	List / ArrayList (commonly used)
✅ You want to avoid duplicates	 ==== Set / HashSet
✅ You need fast insert/remove at both ends	 ==== LinkedList



```

- for password encoder

| Implementation          | Description                                 |
| ----------------------- | ------------------------------------------- |
| `NoOpPasswordEncoder`   | No encoding (`{noop}` is shortcut for this) |
| `BCryptPasswordEncoder` | Strong hashing algorithm, **recommended**   |
| `Pbkdf2PasswordEncoder` | Key derivation based, secure                |
| `Argon2PasswordEncoder` | New and very secure, slower                 |
| `SCryptPasswordEncoder` | Secure, used in high-security environments  |

- take a look at the Config
[src/main/java/org/example/springsecurity/lesson1/config/WebSecurityConfig.java](src/main/java/org/example/springsecurity/lesson1/config/WebSecurityConfig.java)

- Test with postman
```text
so now we can test with postman
basic auth
    - with the username and password 
```

![./images/highlevel.png](./images/highlevel.png)


## Lesson 2 - Managing users
- 
- Spring security in a web application starts from the filters
```text

HTTP Request
    ↓
Filter Chain (SecurityFilterChain)
    ↓
[Authentication Filters]
    ├─ UsernamePasswordAuthenticationFilter       ← for form login / HTTP Basic
    ├─ BearerTokenAuthenticationFilter            ← for JWT tokens (OAuth2 Resource Server)
    ├─ OAuth2LoginAuthenticationFilter            ← for OAuth2 logins (e.g., Google)
    ├─ Saml2WebSsoAuthenticationFilter            ← for SAML
    ├─ AbstractPreAuthenticatedProcessingFilter   ← for API Gateway or reverse proxy auth
    ↓
AuthenticationManager (usually only one)
    ↓
[AuthenticationProviders] (multiple can be configured)
    ├─ DaoAuthenticationProvider                  ← checks username/password via UserDetailsService
    ├─ JwtAuthenticationProvider                  ← verifies JWT and extracts user info
    ├─ LdapAuthenticationProvider                 ← authenticates via LDAP
    ├─ OAuth2LoginAuthenticationProvider          ← handles OAuth2 login
    ├─ PreAuthenticatedAuthenticationProvider     ← assumes user is pre-authenticated
    ├─ RememberMeAuthenticationProvider           ← auto-logins using remember-me cookie
    └─ CustomAuthenticationProvider               ← custom logic (e.g., API keys, SMS codes)
    ↓
SecurityContextHolder
    ↓
Controller (if authentication succeeds)

```

```text

HTTP Request
    ↓
Filter Chain (SecurityFilterChain)
    ↓
[Authentication Filters]
    ├─ UsernamePasswordAuthenticationFilter
    │     ↓
    │     AuthenticationManager
    │         ↓
    │         DaoAuthenticationProvider
    │             ├─ UserDetailsService      ← loads user from DB or memory
    │             └─ PasswordEncoder         ← matches raw password with encoded
    │
    ├─ BearerTokenAuthenticationFilter
    │     ↓
    │     AuthenticationManager
    │         ↓
    │         JwtAuthenticationProvider
    │             └─ JWT Decoder / Verifier  ← validates signature, extracts claims
    │
    ├─ OAuth2LoginAuthenticationFilter
    │     ↓
    │     AuthenticationManager
    │         ↓
    │         OAuth2LoginAuthenticationProvider
    │             └─ OAuth2UserService       ← loads user info from provider (e.g., Google)
    │
    ├─ Saml2WebSsoAuthenticationFilter
    │     ↓
    │     AuthenticationManager
    │         ↓
    │         Saml2AuthenticationProvider
    │             └─ SAML Assertion Validator
    │
    ├─ AbstractPreAuthenticatedProcessingFilter
    │     ↓
    │     AuthenticationManager
    │         ↓
    │         PreAuthenticatedAuthenticationProvider
    │             └─ Authentication extracted from headers/token by gateway
    │
    └─ RememberMeAuthenticationFilter
          ↓
          RememberMeAuthenticationProvider
              └─ Uses token in cookie to restore session
              
    ↓
SecurityContextHolder
    ↓
Controller / @PreAuthorize / @Secured


```


```text
HTTP Request
    ↓
Security Filter Chain
    ↓
├── SecurityContextPersistenceFilter
│     ↳ Loads SecurityContext from session (if available)
├── CsrfFilter (Optional)
│     ↳ Validates CSRF token for modifying requests (POST/PUT/DELETE)
├── LogoutFilter (Optional)
│     ↳ Intercepts logout URL and clears authentication/session
├── UsernamePasswordAuthenticationFilter (Form Login)
│     ↳ Extracts username & password → AuthenticationManager.authenticate()
│           ↓
│     AuthenticationManager
│           ↓
│     AuthenticationProvider(s) (e.g., DaoAuthenticationProvider)
│           ↓
│     UserDetailsService
│           ↓
│     Load user by username + check password (with PasswordEncoder)
│           ↓
│     On success: return authenticated Authentication
│           ↓
│     Store in SecurityContextHolder
│           ↓
│     Save SecurityContext to session (handled later by SecurityContextPersistenceFilter)
├── BasicAuthenticationFilter (Optional)
│     ↳ Processes HTTP Basic credentials if present
├── BearerTokenAuthenticationFilter (Optional for JWT)
│     ↳ Extracts JWT token → AuthManager.authenticate()
│           ↓
│     JwtAuthenticationProvider
│           ↓
│     Validates token + loads user details
│           ↓
│     Sets Authentication in SecurityContextHolder
├── ExceptionTranslationFilter
│     ↳ Catches access/auth exceptions → redirects or returns error
├── FilterSecurityInterceptor
│     ↳ Checks authorization (hasRole, hasAuthority, etc.)
│         ↓
│     AccessDecisionManager
│         ↓
│     Based on SecurityContext's Authentication and config

→ Proceed to Controller if authorized

Controller Layer
    ↓
Business Logic

HTTP Response
    ↑
SecurityContextPersistenceFilter
    ↳ Saves SecurityContext back to session (on response)
```

### Defining our own UserDetailsService
- we will be creating our own userdetailsservice
- store the users
- for simplicity—other things like roles, authorities, password wont be stored
```text
- using sql
- mysql dependency, datajpa, security, web
- create a schema `ss_lesson2`
- table myusers: username, password, id
- create some users in the db
    - ` 1   bill   12345`  <id, username, password>

```

- what is the idea here?
  - we want to implement our own userdetailsservice
  - which we are going to get the unique user from the db
  

- for userdetails
  - we will create a wrapper around the MyUser
  - we would not implement it directly in the class

- Note
  - `MyUsersJpaUserDetailsService`  -- implements `UserDetailsService`
    - we override the `loadUserByUsername` which return `UserDetails`
    - so we create our `MyWrapperUserDetails`  -- implement the UserDetails
      - where are the user details coming from?
        - from the `MyUsersRepository`  which uses `MyUser` from my db

```text
so we use postman to verify
- use basic auth and prodvide te credentials 
    in the db already

```