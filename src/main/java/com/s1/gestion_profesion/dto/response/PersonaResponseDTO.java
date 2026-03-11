package com.s1.gestion_profesion.dto.response;

import java.math.BigDecimal;

public record PersonaResponseDTO(
        Long id, String documento, String nombre, String apellido,
        Integer edad, BigDecimal salario, ProfesionResponseDTO profesion
) {

}
