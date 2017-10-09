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
import Enums.Category;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author raugz
 */
@Stateless
public class ProductFacade extends AbstractFacade<Product> {

    @PersistenceContext(unitName = "persistence unit")
    private EntityManager em;
    
    
    public void merge(Product p){
        em.merge(p);
    }   
    
    @Override
    public void create(Product entity) {
        //addUserToProduct(entity);

        getEntityManager().persist(entity);
        if(!entity.getSeller().getProducts().contains(entity)){
            entity.getSeller().getProducts().add(entity);
        }

    }
    
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public ProductFacade() {
        super(Product.class);
    }
    
    public List<Product> getAllSorted(){
        List<Product> a = findAll();
        Collections.sort(a);
        return a;
    }
    
    /**
     * Creates a product and adds it to the database
     * Handles conversion from Strings to userful objects.
     */
    public Product createProduct(String name, String startingPrice, Category cat, String shipsTo,
                                 String description, String imageURL, String date, String isPublished,
                                 AuctionUser seller){
        
        Product p = new Product();
            
            p.setDescription(description);
            p.setImageURL(imageURL);
            p.setName(name);
            p.setCategory(cat);
            p.setShipsTo(shipsTo);
            
            if(isPublished == null)
                p.setIsPublished(false);
            else if(isPublished.equals("on"))
                p.setIsPublished(true);
            else p.setIsPublished(false);
            
            if(!startingPrice.equals(""))
                p.setStartingPrice(Double.parseDouble(startingPrice));
            
            if(!date.equals(""))
                p.setExpirationDate(Date.valueOf(date));
           
            p.setSeller(seller);
                 
            if(!p.getSeller().getProducts().contains(p)){
                p.getSeller().getProducts().add(p);
            }
            
            create(p);
            return p;
        
    }
    
    public String printProductNames(){
        String out ="";
        for(Product a : findAll()){
           out += "Product: " + a.getName() +
                   ", for: " + a.getStartingPrice() + "." + "<br/>" +a.getSeller().getName()  +"<br/>";
        }
        return out;
    }
    
    public String allProductInfo(){
        String out = "";
        return out;
    }

    public void addUserToProduct(Product p) {
        AuctionUser u = findFirstUser();
        if(!u.getProducts().contains(p))
            p.setSeller(u);
    }
    
    public AuctionUser findFirstUser() {
        List a = em.createQuery(
        "SELECT c FROM AuctionUser c").getResultList();
        return (AuctionUser) a.get(0);
    }
    
        
    public String printProductName(int index){
        String out = "";
        List<Product> products= findAll();
        if(products.size() > index){
            out+=products.get(index).getName();
        }
        return out;
    }
    
    public String printDescription(int index){
        String out = "";
        List<Product> products= findAll(); 
        if(index < products.size()){
            out += products.get(index).getDescription();
        }
        return out;
    }
    
        public String printPrice(int index){
        String out = "";
        List<Product> products= findAll(); 
        if(index < products.size()){
            out += "";
        }
        return out;
    }
    public String printSeller(int index){
        String out = "";
        List<Product> products= findAll(); 
        if(index < products.size()){
            out += products.get(index).getSeller().getName();
        }
        return out;
    }
    
        public List<Product> searchForProduct(String searchObject) {
        System.out.println("itemname:: " + searchObject);

        List<Product> listOfAllProducts = findAll();
        List<Product> resultList = new ArrayList();
        for (Product tempProd : listOfAllProducts) {

            if (tempProd.getName().toLowerCase().contains(searchObject.toLowerCase()) && tempProd.isIsPublished()) {
                System.out.println("********Jeg legger til " + tempProd.getName());
                resultList.add(tempProd);
            }
        }
//        for (Product tempProd : resultList) {
//            System.out.println("products in result " + tempProd.getName());
//        }
        return resultList;
    }
}
