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

@WebServlet(name = "SearchServlet", urlPatterns = "/search")
public class SearchServlet extends HttpServlet {

    private final ItemService itemService;

    public SearchServlet() {
        this.itemService = new ItemServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // 验证是否登录
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/user/login.jsp");
            return;
        }

        try {
            String keyword = request.getParameter("keyword");

            if (keyword != null && !keyword.trim().isEmpty()) {
                // 调用Service层进行模糊搜索
                var items = itemService.searchItems(keyword);
                request.setAttribute("items", items);
                request.setAttribute("keyword", keyword);
            } else {
                // 如果没有关键词，显示所有物品
                var items = itemService.getAllItems();
                request.setAttribute("items", items);
            }

            request.getRequestDispatcher("/search/result.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}