package com.soporte.service;

import com.soporte.dto.TicketDto;
import com.soporte.exception.ModelNotFoundException;
import com.soporte.model.Ticket;
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

}
