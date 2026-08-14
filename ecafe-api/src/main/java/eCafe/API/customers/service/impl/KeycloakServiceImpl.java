package eCafe.API.customers.service.impl;

import eCafe.API.customers.dto.KeycloakUserRequest;
import eCafe.API.customers.service.KeycloakClient;
import eCafe.API.customers.service.KeycloakService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class KeycloakServiceImpl implements KeycloakService {

    private final RestClient.Builder restClientBuilder;
    private final KeycloakClient keycloakClient;

    @Value("${keycloak.server-url}")
    private String keycloakServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.admin.client-id}")
    private String clientId;

    @Value("${keycloak.admin.client-secret}")
    private String clientSecret;

    @Override
    public String getAccessToken() {

        RestClient restClient = restClientBuilder
                .baseUrl(keycloakServerUrl)
                .build();

        Map<String, Object> response = restClient.post()
                .uri("/realms/{realm}/protocol/openid-connect/token", realm)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body("grant_type=client_credentials"
                        + "&client_id=" + clientId
                        + "&client_secret=" + clientSecret)
                .retrieve()
                .body(Map.class);

        return (String) response.get("access_token");
    }

    @Override
    public String createUser(String userName, String email, String password) {

        String accessToken = getAccessToken();

        KeycloakUserRequest userRequest = new KeycloakUserRequest(
                userName,
                email,
                true,
                List.of(
                        new KeycloakUserRequest.Credential(
                                "password",
                                password,
                                false
                        )
                )
        );

        ResponseEntity<Void> response = keycloakClient.createUser(
                realm,
                "Bearer " + accessToken,
                userRequest
        );

        if (response.getHeaders().getLocation() == null) {
            throw new IllegalStateException("Keycloak não retornou o ID do usuário.");
        }

        String location = response.getHeaders().getLocation().toString();

        return location.substring(location.lastIndexOf("/") + 1);
    }
}