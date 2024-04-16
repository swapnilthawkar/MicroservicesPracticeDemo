package com.microswapdemo.productservice.controller;

import com.microswapdemo.productservice.dto.ProductRequest;
import com.microswapdemo.productservice.dto.ProductResponce;
import com.microswapdemo.productservice.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productrRequest){
          productService.createProduct(productrRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponce> getAllProduct(){
       return productService.getAllProduct();
    }
}
