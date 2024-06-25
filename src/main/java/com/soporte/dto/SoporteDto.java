package com.soporte.dto;



import lombok.Data;

import java.time.LocalDate;

@Data
public class SoporteDto {

    private Integer id;
    private LocalDate fechaInicio;
    private Boolean estado;
    private LocalDate fechaFin;
    private ProductoDto producto;
    private EmpresaDto empresa;

}
