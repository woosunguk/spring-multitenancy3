package com.example.springmultitenancy3.consumer.config;

import com.example.springmultitenancy3.config.DBContextHolder;
import com.example.springmultitenancy3.entity.TenantDatabase;
import com.example.springmultitenancy3.repository.TenantDatabaseRepository;
import com.example.springmultitenancy3.util.DataSourceUtil;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Slf4j
@Configuration
public class DataSourceBasedMultiTenantConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

    private static final Logger LOG = LoggerFactory.getLogger(DataSourceBasedMultiTenantConnectionProviderImpl.class);

    private static final long serialVersionUID = 1L;

    private Map<String, DataSource> dataSourcesMtApp = new TreeMap<>();

    @Autowired
    private TenantDatabaseRepository tenantDatabaseRepository;

    @Autowired
    ApplicationContext applicationContext;

    @Override
    protected DataSource selectAnyDataSource() {
        // This method is called more than once. So check if the data source map
        // is empty. If it is then rescan master_tenant table for all tenant
        log.error("SDFSFSFSFSDF");
        if (dataSourcesMtApp.isEmpty()) {
            List<TenantDatabase> tenantDatabases = tenantDatabaseRepository.findAll();
            log.error("tenantDatabases : {}", tenantDatabases);
            LOG.info("selectAnyDataSource() method call...Total tenants:" + tenantDatabases.size());
            for (TenantDatabase tenantDatabase : tenantDatabases) {
                dataSourcesMtApp.put(tenantDatabase.getDatabase(), DataSourceUtil.createAndConfigureDataSource(tenantDatabase));
            }
        }

        log.error("selectAnyDataSource : {}", dataSourcesMtApp);

        return this.dataSourcesMtApp.values().iterator().next();
    }

    @Override
    protected DataSource selectDataSource(String tenantIdentifier) {
        // If the requested tenant id is not present check for it in the master
        // database 'master_tenant' table
        tenantIdentifier = initializeTenantIfLost(tenantIdentifier);
        log.error("tenantIdentifier : {}", tenantIdentifier);

        if (!this.dataSourcesMtApp.containsKey(tenantIdentifier)) {
            List<TenantDatabase> tenantDatabases = tenantDatabaseRepository.findAll();
            LOG.info("selectDataSource() method call...Tenant:" + tenantIdentifier + " Total tenants:" + tenantDatabases.size());
            for (TenantDatabase tenantDatabase : tenantDatabases) {
                dataSourcesMtApp.put(tenantDatabase.getDatabase(), DataSourceUtil.createAndConfigureDataSource(tenantDatabase));
            }
        }
        //check again if tenant exist in map after rescan master_db, if not, throw UsernameNotFoundException
        if (!this.dataSourcesMtApp.containsKey(tenantIdentifier)) {
            LOG.warn("Trying to get tenant:" + tenantIdentifier + " which was not found in master db after rescan");
            throw new UsernameNotFoundException(String.format("Tenant not found after rescan, " + " tenant=%s", tenantIdentifier));
        }
        return this.dataSourcesMtApp.get(tenantIdentifier);
    }

    private String initializeTenantIfLost(String tenantIdentifier) {
        if (tenantIdentifier != DBContextHolder.getCurrentDb()) {
            tenantIdentifier = DBContextHolder.getCurrentDb();
        }
        return tenantIdentifier;
    }
}
