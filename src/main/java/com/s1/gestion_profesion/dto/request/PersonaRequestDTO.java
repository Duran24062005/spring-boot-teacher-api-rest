package com.s1.gestion_profesion.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record PersonaRequestDTO(
        @Schema(description = "Se ingresa el documento de la persona",
                example = "1007999211")
        String documento,
        @Schema(description = "Se ingresa el nombre de la persona",
                example = "David")
        String nombre,
        @Schema(description = "Se ingresa el apellido de la persona",
                example = "DM")
        @Size(min = 2, max = 25, message = "Error, el apellido debe tener minimo 2 caracteres maximo 25")
        String apellido,
        @NotNull(message = "Error, la edad no puede ser nulo")
        @Positive(message = "Error, la edad debe ser positiva")
        Integer edad,
        BigDecimal salario,
        Long profesionId
) {
}
