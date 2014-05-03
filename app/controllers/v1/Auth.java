package controllers.v1;

import co.uberdev.ultimateorganizer.core.CoreDataRules;
import co.uberdev.ultimateorganizer.core.CoreUtils;
import co.uberdev.ultimateorganizer.server.utils.Authentication;
import co.uberdev.ultimateorganizer.server.models.User;
import co.uberdev.ultimateorganizer.server.utils.Validation;
import com.fasterxml.jackson.databind.JsonNode;
import play.Logger;
import play.mvc.*;

public class Auth extends Controller
{
    /**
     * The controller for /auth/login
     * @return
     */
    public static Result login()
    {
        JsonNode requestNode = request().body().asJson();

        // Required parameters for login: email (String) & password (String)
        try
        {
            // try to get those parameters from json body
            String userEmail = requestNode.findValue(CoreDataRules.fields.login.email).asText();
            String userPassword = requestNode.findValue(CoreDataRules.fields.login.password).asText();

            User loginUser = new User();
            loginUser.setEmailAddress(userEmail);
            loginUser.setPassword(userPassword);

            if(loginUser.tryLogin())
            {
                // remove user's unencrypted password for security reasons
                loginUser.setPassword("");
                // Successful login. return user details, including secret token and public key
                return ok(loginUser.toString());
            }
            else
            {
                // unsuccessful login. 401 Unauthorized!
                return unauthorized();
            }
        }
        catch (Exception e)
        {
            // The json body does not contain these parameters. 400 Bad request!
            Logger.error(e.toString());
            e.printStackTrace();
            return badRequest(e.toString() + " | " + requestNode.toString());
        }
    }

    /**
     * The controller for /auth/register
     * @return
     */
    public static Result register()
    {
        JsonNode requestNode = request().body().asJson();
        try
        {
            String userEmail = requestNode.findValue(CoreDataRules.fields.register.email).asText();
            String userPassword = requestNode.findValue(CoreDataRules.fields.register.password).asText();

            String firstName = requestNode.findValue(CoreDataRules.fields.register.firstName).asText();
            String lastName = requestNode.findValue(CoreDataRules.fields.register.lastName).asText();

            String schoolName = requestNode.findParent(CoreDataRules.fields.register.schoolName).asText();
            String departmentName = requestNode.findParent(CoreDataRules.fields.register.departmentName).asText();

            int birthday = requestNode.findValue(CoreDataRules.fields.register.birthday).asInt();

            User registerUser = new User();
            registerUser.setEmailAddress(userEmail);
            registerUser.setPassword(userPassword);
            registerUser.setFirstName(firstName);
            registerUser.setLastName(lastName);
            registerUser.setSchoolName(schoolName);     
            registerUser.setDepartmentName(departmentName);
            registerUser.setBirthday(birthday);
            registerUser.setState(User.STATE_USER);

            // try register, go through validation
            if(registerUser.register())
            {
                //
                registerUser.setPassword("");
                return ok(registerUser.toString());
            }
            else
            {
                return internalServerError("could not register");
            }
        }
        catch(Validation.BadInputException e)
        {
            return badRequest(e.toString() +"\n" + CoreUtils.getStackTrace(e));
        }
        catch(Exception e)
        {
            return internalServerError(e.toString() + "\n" + CoreUtils.getStackTrace(e));
        }
    }

    /**
     * The controller for /auth/verify
     * @param public_key
     * @param signature
     * @return
     */
    public static Result verify(String public_key, String signature)
    {
        System.out.println(request().body());
        System.out.println(request().body().asText());
        System.out.println(request().body().asJson());
        System.out.println(request().body().asJson().asText());
        try
        {
            User verifiedUser = Authentication.getAuthenticatedUser(public_key, signature, request().body().asText());
            if(verifiedUser != null)
            {
                return ok(verifiedUser.asJsonString());
            }else
            {
                return unauthorized();
            }
        }
        catch(Exception e)
        {
            return badRequest();
        }
    }

}
