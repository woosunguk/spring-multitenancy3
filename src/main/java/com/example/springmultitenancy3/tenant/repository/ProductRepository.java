package com.example.springmultitenancy3.tenant.repository;


import com.example.springmultitenancy3.tenant.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Md. Amran Hossain
 */
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
