package br.com.fiap.bairroemdia.relatos.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    OpenAPI api() {
        return new OpenAPI().addServersItem(new io.swagger.v3.oas.models.servers.Server().url("/")).info(new Info().title("Bairro em Dia — Relatos").version("v1"));
    }
}
