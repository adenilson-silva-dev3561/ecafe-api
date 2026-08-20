package eCafe.API.customers.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Dados para cadastro de cliente")
public record CustomerRequest(

        @Schema(
                description = "Nome do cliente",
                example = "Adenilson Silva"
        )
        @NotBlank(message = "Nome é obrigatório")
        String name,

        @Schema(
                description = "Email do cliente",
                example = "ecafemercado@gmail.com"
        )
        @NotBlank(message = "Email é obrigatório")
        String email,

        @Schema(
                description = "Senha do cliente",
                example = "Senha@123"
        )
        @NotBlank(message = "Senha é obrigatória")
        String password

) {}