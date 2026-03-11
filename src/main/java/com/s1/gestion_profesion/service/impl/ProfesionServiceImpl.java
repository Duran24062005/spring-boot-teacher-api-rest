package com.s1.gestion_profesion.service.impl;

import com.s1.gestion_profesion.dto.request.ProfesionRequestDTO;
import com.s1.gestion_profesion.dto.response.ProfesionResponseDTO;
import com.s1.gestion_profesion.mapper.ProfesionMapper;
import com.s1.gestion_profesion.model.Profesion;
import com.s1.gestion_profesion.repository.ProfesionRepository;
import com.s1.gestion_profesion.service.ProfesionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfesionServiceImpl implements ProfesionService {
    private final ProfesionMapper profesionMapper;
    private final ProfesionRepository profesionRepository;

    @Override
    public ProfesionResponseDTO guardarProfesion(ProfesionRequestDTO dto) {
        Profesion p=profesionMapper.DTOAEntidad(dto);
        Profesion p_insertada=profesionRepository.save(p);
        return profesionMapper.entidadADTO(p_insertada);
    }

    @Override
    public ProfesionResponseDTO actualizarProfesion(ProfesionRequestDTO dto, Long id) {
        Profesion p=profesionRepository.findById(id).orElseThrow(()->new RuntimeException("No existe dicha profesion a actualizar"));
        profesionMapper.actualizarEntidadDesdeDTO(p,dto);
        Profesion p_actualizada=profesionRepository.save(p);
        return profesionMapper.entidadADTO(p_actualizada);
    }

    @Override
    public void eliminarProfesion(Long id) {
        Profesion p=profesionRepository.findById(id).orElseThrow(()->new RuntimeException("No existe dicha profesion a eliminar"));
        profesionRepository.delete(p);
    }
    @Override
    public List<ProfesionResponseDTO> buscarTodos() {
        List<Profesion> profesiones=profesionRepository.findAll();
        return profesiones.stream().map(profesionMapper::entidadADTO).toList();
    }

    @Override
    public List<ProfesionResponseDTO> buscarNombre(String nombre) {
        List<Profesion> profesiones=profesionRepository.findByNombreIgnoreCase(nombre);
        return profesiones.stream().map(profesionMapper::entidadADTO).toList();
    }

    @Override
    public boolean buscarExisteNombre(String nombre) {
        return profesionRepository.existsByNombre(nombre);
    }

    @Override
    public Long contarNombreRepetidos(String nombre) {
        return profesionRepository.countByNombre(nombre);
    }

    @Override
    public ProfesionResponseDTO buscarPorId(Long id) {
        Profesion p=profesionRepository.findById(id).orElseThrow(()->new RuntimeException("No existe dicha profesion"));
        return profesionMapper.entidadADTO(p);
    }
}
