
package hisab;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class DatabaseHandler {
    
    static PreparedStatement preparedStatement=null;
    static Connection connection=null;
    static ResultSet resultset=null;
    
    public static Connection getConection() {
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url="jdbc:mysql://localhost:3306/estimate_db";
            connection=DriverManager.getConnection(url,"root","");
            return connection;         
            
            
            
        } catch (ClassNotFoundException | SQLException ex) {          
            return null;
        }
          
    }
    
    public static boolean insertData(String query){
        try {
            preparedStatement=connection.prepareStatement(query);
            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {       
            //Call getError() method of ExcepionHandler Class To set Our Custome Error Message 
            JOptionPane.showMessageDialog(null,ExceptionHandler.getError(ex));  
            return false;
        }
        return true;
    } 
    
    
    public static ResultSet selectData(String query){
        try {
            preparedStatement=connection.prepareStatement(query);
            resultset=preparedStatement.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultset;
    } 
    
     public static boolean removeData(String query){
        try {
            preparedStatement=connection.prepareStatement(query);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {       
            
            //Call getError() method of ExcepionHandler Class To set Our Custome Error Message 
            JOptionPane.showMessageDialog(null,ExceptionHandler.getError(ex));
            
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null,e.toString());
        }
        
        return true;
    } 
    
     public static boolean updateData(String query){
        try {
            preparedStatement=connection.prepareStatement(query);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {       
            
            //Call getError() method of ExcepionHandler Class To set Our Custome Error Message 
            JOptionPane.showMessageDialog(null,ExceptionHandler.getError(ex));       
        }
        return true;
    } 
    
}
