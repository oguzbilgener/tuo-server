package controllers.v1;

import co.uberdev.ultimateorganizer.core.CoreTask;
import play.*;
import play.mvc.*;

public class Application extends Controller
{

    public static Result index()
    {
        // Test importing from tuo-core


   
        return ok("Hello world. lolo");
    }

}
