package com.example.springmultitenancy3.controller;

import com.example.springmultitenancy3.tenant.entity.Product;
import com.example.springmultitenancy3.tenant.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TestController {

  private final ProductService productService;

  @GetMapping("/test")
  public List<Product> getMasterTenants() {
    List<Product> products = productService.getAllProduct();

    return products;

  }
}
