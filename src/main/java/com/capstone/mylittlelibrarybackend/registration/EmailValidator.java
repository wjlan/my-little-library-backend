package com.capstone.mylittlelibrarybackend.registration;

import org.springframework.stereotype.Component;
import java.util.function.Predicate;

@Component
public class EmailValidator implements Predicate<String> {

    @Override
    public boolean test(String s) {
        // TODO: Regex to validate email
        return s != null && s.contains("@"); // simple email validation for example
    }
}
