/*
 * BankService.java
 * Created on 02/08/2004
 *
 */
package ex7.services;

import org.apache.axis.*;


/**
 * BankService
 * 
 * @author rank
 */
public class BankService {
    
    public String getAccountBalance() {
        
        MessageContext mc = MessageContext.getCurrentContext();
        String user = mc.getUsername();
        return(user);
    
    }
}
