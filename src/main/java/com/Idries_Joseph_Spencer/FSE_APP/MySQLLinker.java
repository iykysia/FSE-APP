
package com.Idries_Joseph_Spencer.FSE_APP;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLLinker{

    //TODO
    //  this has to be created to secure the system in order to prevent others from using it to query unacceptable stuff
    // keep this private as a means of preventing others from doing work in it
    private static Connection Connect (String database, String user, String password){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+ database +"?useSSL=false&serverTimezone=UTC", user,password);
            conn.close();
            return conn;
        } 
        catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return null;
        }
    }

    public static interface PreparedStatementLambda{
        PreparedStatement PrepareAStatment(Connection conn);
    }

    private static int PreparedStatementExecute(String database, String user, String password, PreparedStatementLambda prepare){
        int ret = -1;
       Connection conn =  Connect (database, user, password);
       if(!conn.equals(null))
       {
           return -1;
       }
       try{
           conn.setAutoCommit(false);
            PreparedStatement stmt = prepare.PrepareAStatment(conn);
            ret = stmt.executeUpdate();
            conn.close();
       }
       catch(Exception e){

       }
       return ret;

    } 
    private static ResultSet PreparedStatementQuery(String database, String user, String password, PreparedStatementLambda prepare){
        ResultSet  ret = null;
        Connection  conn = Connect (database, user, password);
        if(!conn.equals(null))
       {
           return null;
       }
       try{
           conn.setAutoCommit(false);
            PreparedStatement stmt = prepare.PrepareAStatment(conn);
            ret = stmt.executeQuery();
            conn.close();
       }
       catch(Exception e){

       }

        return ret;
    } 

    @Deprecated
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

    @Deprecated
    public static int ConnectAndExecute(String database, String user, String password, String statment){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+ database +"?useSSL=false&serverTimezone=UTC", user,password);
            PreparedStatement prstmt = conn.prepareStatement(statment);
            conn.close();
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