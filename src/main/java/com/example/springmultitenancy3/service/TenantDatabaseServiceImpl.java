package com.example.springmultitenancy3.service;

import com.example.springmultitenancy3.entity.TenantDatabase;
import com.example.springmultitenancy3.repository.TenantDatabaseRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TenantDatabaseServiceImpl implements TenantDatabaseService {

    @Autowired
    TenantDatabaseRepository tenantDatabaseRepository;

    @Override
    public List<TenantDatabase> findAll() {
        return tenantDatabaseRepository.findAll();
    }
}
