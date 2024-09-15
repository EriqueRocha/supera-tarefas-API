package com.supera.desafio.tarefas.infra.doc;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "Erique Rocha - Tarefas - API", version = "1.0",
                contact = @Contact(name = "Erique Rocha", email = "eriquebit@gmail.com", url = "https://www.erique.dev"),
                license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0"), termsOfService = "TOS",
                description = "API de Tarefas"),
        servers = {
                @Server(url = "https://supera-tarefas-api-production.up.railway.app/", description = "Production"),
                @Server(url = "http://localhost:8080/", description = "Development")})
public class OpenAPI30Configuration {
    /**
     * Configure the OpenAPI components.
     *
     * @return Returns fully configure OpenAPI object
     * @see OpenAPI
     */
}