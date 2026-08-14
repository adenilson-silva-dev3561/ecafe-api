package eCafe.API.customers.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Schema(description = "Dados para cadastro de cliente")
public record CustomerRequest(

        @Schema(description = "Nome do cliente", example = "Adenilson Silva")
        @NotBlank(message = "Nome é obrigatório")
        String name,

        @Schema(description = "Email do cliente", example = "ecafemercado@gmail.com")
        @NotBlank(message = "Email é obrigatório")
        String email,

        @Schema(description = "Telefone do cliente", example = "33999999999")
        @NotBlank(message = "Telefone é obrigatório")
        String phone,

        @Schema(description = "Cpf do cliente", example = "52998224725")
        @NotBlank(message = "Cpf é obrigatório")
        String cpf,

        @Schema(description = "Data de nascimento", example = "1964-02-21")
        @NotNull(message = "Data de nascimento é obrigatório")
        LocalDate birth_date,

        @Schema(description = "Senha do cliente", example = "Senha@123")
        @NotBlank(message = "Senha é obrigatória")
        String password,

        @Schema(
                description = "Status do cliente.",
                example = "true"
        )
        @NotNull(message = "Campo active não pode ser null!")
        Boolean active

) {}