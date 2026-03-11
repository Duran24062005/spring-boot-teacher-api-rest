package com.s1.gestion_profesion.service.impl;

import com.s1.gestion_profesion.dto.request.PersonaRequestDTO;
import com.s1.gestion_profesion.dto.response.PersonaResponseDTO;
import com.s1.gestion_profesion.dto.response.ProfesionResponseDTO;
import com.s1.gestion_profesion.exception.BusinessRuleException;
import com.s1.gestion_profesion.mapper.PersonaMapper;
import com.s1.gestion_profesion.mapper.ProfesionMapper;
import com.s1.gestion_profesion.model.Persona;
import com.s1.gestion_profesion.model.Profesion;
import com.s1.gestion_profesion.repository.PersonaRepository;
import com.s1.gestion_profesion.repository.ProfesionRepository;
import com.s1.gestion_profesion.service.PersonaService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonaServiceImpl implements PersonaService {
    private final PersonaRepository personaRepository;
    private final PersonaMapper personaMapper;
    private final ProfesionRepository profesionRepository;
    private final ProfesionMapper profesionMapper;

    @Override
    public PersonaResponseDTO guardarPersona(PersonaRequestDTO dto) {
        Profesion pr= profesionRepository.findById(dto.profesionId()).orElseThrow(()->new RuntimeException("Error, no existe dicha profesion"));
        Persona p=personaMapper.DTOAEntidad(dto, pr);
        Persona p_insertada=personaRepository.save(p);
        ProfesionResponseDTO dtoProfesion=profesionMapper.entidadADTO(pr);
        return personaMapper.entidadADTO(p_insertada,dtoProfesion);
        //return personaMapper.entidadADTO(personaRepository.save(p), profesionMapper.entidadADTO(pr));
    }

    @Override
    public PersonaResponseDTO actualizarPersona(PersonaRequestDTO dto, Long id) {
        Persona p=personaRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Error, no existe dicha persona a actualizar"));
        Profesion pr= profesionRepository.findById(dto.profesionId()).orElseThrow(()->new EntityNotFoundException("Error, no existe dicha profesion"));
        personaMapper.actualizarEntidadDesdeDTO(p,dto, pr);
        Persona p_actualizada=personaRepository.save(p);
        ProfesionResponseDTO dtoProfesion=profesionMapper.entidadADTO(pr);
        return personaMapper.entidadADTO(p_actualizada,dtoProfesion);
    }

    @Override
    public List<PersonaResponseDTO> buscarPorNombre(String nombre) {
        return personaRepository.findByNombreIgnoreCase(nombre).stream().map(dato-> personaMapper.entidadADTO(dato, profesionMapper.entidadADTO(profesionRepository.findById(dato.getProfesion().getId()).orElseThrow(()->new RuntimeException("NO EXISTE DICHA PROFESION"))))).toList();
    }

    @Override
    public void eliminarPersona(Long id) {

    }

    @Override
    public List<PersonaResponseDTO> listarPersonas() {
        return personaRepository.findAll().stream().map(dato-> personaMapper.entidadADTO(dato, profesionMapper.entidadADTO(profesionRepository.findById(dato.getProfesion().getId()).orElseThrow(()->new RuntimeException("NO EXISTE DICHA PROFESION"))))).toList();
    }

    @Override
    public PersonaResponseDTO buscarPorId(Long id) {
       Persona p = personaRepository.findById(id).orElseThrow(()->new RuntimeException("No existe dicha persona"));
       Profesion profesion = profesionRepository.findById(p.getProfesion().getId()).orElseThrow(() -> new RuntimeException("No existe dicha profesion"));
       ProfesionResponseDTO profesionR = profesionMapper.entidadADTO(profesion);
       return personaMapper.entidadADTO(p, profesionR);
    }

    @Override
    public List<PersonaResponseDTO> buscarMayorQueEdad(Integer edad) {
        return personaRepository.findByEdadGreaterThan(edad).stream().map(dato-> personaMapper.entidadADTO(dato, profesionMapper.entidadADTO(profesionRepository.findById(dato.getProfesion().getId()).orElseThrow(()->new EntityNotFoundException("NO EXISTE DICHA PROFESION"))))).toList();
    }
}
