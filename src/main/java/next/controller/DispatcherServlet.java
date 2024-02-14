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

    public static final Logger log= LoggerFactory.getLogger(DispatcherServlet.class);

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {

        HttpServletRequest request=(HttpServletRequest) req;
        HttpServletResponse response=(HttpServletResponse) res;
        Controller controller=RequestMapping.getController(request.getRequestURI());

        try {
            String result=controller.execute(request, response);
            if(result.startsWith("redirect:")){
                redirect(request, response, result);
            }
            else {
                forward(request, response, result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
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
