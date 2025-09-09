package org.lokhit.gamelove.security;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.lokhit.gamelove.util.KeycloakRoleConverter;

public class KeycloakJwtConverter extends JwtAuthenticationConverter {

    public KeycloakJwtConverter() {
        // This sets the converter that extracts authorities from the JWT
        this.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());
    }
}

