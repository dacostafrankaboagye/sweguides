package org.example.oauth2clientcred.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityConfigTest {
    private static final String GET_ACCESS_TOKEN_URL = "/oauth2/token";

    @Autowired
    MockMvc mockMvc;


    // @Test
    void shouldGetAccessToken() throws Exception {
        mockMvc
                .perform(
                        post(GET_ACCESS_TOKEN_URL)
                                .param("grant_type", "client_credentials")
                                .param("client_id", "frx290MMt")
                                .param("client_secret", "frank-secret")
                                .param("scope", "read")
                )
                .andExpect(status().is2xxSuccessful())
                .andDo(
                        result -> System.out.println(result.getResponse().getContentAsString())
                );
    }

    @Test
    void shouldNotGetAccessToken() throws Exception {
        mockMvc
                .perform(
                        post(GET_ACCESS_TOKEN_URL)
                                .param("grant_type", "client_credentials")
                                .param("client_id", "frx290MMt")
                                .param("client_secret", "wrong-secret")
                )
                .andExpect(
                        status().isUnauthorized()
                )
                .andDo(
                        print()
                )
                .andExpect(
                        jsonPath("$.error", is("invalid_client"))
                );
    }


}