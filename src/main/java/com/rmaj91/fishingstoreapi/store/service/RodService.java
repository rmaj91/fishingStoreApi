package com.rmaj91.fishingstoreapi.store.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rmaj91.fishingstoreapi.store.model.Rod;
import com.rmaj91.fishingstoreapi.store.repository.RodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RodService {

    private final RodRepository rodRepository;
    private final ObjectMapper objectMapper;

    public Rod create(Rod rod){
        return rodRepository.save(rod);
    }

    public Rod read(long id){
        Rod rod = rodRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No such RodItem with this id"));
        return rod;
    }

    public List<Rod> readAll(){
        return rodRepository.findAll();
    }

    public Rod update(Rod rod, long id){
        if(!rodRepository.existsById(id)) {
            throw new IllegalArgumentException("No such RodItem with this id");
        }
        return rodRepository.save(rod);
    }

    public Rod patch(Map<String, Object> rodItemUpdates, long id) {
        Rod rodToUpdate = rodRepository
                .findById(id)
                .orElseThrow(()->new IllegalArgumentException("No such RodItem with this id"));
        Map rodItemToUpdateMap = objectMapper.convertValue(rodToUpdate,Map.class);
        rodItemUpdates.forEach(rodItemToUpdateMap::put);
        Rod rod = objectMapper.convertValue(rodItemToUpdateMap, Rod.class);
        return rodRepository.save(rod);

    }

    public void delete(long id){
        rodRepository.deleteById(id);
    }
}
