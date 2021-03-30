package com.database.migration.service;


import com.database.migration.constants.DataMigrationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataMigratorFactory {

    @Autowired
    private DataMigrator personDataMigrator;

    public DataMigrator createDataMigratorService(DataMigrationType migrationType) {
        switch (migrationType) {
            case Person:
                return personDataMigrator;
            default:
                throw new IllegalArgumentException("Invalid Migration Type");
        }
    }
}
