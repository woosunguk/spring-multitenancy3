package com.example.springmultitenancy3.controller;

import com.example.springmultitenancy3.entity.TenantDatabase;
import com.example.springmultitenancy3.consumer.entity.Product;
import com.example.springmultitenancy3.consumer.service.ProductService;
import com.example.springmultitenancy3.service.TenantDatabaseService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TestController {

  private final TenantDatabaseService tenantDatabaseService;
  private final ProductService productService;

  @GetMapping("/test")
  public List<Product> getProducts() {
    List<Product> products = productService.getAllProduct();

    return products;
  }

  @GetMapping("/test2")
  public List<TenantDatabase> getTenantDatabases() {
    return tenantDatabaseService.findAll();
  }
}
