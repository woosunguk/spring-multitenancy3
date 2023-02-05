package com.example.springmultitenancy3.mastertenant.service;


import com.example.springmultitenancy3.mastertenant.entity.MasterTenant;
import java.util.List;

/**
 * @author Md. Amran Hossain
 */
public interface MasterTenantService {

    MasterTenant findByClientId(Integer clientId);
    List<MasterTenant> findAll();
}
