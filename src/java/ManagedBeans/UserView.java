/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManagedBeans;

import EnterpriseJavaBeans.UserFacade;
import Entities.AuctionUser;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author raugz
 */
@Named(value = "userView")
@RequestScoped
public class UserView {

    @EJB
    private UserFacade userFacade;
    private AuctionUser auctionUser;

    /**
     * Creates a new instance of UserView
     */
    public UserView() {
        this.auctionUser = new AuctionUser();
    }

    /*
    public void getCurrentUser(){
        String s = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
        System.out.println(s);
    }
    */
    public AuctionUser getUser() {
        return auctionUser;
    }
    
    public String getAllUsers(){
        return userFacade.printUserNames();
    }
    
    public int getNumberOfUsers(){
        return userFacade.findAll().size();
    }
    
    public String postUser(){
        this.userFacade.create(auctionUser);
        return "theend";
    }
    
    /*
    public void setUser(){
        AuctionUser u = new AuctionUser(); // from db
        auctionUser = u;
    }
*/
}
