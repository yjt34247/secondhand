package com.example.secondhand.controller;

import com.example.secondhand.entity.User;
import com.example.secondhand.service.UserService;
import com.example.secondhand.service.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "UserServlet", urlPatterns = {
        "/user/register",
        "/user/login",
        "/user/logout"
})
public class UserServlet extends HttpServlet {

    private final UserService userService;

    public UserServlet() {
        this.userService = new UserServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();

        try {
            switch (path) {
                case "/user/logout":
                    logout(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();

        try {
            switch (path) {
                case "/user/register":
                    register(request, response);
                    break;
                case "/user/login":
                    login(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    private void register(HttpServletRequest request, HttpServletResponse response)
            throws Exception, IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhone(phone);

        int userId = userService.register(user);

        if (userId > 0) {
            // 注册成功后自动登录
            user.setId(userId);
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            response.sendRedirect(request.getContextPath() + "/index");
        } else {
            request.setAttribute("error", "注册失败");
            request.getRequestDispatcher("/user/register.jsp").forward(request, response);
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response)
            throws Exception, IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        var userOptional = userService.login(username, password);

        if (userOptional.isPresent()) {
            HttpSession session = request.getSession();
            session.setAttribute("user", userOptional.get());
            response.sendRedirect(request.getContextPath() + "/index");
        } else {
            request.setAttribute("error", "用户名或密码错误");
            request.getRequestDispatcher("/user/login.jsp").forward(request, response);
        }
    }

    private void logout(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }
}