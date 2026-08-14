package eCafe.API.customers.service;

public interface KeycloakService {

    String createUser(String name, String email, String password);
    String getAccessToken();
}
