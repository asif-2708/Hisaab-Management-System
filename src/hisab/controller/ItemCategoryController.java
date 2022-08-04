
package hisab.controller;

import com.jfoenix.controls.JFXButton;
import hisab.DatabaseHandler;
import hisab.UserHandler;
import hisab.model.ItemCategoryModel;
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

public class ItemCategoryController implements Initializable {

    String query=null;
    ResultSet rs=null;
    @FXML
    private TableView<ItemCategoryModel> tableItemCategory;
    @FXML
    private TableColumn<ItemCategoryModel, Integer> itemCategoryIdColum;
    @FXML
    private TableColumn<ItemCategoryModel, String> itemCategoryNameColum;
    ObservableList<ItemCategoryModel> list=FXCollections.observableArrayList();
    
    @FXML 
    private TextField txtItemCategory;
    private TextField txtFindItemCategory;
    @FXML
    private Button btnSaveItemCategory;
    @FXML
    private Button btnUpdateItemCategory;
   
    private int selectedRowId;  // for Check The Id of selected Id Colum of the table
    @FXML
    private MenuItem editContextMenu;
    @FXML
    private MenuItem deleteContextMenu;
    @FXML
    private JFXButton btnRefreshItemCategory;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
             btnUpdateItemCategory.setVisible(false);
             tableItemCategory.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
             tableItemCategory.setEditable(true);
             //tableItemCategory.getSelectionModel().cellSelectionEnabledProperty().set(true);
            
            updateItemCategoryTable();
        } catch (SQLException ex) {
            Logger.getLogger(ItemCategoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    

    @FXML
    private void saveItemCategory(ActionEvent event) throws SQLException {
        query="insert into item_category values(null,'"+txtItemCategory.getText()+"')";
        if(DatabaseHandler.insertData(query)){
        updateItemCategoryTable();
        }
        else{
            JOptionPane.showMessageDialog(null, "Data Not Saved! Please follow Description");
        }
        clearAll();
    }
    
    
    @FXML
    private void removeItemCategory(ActionEvent event) throws SQLException {
        ObservableList<ItemCategoryModel> listId= FXCollections.observableArrayList();    
        listId.addAll(tableItemCategory.getSelectionModel().getSelectedItems());
        
        //call Custom Mathod For Confirmation Dialog With passing Total Number Of Selected record 
        Optional<ButtonType> buttonChoice=UserHandler.alertConfirmation(listId.size(),"deleteAlert");
        if(buttonChoice.get()== ButtonType.OK){
            for(int i=0;i<listId.size();i++){
                query="delete from item_category where item_category_id="+listId.get(i).getItemCategoryId()+"";
                DatabaseHandler.removeData(query);                         
            }
        }
        updateItemCategoryTable();
    }
      
    @FXML
    private void editItemCategory(ActionEvent event) throws SQLException {
        ItemCategoryModel itemCategoryModel=(ItemCategoryModel) tableItemCategory.getSelectionModel().getSelectedItem();
     
        txtItemCategory.setText(itemCategoryModel.getItemCategoryName());
        btnSaveItemCategory.setVisible(false);
        btnUpdateItemCategory.setVisible(true);
        selectedRowId=itemCategoryModel.getItemCategoryId(); //This value is used into UpdateItemCategory Function to dwnote which row user want to update
             
    }
    
    
    @FXML
    private void updateItemCategory(ActionEvent event) throws SQLException {
        
        //call Custom Mathod For Confirmation Dialog With passing Total Number Of Selected record 
        Optional<ButtonType> buttonChoice=UserHandler.alertConfirmation("updateAlert");
        if(buttonChoice.get()== ButtonType.OK){          
                query="update item_category set item_category_name='"+txtItemCategory.getText()+"' where item_category_id="+selectedRowId+"";
                DatabaseHandler.updateData(query);                         
        }
        
        updateItemCategoryTable();
        clearAll();     
    }
    
   
    
    
    @FXML
    private void refreshItemCategory(ActionEvent event) throws SQLException {
        clearAll();
        updateItemCategoryTable();
    }
    
    
 
     
    
    
    
    // Populate the Table Viewby fatching Data From Database
    private void updateItemCategoryTable() throws SQLException {
        tableItemCategory.getItems().clear();
        itemCategoryIdColum.setCellValueFactory(new PropertyValueFactory<ItemCategoryModel,Integer>("itemCategoryId"));
        itemCategoryNameColum.setCellValueFactory(new PropertyValueFactory<ItemCategoryModel,String>("itemCategoryName"));
        
        query="select * from item_category";
        
        rs=DatabaseHandler.selectData(query);
        while(rs.next()){
            try {
                list.add(
                        new ItemCategoryModel(Integer.parseInt(rs.getString("item_category_id")),rs.getString("item_category_name"))
                );
            } catch (SQLException ex) {
                Logger.getLogger(ItemCategoryController.class.getName()).log(Level.SEVERE, null, ex);
            }
            tableItemCategory.setItems(list);
            
        }
        
    }
    
    
    
   
    // clear All Fields and Reset All The Setting when Save Delete or Find operation
    private void clearAll() {
        btnSaveItemCategory.setVisible(true);
        btnUpdateItemCategory.setVisible(false);
        txtItemCategory.clear();
        txtFindItemCategory.clear();
        selectedRowId=0;
    }

   

   
    

  
    
}
