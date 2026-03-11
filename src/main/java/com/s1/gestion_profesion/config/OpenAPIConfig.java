package com.s1.gestion_profesion.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI  customOpenApi(){
        return new OpenAPI()
                .info(new Info()
                        .title("API DOCUMENTADA DE CAMPUSLANDS CON PROFESIONES")
                        .version("1.0")
                        .description("Esta API, se construyó para explicar de forma" +
                                " academica que es swagger."));
    }
}
