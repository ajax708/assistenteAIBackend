package com.soporte.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "empleado_id")
    private Empleado solicitante;//fk empleado
    @ManyToOne
    @JoinColumn(name = "personal_id")
    private Agente responsable;//fk de personal
    @ManyToOne
    @JoinColumn(name = "soporte_id")
    private Soporte soporte;
    @OneToMany
    private List<DetalleTicket> detalleTicket;
    private String estado;
    private String asunto;
    private String descripcion;
}
