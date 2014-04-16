package co.uberdev.ultimateorganizer.server.utils;

import co.uberdev.ultimateorganizer.core.CoreDataRules;
import co.uberdev.ultimateorganizer.server.models.User;
import co.uberdev.ultimateorganizer.server.models.Users;
import org.apache.commons.validator.routines.EmailValidator;

/**
 * Created by oguzbilgener on 16/04/14.
 */
public class Validation
{
    // TODO: make this CoreValidation with int responses instead of English strings
    public static boolean validateUser(User user) throws BadInputException
    {
        if(user == null)
            throw new BadInputException("", "user is null.");

        EmailValidator emailValidator = EmailValidator.getInstance();
        if(user.getEmailAddress() == null || ! emailValidator.isValid(user.getEmailAddress()))
            throw new BadInputException(CoreDataRules.fields.register.email, "invalid email address");

        if(user.getPassword() == null)
            throw new BadInputException(CoreDataRules.fields.register.password, "password is null");

        if(user.getPassword().length() < CoreDataRules.PASSWORD_MIN_LENGTH)
            throw new BadInputException(CoreDataRules.fields.register.password, "password is too short");

        if(user.getPassword().length() > CoreDataRules.PASSWORD_MAX_LENGTH)
            throw new BadInputException(CoreDataRules.fields.register.password, "password is too long");

        if(user.getFirstName() == null)
            throw new BadInputException(CoreDataRules.fields.register.firstName, "first name is null");

        if(user.getLastName() == null)
            throw new BadInputException(CoreDataRules.fields.register.lastName, "last name is null");

        if(user.getFirstName().length() > CoreDataRules.FIRSTNAME_MAX_LENGTH)
            throw new BadInputException(CoreDataRules.fields.register.firstName, "first name is too long");

        if(user.getLastName().length() > CoreDataRules.LASTNAME_MAX_LENGTH)
            throw new BadInputException(CoreDataRules.fields.register.lastName, "last name is too long");

        if(user.getSchoolName() == null)
            throw new BadInputException(CoreDataRules.fields.register.schoolName, "school name is null");

        if(user.getDepartmentName() == null)
            throw new BadInputException(CoreDataRules.fields.register.departmentName, "department name is null");

        if(user.getSchoolName().length() > CoreDataRules.SCHOOLNAME_MAX_LENGTH)
            throw new BadInputException(CoreDataRules.fields.register.schoolName, "school name is too long");

        if(user.getDepartmentName().length() > CoreDataRules.DEPARTMENT_MAX_LENGHT)
            throw new BadInputException(CoreDataRules.fields.register.departmentName, "department name is too long");

        // TODO: do not do this on the client side
        if(User.fromEmail(user.getEmailAddress()) != null)
            throw new BadInputException(CoreDataRules.fields.register.email, "email is already taken");

        return true;
    }
    public static class BadInputException extends Exception
    {
        private String fieldName;
        private String validationMessage;

        public BadInputException()
        {
            fieldName = "";
            validationMessage = "";
        }
        public BadInputException(String fieldName, String validationMessage)
        {
            this.fieldName = fieldName;
            this.validationMessage = validationMessage;
        }

        public String toString()
        {
            return "Field ";
        }
    }
}
