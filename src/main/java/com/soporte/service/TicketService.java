package com.soporte.service;

import com.soporte.dto.AgenteDto;
import com.soporte.dto.DetalleTicketDto;
import com.soporte.dto.TicketDto;
import com.soporte.exception.ModelNotFoundException;
import com.soporte.model.Agente;
import com.soporte.model.DetalleTicket;
import com.soporte.model.Ticket;
import com.soporte.repo.DetalleTicketRepo;
import com.soporte.repo.TicketRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepo ticketRepo;
    private final ModelMapper mapper;
    private final DetalleTicketRepo detalleTicketRepo;

    public TicketDto create(String session, TicketDto ticketDto) throws Exception {
        Ticket ticket=mapper.map(ticketDto,Ticket.class);
        ticket=ticketRepo.save(ticket);
        ticketDto.setId(ticket.getId());
        return ticketDto;
    }

    public TicketDto update(String session, TicketDto ticketDto) throws Exception {
        Ticket ticket=mapper.map(ticketDto,Ticket.class);
        ticket= ticketRepo.save(ticket);
        return ticketDto;
    }


    public void delete(String session, Integer id) throws Exception {
        ticketRepo.deleteById(id);
    }



    public TicketDto readById(String session,Integer id) throws Exception {
        Ticket ticket= ticketRepo.findById(id).orElseThrow(() -> new ModelNotFoundException("NO EXIST:" + id));
        return mapper.map(ticket,TicketDto.class);
    }

    public List<TicketDto> all(String session){
        List<Ticket> modelList=ticketRepo.findAll();
        List<TicketDto> eventDtoList=modelList.stream().map(model->mapper.map(model,TicketDto.class)).toList();
        return eventDtoList;
    }

/*
    public DetalleTicketDto createDetale(TicketDto ticketDto,String content) throws Exception {
        Ticket ticket=mapper.map(ticketDto,Ticket.class);
        ticket=ticketRepo.save(ticket);
        ticketDto.setId(ticket.getId());
        return ticketDto;
    }
*/
    public DetalleTicketDto crearDetalleTicket(TicketDto t,String contenido){
        try{
            Ticket ticket=mapper.map(t,Ticket.class);
            Agente a=new Agente();
            a.setId(1);
            DetalleTicket detalleTicket=new DetalleTicket();
            detalleTicket.setAsunto(t.getAsunto());
            detalleTicket.setDescripcion(contenido.substring(0,250));
            detalleTicket.setEstado("ABIERTO");
            detalleTicket.setResponsable(a);
            detalleTicket.setTicket(ticket);
            detalleTicketRepo.save(detalleTicket);
            DetalleTicketDto detalle=mapper.map(detalleTicket,DetalleTicketDto.class);
            return detalle;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }

    }
}
