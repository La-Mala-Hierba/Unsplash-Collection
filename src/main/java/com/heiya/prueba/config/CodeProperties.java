package com.heiya.prueba.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "prueba.code")
@Data
public class CodeProperties {

    private String client_id;
    private String redirect_uri;
    private String response_type;
    private String scope;



}
