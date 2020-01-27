package com.rmaj91.fishingstoreapi.store.repository;

import com.rmaj91.fishingstoreapi.store.model.RodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RodItemRepository extends JpaRepository<RodItem,Long> {
}
