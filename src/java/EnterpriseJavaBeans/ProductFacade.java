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
import javax.persistence.Query;

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
     * 
     * @param cat
     * @return All products within the given category
     */
    public List<Product> getAllCategory(Category cat) {
        String in = cat.toString();
        Query query = em.createQuery("SELECT p FROM Product p WHERE p.category = :in");
        List<Product> products
                = query.setParameter("in", in).getResultList();
        return products;
    }
    
    /**
     * Creates a product and adds it to the database
     * Handles conversion from Strings to userful objects.
     */
    public Product createProduct(String name, String startingPrice, String cat, String shipsTo,
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
    
    
    /**
     * Search function for Product based on search keyword provided by searchObject
     * 
     * @param searchObject
     * @return 
     */
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
