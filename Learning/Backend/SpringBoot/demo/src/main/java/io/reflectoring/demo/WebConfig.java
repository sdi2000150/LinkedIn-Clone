//CORS global configuration

package io.reflectoring.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration  //configuration class
public class WebConfig implements WebMvcConfigurer {

    @Override   //customize how CORS requests are handled by springboot
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("http://localhost:4200")  //allow CORS for all paths and for the angular frontend
        //allow specific methods, all headers, and cookies/HTTP authentication files, to be sent too:
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}