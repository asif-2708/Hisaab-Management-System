/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hisab.controller;

import hisab.DatabaseHandler;
import hisab.model.HomeModel;
import hisab.model.ItemSalesListModel;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javax.swing.JOptionPane;
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
public class ItemSalesListController implements Initializable {
    Connection conn=DatabaseHandler.getConection(); 
    HomeModel homeModel=new HomeModel();
    
    @FXML
    private DatePicker txtFromDate;
    @FXML
    private DatePicker txtToDate;
    @FXML
    private Button btnSearchByDate;
    @FXML
    private Button btnNewSales;
    
    @FXML
    private Button btnPrintSalesList;
    @FXML
    private TableView<ItemSalesListModel> tableSalesList;
    @FXML
    private TableColumn<ItemSalesListModel, String> columSalesDate;
    @FXML
    private TableColumn<ItemSalesListModel, Integer> columSalesBillNo;
    @FXML
    private TableColumn<ItemSalesListModel, String> columSalesPartyName;
    @FXML
    private TableColumn<ItemSalesListModel, String> columSalesType;
    @FXML
    private TableColumn<ItemSalesListModel, Double> columSalesTotalAmt;
    @FXML
    private TableColumn<ItemSalesListModel, Double> columSalesTaxAmt;
    @FXML
    private TableColumn<ItemSalesListModel, Double> columSalesNetAmt;
    @FXML
    private TableColumn<ItemSalesListModel, String> columSalesRemark;
    private String query;
    private ResultSet rs;  // result set for fetch all record from database to fill tableView
    private ResultSet rsColum; // for fetch single or multiple column record to fetch perticular colum value using join etc
    private ObservableList<ItemSalesListModel> list=FXCollections.observableArrayList();
    private String accPrintName; // for get Value from join table according to acc_id from sales_master
    private String salesTermName;
    private String salesTypeName;
    @FXML
    private Button btnRefresh;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        query="select * from sales_master";
        updateItemBrandTable(query);
    }    

    private void updateItemBrandTable(String queryLocal) {
        columSalesDate.setCellValueFactory(new PropertyValueFactory<ItemSalesListModel,String>("salesDate"));
        columSalesBillNo.setCellValueFactory(new PropertyValueFactory<ItemSalesListModel,Integer>("billNo"));
        columSalesPartyName.setCellValueFactory(new PropertyValueFactory<ItemSalesListModel,String>("partyName"));
        columSalesType.setCellValueFactory(new PropertyValueFactory<ItemSalesListModel,String>("salesType"));
        columSalesTotalAmt.setCellValueFactory(new PropertyValueFactory<ItemSalesListModel,Double>("totalAmt"));
        columSalesTaxAmt.setCellValueFactory(new PropertyValueFactory<ItemSalesListModel,Double>("taxAmt"));
        columSalesNetAmt.setCellValueFactory(new PropertyValueFactory<ItemSalesListModel,Double>("netAmt"));
        columSalesRemark.setCellValueFactory(new PropertyValueFactory<ItemSalesListModel,String>("remark"));
        
        //query="select * from sales_master WHERE `sales_date` BETWEEN '2018-03-28' AND '2018-03-31'";
        query=queryLocal;
        rs=DatabaseHandler.selectData(query);
        
        try {
              
            while(rs.next()){
                
             
               //Return Account account_name From Accoumt table according to account_id from sales_master
                query="select ac.account_print_name from account_detail ac JOIN sales_master sm ON sm.account_id=ac.account_master_id where sm.sales_master_id="+rs.getInt("sales_master_id")+"";
                rsColum=DatabaseHandler.selectData(query);
                if(rsColum.next()){
                accPrintName=rsColum.getString("account_print_name");
                }
              
             /*   //Return  Sales_term_name From Sales_term table according to sales_term_id from sales_master
                query="select pt.sales_term_name from sales_term pt JOIN sales_master pm ON pm.sales_term_id=pt.sales_term_id where pm.sales_master_id="+rs.getInt("sales_master_id")+"";
                rsColum=DatabaseHandler.selectData(query);
                if(rsColum.next()){
                salesTermName=rsColum.getString(1);
                }
              */
                
                //Return sales_type_name From sales_type table according to sales_typeid from sales_master
                query="select st.sales_type_name from sales_type st JOIN sales_master sm ON sm.sales_type_id=st.sales_type_id where sm.sales_master_id="+rs.getInt("sales_master_id")+"";
                rsColum=DatabaseHandler.selectData(query);
                if(rsColum.next()){   
                salesTypeName=rsColum.getString("sales_type_name");
                }
              
               
             
               list.add(
                        new ItemSalesListModel(rs.getDate("sales_date").toString(),rs.getInt("sales_master_id"),accPrintName,salesTypeName,rs.getDouble("total_amt"),rs.getDouble("total_tax"),rs.getDouble("net_amt"))
                ); 
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ItemSalesListController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tableSalesList.setItems(list);
        
    }

    @FXML
    private void openSalesReport(ActionEvent event) {
        try {
            
           String report=".\\src\\hisab\\report\\salesReport.jrxml";
           
           JasperDesign jd=JRXmlLoader.load(report);
            String query="select sm.sales_master_id,ac.account_print_name,st.sales_term_name,st2.sales_type_name,sm.total_amt,sm.total_tax,sm.net_amt,sm.sales_date from sales_master as sm join account_detail as ac on ac.account_master_id=sm.account_id join sales_term as st on st.sales_term_id=sm.sales_term_id join sales_type as st2 on st2.sales_type_id=sm.sales_type_id where sales_master_id="+tableSalesList.getSelectionModel().getSelectedItem().getBillNo()+"";

           //String query="select sm.sales_master_id,ac.account_print_name,st.sales_term_name,st2.sales_type_name,sm.total_amt,sm.total_tax,sm.net_amt,sm.sales_date from sales_master as sm join account_detail as ac on ac.account_master_id=sm.account_id join sales_term as st on st.sales_term_id=sm.sales_term_id join sales_type as st2 on st2.sales_type_id=sm.sales_type_id where sm.sales_master_id="+tableSalesList.getSelectionModel().getSelectedItem().getBillNo()+"";
            
           JRDesignQuery newQuery=new JRDesignQuery();
            newQuery.setText(query);
            jd.setQuery(newQuery);
           JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(jr,null,conn);
            
            JasperViewer.viewReport(jp,false);
        } catch (JRException ex) {
            Logger.getLogger(ItemSalesListController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void showItemSale(ActionEvent event) throws IOException {
        
    }

    @FXML
    private void searchByDate(ActionEvent event) {
        
            list.removeAll(list);
            query="select * from sales_master WHERE `sales_date` BETWEEN '"+txtFromDate.getValue().toString()+"' AND '"+txtToDate.getValue().toString()+"'";
            updateItemBrandTable(query);
        
    }

    @FXML
    private void refreshData(ActionEvent event) {
        list.removeAll(list);
        query="select * from sales_master";
        updateItemBrandTable(query);
    }
    
}
