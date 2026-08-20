package eCafe.API.config;

import org.jspecify.annotations.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(
                                "http://127.0.0.1:5500",
                                "http://localhost:8080",
                                "http://127.0.0.1:5501",
                                "http://172.18.64.1:5501",
                                "http://192.168.30.164:5501",
                                "http://192.168.19.118:5501"
                        )
                        .allowedMethods(
                                "GET",
                                "POST",
                                "PUT",
                                "DELETE",
                                "PATCH",
                                "OPTIONS"
                        )
                        .allowedHeaders("*");
            }
        };
    }
}