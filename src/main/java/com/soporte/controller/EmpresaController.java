package com.soporte.controller;

import com.soporte.dto.EmpresaDto;
import com.soporte.dto.ProductoDto;
import com.soporte.service.EmpresaService;
import com.soporte.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequiredArgsConstructor
@RequestMapping("/empresa")
public class EmpresaController {
    private static final Logger LOGGER=Logger.getLogger(EmpresaController.class.getName());
    private final EmpresaService service;

    @PostMapping(consumes = "application/json;charset=UTF-8",produces = "application/json;charset=UTF-8")
    public ResponseEntity<EmpresaDto> save(@RequestBody EmpresaDto dto)throws Exception{
        String session= UUID.randomUUID().toString();
        LOGGER.log(Level.INFO,"[{0}] request:{1}",new Object[]{session,dto});
        dto=service.create(session,dto);
        LOGGER.log(Level.INFO,"[{0}] response:{1}",new Object[]{session,dto});
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping(consumes = "application/json;charset=UTF-8",produces = "application/json;charset=UTF-8")
    public ResponseEntity<EmpresaDto> update(@RequestBody EmpresaDto dto)throws Exception{
        String session= UUID.randomUUID().toString();
        LOGGER.log(Level.INFO,"[{0}] update:{1}",new Object[]{session,dto});
        dto=service.update(session,dto);
        LOGGER.log(Level.INFO,"[{0}] update:{1}",new Object[]{session,dto});
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping(consumes = "application/json;charset=UTF-8",produces = "application/json;charset=UTF-8")
    public ResponseEntity<EmpresaDto> findById(@RequestBody Integer id)throws Exception{
        String session= UUID.randomUUID().toString();
        LOGGER.log(Level.INFO,"[{0}] findById:{1}",new Object[]{session,id});
        EmpresaDto dto=service.readById(session,id);
        LOGGER.log(Level.INFO,"[{0}] findById:{1}",new Object[]{session,dto});
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping(path ="all",produces = "application/json;charset=UTF-8")
    public ResponseEntity<List<EmpresaDto>> all(){
        String session= UUID.randomUUID().toString();
        LOGGER.log(Level.INFO,"[{0}]save all:{1}",new Object[]{session});
        List<EmpresaDto> dtoList=service.all(session);
        LOGGER.log(Level.INFO,"[{0}]all:{1}",new Object[]{session,dtoList});
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }
}
