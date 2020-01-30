package com.rmaj91.fishingstoreapi.store.service;

import com.rmaj91.fishingstoreapi.store.model.ItemReview;
import com.rmaj91.fishingstoreapi.store.repository.ItemReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemReviewService {

    private final ItemReviewRepository itemReviewRepository;

    public ItemReview create(ItemReview itemReview) {
        return itemReviewRepository.save(itemReview);
    }

    public void delete(long id){
        itemReviewRepository.deleteById(id);
    }

    public Optional<ItemReview> read(long id) {
        return itemReviewRepository.findById(id);
    }

    public ItemReview update(ItemReview itemReview, long id) throws Exception {
        if(itemReviewRepository.existsById(id)){
            return itemReviewRepository.save(itemReview);
        }
        throw new Exception("Comment with this id wasn't found");
    }
}
