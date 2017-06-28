package main.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by 71903 on 2017/5/14.
 */
public class DBConnection {
    private String DBDriver;
    private String DBURL;
    private String DBUser;
    private String DBPass;
    private Connection connection=null;
    private PreparedStatement preparedStatement=null;

    public DBConnection(String DBDriver, String DBURL, String DBUser, String DBPass) {
        this.DBDriver = DBDriver;
        this.DBURL = DBURL;
        this.DBUser = DBUser;
        this.DBPass = DBPass;
        try {
            Class.forName(this.DBDriver);
//            System.out.println("DBDriver装载完成");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            this.connection= DriverManager.getConnection(this.DBURL,this.DBUser,this.DBPass);
            this.connection.setAutoCommit(false);
//            System.out.println("DB连接成功");
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void setPreparedStatement(String sql) {
        try{
            this.preparedStatement=this.connection.prepareStatement(sql);
//            System.out.println("sql语句："+this.preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection(){
        return this.connection;
    }

    public PreparedStatement getPreparedStatement(){
        return this.preparedStatement;
    }

    public void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void rollback(){
        try {
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection(){
        try {
            this.connection.close();
            this.preparedStatement.close();
//            System.out.println("DB连接关闭");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String toString() {
        return "DBDriver='" + DBDriver + '\n' + ", DBURL='" + DBURL + '\n' + ", DBUser='" + DBUser + '\n' + ", DBPass='" + DBPass+ '\n';
    }


}
