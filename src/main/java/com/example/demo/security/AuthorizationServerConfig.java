package com.example.demo.security;

import java.time.Duration;
import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
public class AuthorizationServerConfig {

	
	@Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        return http.build();
    }

    @Bean
    public AuthorizationServerSettings providerSettings(AlgaFoodSecurityProperties properties) {
        return AuthorizationServerSettings.builder()
                .issuer("http://localhost:8080")
                .build();
    }
	/*@Bean
    public AuthorizationServerSettings providerSettings() {
        return AuthorizationServerSettings.builder()        		
        		.build();
    }*/

    @Bean
    public RegisteredClientRepository registeredClientRepository(PasswordEncoder passwordEncoder) {

        RegisteredClient algafoodbackend = RegisteredClient
                .withId("1")
                .clientId("algafood-backend")
                .clientSecret(passwordEncoder.encode("backend123"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                //.authorizationGrantType(AuthorizationGrantType.PASSWORD)                
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .scope("READ")
                .tokenSettings(TokenSettings.builder()
                        .accessTokenFormat(OAuth2TokenFormat.REFERENCE)
                        .accessTokenTimeToLive(Duration.ofMinutes(30))
                        .build())
                .build();
        
        RegisteredClient algafoodPassword = RegisteredClient
                .withId("2")
                .clientId("algafood-password")
                .clientSecret(passwordEncoder.encode("backend123"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.PASSWORD)                
                //.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .scope("READ")
                .tokenSettings(TokenSettings.builder()
                        .accessTokenFormat(OAuth2TokenFormat.REFERENCE)
                        .accessTokenTimeToLive(Duration.ofMinutes(30))
                        .build())
                .build();

        return new InMemoryRegisteredClientRepository(Arrays.asList(algafoodbackend, algafoodPassword));
    }
	
	
	/*@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Bean
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
			.withClient("angular") 
			.secret(passwordEncoder.encode("@ngul@r0"))
			.scopes("read", "write")
			.authorizedGrantTypes("password", "refresh_token")
			.accessTokenValiditySeconds(60*10) //1min
			.refreshTokenValiditySeconds(60*60); //1hora 
		.and()
			.withClient("mobile") 
			.secret("m0b1l30")
			.scopes("read", "write")
			.authorizedGrantTypes("password", "refresh_token")
			.accessTokenValiditySeconds(60*2) //2min
			.refreshTokenValiditySeconds(60 * 60 * 24 * 7);
	}*/
	
}
