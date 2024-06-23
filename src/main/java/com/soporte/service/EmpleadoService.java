package com.soporte.service;

import com.soporte.dto.EmpleadoDto;
import com.soporte.exception.ModelNotFoundException;
import com.soporte.model.Empleado;
import com.soporte.model.Ticket;
import com.soporte.repo.EmpleadoRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmpleadoService {
    private final EmpleadoRepo empleadoRepo;
    private final ModelMapper modelMapper;

    public EmpleadoDto create(String session, EmpleadoDto empleadoDto) throws Exception {
        Empleado empleado=modelMapper.map(empleadoDto, Empleado.class);
         empleadoRepo.save(empleado);
        empleadoDto=modelMapper.map(empleado, EmpleadoDto.class);
        return empleadoDto;
    }

    public EmpleadoDto update(String session, EmpleadoDto empleadoDto) throws Exception {
        Empleado empleado=modelMapper.map(empleadoDto, Empleado.class);
        empleadoRepo.save(empleado);
        empleadoDto=modelMapper.map(empleado, EmpleadoDto.class);
        return empleadoDto;
    }


    public void delete(String session, Integer id) throws Exception {
        empleadoRepo.deleteById(id);
    }



    public EmpleadoDto readById(Integer id) throws Exception {
        Empleado empleado= empleadoRepo.findById(id).orElseThrow(() -> new ModelNotFoundException("NO EXIST:" + id));
        EmpleadoDto empleadoDto=modelMapper.map(empleado, EmpleadoDto.class);
        return empleadoDto;
    }

    public EmpleadoDto readByCorreo(String email) throws Exception {
        Empleado empleado= empleadoRepo.findByCorreo(email);
        EmpleadoDto empleadoDto=modelMapper.map(empleado, EmpleadoDto.class);
        return empleadoDto;
    }
}
