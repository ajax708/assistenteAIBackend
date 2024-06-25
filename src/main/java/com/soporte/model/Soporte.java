package com.soporte.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Soporte {
    //TODO: cuando envien uncorreo como saber de que producto es la consulta
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDate fechaInicio;
    private Boolean estado;
    private LocalDate fechaFin;
    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;
    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

}
