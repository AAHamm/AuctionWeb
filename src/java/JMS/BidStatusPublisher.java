/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JMS;

import Entities.Product;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.InitialContext;

/**
 *
 * @author raugz
 */
public class BidStatusPublisher {
    
    public void publish(Product p){
        try  
        {   //Create and start connection  
            InitialContext ctx=new InitialContext();  
            TopicConnectionFactory f=(TopicConnectionFactory)ctx.lookup("AuctionTopicConnectionFactory");  
            TopicConnection con=f.createTopicConnection();  
            con.start();  
            //2) create queue session  
            TopicSession ses=con.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);    
            Topic t=(Topic)ctx.lookup("AuctionTopic");  
            TopicPublisher publisher = ses.createPublisher(t);
            TextMessage msg = ses.createTextMessage();
            //TODO pass object Product here
            msg.setText("");
            publisher.publish(msg);
            System.out.println("Message successfully sent."); 
            con.close();
        }catch(Exception e){System.out.println(e);}  
    }
}
