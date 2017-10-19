/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;


/**
 *
 * @author raugz
 */
@Entity
@XmlAccessorType( XmlAccessType.FIELD)
public class AuctionUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id

    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String password;
    private String description;
    private String pic;

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
        
    @OneToMany(mappedBy="seller", cascade = CascadeType.PERSIST)
    /*@JoinTable
    (
      name="USR_PRODUCTS",
      joinColumns={ @JoinColumn(name="ID", referencedColumnName="ID") },
      inverseJoinColumns={ @JoinColumn(name="PROD_ID", referencedColumnName="ID", unique=false) }
    )*/
    
    private List<Product> products = new ArrayList();
    

    @OneToMany(mappedBy="buyer", cascade = CascadeType.PERSIST)
    @XmlTransient
    private List<Bid> bids = new ArrayList();

    public AuctionUser() {
    }
   
    public List<Bid> getBids() {
        return bids;
    }
    
    /**
     * Does not work, TODO fix
     * @return The users bids that are still currently highest on product
     */
    public List<Bid> getRelevantBids() {
        List<Bid> resultList = new ArrayList<Bid>();
        //for(Bid b : bids){
            //resultList.add(b);
        //}
        
        List<Product> prodList = new ArrayList<>();
        for (Bid b1 : bids) {
            //if(!prodList.contains(b1.getProduct())){ // product not in list
                //prodList.add(b1.getProduct());
               /*
                if(b1.equals(prodHighest)){
                    resultList.add(b1);
                    System.out.println(b1.getProduct() + " " + b1.getAmount());
                */
               // }
            //}
                /*
            else{
                if(b1.getAmount().equals(b1.getProduct().getStartingPrice())){ // current highest price
                    // we good
                }
                else{
                    resultList.remove(b1);
                }
            }
        */
        }
        return resultList;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    
    
    public void setProducts(List<Product> products) {
        this.products = products;
    }
    
    public List<Product> getProducts() {
        return products;
    }
    
    public void addProduct(Product p){
        this.products.add(p);
        if (p.getSeller() != this) {
            p.setSeller(this);
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AuctionUser)) {
            return false;
        }
        AuctionUser other = (AuctionUser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.AuctionUser[ id=" + id + " ]";
    }
    
}
