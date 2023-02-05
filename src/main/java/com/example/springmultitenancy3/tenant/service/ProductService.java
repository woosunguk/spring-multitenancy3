package com.example.springmultitenancy3.tenant.service;


import com.example.springmultitenancy3.tenant.entity.Product;
import java.util.List;

/**
 * @author Md. Amran Hossain
 */
public interface ProductService {

    List<Product> getAllProduct();
}
