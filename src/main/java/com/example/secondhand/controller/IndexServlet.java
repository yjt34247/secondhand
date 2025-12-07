package com.example.secondhand.controller;

import com.example.secondhand.service.ItemService;
import com.example.secondhand.service.ItemServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "IndexServlet", urlPatterns = {"/index", "/"})
public class IndexServlet extends HttpServlet {

    private final ItemService itemService;

    public IndexServlet() {
        this.itemService = new ItemServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        try {
            if (session == null || session.getAttribute("user") == null) {
                // 未登录，不显示物品，只显示登录/注册提示
                request.setAttribute("showItems", false);
                request.setAttribute("message", "请先登录以查看二手物品信息");
            } else {
                // 已登录，加载所有在售物品
                var items = itemService.getAllItems();
                request.setAttribute("items", items);
                request.setAttribute("showItems", true);
            }

            request.getRequestDispatcher("/index.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}