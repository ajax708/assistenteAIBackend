package com.soporte.service;

import com.soporte.dto.EmpresaDto;
import com.soporte.exception.ModelNotFoundException;
import com.soporte.model.Empresa;
import com.soporte.repo.EmpresaRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpresaService {
    private final EmpresaRepo repo;
    private final ModelMapper mapper;

    public EmpresaDto create(String session, EmpresaDto dto) throws Exception {
        Empresa model=mapper.map(dto,Empresa.class);
        model= repo.save(model);
        dto.setId(model.getId());
        return dto;
    }

    public EmpresaDto update(String session, EmpresaDto dto) throws Exception {
        Empresa model=mapper.map(dto,Empresa.class);
        model= repo.save(model);
        return dto;
    }


    public void delete(String session, Integer id) throws Exception {
        repo.deleteById(id);
    }



    public EmpresaDto readById(String session, Integer id) throws Exception {
        Empresa model= repo.findById(id).orElseThrow(() -> new ModelNotFoundException("NO EXIST:" + id));
        return mapper.map(model,EmpresaDto.class);
    }

    public List<EmpresaDto> all(String session){
        List<Empresa> modelList= repo.findAll();
        List<EmpresaDto> eventDtoList=modelList.stream().map(model->mapper.map(model,EmpresaDto.class)).toList();
        return eventDtoList;
    }
}
