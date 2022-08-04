
package hisab.model;

import java.util.Date;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


public class ItemPurchaseListModel {

   
    private final SimpleIntegerProperty billNo ;
    private final SimpleStringProperty purchaseDate;
    private final SimpleStringProperty partyName;
    private final SimpleStringProperty purchaseType;
    private final SimpleDoubleProperty totalAmt ;
    private final SimpleDoubleProperty taxAmt ;
    private final SimpleDoubleProperty netAmt ;
   // private final SimpleStringProperty remark ;

    public ItemPurchaseListModel(String purchaseDate,int billNo, String partyName, String purchaseType, double totalAmt, double taxAmt, double netAmt) {
       
        this.purchaseDate = new SimpleStringProperty(purchaseDate);
        this.billNo=new SimpleIntegerProperty(billNo);
        this.partyName = new SimpleStringProperty(partyName);
        this.purchaseType = new SimpleStringProperty(purchaseType);
        this.totalAmt = new SimpleDoubleProperty(totalAmt);
        this.taxAmt = new SimpleDoubleProperty(taxAmt);
        this.netAmt = new SimpleDoubleProperty(netAmt);
        
    }

    public int getBillNo() {
        return billNo.get();
    }

    public String getPurchaseDate() {
        return purchaseDate.get();
    }

    public String getPartyName() {
        return partyName.get();
    }

    public String getPurchaseType() {
        return purchaseType.get();
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

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate.set(purchaseDate);
    }

    public void setPartyName(String partyName) {
       this.partyName.set(partyName);
    }

    public void setPurchaseType(String purchaseType) {
        this.purchaseType.set(purchaseType);
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
