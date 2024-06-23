package com.soporte.dto;



import lombok.Data;

import java.util.List;

@Data
public class TicketDto {
    private Integer id;
    private EmpleadoDto solicitante;
    private AgenteDto responsable;
    private SoporteDto soporte;
    private List<DetalleTicketDto> detalleTicket;
    private String estado;
    private String asunto;
    private String descripcion;
}
