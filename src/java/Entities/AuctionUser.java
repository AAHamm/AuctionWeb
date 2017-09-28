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


/**
 *
 * @author raugz
 */
@Entity
public class AuctionUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id

    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String password;
        
    @OneToMany(mappedBy="seller", cascade = CascadeType.PERSIST)
    /*@JoinTable
    (
      name="USR_PRODUCTS",
      joinColumns={ @JoinColumn(name="ID", referencedColumnName="ID") },
      inverseJoinColumns={ @JoinColumn(name="PROD_ID", referencedColumnName="ID", unique=false) }
    )*/
    private List<Product> products = new ArrayList();
    
    @OneToMany(mappedBy="buyer", cascade = CascadeType.PERSIST)
    private List<Bid> bids = new ArrayList();

    public AuctionUser() {
    }

    public List<Bid> getBids() {
        return bids;
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
