package com.rmaj91.fishingstoreapi.store.model;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import java.util.Collection;

@MappedSuperclass
@Data
public class Item {

    private double price;
    private int quantity;
    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(orphanRemoval = true,cascade = CascadeType.ALL)
    private Collection<ItemReview> reviews;
}
