/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hisab.controller;

import com.jfoenix.controls.JFXTextField;
import hisab.DatabaseHandler;
import hisab.model.StockMasterModel;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class StockMasterController implements Initializable {

    
    int stockId,itemId,clQty;
    double itemPrice,opValue,clValue,totalvalue;
    String itemName;
    
    
    @FXML
    private ComboBox<String> cmbItemList;
    @FXML
    private JFXTextField txtOpQty;
    @FXML
    private Button btnSaveStock;
    @FXML
    private Button btnClearStock;
    @FXML
    private TableView<StockMasterModel> tableStockMaster;
    @FXML
    private TableColumn<StockMasterModel, Integer> StockMasterIdColum;
    @FXML
    private TableColumn<StockMasterModel, String> StockMasterItemNameColum;
    @FXML
    private TableColumn<StockMasterModel, Integer> StockMasterOpQtyColum;
    @FXML
    private TableColumn<StockMasterModel, Double> StockMasterOpValueColum;
    @FXML
    private TableColumn<StockMasterModel, Integer> StockMasterPurQtyColum;
    @FXML
    private TableColumn<StockMasterModel, Double> StockMasterPurValueColum;
    @FXML
    private TableColumn<StockMasterModel, Integer> StockMasterSaleQtyColum;
    @FXML
    private TableColumn<StockMasterModel, Double> StockMasterSaleValueColum;
    @FXML
    private TableColumn<StockMasterModel, Integer> StockMasterClQtyColum;
   @FXML
    private TableColumn<StockMasterModel, Integer> StockMasterTotalQtyColum;
    @FXML
    private TableColumn<StockMasterModel,Double> StockMasterClValueColum;

    ResultSet rs=null;
    ResultSet rsStockMaster=null;
    ObservableList<StockMasterModel> list=FXCollections.observableArrayList();
    ObservableList<String> listItemName=FXCollections.observableArrayList();
    private String query;
    @FXML
    private MenuItem editContextMenu;
   
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         
        try {
             fillCmbItemList();
            updateStockMasterTable();
        }catch (SQLException ex) {
            Logger.getLogger(StockMasterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    private void updateStockMasterTable() {
        tableStockMaster.getItems().clear();
        StockMasterIdColum.setCellValueFactory(new PropertyValueFactory<StockMasterModel,Integer>("stockMasterId"));
        StockMasterItemNameColum.setCellValueFactory(new PropertyValueFactory<StockMasterModel,String>("itemName"));
        StockMasterOpQtyColum.setCellValueFactory(new PropertyValueFactory<StockMasterModel,Integer>("opQty"));
        StockMasterOpValueColum.setCellValueFactory(new PropertyValueFactory<StockMasterModel,Double>("opValue"));
        StockMasterPurQtyColum.setCellValueFactory(new PropertyValueFactory<StockMasterModel,Integer>("purQty"));
        StockMasterPurValueColum.setCellValueFactory(new PropertyValueFactory<StockMasterModel,Double>("purValue"));
        StockMasterTotalQtyColum.setCellValueFactory(new PropertyValueFactory<StockMasterModel,Integer>("totalQty"));
        StockMasterSaleQtyColum.setCellValueFactory(new PropertyValueFactory<StockMasterModel,Integer>("saleQty"));
        StockMasterSaleValueColum.setCellValueFactory(new PropertyValueFactory<StockMasterModel,Double>("saleValue"));
        StockMasterClQtyColum.setCellValueFactory(new PropertyValueFactory<StockMasterModel,Integer>("clQty"));
        StockMasterClValueColum.setCellValueFactory(new PropertyValueFactory<StockMasterModel,Double>("clValue"));
       
        
        

        query="select sm.*,im.item_name from stock_master as sm join item_master as im on im.item_master_id=sm.item_master_id ";
        rs=DatabaseHandler.selectData(query);
        try {
            
            while(rs.next()){
                list.add(
                        new StockMasterModel(rs.getInt(1),rs.getString("item_name"),rs.getInt(3),rs.getDouble(4),rs.getInt(5),rs.getDouble(6),rs.getInt(7),rs.getInt(8),rs.getDouble(9),rs.getInt(10),rs.getDouble(11))
                
                );
            }
            tableStockMaster.setItems(list);
            
           
        } catch (SQLException ex) {
            Logger.getLogger(StockMasterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void editStockMaster(ActionEvent event) {
        StockMasterModel stockMasterModel=(StockMasterModel)tableStockMaster.getSelectionModel().getSelectedItem();  
        cmbItemList.getSelectionModel().select(stockMasterModel.getItemName());
        txtOpQty.setText(String.valueOf(stockMasterModel.getOpQty()));
        itemName=stockMasterModel.getItemName();      
    }
/*
    @FXML
    private void updateStockMaster(ActionEvent event) {
        query="select item_master_id,pur_price from item_master where item_name="++""
        query="update stock_master set item_master_id="++",op_qty="++"";
    }
*/
    
    //Event Handler Automatically call when user select for new Item to add OpQty
    @FXML
    private void insertOpStock(ActionEvent event) throws SQLException {
        itemName=cmbItemList.getSelectionModel().getSelectedItem();
        query="select sm.op_qty,im.pur_price,sm.item_master_id,im.item_name from stock_master sm JOIN item_master im on im.item_master_id=sm.item_master_id where sm.item_master_id= (select item_master_id from item_master where item_name='"+itemName+"') ";
        rsStockMaster=DatabaseHandler.selectData(query);
        if(rsStockMaster.next()){
            txtOpQty.setText(String.valueOf(rsStockMaster.getInt("op_qty")));
            
        }
        itemPrice=rsStockMaster.getDouble("pur_price");
        itemId=rsStockMaster.getInt("item_master_id");
        itemName=rsStockMaster.getString("item_name"); 
    }

    private void fillCmbItemList() throws SQLException {
        query="select item_name from item_master";
        rsStockMaster=DatabaseHandler.selectData(query);
        while(rsStockMaster.next()){
            listItemName.add( rsStockMaster.getString("item_name"));
        }
        cmbItemList.setItems(listItemName);
    }

    @FXML
    private void saveStockMaster(ActionEvent event) throws SQLException {
        opValue=itemPrice*Integer.parseInt(txtOpQty.getText());
        
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Save Confirmation");
        alert.setContentText("Do You Want To Save new Opening Qty? \n Note: It Will Replace Your Old Qty for Current Item \n Item Name:" +itemName+ "\n Op Qty: "+Integer.parseInt(txtOpQty.getText()));
        Optional<ButtonType> buttonChoice=alert.showAndWait();
        if(buttonChoice.get()==ButtonType.OK){
            
            query="update stock_master set op_qty="+Integer.parseInt(txtOpQty.getText())+", op_value="+opValue+" where item_master_id="+itemId+"";
            DatabaseHandler.updateData(query);
            query="select op_qty+pur_qty-sale_qty,op_value+pur_value-sale_value from stock_master where item_master_id="+itemId+"";
            rsStockMaster=DatabaseHandler.selectData(query);
            if(rsStockMaster.next()){
                query="update stock_master set cl_stock="+rsStockMaster.getInt("op_qty+pur_qty-sale_qty")+", cl_value="+rsStockMaster.getInt("op_value+pur_value-sale_value")+",total_qty="+rsStockMaster.getInt("op_qty+pur_qty")+" where item_master_id="+itemId+" ";
                DatabaseHandler.updateData(query);
            }
            
        }
        clearStockMaster();
        updateStockMasterTable();
        
    }

    @FXML
    private void clearStockMaster(ActionEvent event) throws SQLException {
        clearStockMaster();
        updateStockMasterTable();
    }

    private void clearStockMaster() throws SQLException {
        cmbItemList.setItems(null);
        fillCmbItemList();
        txtOpQty.setText("0");
    }
    
    
    
}
