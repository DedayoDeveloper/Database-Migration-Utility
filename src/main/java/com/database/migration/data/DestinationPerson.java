package com.database.migration.data;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class DestinationPerson {

    private String firstName;
    private String lastName;
    private String email;
    private String gender;
}
