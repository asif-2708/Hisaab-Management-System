
package hisab.controller;

import com.jfoenix.controls.JFXButton;
import hisab.DatabaseHandler;
import hisab.UserHandler;
import hisab.model.ItemBrandModel;
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

public class ItemBrandController implements Initializable {
    private int selectedRowId;  // for Check The Id of selected Id Colum of the table
    String query=null;
    ResultSet rs=null;
    @FXML
    private TableView<ItemBrandModel> tableItemBrand;
    @FXML
    private TableColumn<ItemBrandModel, Integer> itemBrandIdColum;
    @FXML
    private TableColumn<ItemBrandModel, String> itemBrandNameColum;
    @FXML
    ObservableList<ItemBrandModel> list=FXCollections.observableArrayList();
    
    @FXML 
    private TextField txtItemBrand;
    @FXML
    private TextField txtFindItemBrand;
    @FXML
    private Button btnSaveItemBrand;
    @FXML
    private Button btnUpdateItemBrand;
   
    
    @FXML
    private MenuItem editContextMenu;
    @FXML
    private MenuItem deleteContextMenu;
    @FXML
    private JFXButton btnRefreshItemBrand;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
             btnUpdateItemBrand.setVisible(false);
             tableItemBrand.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
             tableItemBrand.setEditable(true);
             //tableItemBrand.getSelectionModel().cellSelectionEnabledProperty().set(true);
            
            updateItemBrandTable();
        } catch (SQLException ex) {
            Logger.getLogger(ItemBrandController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    

    @FXML
    private void saveItemBrand(ActionEvent event) throws SQLException {
        query="insert into item_brand values(null,'"+txtItemBrand.getText()+"')";
        if(DatabaseHandler.insertData(query)){
        updateItemBrandTable();
        }
        else{
            JOptionPane.showMessageDialog(null, "Data Not Saved! Please follow Description");
        }
        clearAll();
    }
    
    
    @FXML
    private void removeItemBrand(ActionEvent event) throws SQLException {
        ObservableList<ItemBrandModel> listId= FXCollections.observableArrayList();    
        listId.addAll(tableItemBrand.getSelectionModel().getSelectedItems());
        
        //call Custom Mathod For Confirmation Dialog With passing Total Number Of Selected record 
        Optional<ButtonType> buttonChoice=UserHandler.alertConfirmation(listId.size(),"deleteAlert");
        if(buttonChoice.get()== ButtonType.OK){
            for(int i=0;i<listId.size();i++){
                query="delete from item_brand where item_brand_id="+listId.get(i).getItemBrandId()+"";
                DatabaseHandler.removeData(query);                         
            }
        }
        updateItemBrandTable();
    }
      
    @FXML
    private void editItemBrand(ActionEvent event) throws SQLException {
        ItemBrandModel itemBrandModel=(ItemBrandModel) tableItemBrand.getSelectionModel().getSelectedItem();
     
        txtItemBrand.setText(itemBrandModel.getItemBrandName());
        btnSaveItemBrand.setVisible(false);
        btnUpdateItemBrand.setVisible(true);
        selectedRowId=itemBrandModel.getItemBrandId(); //This value is used into UpdateItemBrand Function to dwnote which row user want to update
             
    }
    
    
    @FXML
    private void updateItemBrand(ActionEvent event) throws SQLException {
        
        //call Custom Mathod For Confirmation Dialog With passing Total Number Of Selected record 
        Optional<ButtonType> buttonChoice=UserHandler.alertConfirmation("updateAlert");
        if(buttonChoice.get()== ButtonType.OK){          
                query="update item_brand set item_brand_name='"+txtItemBrand.getText()+"' where item_brand_id="+selectedRowId+"";
                DatabaseHandler.updateData(query);                         
        }
        
        updateItemBrandTable();
        clearAll();     
    }
    
   
    
    
    @FXML
    private void refreshItemBrand(ActionEvent event) throws SQLException {
        clearAll();
        updateItemBrandTable();
    }
    
    
 
     
    
    
    
    // Populate the Table Viewby fatching Data From Database
    private void updateItemBrandTable() throws SQLException {
        tableItemBrand.getItems().clear();
        itemBrandIdColum.setCellValueFactory(new PropertyValueFactory<ItemBrandModel,Integer>("itemBrandId"));
        itemBrandNameColum.setCellValueFactory(new PropertyValueFactory<ItemBrandModel,String>("itemBrandName"));
        
        query="select * from item_brand";
        
        rs=DatabaseHandler.selectData(query);
        while(rs.next()){
            try {
                list.add(
                        new ItemBrandModel(Integer.parseInt(rs.getString("item_brand_id")),rs.getString("item_brand_name"))
                );
            } catch (SQLException ex) {
                Logger.getLogger(ItemBrandController.class.getName()).log(Level.SEVERE, null, ex);
            }
            tableItemBrand.setItems(list);
            
        }
        
    }
    
    
    
   
    // clear All Fields and Reset All The Setting when Save Delete or Find operation
    private void clearAll() {
        btnSaveItemBrand.setVisible(true);
        btnUpdateItemBrand.setVisible(false);
        txtItemBrand.clear();
        txtFindItemBrand.clear();
        selectedRowId=0;
    }
    
}
