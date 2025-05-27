package org.example.securingapiwithoauth2.controller;

import org.example.securingapiwithoauth2.config.SecurityConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@Import(SecurityConfig.class)
@WebMvcTest(AppController.class)
class AppControllerTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void publicEndpoint() {
    }

    @Test
    void securedHello() {
    }

    @Test
    void getToken() {
    }

    @Test
    void userInfo() {
    }
}