package com.example.hot_deal.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(title = "Hot Deal API 명세서",
                description = "정시에 오픈하는 제품 판매 서비스입니다.",
                version = "v1"))
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI();
    }
}