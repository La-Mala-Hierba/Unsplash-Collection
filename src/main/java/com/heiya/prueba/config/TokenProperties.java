package com.heiya.prueba.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@ConfigurationProperties(prefix = "prueba.token")
@Data
public class TokenProperties {

    private String client_id;
    private String client_secret;
    private String redirect_uri;
    private String grant_type;
    private String token;



}
