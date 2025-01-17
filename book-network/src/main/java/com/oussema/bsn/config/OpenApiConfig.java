package com.oussema.bsn.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

import static io.swagger.v3.oas.annotations.enums.SecuritySchemeType.HTTP;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Oussema sahbeni",
                        email ="oussemasahbeni300@gmail.com",
                        url = "https://oussemasahbeni.tn"
                ),
                description = "OpenApi documentation for the Book Network API",
                title = "OpenApi specification",
                version = "1.0",
                license = @License(
                        name = "Licence name",
                        url= "https://some-url.com"
                ),
                termsOfService = "Terms of service"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8088/api/v1"
                ),
                @Server(
                        description = "Production ENV",
                        url = "https://book-network.herokuapp.com/api/v1"
                )
        },
        security = {
                @SecurityRequirement(
                        name="bearerAuth"

                )
        }
)
@SecurityScheme(
        name="bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type= HTTP,
        bearerFormat = "JWT",
        in= SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
