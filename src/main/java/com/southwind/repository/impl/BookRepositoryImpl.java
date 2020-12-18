package com.southwind.repository.impl;

import com.southwind.entity.Book;
import com.southwind.entity.BookCase;
import com.southwind.repository.BookRepository;
import com.southwind.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookRepositoryImpl implements BookRepository {
    /**
     * 从数据库查询某页的图书数据
     * @param index
     * @param limit
     * @return
     */
    @Override
    public List<Book> findAll(int index, int limit) {
        Connection conn = JDBCUtils.getConnection();
        String sql = "select * from book,bookcase where book.bookcaseid = bookcase.id LIMIT ?,?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Book> bookList = new ArrayList<>();
        Book book;
        BookCase bookCase;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1,index);
            ps.setInt(2,limit);
            rs = ps.executeQuery();
            while(rs.next()){
                bookCase = new BookCase(rs.getInt(9),rs.getString(10));
                book = new Book(rs.getInt(1),rs.getString(2),rs.getString(3),
                        rs.getString(4),rs.getInt(5),rs.getDouble(6), bookCase);
                bookList.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            JDBCUtils.release(conn, ps, rs);
        }
        return bookList;
    }


    /**
     * 返回数据库book表的条目个数
     * @return  数据库book表的条目个数
     */
    @Override
    public int count() {
        Connection conn = JDBCUtils.getConnection();
        String sql = "select count(*) from book";
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 0;
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next()){
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            JDBCUtils.release(conn, ps, rs);
        }
        return count;
    }
}
