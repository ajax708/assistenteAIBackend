package com.soporte.repo;

import com.soporte.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoRepo extends JpaRepository<Empleado, Integer>{

    public Empleado findByCorreo(String email);
}
