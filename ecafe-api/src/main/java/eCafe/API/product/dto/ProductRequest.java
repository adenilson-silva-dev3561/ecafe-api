package eCafe.API.product.dto;

import eCafe.API.common.enums.UnitType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Schema(description = "Dados para cadastro de um produto.")
public record ProductRequest(

        @Schema(
                description = "Identificador da categoria do produto.",
                example = "1"
        )
        @NotNull(message = "Categoria é obrigatória!")
        Long categoryId,

        @Schema(
                description = "Nome do produto.",
                example = "Café Gourmet 500g"
        )
        @NotBlank(message = "Nome é obrigatório!")
        String name,

        @Schema(
                description = "Descrição detalhada do produto.",
                example = "Café gourmet 100% arábica, torrado e moído."
        )
        @NotBlank(message = "A descrição é obrigatória!")
        String description,

        @Schema(
                description = "Preço de venda do produto.",
                example = "29.90"
        )
        @NotNull(message = "Preço é obrigatório!")
        @Positive(message = "Preço deve ser maior que zero!")
        @Digits(integer = 10, fraction = 2, message = "Preço inválido!")
        BigDecimal price,

        @NotNull(message = "Unidade de venda é obrigatória!")
        UnitType unitType,

        @Schema(
                description = "Quantidade disponível em estoque.",
                example = "100"
        )
        @NotNull(message = "Quantidade em estoque é obrigatória!")
        @PositiveOrZero(message = "Quantidade em estoque não pode ser negativa!")
        Integer stockQuantity,

        @Schema(description = "Imagem referente ao produto", example = "url da imagem")
        @NotNull(message = "A url da imagem é obrigatoria!")
        String imageUrl,

        @Schema(
                description = "Indica se o produto está disponível para venda.",
                example = "true"
        )
        @NotNull(message = "Status ativo é obrigatório!")
        Boolean active

){}
