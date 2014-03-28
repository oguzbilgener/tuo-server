package controllers;

import play.*;
import play.mvc.*;

public class Application extends Controller {

    public static Result index() {
        return ok("Hello world.");
    }

}
