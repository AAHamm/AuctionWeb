/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TimerTools;

import EnterpriseJavaBeans.ProductFacade;
import Entities.Product;
import JMS.BidStatusPublisher;
import java.util.Date;
import java.util.TimerTask;
import javax.ejb.EJB;

/**
 * Posts the product to AuctionTopic when it expires
 * @author raugz
 */
public class Expiration extends TimerTask{
    private final Product product;
    
    public Expiration(Product p){
        product = p;
    }
    
    @Override
    public void run() {
        BidStatusPublisher pub = new BidStatusPublisher();
        System.out.println("******************" + product.getName());
        pub.publish(product);
    }
    
}
