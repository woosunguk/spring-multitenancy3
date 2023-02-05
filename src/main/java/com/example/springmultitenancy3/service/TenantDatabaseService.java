package com.example.springmultitenancy3.service;


import com.example.springmultitenancy3.entity.TenantDatabase;
import java.util.List;

public interface TenantDatabaseService {
  List<TenantDatabase> findAll();
}
