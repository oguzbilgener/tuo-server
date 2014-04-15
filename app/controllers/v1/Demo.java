package controllers.v1;

import co.uberdev.ultimateorganizer.core.CoreTask;
import co.uberdev.ultimateorganizer.server.models.User;
import play.*;
import play.mvc.*;

import java.util.Date;

public class Demo extends Controller
{

    public static Result index()
    {
        // Test importing from tuo-core
        User myUser = new User();
        myUser.setEmailAddress("ataalik@gmail.com");
        myUser.setFirstName("Ata Ali");
        myUser.setLastName("Kilicli");
        myUser.setPassword("123456");
        myUser.setBirthday(new Date());

        String strUser = myUser.asJsonString();

        User backUser = User.fromJson(strUser, User.class);


        return ok("this user is "+backUser.asJsonString());
    }

}
