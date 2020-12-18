package com.southwind.controller;

import com.southwind.entity.Admin;
import com.southwind.entity.Borrow;
import com.southwind.service.BookService;
import com.southwind.service.impl.BookServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    BookService bookService = new BookServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if(method==null){
            method = "findAllBorrow";
        }
        HttpSession httpSession = req.getSession();

        switch(method){
            case "findAllBorrow":
                String pageStr = req.getParameter("page");
                String stateStr = req.getParameter("state");
                Integer page = Integer.parseInt(pageStr);
                Integer state = Integer.parseInt(stateStr);
                List<Borrow> borrowList = bookService.findAllBorrowByState(state, page);
                req.setAttribute("borrowList", borrowList);
                req.setAttribute("currentPage", page);
                req.setAttribute("dataPrePage", 5);
                req.setAttribute("pages", bookService.getBorrowPagesByState(state));
                req.getRequestDispatcher("admin.jsp").forward(req, resp);
                break;
            case "handle":
                String borrowIDStr = req.getParameter("borrowID");
                stateStr = req.getParameter("state");
                Integer borrowID = Integer.parseInt(borrowIDStr);
                state = Integer.parseInt(stateStr);
                Admin admin = (Admin) httpSession.getAttribute("admin");
                //得到借阅ID、相应的操作、管理员ID之后，将操作写入数据库
                bookService.handleBorrowState(borrowID, state, admin.getId());
                //重定向到admin的findAllBorrow方法
                resp.sendRedirect("/admin?method=findAllBorrow&page=1&state=0");
        }
    }
}
