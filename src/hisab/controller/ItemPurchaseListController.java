/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hisab.controller;

import hisab.DatabaseHandler;
import hisab.model.ItemPurchaseListModel;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author Administrator
 */
public class ItemPurchaseListController implements Initializable {
    Connection conn=DatabaseHandler.getConection(); 
    @FXML
    private DatePicker txtFromDate;
    @FXML
    private DatePicker txtToDate;
    @FXML
    private Button btnSearchByDate;
    @FXML
    private Button btnNewPurchase;
    @FXML
    private Button btnPrintPurchaseList;
    @FXML
    private TableView<ItemPurchaseListModel> tablePurchaseList;
    @FXML
    private TableColumn<ItemPurchaseListModel, String> columPurchaseDate;
    @FXML
    private TableColumn<ItemPurchaseListModel, Integer> columPurchaseBillNo;
    @FXML
    private TableColumn<ItemPurchaseListModel, String> columPurchasePartyName;
    @FXML
    private TableColumn<ItemPurchaseListModel, String> columPurchaseType;
    @FXML
    private TableColumn<ItemPurchaseListModel, Double> columPurchaseTotalAmt;
    @FXML
    private TableColumn<ItemPurchaseListModel, Double> columPurchaseTaxAmt;
    @FXML
    private TableColumn<ItemPurchaseListModel, Double> columPurchaseNetAmt;
    @FXML
    private TableColumn<ItemPurchaseListModel, String> columPurchaseRemark;
    private String query;
    private ResultSet rs;  // result set for fetch all record from database to fill tableView
    private ResultSet rsColum; // for fetch single or multiple column record to fetch perticular colum value using join etc
    private ObservableList<ItemPurchaseListModel> list=FXCollections.observableArrayList();
    private String accPrintName; // for get Value from join table according to acc_id from purchase_master
    private String purchaseTermName;
    private String purchaseTypeName;
    @FXML
    private Button btnRefresh;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        query="select * from purchase_master";
        updateItemBrandTable(query);
    }    

    private void updateItemBrandTable(String queryLocal) {
        columPurchaseDate.setCellValueFactory(new PropertyValueFactory<ItemPurchaseListModel,String>("purchaseDate"));
        columPurchaseBillNo.setCellValueFactory(new PropertyValueFactory<ItemPurchaseListModel,Integer>("billNo"));
        columPurchasePartyName.setCellValueFactory(new PropertyValueFactory<ItemPurchaseListModel,String>("partyName"));
        columPurchaseType.setCellValueFactory(new PropertyValueFactory<ItemPurchaseListModel,String>("purchaseType"));
        columPurchaseTotalAmt.setCellValueFactory(new PropertyValueFactory<ItemPurchaseListModel,Double>("totalAmt"));
        columPurchaseTaxAmt.setCellValueFactory(new PropertyValueFactory<ItemPurchaseListModel,Double>("taxAmt"));
        columPurchaseNetAmt.setCellValueFactory(new PropertyValueFactory<ItemPurchaseListModel,Double>("netAmt"));
        columPurchaseRemark.setCellValueFactory(new PropertyValueFactory<ItemPurchaseListModel,String>("remark"));
        
        query=queryLocal;
        rs=DatabaseHandler.selectData(query);
        
        try {
              
            while(rs.next()){
                
             
               //Return Account account_name From Accoumt table according to account_id from purchase_master
                query="select ac.account_print_name from account_detail ac JOIN purchase_master pm ON pm.account_id=ac.account_master_id where pm.purchase_master_id="+rs.getInt("purchase_master_id")+"";
                rsColum=DatabaseHandler.selectData(query);
                if(rsColum.next()){
                accPrintName=rsColum.getString("account_print_name");
                }
              
             /*   //Return  Purchase_term_name From Purchase_term table according to purchase_term_id from purchase_master
                query="select pt.purchase_term_name from purchase_term pt JOIN purchase_master pm ON pm.purchase_term_id=pt.purchase_term_id where pm.purchase_master_id="+rs.getInt("purchase_master_id")+"";
                rsColum=DatabaseHandler.selectData(query);
                if(rsColum.next()){
                purchaseTermName=rsColum.getString(1);
                }
              */
                
                //Return purchase_type_name From purchase_type table according to purchase_typeid from purchase_master
                query="select pt.purchase_type_name from purchase_type pt JOIN purchase_master pm ON pm.purchase_type_id=pt.purchase_type_id where pm.purchase_master_id="+rs.getInt("purchase_master_id")+"";
                rsColum=DatabaseHandler.selectData(query);
                if(rsColum.next()){   
                purchaseTypeName=rsColum.getString("purchase_type_name");
                }
              
               
             
               list.add(
                        new ItemPurchaseListModel(rs.getDate("pur_date").toString(),rs.getInt("purchase_master_id"),accPrintName,purchaseTypeName,rs.getDouble("total_amt"),rs.getDouble("total_tax"),rs.getDouble("net_amt"))
                ); 
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ItemPurchaseListController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tablePurchaseList.setItems(list);
        
    }

    @FXML
    private void openPurchaseReport(ActionEvent event) {
        try {
            String report=".\\src\\hisab\\report\\purchaseReport.jrxml";
            
            JasperDesign jd=JRXmlLoader.load(report);
            String query="select pm.purchase_master_id,ac.account_print_name,pt.purchase_term_name,pt2.purchase_type_name,pm.total_amt,pm.total_tax,pm.net_amt,pm.pur_date from purchase_master as pm join account_detail as ac on ac.account_master_id=pm.account_id join purchase_term as pt on pt.purchase_term_id=pm.purchase_term_id join purchase_type as pt2 on pt2.purchase_type_id=pm.purchase_type_id where purchase_master_id="+tablePurchaseList.getSelectionModel().getSelectedItem().getBillNo()+"";
            //String query="select pm.purchase_master_id,ac.account_print_name,pt.purchase_term_name,pt2.purchase_type_name,pm.total_amt,pm.total_tax,pm.net_amt,pm.pur_date from purchase_master as pm join account_detail as ac on ac.account_master_id=pm.account_id join purchase_term as pt on pt.purchase_term_id=pm.purchase_term_id join purchase_type as pt2 on pt2.purchase_type_id=pm.purchase_type_id where purchase_master_id="+tablePurchaseList.getSelectionModel().getSelectedItem().getBillNo()+"";
            JRDesignQuery newQuery=new JRDesignQuery();
            newQuery.setText(query);
            jd.setQuery(newQuery);
            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(jr,null,conn);
            
            JasperViewer.viewReport(jp,false);
        } catch (JRException ex) {
            Logger.getLogger(ItemPurchaseListController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void searchByDate(ActionEvent event) {
        list.removeAll(list);
        query="select * from purchase_master WHERE `pur_date` BETWEEN '"+txtFromDate.getValue().toString()+"' AND '"+txtToDate.getValue().toString()+"'";
        updateItemBrandTable(query);
    }

    @FXML
    private void refreshData(ActionEvent event) {
        list.removeAll(list);
         query="select * from purchase_master";
        updateItemBrandTable(query);
    }
    
}
