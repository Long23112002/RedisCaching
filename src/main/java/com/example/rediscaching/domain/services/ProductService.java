package com.example.rediscaching.domain.services;

import com.example.rediscaching.app.dtos.ProductDto;
import com.example.rediscaching.app.response.MessageResponse;
import com.example.rediscaching.app.response.ProductResponse;
import com.example.rediscaching.app.response.ResponsePage;
import com.example.rediscaching.domain.entities.Product;
import org.springframework.data.domain.Pageable;

public interface ProductService {

  ResponsePage<Product, ProductResponse > getAllProducts(Pageable pageable);

  ProductResponse getProductById(Long id);

  ProductResponse createProduct(ProductDto productDto);

  ProductResponse updateProduct(Long id, ProductDto productDto);

  MessageResponse deleteProduct(Long id);
}
