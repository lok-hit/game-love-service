package org.lokhit.gamelove.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@NoArgsConstructor
public class SwaggerConfiguration {
    @Bean
    public OpenAPI gameLoveOpenAPI() {

        Scopes scopes = new Scopes();
        scopes.addString("openid", "OpenID Connect scope");

        return new OpenAPI()
                .info(new Info()
                        .title("Game Love Service API")
                        .version("1.0.0")
                        .description("Track which games players love"))
                .components(new Components()
                        .addSecuritySchemes("Keycloak", new SecurityScheme()
                                .type(SecurityScheme.Type.OAUTH2)
                                .flows(new OAuthFlows()
                                        .authorizationCode(new OAuthFlow()
                                                .authorizationUrl("http://localhost:8080/realms/gamelove/protocol/openid-connect/auth")
                                                .tokenUrl("http://localhost:8080/realms/gamelove/protocol/openid-connect/token")
                                                .scopes(scopes)))));
    }
}


