package controllers;

import co.uberdev.ultimateorganizer.core.CoreTask;
import play.*;
import play.mvc.*;

public class Application extends Controller
{

    public static Result index()
    {
        // Test importing from tuo-core
        CoreTask task = new CoreTask();

        Logger.debug("SA");
        return ok("Hello world.");
    }

}
