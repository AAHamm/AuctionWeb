/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManagedBeans;


import EnterpriseJavaBeans.MessageFacade;

import Entities.Message;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Gard
 */
@Named(value = "MessageView")
@RequestScoped
public class MessageView {
    
    // Creates a new field
    private Message message;
   

    @EJB
    private MessageFacade messageFacade;

    /** Creates a new instance of MessageView */
    public MessageView() {
       int i = 0; //test
       this.message = new Message();
    }

    // Calls getMessage to retrieve the message
    public Message getMessage() {
       //pointless comment
       return message;
    }
    
    // Returns the total number of messages
    public int getNumberOfMessages(){
       return messageFacade.findAll().size();
    }
    
    public String getAllMessages(){
        return messageFacade.printMessages();
    }

    // Saves the message and then returns the string "theend"
    public String postMessage(){
       this.messageFacade.create(message);
       return "theend";
    }
    
    
}
