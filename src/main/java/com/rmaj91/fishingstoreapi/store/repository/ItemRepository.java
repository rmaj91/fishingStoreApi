package com.rmaj91.fishingstoreapi.store.repository;

import com.rmaj91.fishingstoreapi.store.model.Category;
import com.rmaj91.fishingstoreapi.store.model.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends PagingAndSortingRepository<Item,Long> {
    List<Item> findByCategory(Category category, Pageable pageable);
    List<Item> findByCategory(Category category);

}
