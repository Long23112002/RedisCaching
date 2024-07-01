package com.example.rediscaching.domain.services.impl;

import com.example.rediscaching.app.dtos.ProductDto;
import com.example.rediscaching.app.response.MessageResponse;
import com.example.rediscaching.app.response.ProductResponse;
import com.example.rediscaching.app.response.ResponsePage;
import com.example.rediscaching.domain.entities.Product;
import com.example.rediscaching.domain.repositories.ProductRepository;
import com.example.rediscaching.domain.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

  private final ProductRepository productRepository;
  private final ModelMapper modelMapper;

  @Override
  @Cacheable(value = "products", key = "#pageable.pageNumber")
  public ResponsePage<Product, ProductResponse> getAllProducts(Pageable pageable) {
    Page<Product> productPages = productRepository.findAll(pageable);
    logger.info("Products fetched: {}", productPages.getContent());
    return new ResponsePage<>(productPages, ProductResponse.class);
  }

  @Override
  @Cacheable(value = "product", key = "#id")
  public ProductResponse getProductById(Long id) {
    logger.info("Fetching product with id: {}", id);
    return modelMapper.map(productRepository.findById(id).orElseThrow(), ProductResponse.class);
  }

  @Override
  @Caching(
      put = {@CachePut(value = "product", key = "#result.id")},
      evict = {@CacheEvict(value = "products", allEntries = true)})
  public ProductResponse createProduct(ProductDto productDto) {
    Product product = modelMapper.map(productDto, Product.class);
    Product savedProduct = productRepository.save(product);
    logger.info("Created new product: {}", savedProduct);
    return modelMapper.map(savedProduct, ProductResponse.class);
  }

  @Override
  @Transactional
  @Caching(
      put = {@CachePut(value = "product", key = "#id")},
      evict = {@CacheEvict(value = "products", allEntries = true)})
  public ProductResponse updateProduct(Long id, ProductDto productDto) {
    Product product = productRepository.findById(id).orElseThrow();
    modelMapper.map(productDto, product);
    Product updatedProduct = productRepository.save(product);
    logger.info("Updated product: {}", updatedProduct);
    return modelMapper.map(updatedProduct, ProductResponse.class);
  }

  @Override
  @CacheEvict(
      value = {"product", "products"},
      key = "#id")
  public MessageResponse deleteProduct(Long id) {
    productRepository.deleteById(id);
    logger.info("Deleted product with id: {}", id);
    return new MessageResponse("Product deleted successfully");
  }

  // Uncomment and modify as needed for testing
  //  @Transactional
  //  public void createProducts(int numberOfProducts) {
  //    List<Product> products = new ArrayList<>();
  //    for (int i = 0; i < numberOfProducts; i++) {
  //      Product product = new Product();
  //      product.setName("Product " + i);
  //      product.setDescription("Description of Product " + i);
  //      product.setPrice(BigDecimal.valueOf(10 + i));
  //      product.setImage("image" + i + ".jpg");
  //      products.add(product);
  //    }
  //    productRepository.saveAll(products);
  //  }
}
