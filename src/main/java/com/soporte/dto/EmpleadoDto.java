package com.soporte.dto;

import lombok.Data;

@Data
public class EmpleadoDto {

    private Integer id;
    private String nombre;
    private String correo;
    private String telefono;

    private EmpresaDto empresa;
}
