package com.s1.gestion_profesion.repository;

import com.s1.gestion_profesion.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
    List<Persona> findByNombreIgnoreCase(String nombre);
    //Lo que retorna, que necesito que JPA haga con base a su estructura, parametros
    //camelCase = JPA | mysql = snake_case | Clases PascalCase
    //select * from persona where edad>18;
    List<Persona> findByEdadGreaterThan(Integer edad);
}
