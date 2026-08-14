package eCafe.API.auth.service.impl;

import eCafe.API.auth.dto.LoginRequest;
import eCafe.API.auth.dto.LoginResponse;
import eCafe.API.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final RestClient.Builder restClientBuilder;

    @Value("${keycloak.server-url}")
    private String keycloakServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.admin.client-id}")
    private String clientId;

    @Value("${keycloak.admin.client-secret}")
    private String clientSecret;

    @Override
    public LoginResponse login(LoginRequest request) {

        RestClient restClient = restClientBuilder
                .baseUrl(keycloakServerUrl)
                .build();

        Map<String, Object> response = restClient.post()
                .uri("/realms/{realm}/protocol/openid-connect/token", realm)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body("grant_type=password"
                        + "&client_id=" + clientId
                        + "&client_secret=" + clientSecret
                        + "&username=" + request.email()
                        + "&password=" + request.password())
                .retrieve()
                .body(Map.class);

        return new LoginResponse(
                (String) response.get("access_token"),
                (String) response.get("refresh_token"),
                ((Number) response.get("expires_in")).longValue()
        );
    }
}