
package hisab.controller;


import hisab.model.LoginModel;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;


public class LoginController implements Initializable {
    
    @FXML private TextField txtUsername;
    @FXML private TextField txtPassword;
    @FXML private RadioButton rbUser;
    @FXML private RadioButton rbAdmin;
    private String userType="";
      
    LoginModel loginModel=new LoginModel();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    // when Click on Login Buttor For logged In then This Fuction Event Is Excecuted
    public void Login(ActionEvent event) throws SQLException{
        if(loginModel.isLogin(txtUsername.getText(), txtPassword.getText(), userType)){
            JOptionPane.showMessageDialog(null, "Login Successfull");
            try {
                ((Node)event.getSource()).getScene().getWindow().hide();
                Stage primaryStage=new Stage();
                FXMLLoader loader=new FXMLLoader();
                Pane root=loader.load(getClass().getResource("/hisab/view/Home.fxml").openStream());
                Scene scene = new Scene(root);
                primaryStage.setTitle("Hisab");
                primaryStage.setScene(scene);
                primaryStage.setMaximized(true);
                primaryStage.show();
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
             JOptionPane.showMessageDialog(null, "Login Failed");
        }
    } 
    // when user select the user type like user / Admin then this  Fuction Event Is Excecuted for select the user type
    public void LoginType(ActionEvent event){
        if(rbAdmin.isSelected())
           userType += rbAdmin.getText();
        //else
         //  userType +=rbUser.getText();
    } 
    
 
    
    
}
