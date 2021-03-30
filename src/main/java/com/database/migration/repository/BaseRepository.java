package com.database.migration.repository;

import com.database.migration.constants.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;


public abstract class BaseRepository<S,D> {

    @Autowired
    @Qualifier(CommonConstants.SOURCE_JDBC_TEMPLATE_BEAN_NAME)
    private JdbcTemplate sourceJdbcTemplate;

    @Autowired
    @Qualifier(CommonConstants.DESTINATION_JDBC_TEMPLATE_BEAN_NAME)
    private JdbcTemplate destinationJdbcTemplate;

    @Value("${database.record.fetch-size:1000}")
    private int fetchSize;

    public BaseRepository(JdbcTemplate sourceJdbcTemplate, JdbcTemplate destinationJdbcTemplate) {
        this.sourceJdbcTemplate = sourceJdbcTemplate;
        this.destinationJdbcTemplate = destinationJdbcTemplate;
    }

    protected JdbcTemplate getSourceJdbcTemplate() {
        return sourceJdbcTemplate;
    }

    protected JdbcTemplate getDestinationJdbcTemplate() {
        return destinationJdbcTemplate;
    }

    public int getFetchSize() {
        return fetchSize;
    }

    public abstract List<S> retrieveDataFromSourceDatabase(int startFrom);

    public abstract boolean saveDataToDestinationDatabase(List<D> migrationData);
}
