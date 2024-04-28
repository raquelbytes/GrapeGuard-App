package com.raquelbytes.grapeguardapi.Repository;

import com.raquelbytes.grapeguardapi.Model.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TareaRepository extends JpaRepository<Tarea, Integer> {

}