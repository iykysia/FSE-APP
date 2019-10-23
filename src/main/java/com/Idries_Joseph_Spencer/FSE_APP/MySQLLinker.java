import java.sql.Connection;
import java.sql.DriverManager;
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

        
        } 
        catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
}