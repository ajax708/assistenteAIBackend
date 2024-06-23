package com.soporte.controller;

import com.soporte.dto.ProductoDto;
import com.soporte.dto.SoporteDto;
import com.soporte.service.ProductoService;
import com.soporte.service.SoporteService;
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
@RequestMapping("/producto")
public class ProductoController {
    private static final Logger LOGGER=Logger.getLogger(ProductoController.class.getName());
    private final ProductoService service;

    @PostMapping(consumes = "application/json;charset=UTF-8",produces = "application/json;charset=UTF-8")
    public ResponseEntity<ProductoDto> save(@RequestBody ProductoDto dto)throws Exception{
        String session= UUID.randomUUID().toString();
        LOGGER.log(Level.INFO,"[{0}] request:{1}",new Object[]{session,dto});
        dto=service.create(session,dto);
        LOGGER.log(Level.INFO,"[{0}] response:{1}",new Object[]{session,dto});
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping(consumes = "application/json;charset=UTF-8",produces = "application/json;charset=UTF-8")
    public ResponseEntity<ProductoDto> update(@RequestBody ProductoDto dto)throws Exception{
        String session= UUID.randomUUID().toString();
        LOGGER.log(Level.INFO,"[{0}] update:{1}",new Object[]{session,dto});
        dto=service.update(session,dto);
        LOGGER.log(Level.INFO,"[{0}] update:{1}",new Object[]{session,dto});
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping(consumes = "application/json;charset=UTF-8",produces = "application/json;charset=UTF-8")
    public ResponseEntity<ProductoDto> findById(@RequestBody Integer id)throws Exception{
        String session= UUID.randomUUID().toString();
        LOGGER.log(Level.INFO,"[{0}] findById:{1}",new Object[]{session,id});
        ProductoDto dto=service.readById(session,id);
        LOGGER.log(Level.INFO,"[{0}] findById:{1}",new Object[]{session,dto});
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping(path ="all",produces = "application/json;charset=UTF-8")
    public ResponseEntity<List<ProductoDto>> all(){
        String session= UUID.randomUUID().toString();
        LOGGER.log(Level.INFO,"[{0}]save all:{1}",new Object[]{session});
        List<ProductoDto> dtoList=service.all(session);
        LOGGER.log(Level.INFO,"[{0}]all:{1}",new Object[]{session,dtoList});
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }
}
