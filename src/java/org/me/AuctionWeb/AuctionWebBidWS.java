/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.AuctionWeb;

import EnterpriseJavaBeans.BidFacade;
import EnterpriseJavaBeans.ProductFacade;
import Entities.AuctionUser;
import Entities.Bid;
import Entities.Product;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Åsmund
 */
@WebService(serviceName = "AuctionWebBidWS")
public class AuctionWebBidWS {

    @EJB
    private BidFacade ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Web Service Operation")
    
    @EJB
    private ProductFacade productFacade;
    
    @WebMethod(operationName = "getActiveAuctions")
    public List<Product> getActiveAuctions(){
        return productFacade.findAll();
    }
    
    @WebMethod(operationName = "bidForAuction")
    public String bidForAuction(Bid b){ 
        return ejbRef.createWithRespone(b);
    }
    
    
    
    
    
    
    /*
    @WebMethod(operationName = "create")
    @Oneway
    public void create(@WebParam(name = "entity") Bid entity) {
        ejbRef.create(entity);
    }

    @WebMethod(operationName = "edit")
    @Oneway
    public void edit(@WebParam(name = "entity") Bid entity) {
        ejbRef.edit(entity);
    }

    @WebMethod(operationName = "remove")
    @Oneway
    public void remove(@WebParam(name = "entity") Bid entity) {
        ejbRef.remove(entity);
    }

    @WebMethod(operationName = "find")
    public Bid find(@WebParam(name = "id") Object id) {
        return ejbRef.find(id);
    }

    @WebMethod(operationName = "findAll")
    public List<Bid> findAll() {
        return ejbRef.findAll();
    }

    @WebMethod(operationName = "findRange")
    public List<Bid> findRange(@WebParam(name = "range") int[] range) {
        return ejbRef.findRange(range);
    }

    @WebMethod(operationName = "count")
    public int count() {
        return ejbRef.count();
    }

    @WebMethod(operationName = "createBid")
    public Bid createBid(@WebParam(name = "amount") double amount, @WebParam(name = "product") Product product, @WebParam(name = "user") AuctionUser user) {
        return ejbRef.createBid(amount, product, user);
    }
    
    */
}
