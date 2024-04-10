package xyz.mrkwcode.aiimusicserver.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import xyz.mrkwcode.aiimusicserver.annos.Gender;

public class GenderValidation implements ConstraintValidator<Gender, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(s == null){
            return false;
        } else if(!s.equals("male") && !s.equals("female") && !s.equals("unknown")) {
            return false;
        } else {
            return true;
        }
    }
}
