package com.soporte.repo;

import com.soporte.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepo extends JpaRepository<Empresa, Integer>{
}
