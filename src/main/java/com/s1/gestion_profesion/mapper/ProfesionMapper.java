package com.s1.gestion_profesion.mapper;

import com.s1.gestion_profesion.dto.request.ProfesionRequestDTO;
import com.s1.gestion_profesion.dto.response.ProfesionResponseDTO;
import com.s1.gestion_profesion.model.Profesion;
import org.springframework.stereotype.Component;

@Component
public class ProfesionMapper {
    public ProfesionResponseDTO entidadADTO(Profesion profesion){
        if(profesion==null) return null;
        return new ProfesionResponseDTO(
                profesion.getId(), profesion.getNombre(), profesion.getDescripcion()
        );
    }
    public Profesion DTOAEntidad(ProfesionRequestDTO dto){
        if(dto==null) return null;
        Profesion p=new Profesion();
        p.setDescripcion(dto.descripcion());
        p.setNombre(dto.nombre());
        return p;
    }

    public void actualizarEntidadDesdeDTO(Profesion profesion, ProfesionRequestDTO dto){
        if(profesion==null || dto==null) return;
        profesion.setDescripcion(dto.descripcion());
        profesion.setNombre(dto.nombre());
    }

}
