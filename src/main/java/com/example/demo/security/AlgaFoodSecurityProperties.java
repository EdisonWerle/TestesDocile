package com.example.demo.security;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;


@Component
@Getter
@Setter
@Validated
@ConfigurationProperties("algafood.auth")
public class AlgaFoodSecurityProperties {
    
    private String providerUrl;

}