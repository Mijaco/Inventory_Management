package com.ibcs.desco.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * For phone number validation
 * Created by hossaindoula on 4/27/2016.
 */
public class PhoneNumberValidator {
    private Pattern pattern;
    private Matcher matcher;

    private static final String PHONE_NUMBER_PATTERN =
            "^(^[+00][0-9]{3})?[-.]?([0-9]{4,5})[-.]?([0-9]{7})$";

    public PhoneNumberValidator() {
        pattern = Pattern.compile(PHONE_NUMBER_PATTERN);
    }

    /**
     * Validate hex with regular expression
     *
     * @param hex
     *            hex for validation
     * @return true valid hex, false invalid hex
     * validator.validate("+88001552948737")
     */
    public boolean validate(final String hex) {
        matcher = pattern.matcher(hex);
        return matcher.matches();

    }
}
