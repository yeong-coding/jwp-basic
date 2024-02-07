package next.web;

import core.db.DataBase;
import next.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/user/login")
public class LoginUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/user/login.html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId=req.getParameter("userId");
        String password=req.getParameter("password");
        User user=DataBase.findUserById(userId);

        if(user!=null && password.equals(user.getPassword())){
            HttpSession session=req.getSession();
            session.setAttribute("user", user);
            RequestDispatcher rd=req.getRequestDispatcher("/index.jsp");
            rd.forward(req, resp);
        }

        resp.sendRedirect("/index.jsp");
    }
}
