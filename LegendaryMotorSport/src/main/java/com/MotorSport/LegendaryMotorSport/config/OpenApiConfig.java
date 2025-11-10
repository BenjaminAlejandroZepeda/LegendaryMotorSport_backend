package com.MotorSport.LegendaryMotorSport.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Legendary MotorSport API")
                .description("API para la gestión del proyecto Legendary Motor Sport.")
                .version("v1.0.0")
                .contact(new Contact()
                    .name("Equipo Legendary Devs")
                    .email("ben.zepeda@duocuc.cl")
                    .url("https://github.com/BenjaminAlejandroZepeda/LegendaryMotorSport_backend.git"))
                .license(new License()
                    .name("Apache 2.0")
                    .url("https://www.apache.org/licenses/LICENSE-2.0.html"))
            );
    }
}

