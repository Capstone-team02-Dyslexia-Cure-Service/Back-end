package com.capstone.dyslexia.global.config.openApi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Documentation")
                        .version("1.0")
                        .description("API documentation for the application"));
    }

    @Bean
    public OperationCustomizer customOperationCustomizer() {
        return (operation, handlerMethod) -> {
            ApiResponse response = new ApiResponse()
                    .description("OK")
                    .content(new Content().addMediaType("application/json",
                            new MediaType().schema(new Schema<>()
                                    .$ref("#/components/schemas/ApiResponseTemplate"))));
            operation.getResponses().addApiResponse("200", response);
            return operation;
        };
    }
}