package com.rmaj91.fishingstoreapi.store.model;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Item {

    private String category;
    private double price;
    private ItemComment itemComment;
    private int quantity;

}
