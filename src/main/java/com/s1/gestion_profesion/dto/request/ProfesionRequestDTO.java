package com.s1.gestion_profesion.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * EL record, permite ser llenado por constructor,
 * se puede acceder a sus datos, directamente por el nombre de la clase.
 * Con esto evitamos los getter, setters y demas validaciones. Centrandonos
 * unicamente en transferencia y obtención de datos.
 * */

public record ProfesionRequestDTO(
        @Schema(description = "Nombre de la profesion", example = "Sistemas")
        @NotBlank(message = "El nombre no puede estar vacio.")
        @Size(min = 2, max = 50, message = "Error, el rango del nombre debe estar entre 2 y 50 caracteres")
        String nombre,
        @Schema(description = "Recibe la descripcion de la proofesion", example = "mundo tech")
        @NotBlank(message = "El nombre no puede estar vacio.")
        @Size(min = 2, max = 50, message = "Error, el rango del nombre debe estar entre 2 y 50 caracteres")
        String descripcion
) {

        //¿QUE ES UN JSON?
        //DICCIONARIO, MAP.
        //CONTIENEN CLAVE Y DATO.
        /**
         * [
         *      {
         *              clave     valor
         *              "cedula": 1007,
         *              "nombre": "david",
         *              "edad": 27,
         *              "pasatiempos": ["leer", "escribir", "comer", "pasear"]
         *      },
         *      {
         *              clave     valor
         *              "cedula": 1008,
         *              "nombre": "pablo",
         *              "edad": 21,
         *              "pasatiempos": ["comer", "jugar"]
         *      }
         * ]
         *
         * */
}
