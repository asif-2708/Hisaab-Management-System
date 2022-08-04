
package hisab;

import java.sql.SQLException;


public class ExceptionHandler {
    
    private static String errorMessage=null;
    private static  int errorCode;
    
   
    public static String getError(SQLException ex){
        
        errorCode=ex.getErrorCode();
        switch(errorCode){
            case 1062:  errorMessage=ex.getMessage().replaceAll(ex.getMessage(),"Duplicate Entry Not Allowed !");
                        break;
            case 1451:  errorMessage=ex.getMessage().replaceAll(ex.getMessage()," You Can not delete Item ! Item Is Already In use In Another Table  !");
                        break;
            default: errorMessage="Data Operation Failed, Contact Your Software Provider"+errorCode +ex.getMessage();
        }
        return errorMessage;
    }
    
   
    
}
