package eCafe.API.customers.dto;

import java.time.LocalDate;

public record CustomerUpdateRequest(
        String name,
        String email,
        String phone,
        String cpf,
        LocalDate birth_date,
        Boolean active
) {
}
