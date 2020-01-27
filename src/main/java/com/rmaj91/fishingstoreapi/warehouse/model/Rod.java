package com.rmaj91.fishingstoreapi.warehouse.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Rod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;
    private String model;
    private double rodLength;
    private String castingWeight;
    private String description;
    @JsonProperty("pricePLN")
    private double pricePln;

}
