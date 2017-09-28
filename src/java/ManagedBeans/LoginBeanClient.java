/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManagedBeans;

import EnterpriseJavaBeans.LoginBean;
import EnterpriseJavaBeans.LoginBeanRemote;
import Entities.AuctionUser;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author Gard
 */
@Named(value = "loginClient")
@SessionScoped
public class LoginBeanClient implements Serializable{

    @EJB
    private LoginBeanRemote serverBean;
    
    private String userName;
    private String password;
    private Boolean loggedIn = null;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Use this to get currently inlogged user
     * TODO somewhere check if user is logged on to avoid nullpointers
     * @return 
     */
    public AuctionUser getUser() {
        return serverBean.getLoggedInUser();
    }
    
    public void login(){
        //Todo options if authentication fails
        String encryptedPassword = org.apache.commons.codec.digest.DigestUtils.sha256Hex(password);
        loggedIn = serverBean.login(userName, encryptedPassword); // returns boolean value
        //userName = serverBean.getLoggedInUser().getName();
        //password = serverBean.getLoggedInUser().getPassword();
    }
   
    
    public void logout(){
        loggedIn = null;
        serverBean.logout();
    }

    public String getLoginSuccessOutput() {
        if(!(loggedIn == null)){
            if(loggedIn){
                return "Sucessful Login";
            }
            return "Invalid Credentials";
        }
        return "";
    }
    
    
    
    
    /**
     * Creates a new instance of LoginBean
     */
    public LoginBeanClient() {
    }
    
    /*
    public void login(){
        System.out.println("username::" + serverBean.getLoggedInUser().getName());
              System.out.println("pw::" + serverBean.getLoggedInUser().getPassword());
    }
    */
    
}
