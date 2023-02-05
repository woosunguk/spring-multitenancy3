package com.example.springmultitenancy3.repository;

import com.example.springmultitenancy3.entity.TenantDatabase;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantDatabaseRepository extends JpaRepository<TenantDatabase, Long> {
    Optional<TenantDatabase> findById(Long id);
}
