package com.capstone.dyslexia.global.config.WebConfig;

import com.capstone.dyslexia.global.util.SecretValue;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final SecretValue secretValue;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping(secretValue.getAddMapping())
                .allowedOrigins(
                        secretValue.getAllowedOrigins().values().toArray(new String[0])
                )
                .allowedMethods(
                        secretValue.getAllowedMethods().toArray(new String[0])
                )
                .allowedHeaders(
                        secretValue.getAllowedHeaders().toArray(new String[0])
                )
                .allowCredentials(secretValue.isAllowCredentials())
                .maxAge(secretValue.getMaxAge());
    }
}
