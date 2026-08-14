package eCafe.API.customers.service;

public interface KeycloakService {

    String createUser(String userName, String email, String password);
    String getAccessToken();
}
