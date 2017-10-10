/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EnterpriseJavaBeans;

import EnterpriseJavaBeans.AbstractFacade;
import Entities.AuctionUser;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author raugz
 */
@Stateless
public class UserFacade extends AbstractFacade<AuctionUser> {

    @PersistenceContext(unitName = "persistence unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(AuctionUser.class);
    }
    
    public Boolean register(String username, String password){
        for(AuctionUser u : findAll()){
            if(u.getName().equals(username)){
                return false;
            } 
        }
        createUser(username, password);
        return true;

    }
    

    //creates a user and adds it to the database
    public AuctionUser createUser(String name, String password){
            AuctionUser u = new AuctionUser();
            u.setName(name);
            u.setPassword(password);
            
            create(u); 
            
            return u;
    }
    
    public void merge(AuctionUser u){
        em.merge(u);
    }
}
