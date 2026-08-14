package eCafe.API.customers.service;

import eCafe.API.customers.dto.KeycloakUserRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "keycloak-client",
        url = "${keycloak.server-url}"
)
public interface KeycloakClient {

    @PostMapping("/admin/realms/{realm}/users")
    ResponseEntity<Void> createUser(
            @PathVariable("realm") String realm,
            @RequestHeader("Authorization") String authorization,
            @RequestBody KeycloakUserRequest user
    );
}