package eCafe.API.auth.dto;

public record LoginResponse(
        String accessToken,
        String refreshToken,
        Long expiresIn
) {
}
