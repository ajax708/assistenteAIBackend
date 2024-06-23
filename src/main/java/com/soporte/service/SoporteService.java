package com.soporte.service;

import com.soporte.dto.EmpresaDto;
import com.soporte.dto.ProductoDto;
import com.soporte.dto.SoporteDto;
import com.soporte.exception.ModelNotFoundException;
import com.soporte.model.Empresa;
import com.soporte.model.Producto;
import com.soporte.model.Soporte;
import com.soporte.repo.SoporteRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SoporteService {
    private final SoporteRepo soporteRepo;
    private final ModelMapper modelMapper;

    public SoporteDto create(String session, SoporteDto soporteDto) throws Exception {
        Soporte soporte=modelMapper.map(soporteDto, Soporte.class);
        soporte= soporteRepo.save(soporte);
        soporteDto=modelMapper.map(soporte, SoporteDto.class);
        return soporteDto;
    }

    public SoporteDto update(String session, SoporteDto soporteDto) throws Exception {
        Soporte soporte=modelMapper.map(soporteDto, Soporte.class);
        soporte= soporteRepo.save(soporte);
        soporteDto=modelMapper.map(soporte, SoporteDto.class);
        return soporteDto;
    }


    public void delete(String session, Integer id) throws Exception {
        /*Soporte soporte=soporteRepo.deleteById(id);
        Soporte soporte=modelMapper.map(soporteDto, Soporte.class);
        soporte= soporteRepo.save(soporte);
        soporteDto=modelMapper.map(soporte, SoporteDto.class);
        return soporteDto;*/
    }



    public SoporteDto readById(String session,Integer id) throws Exception {
        Soporte soporte=soporteRepo.findById(id).orElseThrow(() -> new ModelNotFoundException("NO EXIST:" + id));
        SoporteDto soporteDto=modelMapper.map(soporte, SoporteDto.class);
        return soporteDto;

    }

    public List<SoporteDto> all(String session){
        List<Soporte> modelList=soporteRepo.findAll();
        List<SoporteDto> list=modelList.stream().map(model->modelMapper.map(model,SoporteDto.class)).toList();
        return list;
    }

    public SoporteDto readByEmpresaAndProducto(EmpresaDto empresaDto, ProductoDto productoDto) throws Exception {
        Empresa empresa=modelMapper.map(empresaDto, Empresa.class);
        Producto producto=modelMapper.map(productoDto, Producto.class);
        Soporte soporte=soporteRepo.findByEmpresaAndProducto(empresa,producto);
        SoporteDto soporteDto=modelMapper.map(soporte, SoporteDto.class);
        return soporteDto;

    }

}
