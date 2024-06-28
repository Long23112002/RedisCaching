package com.example.rediscaching.app.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDto {

    private String name;

    private String description;

    private BigDecimal price;

    private String image;

}
