/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.entities;

import co.edu.uniandes.csw.crud.spi.entity.BaseEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author juan
 */
@Entity
public class CheckOutEntity extends BaseEntity implements Serializable {
    
    @PodamExclude
    @ManyToOne
    private ClientEntity client;    
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date checkOutDate;
    
    private Long total;
    
    private String shippingAddress;
    
    @PodamExclude
    @OneToMany(mappedBy = "checkOut", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CheckOutItemEntity> checkOutItemsList = new ArrayList<>();
    
    @PodamExclude
    @ManyToOne
    private CreditCardEntity creditCard;       
    
    /**
     * Obtiene la colección de checkOutItems.
     *
     * @return colección checkOutItems.
     * @generated
     */
    public List<CheckOutItemEntity> getCheckOutItemsList() {
        return checkOutItemsList;
    }

    /**
     * Establece el valor de la colección de checkOutItems.
     *
     * @param checkOutItemsList nuevo valor de la colección.
     * @generated
     */
    public void setCheckOutItemsList(List<CheckOutItemEntity> checkOutItemsList) {
        this.checkOutItemsList = checkOutItemsList;
    }    
    
    /**
     * Obtiene el atributo client.
     *
     * @return atributo client.
     * @generated
     */
    public ClientEntity getClient() {
        return client;
    }    

    /**
     * Establece el valor del atributo client.
     *
     * @param client nuevo valor del atributo
     * @generated
     */
    public void setClient(ClientEntity client) {
        this.client = client;
    }        
    
    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }    

    public CreditCardEntity getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCardEntity creditCard) {
        this.creditCard = creditCard;
    }    

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
    
    /**
     * Establece el valor del atributo total.
     *
     * @param total nuevo valor del atributo
     * @generated
     */
    public void setTotal(Long total){
        this.total = total;
    }

     /**
     * Obtiene el atributo total.
     *
     * @return atributo total.
     * @generated
     */
    public Long getTotal(){
        return total;
    }     
}
