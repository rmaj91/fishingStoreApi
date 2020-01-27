package com.rmaj91.fishingstoreapi.store.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rmaj91.fishingstoreapi.store.model.RodItem;
import com.rmaj91.fishingstoreapi.store.repository.RodItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RodItemService {

    private final RodItemRepository rodItemRepository;
    private final ObjectMapper objectMapper;

    public RodItem create(RodItem rodItem){
        return rodItemRepository.save(rodItem);
    }

    public RodItem read(long id){
        RodItem rodItem = rodItemRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("no such RodItem with this id"));
        return rodItem;
    }

    public List<RodItem> readAll(){
        return rodItemRepository.findAll();
    }

    public RodItem update(RodItem rodItem, long id){
        if(!rodItemRepository.existsById(id)) {
            throw new IllegalArgumentException("no such RodItem with this id");
        }
        return rodItemRepository.save(rodItem);
    }

    public RodItem patch(Map<String, Object> rodItemUpdates, long id) {
        RodItem rodItemToUpdate = rodItemRepository
                .findById(id)
                .orElseThrow(()->new IllegalArgumentException("no such RodItem with this id"));
        Map rodItemToUpdateMap = objectMapper.convertValue(rodItemToUpdate,Map.class);
        rodItemUpdates.forEach(rodItemToUpdateMap::put);
        RodItem rodItem = objectMapper.convertValue(rodItemToUpdateMap,RodItem.class);
        return rodItemRepository.save(rodItem);

    }

    public void delete(long id){
        rodItemRepository.deleteById(id);
    }
}
