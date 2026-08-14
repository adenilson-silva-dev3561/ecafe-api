package eCafe.API.auth.controller;

import eCafe.API.auth.dto.LoginRequest;
import eCafe.API.auth.dto.LoginResponse;
import eCafe.API.auth.service.AuthService;
import eCafe.API.common.constants.ApiRoutes;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiRoutes.AUTH)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody @Valid LoginRequest request) {

        LoginResponse login = authService.login(request);

        return ResponseEntity.ok(login);
    }
}