package com.soporte.repo;

import com.soporte.model.DetalleTicket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleTicketRepo extends JpaRepository<DetalleTicket, Integer>{
}
