package com.rmaj91.fishingstoreapi.store.model;

import com.rmaj91.fishingstoreapi.warehouse.model.Rod;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;

@Entity
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class RodItem extends Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private RodItemCategory category;
    @OneToOne
    private Rod rod;
}
