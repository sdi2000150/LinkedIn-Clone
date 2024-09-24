//CORS global configuration

package com.Backend_v10;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration  //configuration class
public class WebConfig implements WebMvcConfigurer {

    @Override   //customize how CORS requests are handled by springboot
    public void addCorsMappings(CorsRegistry registry) {
        //allow CORS for all paths and for the angular frontend (changed to https after the SSL/TLS integration)
        registry.addMapping("/**").allowedOrigins("https://localhost:4200")
        //allow specific methods, all headers, and cookies/HTTP authentication files, to be sent too:
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}