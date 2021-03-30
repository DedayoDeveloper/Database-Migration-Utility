package com.database.migration.repository;

import com.database.migration.data.Persons;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonRowMapper implements RowMapper<Persons> {
    @Override
    public Persons mapRow(ResultSet resultSet, int i) throws SQLException {
        Persons persons = new Persons();
        persons.setFirstName(resultSet.getString("FIRST_NAME"));
        persons.setLastName(resultSet.getString("LAST_NAME"));
        persons.setEmail(resultSet.getString("EMAIL"));
        persons.setGender(resultSet.getString("GENDER"));
        return persons;
    }

    public PersonRowMapper() {
    }
}
