package com.database.migration;

import com.database.migration.constants.DataMigrationType;
import com.database.migration.service.DataMigratorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationInitializer {

    @Value("${run.staticdata.migration}")
    private boolean migrateStaticData;

    @Value("${staticdata.migration.order}")
    private DataMigrationType[] staticDataMigrationSequence;

    @Autowired
    private DataMigratorFactory dataMigratorFactory;

    void init() {
        if (migrateStaticData) {
            for (DataMigrationType migrationType : staticDataMigrationSequence) {
                dataMigratorFactory.createDataMigratorService(migrationType).migrate();
            }
        }
    }
}
