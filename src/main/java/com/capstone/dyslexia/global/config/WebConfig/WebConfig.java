package com.capstone.dyslexia.global.config.WebConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173", "https://dyslexiazoo.netlify.app")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("Origin", "X-Requested-With", "Content-Type", "Accept", "Authorization", "memberId", "animalId", "numOfQuestions", "solvingRecordId", "questionId", "questionResponseType", "questionType")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
