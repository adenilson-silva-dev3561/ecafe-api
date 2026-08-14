package eCafe.API.customers.dto;

import java.util.List;

public record KeycloakUserRequest(
        String username,
        String email,
        String firstName,
        String lastName,
        boolean enabled,
        boolean emailVerified,
        List<Credential> credentials
) {

    public record Credential(
            String type,
            String value,
            boolean temporary
    ) {}
}