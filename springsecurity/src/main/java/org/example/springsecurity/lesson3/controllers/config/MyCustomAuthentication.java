//package org.example.springsecurity.lesson3.controllers.config;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//
//import javax.security.auth.Subject;
//import java.util.Collection;
//import java.util.List;
//
//
//@AllArgsConstructor
//@Getter
//@Setter
//public class MyCustomAuthentication implements Authentication {
//
//    private boolean authenticated;
//    private final String key;
//
//    @Override
//    public boolean isAuthenticated() {
//        return authenticated;
//    }
//
//    @Override
//    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
//
//
//    }
//
//    // we leave the rest as it is..
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of();
//    }
//
//    @Override
//    public Object getCredentials() {
//        return null;
//    }
//
//    @Override
//    public Object getDetails() {
//        return null;
//    }
//
//    @Override
//    public Object getPrincipal() {
//        return null;
//    }
//
//
//
//    @Override
//    public String getName() {
//        return "";
//    }
//
//    @Override
//    public boolean implies(Subject subject) {
//        return Authentication.super.implies(subject);
//    }
//}
