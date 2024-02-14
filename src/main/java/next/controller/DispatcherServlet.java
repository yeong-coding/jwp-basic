package next.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {

    private static final long serialVersionUID=1L;
    private static final Logger log= LoggerFactory.getLogger(DispatcherServlet.class);
    private static final String DEFAULT_REDIRECT_PREFIX="redirect:";
    private RequestMapping rm;

    @Override
    public void init() throws ServletException {
        rm=new RequestMapping();
        rm.initMapping();
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {

        HttpServletRequest request=(HttpServletRequest) req;
        HttpServletResponse response=(HttpServletResponse) res;
        Controller controller=rm.getController(request.getRequestURI());

        try {
            String result=controller.execute(request, response);
            move(result, request, response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void move(String viewName, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if(viewName.startsWith(DEFAULT_REDIRECT_PREFIX)){
            redirect(req, resp, viewName);
        }
        else {
            forward(req, resp, viewName);
        }
    }

    public void redirect(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {

        int idx=url.indexOf(":");
        url=url.substring(idx+1);
        log.info("redirect url: {}", url);
        response.sendRedirect(url);
    }

    public void forward(HttpServletRequest request, HttpServletResponse response, String url) throws ServletException, IOException {

        RequestDispatcher rd = request.getRequestDispatcher(url);
        rd.forward(request, response);

    }
}
