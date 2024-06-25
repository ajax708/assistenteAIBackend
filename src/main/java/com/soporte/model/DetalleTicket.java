package com.soporte.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class DetalleTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String asunto;
    private String descripcion;
    private String estado;
    @ManyToOne
    @JoinColumn(name = "agente_id")
    private Agente responsable;
    private Integer ticketId;
}
