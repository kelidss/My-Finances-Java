package com.api.my_finances.loggers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class DatabaseConfigLogger {

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @PostConstruct
    public void logDatasourceUrl() {
        System.out.println("Datasource URL: " + datasourceUrl);
    }
}
