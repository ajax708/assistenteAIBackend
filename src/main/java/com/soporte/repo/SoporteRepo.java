package com.soporte.repo;

import com.soporte.model.Empresa;
import com.soporte.model.Producto;
import com.soporte.model.Soporte;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SoporteRepo extends JpaRepository<Soporte, Integer>{

    public Soporte findByEmpresaAndProducto(Empresa empresa, Producto producto);
}
