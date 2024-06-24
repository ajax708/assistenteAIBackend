package com.soporte.repo;

import com.soporte.model.DetalleTicket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDetalleTicket extends JpaRepository<DetalleTicket, Integer>{
}
