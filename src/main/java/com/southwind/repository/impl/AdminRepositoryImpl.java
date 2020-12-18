package com.southwind.repository.impl;

import com.southwind.entity.Admin;
import com.southwind.entity.Reader;
import com.southwind.repository.AdminRepository;
import com.southwind.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminRepositoryImpl implements AdminRepository {

    public Admin login(String username, String password) {
        Connection conn = JDBCUtils.getConnection();
        String sql = "select * from bookadmin where username = ? and password = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Admin admin = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1,username);
            ps.setString(2,password);
            rs = ps.executeQuery();
            if(rs.next()){
                admin = new Admin(rs.getInt(1),rs.getString(2),rs.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            JDBCUtils.release(conn, ps, rs);
        }
        return admin;
    }
}
