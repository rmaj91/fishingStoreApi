package com.rmaj91.fishingstoreapi.store.repository;

import com.rmaj91.fishingstoreapi.store.model.Item;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends PagingAndSortingRepository<Item,Long> {
}
