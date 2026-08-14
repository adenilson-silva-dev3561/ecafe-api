package eCafe.API.auth.service;

import eCafe.API.auth.dto.LoginRequest;
import eCafe.API.auth.dto.LoginResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    LoginResponse login(LoginRequest request);
}
