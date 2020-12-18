package com.southwind.repository.impl;

import com.southwind.entity.Reader;
import com.southwind.repository.ReaderRepository;
import com.southwind.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReaderRepositoryImpl implements ReaderRepository {
    public Reader login(String username, String password) {
        Connection conn = JDBCUtils.getConnection();
        String sql = "select * from reader where username = ? and password = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Reader reader = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1,username);
            ps.setString(2,password);
            rs = ps.executeQuery();
            if(rs.next()){
                reader = new Reader(rs.getInt(1),rs.getString(2),rs.getString(3),
                        rs.getString(4),rs.getString(5),rs.getString(6),
                        rs.getString(7));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            JDBCUtils.release(conn, ps, rs);
        }
        return reader;

    }
}
