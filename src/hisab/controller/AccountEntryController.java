
package hisab.controller;
/*
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import hisab.DatabaseHandler;
import hisab.UserHandler;
import hisab.model.AccountEntryModel;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Administrator
 */
/*
public class AccountEntryController implements Initializable {

    @FXML
    private JFXTextField txtFirstName;
    @FXML
    private JFXTextField txtLastName;
    @FXML
    private JFXTextField txtMobile;
    @FXML
    private JFXComboBox<String> cmbUserType;
    @FXML
    private Button btnSaveUser;
    @FXML
    private Button btnClearUser;
    @FXML
    private Button btnUpdateUser;
    @FXML
    private TableView<AccountEntryModel> tableUserRegistration;
    @FXML
    private TableColumn<AccountEntryModel, String> columFirstName;
    @FXML
    private TableColumn<AccountEntryModel, String> columLastName;
    @FXML
    private TableColumn<AccountEntryModel, String> columMobile;
    @FXML
    private TableColumn<AccountEntryModel, String> columUserType;
    private String query;
    private ResultSet rs;
     ObservableList<AccountEntryModel> list=FXCollections.observableArrayList();
    @FXML
    private MenuItem editContextMenu;
    @FXML
    private MenuItem deleteContextMenu;
    private String selectedUserName;

    /**
     * Initializes the controller class.
     */
/*
    @Override
    public void initialize(URL url, ResourceBundle rb) {
           cmbUserTypeFill();
          btnUpdateUser.setVisible(false);
          tableUserRegistration.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
          tableUserRegistration.setEditable(true);
         // tableUserRegistration.getSelectionModel().cellSelectionEnabledProperty().set(true);
          updateItemBrandTable();
    }    
    private void updateItemBrandTable() {
        tableUserRegistration.getItems().clear();
        columFirstName.setCellValueFactory(new PropertyValueFactory<AccountEntryModel,String>("fistName"));
        columLastName.setCellValueFactory(new PropertyValueFactory<AccountEntryModel,String>("lastName"));
        columMobile.setCellValueFactory(new PropertyValueFactory<AccountEntryModel,String>("mobile"));
        
        columUserType.setCellValueFactory(new PropertyValueFactory<AccountEntryModel,String>("userType"));
        query="select * from user_master ";
        rs=DatabaseHandler.selectData(query);
        try {
            while(rs.next()){
                list.add(new AccountEntryModel(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5))
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserRegistrationController.class.getName()).log(Level.SEVERE, null, ex);
        }  
        tableUserRegistration.setItems(list);
    }

    @FXML
    private void saveUserRegistration(ActionEvent event) {
        
        if(validationUserRegistration()){
        
            query="insert into user_master values(null,'"+txtFirstName.getText()+"','"+txtLastName.getText()+"','"+txtMobile.getText()+"','"+cmbUserType.getSelectionModel().getSelectedItem()+"')";
            if(DatabaseHandler.insertData(query))
            {
               Alert alert=new Alert(Alert.AlertType.INFORMATION);
               alert.setContentText("Data Saved Successfully");
               updateItemBrandTable();
               clearAll();
               
            }
            else{
                 UserHandler.alertConfirmation("saveAlertFailed"); 
            }
        }
      
        
    
    }

    @FXML
    private void clearUserRegistration(ActionEvent event) {
        clearAll();
        updateItemBrandTable();
    }

    @FXML
    private void updateUserRegistration(ActionEvent event) {
        if(validationUserRegistration()){
            
            
            query="update user_master set user_first_name='"+txtFirstName.getText()+"',user_last_name='"+txtLastName.getText()+"',mobile='"+txtMobile.getText()+"',user_type='"+cmbUserType.getSelectionModel().getSelectedItem()+"' where user_name='"+selectedUserName+"'";
            if(DatabaseHandler.updateData(query))
            {
                updateItemBrandTable();
                clearAll();
                 Alert alert=new Alert(Alert.AlertType.INFORMATION);
                 alert.setContentText("Data Updated Successfully !!");
            }
            else{
                   Alert alert=new Alert(Alert.AlertType.WARNING);
                 alert.setContentText("Data Updated Failed!!");
            }
            
        }
        
    }
    
    
    
    
    
     // clear All Fields and Reset All The Setting when Save Delete or Find operation
    private void clearAll() {
        btnSaveUser.setVisible(true);
        btnUpdateUser.setVisible(false);
        txtFirstName.clear();
        txtLastName.clear();
        txtMobile.clear();
        cmbUserType.setItems(null);
        cmbUserTypeFill();
              
    }

    private void cmbUserTypeFill() {
        ObservableList<String> listUserType=FXCollections.observableArrayList();
        listUserType.clear();
      listUserType.add("Supplier");
        listUserType.add("Customer");
        cmbUserType.setItems(listUserType);
    }

    private boolean validationUserRegistration() {
        if(txtFirstName.getText().isEmpty() || txtLastName.getText().isEmpty() || txtMobile.getText().isEmpty()  ){
            UserHandler.alertConfirmation("validation");         
        }
        else if(!txtFirstName.getText().matches("[a-zA-Z]*")|| !txtLastName.getText().matches("[a-zA-Z]*"))
        {
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please Enter Correct Name. !!");
            alert.showAndWait();
        }
        else if(txtMobile.getLength()!=10 || !txtMobile.getText().matches("[0-9]*")){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please Enter Correct Mobile No. !!");
            alert.showAndWait();
        }
        else if(cmbUserType.getSelectionModel().isEmpty()){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please Select User Type !!");
            alert.showAndWait();
        }
        else{
            return true;
        }    
        
        return false;   
    }

    @FXML
    private void editUserRegistration(ActionEvent event) {
        btnSaveUser.setVisible(false);
        btnUpdateUser.setVisible(true);
        txtFirstName.setText(tableUserRegistration.getSelectionModel().getSelectedItem().getFistName());
        txtLastName.setText(tableUserRegistration.getSelectionModel().getSelectedItem().getLastName());
        txtMobile.setText(tableUserRegistration.getSelectionModel().getSelectedItem().getMobile());
        cmbUserType.getSelectionModel().select(tableUserRegistration.getSelectionModel().getSelectedItem().getUserType());
    }

    @FXML
    private void deleteUserRegistration(ActionEvent event) {
        ObservableList<AccountEntryModel> selectedUserList=FXCollections.observableArrayList();
        selectedUserList.addAll(tableUserRegistration.getSelectionModel().getSelectedItems());
        //call Custom Mathod For Confirmation Dialog With passing Total Number Of Selected record 
        Optional<ButtonType> buttonChoice=UserHandler.alertConfirmation(selectedUserList.size(),"deleteAlert");
        if(buttonChoice.get()== ButtonType.OK){
            for(int i=0;i<selectedUserList.size();i++){
             query="delete from user_master where user_name='"+selectedUserList.get(i).getFistName()+"'";
                DatabaseHandler.removeData(query);                         
            }
        }
        updateItemBrandTable();
    }
}
*/