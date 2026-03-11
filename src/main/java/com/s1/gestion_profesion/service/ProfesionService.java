package com.s1.gestion_profesion.service;

import com.s1.gestion_profesion.dto.request.ProfesionRequestDTO;
import com.s1.gestion_profesion.dto.response.ProfesionResponseDTO;

import java.util.List;
import java.util.Optional;

public interface ProfesionService {
    ProfesionResponseDTO guardarProfesion(ProfesionRequestDTO dto);
    ProfesionResponseDTO actualizarProfesion(ProfesionRequestDTO dto, Long id);
    void eliminarProfesion(Long id);
    List<ProfesionResponseDTO> buscarTodos();
    List<ProfesionResponseDTO> buscarNombre(String nombre);
    boolean buscarExisteNombre(String nombre);
    Long contarNombreRepetidos(String nombre);
    ProfesionResponseDTO buscarPorId(Long id);
}
