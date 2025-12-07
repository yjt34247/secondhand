package com.example.secondhand.controller;

import com.example.secondhand.entity.Item;
import com.example.secondhand.entity.User;
import com.example.secondhand.service.ItemService;
import com.example.secondhand.service.ItemServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet(name = "ItemServlet", urlPatterns = {
        "/item/publish",
        "/item/edit",
        "/item/update",
        "/item/delete",
        "/item/detail"
})
public class ItemServlet extends HttpServlet {

    private final ItemService itemService;

    public ItemServlet() {
        this.itemService = new ItemServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();

        try {
            switch (path) {
                case "/item/publish":
                    showPublishForm(request, response);
                    break;
                case "/item/edit":
                    showEditForm(request, response);
                    break;
                case "/item/detail":
                    showDetail(request, response);
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
                case "/item/publish":
                    publishItem(request, response);
                    break;
                case "/item/update":
                    updateItem(request, response);
                    break;
                case "/item/delete":
                    deleteItem(request, response);
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

    private void showPublishForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/user/login.jsp");
            return;
        }
        request.getRequestDispatcher("/item/publish.jsp").forward(request, response);
    }

    private void publishItem(HttpServletRequest request, HttpServletResponse response)
            throws Exception, IOException, ServletException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/user/login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");

        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String priceStr = request.getParameter("price");
        String category = request.getParameter("category");

        BigDecimal price = new BigDecimal(priceStr);

        Item item = new Item();
        item.setUserId(user.getId());
        item.setTitle(title);
        item.setDescription(description);
        item.setPrice(price);
        item.setCategory(category);
        item.setStatus("在售");

        int itemId = itemService.publishItem(item);

        if (itemId > 0) {
            response.sendRedirect(request.getContextPath() + "/item/detail?id=" + itemId);
        } else {
            request.setAttribute("error", "发布失败");
            request.getRequestDispatcher("/item/publish.jsp").forward(request, response);
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws Exception, IOException, ServletException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/user/login.jsp");
            return;
        }

        String idStr = request.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        int id = Integer.parseInt(idStr);
        var itemOptional = itemService.getItemById(id);

        if (itemOptional.isPresent()) {
            Item item = itemOptional.get();
            User user = (User) session.getAttribute("user");

            // 检查权限：只有物品的所有者可以编辑
            if (item.getUserId().equals(user.getId())) {
                request.setAttribute("item", item);
                request.getRequestDispatcher("/item/edit.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "没有权限编辑此物品");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("error", "物品不存在");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    private void updateItem(HttpServletRequest request, HttpServletResponse response)
            throws Exception, IOException, ServletException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/user/login.jsp");
            return;
        }

        String idStr = request.getParameter("id");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String priceStr = request.getParameter("price");
        String category = request.getParameter("category");
        String status = request.getParameter("status");

        int id = Integer.parseInt(idStr);
        BigDecimal price = new BigDecimal(priceStr);

        // 先获取原物品信息
        var itemOptional = itemService.getItemById(id);
        if (!itemOptional.isPresent()) {
            request.setAttribute("error", "物品不存在");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        Item item = itemOptional.get();
        User user = (User) session.getAttribute("user");

        // 检查权限
        if (!item.getUserId().equals(user.getId())) {
            request.setAttribute("error", "没有权限编辑此物品");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        // 更新物品信息
        item.setTitle(title);
        item.setDescription(description);
        item.setPrice(price);
        item.setCategory(category);
        item.setStatus(status);

        boolean success = itemService.updateItem(item);

        if (success) {
            response.sendRedirect(request.getContextPath() + "/item/detail?id=" + id);
        } else {
            request.setAttribute("error", "更新失败");
            request.getRequestDispatcher("/item/edit.jsp").forward(request, response);
        }
    }

    private void deleteItem(HttpServletRequest request, HttpServletResponse response)
            throws Exception, IOException, ServletException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/user/login.jsp");
            return;
        }

        String idStr = request.getParameter("id");
        int id = Integer.parseInt(idStr);

        // 先获取原物品信息
        var itemOptional = itemService.getItemById(id);
        if (!itemOptional.isPresent()) {
            request.setAttribute("error", "物品不存在");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        Item item = itemOptional.get();
        User user = (User) session.getAttribute("user");

        // 检查权限
        if (!item.getUserId().equals(user.getId())) {
            request.setAttribute("error", "没有权限删除此物品");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        boolean success = itemService.deleteItem(id);

        if (success) {
            response.sendRedirect(request.getContextPath() + "/index"); // 删除后返回首页
        } else {
            request.setAttribute("error", "删除失败");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    private void showDetail(HttpServletRequest request, HttpServletResponse response)
            throws Exception, IOException, ServletException {
        HttpSession session = request.getSession(false);

        // 验证是否登录
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/user/login.jsp");
            return;
        }

        String idStr = request.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        int id = Integer.parseInt(idStr);
        var itemOptional = itemService.getItemById(id);

        if (itemOptional.isPresent()) {
            request.setAttribute("item", itemOptional.get());
            request.getRequestDispatcher("/item/detail.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "物品不存在");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

}