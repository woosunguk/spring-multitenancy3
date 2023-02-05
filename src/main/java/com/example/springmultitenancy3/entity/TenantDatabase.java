package com.example.springmultitenancy3.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;

@Entity
@Data
@Table(name = "tenant_databases")
public class TenantDatabase implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "database",nullable = false)
    private String database;

    @Column(name = "url",nullable = false)
    private String url;

    @Column(name = "username",nullable = false)
    private String username;

    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "driver_class",nullable = false)
    private String driverClass;

    @Column(name = "status",nullable = false)
    private String status;

}
