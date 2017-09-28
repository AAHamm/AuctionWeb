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
    
    public String printUserNames(){
    String out ="";
    for(AuctionUser a : findAll()){
           out += a.getName()+ "; ";
       }
       return out;
    }
}
