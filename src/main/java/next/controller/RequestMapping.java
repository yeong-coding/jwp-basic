package next.controller;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class RequestMapping {

    private static final Logger log= LoggerFactory.getLogger(DispatcherServlet.class);

    private static Map<String, Controller> controllers=new HashMap<>();

    void initMapping() {
        controllers.put("/users/create", new CreateUserController());
        controllers.put("/users/form", new ForwardController("/users/form.jsp"));
        controllers.put("/", new HomeController());
        controllers.put("/users/login", new LoginController());
        controllers.put("/users/loginForm", new LoginController());
        controllers.put("/users/logout", new LogoutController());
        controllers.put("/users/profile", new ProfileController());
        controllers.put("/users/update", new UpdateUserController());
        controllers.put("/users/updateForm", new UpdateUserController());
        controllers.put("/users", new ListUserController());
        log.debug("Initialized Request Mapping!");
    }
    public Controller getController(String requestUrl){
        return controllers.get(requestUrl);
    }

    void put(String url, Controller controller){
        controllers.put(url, controller);
    }
}
