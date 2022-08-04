
package hisab.controller;

import com.jfoenix.controls.JFXButton;
import hisab.DatabaseHandler;
import hisab.UserHandler;
import hisab.model.AccountCategoryModel;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;

public class AccountCategoryController implements Initializable {

    String query=null;
    ResultSet rs=null;
    @FXML
    private TableView<AccountCategoryModel> tableAccountCategory;
    @FXML
    private TableColumn<AccountCategoryModel, Integer> accountCategoryIdColum;
    @FXML
    private TableColumn<AccountCategoryModel, String> accountCategoryNameColum;
    ObservableList<AccountCategoryModel> list=FXCollections.observableArrayList();
    
    @FXML 
    private TextField txtAccountCategory;
    private TextField txtFindAccountCategory;
    @FXML
    private Button btnSaveAccountCategory;
    @FXML
    private Button btnUpdateAccountCategory;
   
    private int selectedRowId;  // for Check The Id of selected Id Colum of the table
    @FXML
    private MenuItem editContextMenu;
    @FXML
    private MenuItem deleteContextMenu;
    @FXML
    private JFXButton btnRefreshAccountCategory;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
             btnUpdateAccountCategory.setVisible(false);
             tableAccountCategory.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
             tableAccountCategory.setEditable(true);
             //tableAccountCategory.getSelectionModel().cellSelectionEnabledProperty().set(true);
            
            updateAccountCategoryTable();
        } catch (SQLException ex) {
            Logger.getLogger(AccountCategoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    

    @FXML
    private void saveAccountCategory(ActionEvent event) throws SQLException {
        query="insert into account_category values(null,'"+txtAccountCategory.getText()+"')";
        if(DatabaseHandler.insertData(query)){
        updateAccountCategoryTable();
        }
        else{
            JOptionPane.showMessageDialog(null, "Data Not Saved! Please follow Description");
        }
        clearAll();
    }
    
    
    @FXML
    private void removeAccountCategory(ActionEvent event) throws SQLException {
        ObservableList<AccountCategoryModel> listId= FXCollections.observableArrayList();    
        listId.addAll(tableAccountCategory.getSelectionModel().getSelectedItems());
        
        //call Custom Mathod For Confirmation Dialog With passing Total Number Of Selected record 
        Optional<ButtonType> buttonChoice=UserHandler.alertConfirmation(listId.size(),"deleteAlert");
        if(buttonChoice.get()== ButtonType.OK){
            for(int i=0;i<listId.size();i++){
                query="delete from account_category where account_category_id="+listId.get(i).getAccountCategoryId()+"";
                DatabaseHandler.removeData(query);                         
            }
        }
        updateAccountCategoryTable();
    }
      
    @FXML
    private void editAccountCategory(ActionEvent event) throws SQLException {
        AccountCategoryModel accountCategoryModel=(AccountCategoryModel) tableAccountCategory.getSelectionModel().getSelectedItem();
     
        txtAccountCategory.setText(accountCategoryModel.getAccountCategoryName());
        btnSaveAccountCategory.setVisible(false);
        btnUpdateAccountCategory.setVisible(true);
        selectedRowId=accountCategoryModel.getAccountCategoryId(); //This value is used into UpdateAccountCategory Function to dwnote which row user want to update
             
    }
    
    
    @FXML
    private void updateAccountCategory(ActionEvent event) throws SQLException {
        
        //call Custom Mathod For Confirmation Dialog With passing Total Number Of Selected record 
        Optional<ButtonType> buttonChoice=UserHandler.alertConfirmation("updateAlert");
        if(buttonChoice.get()== ButtonType.OK){          
                query="update account_category set account_category_name='"+txtAccountCategory.getText()+"' where account_category_id="+selectedRowId+"";
                DatabaseHandler.updateData(query);                         
        }
        
        updateAccountCategoryTable();
        clearAll();     
    }
    
   
    
    
    @FXML
    private void refreshAccountCategory(ActionEvent event) throws SQLException {
        clearAll();
        updateAccountCategoryTable();
    }
    
    
 
     
    
    
    
    // Populate the Table Viewby fatching Data From Database
    private void updateAccountCategoryTable() throws SQLException {
        tableAccountCategory.getItems().clear();
        accountCategoryIdColum.setCellValueFactory(new PropertyValueFactory<AccountCategoryModel,Integer>("accountCategoryId"));
        accountCategoryNameColum.setCellValueFactory(new PropertyValueFactory<AccountCategoryModel,String>("accountCategoryName"));
        
        query="select * from account_category";
        
        rs=DatabaseHandler.selectData(query);
        while(rs.next()){
            try {
                list.add(
                        new AccountCategoryModel(Integer.parseInt(rs.getString("account_category_id")),rs.getString("account_category_name"))
                );
            } catch (SQLException ex) {
                Logger.getLogger(AccountCategoryController.class.getName()).log(Level.SEVERE, null, ex);
            }
            tableAccountCategory.setItems(list);
            
        }
        
    }
    
    
    
   
    // clear All Fields and Reset All The Setting when Save Delete or Find operation
    private void clearAll() {
        btnSaveAccountCategory.setVisible(true);
        btnUpdateAccountCategory.setVisible(false);
        txtAccountCategory.clear();
        txtFindAccountCategory.clear();
        selectedRowId=0;
    }

   

   
    

  
    
}
