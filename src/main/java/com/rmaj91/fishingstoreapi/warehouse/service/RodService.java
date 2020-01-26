package com.rmaj91.fishingstoreapi.warehouse.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rmaj91.fishingstoreapi.warehouse.model.Rod;
import com.rmaj91.fishingstoreapi.warehouse.repository.RodRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RodService {

    private final RodRespository rodRespository;
    private final ObjectMapper objectMapper;

    public Rod create(Rod rod){
        return rodRespository.save(rod);
    }

    public Collection<Rod> readAll(){
        return rodRespository.findAll();
    }

    public Rod read(Long id){
        return rodRespository
                .findById(id)
                .orElseThrow(()->new IllegalArgumentException("Rod with exact id not found"));
    }

    public void delete(Long id) {
        rodRespository.deleteById(id);
    }

    public Rod update(Rod rod, Long id) {
        Optional<Rod> rodById = rodRespository.findById(id);
        if(!rodById.isPresent()){
            throw new IllegalArgumentException("Rod with exact id not found");
        }
        return rodRespository.save(rod);
    }

    public Rod patch(Map<String, String> rodUpdates, long id) {
        Rod rodToUpdate = rodRespository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rod with exact id not found"));
        Map rodToUpdateMap = objectMapper.convertValue(rodToUpdate,Map.class);
        rodUpdates.forEach(rodToUpdateMap::put);
        Rod rod = objectMapper.convertValue(rodToUpdateMap,Rod.class);
        return rod;
    }
}
