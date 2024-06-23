package com.soporte.service;

import com.soporte.dto.ProductoDto;
import com.soporte.dto.TicketDto;
import com.soporte.exception.ModelNotFoundException;
import com.soporte.model.Producto;
import com.soporte.repo.ProductoRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoService {
    private final ProductoRepo repo;
    private final ModelMapper mapper;

    public ProductoDto create(String session, ProductoDto dto) throws Exception {
        Producto model=mapper.map(dto,Producto.class);
        model= repo.save(model);
        dto.setId(model.getId());
        return dto;
    }

    public ProductoDto update(String session, ProductoDto dto) throws Exception {
        Producto model=mapper.map(dto,Producto.class);
        model= repo.save(model);
        return dto;
    }


    public void delete(String session, Integer id) throws Exception {
        repo.deleteById(id);
    }



    public ProductoDto readById(String session,Integer id) throws Exception {
        Producto model= repo.findById(id).orElseThrow(() -> new ModelNotFoundException("NO EXIST:" + id));
        return mapper.map(model,ProductoDto.class);
    }

    public List<ProductoDto> all(String session){
        List<Producto> modelList= repo.findAll();
        List<ProductoDto> list=modelList.stream().map(model->mapper.map(model,ProductoDto.class)).toList();
        return list;
    }
}
