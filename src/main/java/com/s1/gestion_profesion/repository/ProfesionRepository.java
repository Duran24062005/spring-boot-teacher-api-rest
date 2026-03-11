package com.s1.gestion_profesion.repository;

import com.s1.gestion_profesion.model.Profesion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfesionRepository extends JpaRepository<Profesion,Long> {
    List<Profesion> findByNombreIgnoreCase(String nombre);
    boolean existsByNombre(String nombre);
    Long countByNombre(String nombre);
}
