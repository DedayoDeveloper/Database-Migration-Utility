package com.database.migration.service;


import com.database.migration.data.DestinationPerson;
import com.database.migration.data.Persons;
import com.database.migration.repository.PersonRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.util.ObjectUtils;
import sun.security.krb5.internal.crypto.Des;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonDataMigrator implements DataMigrator {

    @Autowired
    private PersonRepository personRepository;


    @Override
    public void migrate() {
        System.out.println("<<<<<<<<<<< Migrating Person Data >>>>>>>>>>>");
       List<Persons>  getAll = personRepository.retrieveDataFromSourceDatabase(1);

            ObjectMapper mapper = new ObjectMapper();
            try {
                String jsonData = mapper.writeValueAsString(getAll);
                List<DestinationPerson> personsData = mapper.readValue(jsonData, new TypeReference<List<DestinationPerson>>() {});
                System.out.println(personsData.size());
                boolean saveToDestination = personRepository.saveDataToDestinationDatabase(personsData);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }


    }
}
