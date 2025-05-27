package org.example.securingapiwithoauth2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MyTokenResponse {
    String access_token;
    String id_token;
}
