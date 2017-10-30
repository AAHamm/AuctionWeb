package JMS;

import javax.jms.*;  
import javax.naming.InitialContext;  
  
public class NotifyBuyer { 
    /**
     * should not be called anywhere now
     */
    /*
    public void recieveMessage(){
        try {  
            //TODO make everything, dont use for now
            //1) Create and start connection  
            InitialContext ctx=new InitialContext();  
            TopicConnectionFactory f=(TopicConnectionFactory)ctx.lookup("AuctionTopicConnectionFactory");  
            TopicConnection con=f.createTopicConnection();  
            con.start();  
            TopicSession ses=con.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);  
            //3) get the Topic object  
            Topic t=(Topic)ctx.lookup("AuctionTopic");  
            //4)create TopicSubscriber  
            TopicSubscriber receiver=ses.createSubscriber(t);  
              
            //5) create listener object  
            ProductListener listener=new ProductListener();  
              
            //6) register the listener object with subscriber  
            receiver.setMessageListener(listener);  
                          
            System.out.println("Subscriber1 is ready, waiting for messages...");  
            System.out.println("press Ctrl+c to shutdown...");  
            while(true){                  
                Thread.sleep(1000);  
            }  
        }catch(Exception e){System.out.println(e);}
    }  
*/
  
}