package com.southwind.repository.impl;

import com.southwind.entity.Book;
import com.southwind.entity.Borrow;
import com.southwind.entity.Reader;
import com.southwind.repository.BorrowRepository;
import com.southwind.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BorrowRepositoryImpl implements BorrowRepository {
    @Override
    public void addBorrow(Integer bookID, Integer readerID, String borrowTime, String returnTime, Integer adminID, Integer state) {
        Connection conn = JDBCUtils.getConnection();
        String sql = "INSERT INTO borrow(bookid, readerid, borrowtime, returntime, state) values(?,?,?,?,0)";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1,bookID);
            ps.setInt(2, readerID);
            ps.setString(3,borrowTime);
            ps.setString(4,returnTime);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(conn, ps, null);
        }
    }

    @Override
    public int count(Integer readerID) {
        Connection conn = JDBCUtils.getConnection();
        String sql = "select count(*) from book b, borrow br,reader r where br.readerid=? and b.id = br.bookid and br.readerid = r.id";
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 0;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, readerID);
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(conn, ps, rs);
        }
        return count;
    }

    @Override
    public List<Borrow> findAllByState(Integer state, int index, int limit) {
        Connection conn = JDBCUtils.getConnection();
        String sql = "select br.id,b.name,b.author,b.publish, r.name,r.tel,r.cardid, " +
                "br.borrowtime,br.returntime,br.state from book b,borrow br,reader r " +
                "where br.state=? and b.id = br.bookid and br.readerid = r.id LIMIT ?,?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Book book = null;
        Borrow borrow = null;
        Reader reader = null;
        List<Borrow> borrowList = new ArrayList<>();

        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, state);
            ps.setInt(2, index);
            ps.setInt(3, limit);
            rs = ps.executeQuery();
            while(rs.next()){
                book = new Book( rs.getString(2),
                        rs.getString(3),rs.getString(4));
                reader = new Reader(rs.getString(5), rs.getString(6),
                        rs.getString(7));
                borrow = new Borrow(rs.getInt(1), book, reader,
                        rs.getString(8),rs.getString(9),rs.getInt(10));
                borrowList.add(borrow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(conn, ps, rs);
        }
        return borrowList;
    }

    @Override
    public int countByState(Integer state) {
        Connection conn = JDBCUtils.getConnection();
        String sql = "select count(*) from book b, borrow br,reader r where br.state=? and b.id = br.bookid and br.readerid = r.id";
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 0;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, state);
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(conn, ps, rs);
        }
        return count;
    }

    @Override
    public void handleBorrowState(Integer borrowID, Integer state, Integer adminID) {
        Connection conn = JDBCUtils.getConnection();
        String sql = "UPDATE borrow SET state=?, adminid=? where id=?";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, state);
            ps.setInt(2, adminID);
            ps.setInt(3, borrowID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(conn, ps, null);
        }


    }


    @Override
    public List<Borrow> findAllByReaderID(Integer readerID, int index, int limit) {
        Connection conn = JDBCUtils.getConnection();
        String sql = "select br.id,b.name,b.author,b.publish, r.name,r.tel,r.cardid, " +
                "br.borrowtime,br.returntime,br.state from book b,borrow br,reader r " +
                "where br.readerid=? and b.id = br.bookid and br.readerid = r.id LIMIT ?,?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Book book = null;
        Borrow borrow = null;
        Reader reader = null;
        List<Borrow> borrowList = new ArrayList<>();

        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1,readerID);
            ps.setInt(2, index);
            ps.setInt(3, limit);
            rs = ps.executeQuery();
            while(rs.next()){
                book = new Book( rs.getString(2),
                        rs.getString(3),rs.getString(4));
                reader = new Reader(rs.getString(5), rs.getString(6),
                        rs.getString(7));
                borrow = new Borrow(rs.getInt(1), book, reader,
                        rs.getString(8),rs.getString(9),rs.getInt(10));
                borrowList.add(borrow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(conn, ps, rs);
        }
        return borrowList;
    }



}
