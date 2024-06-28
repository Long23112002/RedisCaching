package com.example.rediscaching.app.response;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductResponse {

  private Long id;

  private String name;

  private String description;

  private BigDecimal price;

  private String image;
}
