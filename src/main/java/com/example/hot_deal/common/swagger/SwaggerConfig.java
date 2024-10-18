package com.example.hot_deal.common.swagger;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("Hot Deal API 명세서")
                .version("v1.0")
                .description("""
                        핫딜 API 명세서입니다. \n
                        """);
        return new OpenAPI()
                .info(info)
                .servers(
                        List.of(new Server()
                                .url("http://localhost:8080")
                                .description("로컬 서버"))
                );
    }
}