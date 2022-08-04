
package hisab.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import hisab.DatabaseHandler;
import hisab.UserHandler;
import hisab.model.ItemUnitModel;
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

public class ItemUnitController implements Initializable {

    String query=null;
    ResultSet rs=null;
    @FXML
    private TableView<ItemUnitModel> tableItemUnit;
    @FXML
    private TableColumn<ItemUnitModel, Integer> itemUnitIdColum;
    @FXML
    private TableColumn<ItemUnitModel, String> itemUnitNameColum;
    @FXML
    private TableColumn<ItemUnitModel, Integer> itemUnitQtyColum;
    
    ObservableList<ItemUnitModel> list=FXCollections.observableArrayList();
    
    @FXML
    private JFXTextField txtItemUnitName;
    @FXML
    private JFXTextField txtItemUnitQty;
    @FXML
    private TextField txtFindItemUnit;
    @FXML
    private Button btnSaveItemUnit;
    @FXML
    private Button btnUpdateItemUnit;
   
    private int selectedRowId;  // for Check The Id of selected Id Colum of the table
    @FXML
    private MenuItem editContextMenu;
    @FXML
    private MenuItem deleteContextMenu;
    @FXML
    private JFXButton btnRefreshItemUnit;
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
             btnUpdateItemUnit.setVisible(false);
             tableItemUnit.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
             tableItemUnit.setEditable(true);
             //tableItemUnit.getSelectionModel().cellSelectionEnabledProperty().set(true);
             updateItemUnitTable();
        } catch (SQLException ex) {
            Logger.getLogger(ItemUnitController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    

    @FXML
    private void saveItemUnit(ActionEvent event) throws SQLException {
        query="insert into item_unit values(null,'"+txtItemUnitName.getText()+"',"+txtItemUnitQty.getText()+")";
        if(DatabaseHandler.insertData(query)){
        updateItemUnitTable();
        }
        else{
            JOptionPane.showMessageDialog(null, "Data Not Saved! Please follow Description");
        }
        clearAll();
    }
    
    
    @FXML
    private void removeItemUnit(ActionEvent event) throws SQLException {
        ObservableList<ItemUnitModel> listId= FXCollections.observableArrayList();    
        listId.addAll(tableItemUnit.getSelectionModel().getSelectedItems());
        
        //call Custom Mathod For Confirmation Dialog With passing Total Number Of Selected record 
        Optional<ButtonType> buttonChoice=UserHandler.alertConfirmation(listId.size(),"deleteAlert");
        if(buttonChoice.get()== ButtonType.OK){
            for(int i=0;i<listId.size();i++){
                query="delete from item_unit where item_unit_id="+listId.get(i).getItemUnitId()+"";
                DatabaseHandler.removeData(query);                         
            }
        }
        updateItemUnitTable();
    }
      
    @FXML
    private void editItemUnit(ActionEvent event) throws SQLException {
        ItemUnitModel itemUnitModel=(ItemUnitModel) tableItemUnit.getSelectionModel().getSelectedItem();
     
        txtItemUnitName.setText(itemUnitModel.getItemUnitName());
        txtItemUnitQty.setText(String.valueOf(itemUnitModel.getItemUnitQty()));
        
        btnSaveItemUnit.setVisible(false);
        btnUpdateItemUnit.setVisible(true);
        selectedRowId=itemUnitModel.getItemUnitId(); //This value is used into UpdateItemUnit Function to dwnote which row user want to update
             
    }
    
    
    @FXML
    private void updateItemUnit(ActionEvent event) throws SQLException {
        
        //call Custom Mathod For Confirmation Dialog With passing Total Number Of Selected record 
        Optional<ButtonType> buttonChoice=UserHandler.alertConfirmation("updateAlert");
        if(buttonChoice.get()== ButtonType.OK){          
                query="update item_unit set item_unit_name='"+txtItemUnitName.getText()+"',item_unit_qty="+txtItemUnitQty.getText()+" where item_unit_id="+selectedRowId+"";
                DatabaseHandler.updateData(query);                         
        }
        
        updateItemUnitTable();
        clearAll();     
    }
    
   
    
    
    @FXML
    private void refreshItemUnit(ActionEvent event) throws SQLException {
        clearAll();
        updateItemUnitTable();
    }
    
    
 
     
    
    
    
    // Populate the Table Viewby fatching Data From Database
    private void updateItemUnitTable() throws SQLException {
        tableItemUnit.getItems().clear();
        itemUnitIdColum.setCellValueFactory(new PropertyValueFactory<ItemUnitModel,Integer>("itemUnitId"));
        itemUnitNameColum.setCellValueFactory(new PropertyValueFactory<ItemUnitModel,String>("itemUnitName"));
        itemUnitQtyColum.setCellValueFactory(new PropertyValueFactory<ItemUnitModel,Integer>("itemUnitQty"));
        query="select * from item_unit";
        
        rs=DatabaseHandler.selectData(query);
        while(rs.next()){
            try {
                list.add(
                        new ItemUnitModel(Integer.parseInt(rs.getString("item_unit_id")),rs.getString("item_unit_name"),Integer.parseInt(rs.getString("item_unit_qty")))
                );
            } catch (SQLException ex) {
                Logger.getLogger(ItemUnitController.class.getName()).log(Level.SEVERE, null, ex);
            }
            tableItemUnit.setItems(list);
            
        }
        
    }
    
    
    
   
    // clear All Fields and Reset All The Setting when Save Delete or Find operation
    private void clearAll() {
        btnSaveItemUnit.setVisible(true);
        btnUpdateItemUnit.setVisible(false);
        txtItemUnitName.clear();
        txtItemUnitQty.clear();
        txtFindItemUnit.clear();
        selectedRowId=0;
    }

   

   
    

  
    
}
