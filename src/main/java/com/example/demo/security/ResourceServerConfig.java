package com.example.demo.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class ResourceServerConfig {

    @Bean
    public SecurityFilterChain resourceServerFilterChain(HttpSecurity http) throws Exception {
        http
        .authorizeHttpRequests((authorizeHttpRequests) ->
   	  		 		authorizeHttpRequests
   	  		 		//.anyRequest().permitAll()
   	  			 	.requestMatchers("/oauth2/**").authenticated()
   	  			 	.requestMatchers("/teste/**").authenticated()
   	  			 		
   	  		 	)      
            
            .csrf().disable()
            .cors().and()
            .oauth2ResourceServer().opaqueToken();

        return http.build();
    }
    
    @Bean
	UserDetailsService users() {
		UserDetails user = User.withDefaultPasswordEncoder()
				.username("edison")
				.password("123")
				.roles("USER")
				.build();
		return new InMemoryUserDetailsManager(user);
	}

}