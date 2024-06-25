package com.soporte.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String correo;
    private String telefono;
    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;
}
