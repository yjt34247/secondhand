package com.example.secondhand.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


public class JdbcHelper {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://10.100.164.38:3306/secondhand_db?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
    private static final String USER = "root";
    private static final String PASSWORD = "MySQL@2025";

    // 1. 加载驱动程序
    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("加载数据库驱动失败", e);
        }
    }

    // 2. 建立连接
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // 3. 创建PreparedStatement
    public static PreparedStatement prepareStatement(Connection conn, String sql) throws SQLException {
        return conn.prepareStatement(sql);
    }

    // 3. 创建能返回主键的PreparedStatement
    public static PreparedStatement prepareStatementWithGeneratedKeys(Connection conn, String sql) throws SQLException {
        return conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    }

    // 资源关闭
    public static void close(AutoCloseable... closables) {
        for (AutoCloseable closable : closables) {
            if (closable != null) {
                try {
                    closable.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static <T> T queryForObject(String sql, Function<ResultSet, T> mapper, Object... params) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = prepareStatement(conn, sql);

            // 设置参数
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }

            rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapper.apply(rs);
            }
            return null;
        } finally {
            close(rs, pstmt, conn);
        }
    }

    public static <T> List<T> queryForList(String sql, Function<ResultSet, T> mapper, Object... params) throws SQLException {
        List<T> result = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = prepareStatement(conn, sql);

            // 设置参数
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }

            rs = pstmt.executeQuery();
            while (rs.next()) {
                result.add(mapper.apply(rs));
            }
            return result;
        } finally {
            close(rs, pstmt, conn);
        }
    }


    public static int executeUpdate(String sql, Object... params) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();
            pstmt = prepareStatement(conn, sql);

            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }

            return pstmt.executeUpdate();
        } finally {
            close(pstmt, conn);
        }
    }


    public static int executeInsertReturnKey(String sql, Object... params) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = prepareStatementWithGeneratedKeys(conn, sql);

            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
            return 0;
        } finally {
            close(rs, pstmt, conn);
        }
    }
}