package com.example.springmultitenancy3.util;

import com.example.springmultitenancy3.entity.TenantDatabase;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class DataSourceUtil {
    public static DataSource createAndConfigureDataSource(TenantDatabase tenantDatabase) {
        log.error("createAndConfigureDataSource : {}", tenantDatabase);

        HikariDataSource ds = new HikariDataSource();
        ds.setUsername(tenantDatabase.getUsername());
        ds.setPassword(tenantDatabase.getPassword());
        ds.setJdbcUrl(tenantDatabase.getUrl());
        ds.setDriverClassName(tenantDatabase.getDriverClass());
        // HikariCP settings - could come from the master_tenant table but
        // hardcoded here for brevity
        // Maximum waiting time for a connection from the pool
        ds.setConnectionTimeout(20000);
        // Minimum number of idle connections in the pool
        ds.setMinimumIdle(3);
        // Maximum number of actual connection in the pool
        ds.setMaximumPoolSize(500);
        // Maximum time that a connection is allowed to sit idle in the pool
        ds.setIdleTimeout(300000);
        ds.setConnectionTimeout(20000);
        // Setting up a pool name for each tenant datasource
        String tenantConnectionPoolName = tenantDatabase.getDatabase() + "-connection-pool";
        ds.setPoolName(tenantConnectionPoolName);

        return ds;
    }
}
