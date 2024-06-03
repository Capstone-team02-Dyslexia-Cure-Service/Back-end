package com.capstone.dyslexia.global.config.flaskAPI;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class FlaskAPIConfig {

    @Value("${flask.api.url}")
    private String flaskAPIUrl;

}
