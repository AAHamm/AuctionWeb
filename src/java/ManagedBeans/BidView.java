/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManagedBeans;

import EnterpriseJavaBeans.BidFacade;
import Entities.Bid;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Åsmund
 */
@Named(value = "bidView")
@RequestScoped
public class BidView {

    @EJB
    private BidFacade bidFacade;
    
    private Bid bid;
    
    private double bidVal;


    /**
     * Creates a new instance of BidView
     */
    public BidView() {
    }
    
    public void makeBid(){
        bidFacade.bid(bidVal, bid);
    }



    public Bid getBid() {
       return bid;
    }
    
}
