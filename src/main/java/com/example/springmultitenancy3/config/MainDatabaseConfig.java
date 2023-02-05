package com.example.springmultitenancy3.config;

import com.example.springmultitenancy3.entity.TenantDatabase;
import com.example.springmultitenancy3.repository.TenantDatabaseRepository;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import java.util.Properties;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@Configuration
@EnableConfigurationProperties(MainDatabaseConfigProperties.class)
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.example.springmultitenancy3.entity", "com.example.springmultitenancy3.repository"},
        entityManagerFactoryRef = "mainEntityManagerFactory",
        transactionManagerRef = "mainTransactionManager")
public class MainDatabaseConfig {

    @Autowired
    private MainDatabaseConfigProperties mainDatabaseConfigProperties;

    //Create Master Data Source using master properties and also configure HikariCP
    @Bean(name = "mainDataSource")
    public DataSource mainDataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setUsername(mainDatabaseConfigProperties.getUsername());
        hikariDataSource.setPassword(mainDatabaseConfigProperties.getPassword());
        hikariDataSource.setJdbcUrl(mainDatabaseConfigProperties.getUrl());
        hikariDataSource.setDriverClassName(mainDatabaseConfigProperties.getDriverClassName());
        hikariDataSource.setPoolName(mainDatabaseConfigProperties.getPoolName());
        // HikariCP settings
        hikariDataSource.setMaximumPoolSize(mainDatabaseConfigProperties.getMaxPoolSize());
        hikariDataSource.setMinimumIdle(mainDatabaseConfigProperties.getMinIdle());
        hikariDataSource.setConnectionTimeout(mainDatabaseConfigProperties.getConnectionTimeout());
        hikariDataSource.setIdleTimeout(mainDatabaseConfigProperties.getIdleTimeout());

        return hikariDataSource;
    }

    @Primary
    @Bean(name = "mainEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean mainEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        // Set the master data source
        em.setDataSource(mainDataSource());
        // The master tenant entity and repository need to be scanned
        em.setPackagesToScan(new String[]{TenantDatabase.class.getPackage().getName(), TenantDatabaseRepository.class.getPackage().getName()});
        // Setting a name for the persistence unit as Spring sets it as
        // 'default' if not defined
        em.setPersistenceUnitName("maindb-persistence-unit");
        // Setting Hibernate as the JPA provider
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        // Set the hibernate properties
        em.setJpaProperties(hibernateProperties());

        return em;
    }

    @Bean(name = "mainTransactionManager")
    public JpaTransactionManager mainTransactionManager(@Qualifier("mainEntityManagerFactory") EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    //Hibernate configuration properties
    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put(org.hibernate.cfg.Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
        properties.put(org.hibernate.cfg.Environment.SHOW_SQL, true);
        properties.put(org.hibernate.cfg.Environment.FORMAT_SQL, true);
        properties.put(org.hibernate.cfg.Environment.HBM2DDL_AUTO, "none");
        return properties;
    }
}
