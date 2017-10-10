/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EnterpriseJavaBeans;

import EnterpriseJavaBeans.AbstractFacade;
import Entities.AuctionUser;
import Entities.Bid;
import Entities.Product;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.PessimisticLockException;

/**
 *
 * @author Åsmund
 */
@Stateless
public class BidFacade extends AbstractFacade<Bid> {

    @PersistenceContext(unitName = "persistence unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BidFacade() {
        super(Bid.class);
    }
    
    /**
     * Returns null if trying to write to product.startingPrice same time 
     * as another process
     * @param amount
     * @param product
     * @param user
     * @return 
     */
    public Bid createBid(double amount, Product product, AuctionUser user){
        if(product.getExpirationDate() != null){
        
            java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
            if(product.getExpirationDate().compareTo(date) < 0){
                product.setIsExpired(true);
                return null;
            }
        
        }
        Bid b = new Bid(); 
            
        b.setAmount(amount);
        b.setProduct(product);
        b.setBuyer(user);
        
        create(b);
        
        //warning: slow for users with many bids!
        if(!b.getBuyer().getBids().contains(b)){
            b.getBuyer().getBids().add(b);
                    
        }
        
        
        if(!b.getProduct().getBids().contains(b)){
            List<Bid> otherBids = new ArrayList<>();
            if(!b.getBuyer().getBids().isEmpty()){
                for(Bid bid : b.getBuyer().getBids()){
                    if(bid.getProduct().equals(b.getProduct())){
                        otherBids.add(bid);
                    }
                }
            }
            for(Bid bid : otherBids){
                if(b.getAmount() > bid.getAmount()){
                    b.getBuyer().getBids().remove(bid);
                }
            }
            b.getProduct().getBids().add(b);
        }
        
        try{
         //   em.lock(product, LockModeType.PESSIMISTIC_FORCE_INCREMENT);
            product.setStartingPrice(amount);
        }
        catch(PessimisticLockException e){
            return null;
        }

        
        return b;
    }

    
}
