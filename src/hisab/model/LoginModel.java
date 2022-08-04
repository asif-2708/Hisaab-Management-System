package hisab.model;

import hisab.DatabaseHandler;
import java.sql.*;
import javax.swing.JOptionPane;




public class LoginModel {
    Connection connection=null;
    //Constructor automatically call using loginModel object which create in Login Controller to Check whether Connection is successfull or not if not then provide error mssage box and system will exit.
    public LoginModel() {
        connection=DatabaseHandler.getConection();
        if(connection==null){
            JOptionPane.showMessageDialog(null,"Opps!!! Database Is Not Connected" );
            System.exit(0);
        }
    }    
    //it will call for Checking Login detail ( it is called by Login actin event Method)
    public boolean isLogin(String user,String pass,String userType) throws SQLException{
        PreparedStatement preparedStatement=null;
        ResultSet resultset=null;
        String query="select * from user_master where user_name=? and user_password=?";
        try {        
            preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, pass);
            resultset=preparedStatement.executeQuery();
            if(resultset.next()){
                if(resultset.getString("user_type").equals(userType)){
                    return true;
                }else{
                    return false;
                }
            }
            else{
                return false;
            }
        } catch (Exception ex) {         
            return false;
            
        } finally{
            preparedStatement.close();
            resultset.close();
        }
       
    }
        
}

