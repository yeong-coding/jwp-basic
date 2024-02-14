package next.controller;

import core.db.DataBase;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ListUserController implements Controller {
    private static final long serialVersionUID = 1L;

    protected String doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return "/users/loginForm";
        }

        req.setAttribute("users", DataBase.findAll());

        return "/user/list.jsp";
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return doGet(request, response);
    }
}
