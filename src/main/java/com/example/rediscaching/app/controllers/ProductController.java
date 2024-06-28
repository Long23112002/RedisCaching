package com.example.rediscaching.app.controllers;

import com.example.rediscaching.app.dtos.ProductDto;
import com.example.rediscaching.app.response.MessageResponse;
import com.example.rediscaching.app.response.ProductResponse;
import com.example.rediscaching.app.response.ResponsePage;
import com.example.rediscaching.domain.entities.Product;
import com.example.rediscaching.domain.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {

  private final ProductService productService;

  @GetMapping
  public ResponsePage<Product, ProductResponse> getAllProduct(Pageable pageable) {
    return productService.getAllProducts(pageable);
  }

  @PostMapping
  public ProductResponse createProduct( @RequestBody ProductDto productDto) {
    return productService.createProduct(productDto);
  }

  @PutMapping("/{id}")
  public ProductResponse updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
    return productService.updateProduct(id, productDto);
  }

  @GetMapping("/{id}")
  public ProductResponse getProductById(@PathVariable Long id) {
    ProductResponse productResponse =  productService.getProductById(id);
    log.info("Product fetched: {}", productResponse);
    return productResponse;
  }

  @DeleteMapping("/{id}")
  public MessageResponse deleteProduct(@PathVariable Long id) {
    return productService.deleteProduct(id);
  }
}
