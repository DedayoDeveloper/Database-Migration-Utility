package com.database.migration.repository;


import com.database.migration.constants.CommonConstants;
import com.database.migration.data.DestinationPerson;
import com.database.migration.data.Persons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Repository
public class PersonRepository extends BaseRepository<Persons, DestinationPerson> {


    @Autowired
    public PersonRepository(JdbcTemplate sourceJdbcTemplate, @Qualifier(CommonConstants.DESTINATION_JDBC_TEMPLATE_BEAN_NAME)
            JdbcTemplate destinationJdbcTemplate) {
        super(sourceJdbcTemplate, destinationJdbcTemplate);
    }

    @Transactional(readOnly = true)
    @Override
   public List<Persons> retrieveDataFromSourceDatabase(int startFrom) {
        String sql = "select * from PERSONS where id >= " + startFrom + " order by id";
        List<Persons> persons = super.getSourceJdbcTemplate().query(sql, new PersonRowMapper());
        if (persons == null) {
            return new ArrayList<>();
        }
        return persons;
    }

    @Override
    public boolean saveDataToDestinationDatabase(List<DestinationPerson> migrationData) {
        try{
        String sql = "insert into PERSONS (FIRST_NAME, LAST_NAME, EMAIL, GENDER) values (?, ?, ?, ?)";
        super.getDestinationJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement pStmt, int branchIndx) throws SQLException {
                int parameterIndx = 1;
                pStmt.setString(parameterIndx++, migrationData.get(branchIndx).getFirstName());
                pStmt.setString(parameterIndx++, migrationData.get(branchIndx).getLastName());
                pStmt.setString(parameterIndx++, migrationData.get(branchIndx).getGender());
                pStmt.setString(parameterIndx++, migrationData.get(branchIndx).getEmail());
            }

            @Override
            public int getBatchSize() {
                return migrationData.size();
            }
        });
        return true;
    } catch (Exception ex) {
        ex.printStackTrace();
        return false;
    }
    }
}
