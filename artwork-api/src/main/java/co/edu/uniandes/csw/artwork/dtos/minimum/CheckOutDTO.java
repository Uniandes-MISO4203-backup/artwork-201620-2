/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.dtos.minimum;

import co.edu.uniandes.csw.artwork.entities.CheckOutEntity;
import co.edu.uniandes.csw.artwork.entities.CheckOutItemEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author juan
 */
@XmlRootElement
public class CheckOutDTO  implements Serializable{
    
    private Long id;
    
    private Long total;
    
    @PodamExclude
    private CreditCardDTO creditCard;
    
    private Date checkOutDate;
    
    private String shippingAddress;    
    
    private List<CheckOutItemDTO> checkOutItems;
    
    /**
     * Convierte una lista de ItemEntity a una lista de ItemDetailDTO
     *
     * @param entityList Lista de ItemEntity a convertir
     * @return Lista de ItemDetailDTO convertida
     * @generated
     */
    private List<CheckOutItemDTO> listEntity2DTO(List<CheckOutItemEntity> entityList){
        List<CheckOutItemDTO> list = new ArrayList<>();
        for (CheckOutItemEntity entity : entityList) {
            list.add(new CheckOutItemDTO(entity));
        }
        return list;
    }     
    
    public CheckOutDTO(CheckOutEntity entity) {
        this.setId(entity.getId());
        this.setTotal(entity.getTotal());
        
        this.setCheckOutDate(entity.getCheckOutDate());
        this.setTotal(entity.getTotal());
        this.setShippingAddress(entity.getShippingAddress());
        this.setCreditCard(new CreditCardDTO(entity.getCreditCard()));
        this.setCheckOutItems(listEntity2DTO(entity.getCheckOutItemsList()));
    }

    public CheckOutDTO() {
    }
    
    /**
     * Convierte un objeto ItemDTO a ItemEntity.
     *
     * @return Nueva objeto ItemEntity.
     * @generated
     */
    public CheckOutEntity toEntity() {
        CheckOutEntity entity = new CheckOutEntity();
        entity.setId(this.getId());
        entity.setCreditCard(this.getCreditCard().toEntity());
        entity.setShippingAddress(this.getShippingAddress());
        
        return entity;
    }      

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CreditCardDTO getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCardDTO creditCard) {
        this.creditCard = creditCard;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public List<CheckOutItemDTO> getCheckOutItems() {
        return checkOutItems;
    }

    public void setCheckOutItems(List<CheckOutItemDTO> checkOutItems) {
        this.checkOutItems = checkOutItems;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
