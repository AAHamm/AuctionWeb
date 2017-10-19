/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Singletons;

import Entities.AuctionUser;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Åsmund
 */
@Singleton
//@Startup/home/armons/NetBeansProjects/AuctionWeb/nbproject/build-impl.xml:1045
public class LoadAddressData {
//@PersistenceContext(unitName = "AuctionWebSingleton")
private EntityManager em;
@PostConstruct

public void createData() {
    AuctionUser u = new AuctionUser();
    u.setName("Viper");
    u.setPassword("Hahaaa");
    u.setPic("http://i.imgur.com/wvtM1lG.jpg");
    u.setDescription("ey, pm me fo goo shie");
    em.persist(u); // persist operation is cascaded
    
}
}