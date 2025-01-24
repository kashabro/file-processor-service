package com.example.file.processor.testSupport;

import com.example.file.processor.model.PersonData;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PersonDataUtil {

    public static PersonData getPersonData() {
        return new PersonData("John Smith", "Rides A Bike", 12.1);
    }
}
