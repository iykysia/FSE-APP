
package com.Idries_Joseph_Spencer.FSE_APP;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLLinker{
    public static ResultSet ConnectAndQuery(String database, String user, String password, String query){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+ database +"?useSSL=false&serverTimezone=UTC", user,password);
            Statement stmt=conn.createStatement(); 
            ResultSet rs = stmt.executeQuery(query);             
            System.out.println(rs.toString());
            conn.close();
            return rs;
        } 
        catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return null;
        }
    }
    public static int ConnectAndExecute(String database, String user, String password, String statment){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+ database +"?useSSL=false&serverTimezone=UTC", user,password);
            PreparedStatement prstmt = conn.prepareStatement(statment);
            return prstmt.executeUpdate();
        } 
        catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return -1;
        }
    }
}