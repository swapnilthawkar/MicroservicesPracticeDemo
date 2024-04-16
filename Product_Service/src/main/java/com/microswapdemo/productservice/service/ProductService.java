package com.microswapdemo.productservice.service;

import com.microswapdemo.productservice.dto.ProductRequest;
import com.microswapdemo.productservice.dto.ProductResponce;
import com.microswapdemo.productservice.model.Product;
import com.microswapdemo.productservice.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void createProduct(ProductRequest productRequest){
        Product product = Product.builder().
                       name(productRequest.getName()).
                       discription(productRequest.getDiscription()).
                       price(productRequest.getPrice()).build();
        productRepository.save(product);
        log.info("Product {} is saved..",product.getId());

    }

    public List<ProductResponce> getAllProduct(){
        List<Product> productList = productRepository.findAll();

        return productList.stream().map(this::mapToProductResponce).toList();
    }

    private ProductResponce mapToProductResponce(Product product) {

        return ProductResponce.builder().
                id(product.getId()).
                name(product.getName()).
                discription(product.getDiscription()).
                price(product.getPrice()).
                build();
    }
}
