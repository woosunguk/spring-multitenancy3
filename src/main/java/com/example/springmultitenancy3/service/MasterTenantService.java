package com.example.springmultitenancy3.service;


import com.example.springmultitenancy3.entity.MasterTenant;
import java.util.List;

/**
 * @author Md. Amran Hossain
 */
public interface MasterTenantService {

    MasterTenant findByClientId(Integer clientId);
    List<MasterTenant> findAll();
}
