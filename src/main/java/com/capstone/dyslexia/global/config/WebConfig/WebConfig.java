package com.capstone.dyslexia.global.config.WebConfig;

import com.capstone.dyslexia.global.util.SecretStaticValue;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final SecretStaticValue secretStaticValue;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping(secretStaticValue.getAddMapping())
                .allowedOrigins(
                        secretStaticValue.getAllowedOrigins().values().toArray(new String[0])
                )
                .allowedMethods(
                        secretStaticValue.getAllowedMethods().toArray(new String[0])
                )
                .allowedHeaders(
                        secretStaticValue.getAllowedHeaders().toArray(new String[0])
                )
                .allowCredentials(secretStaticValue.isAllowCredentials())
                .maxAge(secretStaticValue.getMaxAge());
    }
}
