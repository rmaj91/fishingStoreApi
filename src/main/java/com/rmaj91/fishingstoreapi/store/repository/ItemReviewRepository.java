package com.rmaj91.fishingstoreapi.store.repository;

import com.rmaj91.fishingstoreapi.store.model.ItemReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemReviewRepository extends JpaRepository<ItemReview,Long> {
}
