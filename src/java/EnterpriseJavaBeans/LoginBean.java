/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EnterpriseJavaBeans;

import Entities.AuctionUser;
import java.util.List;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author raugz
 */
@Stateful
public class LoginBean extends AbstractFacade<AuctionUser> implements LoginBeanRemote {
    private AuctionUser user;

    public LoginBean() {
        super(AuctionUser.class);
    }
    
    // or use em from UserFacade?
    @PersistenceContext(unitName = "persistence unit")
    private EntityManager em;
    

    @Override
    public Boolean login(String username, String password) {
        //TODO checks on info from db etc
        if(inputMatchesDBList(username, password, dbQueryAllUsers())){
            return true;
        }
        else{
            return false;
        }
        /*
        user = new AuctionUser();
        user.setName(username);
        user.setPassword(password);
        return true;
        */
    }

    @Override
    public AuctionUser getLoggedInUser() {
        return user;
    }

    @Override
    public void logout() {
        user.setName("");
        user.setPassword("");
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    private List dbQueryAllUsers() {
        List userList = findAll();
        return userList;
    }
    
    private Boolean inputMatchesDBList(String usr, String pw, List<AuctionUser> l){
        for(AuctionUser u : l){
            if((u.getName().equals(usr)) && (u.getPassword().equals(pw))){
                user = u;
                return true;
            }
        }
        return false;
    }
    
}
