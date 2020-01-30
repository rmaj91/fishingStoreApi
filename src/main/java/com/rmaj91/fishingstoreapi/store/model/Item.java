package com.rmaj91.fishingstoreapi.store.model;

import lombok.Data;

import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import java.util.Collection;

@MappedSuperclass
@Data
public class Item {

    private double price;
    private int quantity;
    @OneToMany(orphanRemoval = true)
    private Collection<itemReview> reviews;
}
