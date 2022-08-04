
package hisab.model;

import java.util.Date;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


public class ItemSalesListModel {

   
    private final SimpleIntegerProperty billNo ;
    private final SimpleStringProperty salesDate;
    private final SimpleStringProperty partyName;
    private final SimpleStringProperty salesType;
    private final SimpleDoubleProperty totalAmt ;
    private final SimpleDoubleProperty taxAmt ;
    private final SimpleDoubleProperty netAmt ;
   // private final SimpleStringProperty remark ;

    public ItemSalesListModel(String salesDate,int billNo, String partyName, String salesType, double totalAmt, double taxAmt, double netAmt) {
       
        this.salesDate = new SimpleStringProperty(salesDate);
        this.billNo=new SimpleIntegerProperty(billNo);
        this.partyName = new SimpleStringProperty(partyName);
        this.salesType = new SimpleStringProperty(salesType);
        this.totalAmt = new SimpleDoubleProperty(totalAmt);
        this.taxAmt = new SimpleDoubleProperty(taxAmt);
        this.netAmt = new SimpleDoubleProperty(netAmt);
        
    }

    public int getBillNo() {
        return billNo.get();
    }

    public String getSalesDate() {
        return salesDate.get();
    }

    public String getPartyName() {
        return partyName.get();
    }

    public String getSalesType() {
        return salesType.get();
    }

    public double getTotalAmt() {
        return totalAmt.get();
    }

    public double getTaxAmt() {
        return taxAmt.get();
    }

    public double getNetAmt() {
        return netAmt.get();
    }

   
    
    public void setBillNo(int billNo) {
        this.billNo.set(billNo);
    }

    public void setSalesDate(String salesDate) {
        this.salesDate.set(salesDate);
    }

    public void setPartyName(String partyName) {
       this.partyName.set(partyName);
    }

    public void setSalesType(String salesType) {
        this.salesType.set(salesType);
    }

    public void setTotalAmt(double totalAmt) {
        this.totalAmt.set(totalAmt);
    }

    public void setTaxAmt(double taxAmt) {
        this.taxAmt.set(taxAmt);
    }

    public void setNetAmt(double netAmt) {
        this.netAmt.set(netAmt);
    }

   
    
}
