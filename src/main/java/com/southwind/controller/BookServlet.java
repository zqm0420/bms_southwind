package com.southwind.controller;

import com.southwind.entity.Book;
import com.southwind.entity.Borrow;
import com.southwind.entity.Reader;
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

@WebServlet("/book")
public class BookServlet extends HttpServlet {
    private BookService bookService = new BookServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if(method==null){
            method = "findAll";
        }
        HttpSession httpSession = req.getSession();
        Reader reader = (Reader) httpSession.getAttribute("reader");

        switch(method){
            case "findAll":
                String pageStr = req.getParameter("page");
                Integer page = Integer.parseInt(pageStr);
                List<Book> bookList = bookService.findAll(page);
                req.setAttribute("books", bookList);
                req.setAttribute("dataPrePage", 5);
                req.setAttribute("currentPage", page);
                req.setAttribute("pages", bookService.getPages());
                req.getRequestDispatcher("index.jsp").forward(req, resp);
                break;
            case "addBorrow":
                //点击借阅后将此次借书记录写入数据库
                Integer bookID = Integer.parseInt(req.getParameter("bookid"));
                Integer readerID = reader.getId();
                bookService.addBorrow(bookID, readerID);
                resp.sendRedirect("/book?method=findAllBorrow&page=1");
                break;

            case "findAllBorrow":
                //将本读者的借书记录返回到jsp页面上
                pageStr = req.getParameter("page");
                page = Integer.parseInt(pageStr);
                List<Borrow> borrowList = bookService.findAllByReaderID(reader.getId(), page);
                req.setAttribute("borrowList", borrowList);
                req.setAttribute("currentPage", page);
                req.setAttribute("dataPrePage", 5);
                req.setAttribute("pages", bookService.getBorrowPages(reader.getId()));
                req.getRequestDispatcher("borrow.jsp").forward(req, resp);
                break;

        }




    }
}
