/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rest.utilitarios;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author edwin
 */

public class ValidadorEmail  {

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\."
            + "[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*"
            + "(\\.[A-Za-z]{2,})$";
    private Pattern pattern;
    private Matcher matcher;

    public ValidadorEmail() {
        pattern = Pattern.compile(EMAIL_PATTERN);
    }


    public boolean validate(Object value) {
        matcher = pattern.matcher(value.toString());
        if (!matcher.matches()) {
           return false;
        }
        return true;

    }
}