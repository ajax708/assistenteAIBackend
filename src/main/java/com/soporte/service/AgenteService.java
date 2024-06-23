package com.soporte.service;

import com.soporte.dto.AgenteDto;
import com.soporte.dto.EmpresaDto;
import com.soporte.exception.ModelNotFoundException;
import com.soporte.model.Agente;
import com.soporte.model.Empresa;
import com.soporte.repo.AgenteRepo;
import com.soporte.repo.EmpresaRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgenteService {
    private final AgenteRepo repo;
    private final ModelMapper mapper;

    public AgenteDto create(String session, AgenteDto dto) throws Exception {
        Agente model=mapper.map(dto,Agente.class);
        model= repo.save(model);
        dto.setId(model.getId());
        return dto;
    }

    public AgenteDto update(String session, AgenteDto dto) throws Exception {
        Agente model=mapper.map(dto,Agente.class);
        model= repo.save(model);
        return dto;
    }


    public void delete(String session, Integer id) throws Exception {
        repo.deleteById(id);
    }



    public AgenteDto readById(String session, Integer id) throws Exception {
        Agente model= repo.findById(id).orElseThrow(() -> new ModelNotFoundException("NO EXIST:" + id));
        return mapper.map(model,AgenteDto.class);
    }

    public List<AgenteDto> all(String session){
        List<Agente> modelList= repo.findAll();
        List<AgenteDto> list=modelList.stream().map(model->mapper.map(model,AgenteDto.class)).toList();
        return list;
    }
}
