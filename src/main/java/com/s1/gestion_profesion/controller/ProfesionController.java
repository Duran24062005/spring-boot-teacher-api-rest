package com.s1.gestion_profesion.controller;

import com.s1.gestion_profesion.dto.request.ProfesionRequestDTO;
import com.s1.gestion_profesion.dto.response.ProfesionResponseDTO;
import com.s1.gestion_profesion.service.impl.ProfesionServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Profesion", description = "Procesa todo lo relacionado con profesiones")
@RestController
//localhost:8080/api/profesion
@RequestMapping("/api/profesion")
@RequiredArgsConstructor

public class ProfesionController {
    private final ProfesionServiceImpl profesionService;
    //EndPoints: Estos son los que me dice y administran que solicitud HTTP se va a usar.


    @PostMapping
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201",
                            description = "Usuario creado exitosamente"),
                    @ApiResponse(responseCode = "400",
                            description = "Datos no válidos / body mal estructurado")
            }
    )
    public ResponseEntity<ProfesionResponseDTO> guardar(@RequestBody ProfesionRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(profesionService.guardarProfesion(dto));
    }
    //localhost:8080/api/profesion/2
    @PutMapping("/{id}")
    public ResponseEntity<ProfesionResponseDTO> actualizar(@RequestBody ProfesionRequestDTO dto,
                                                           @Parameter(description = "Id de la profesion a actualizar", example = "1")@PathVariable Long id){
        return ResponseEntity.ok().body(profesionService.actualizarProfesion(dto, id));
    }

    @GetMapping
    public ResponseEntity<List<ProfesionResponseDTO>>  listarTodos(){
        return ResponseEntity.ok().body(profesionService.buscarTodos());
    }
    @Operation(summary = "Lista las profesiones por Id",
            description = "Permite mostrar las profesiones por parametro id tipo entero")
    @GetMapping("/{id}")
    public ResponseEntity<ProfesionResponseDTO>  buscarId(@PathVariable Long id){
        return ResponseEntity.ok().body(profesionService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "Id de la profesion a eliminar", example = "1")
            @PathVariable Long id){
        profesionService.eliminarProfesion(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
