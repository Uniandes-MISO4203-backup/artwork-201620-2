/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.dtos.minimum;

import co.edu.uniandes.csw.artwork.entities.CheckOutEntity;
import java.io.Serializable;
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
    
    @PodamExclude
    private CreditCardDTO creditCard;
    
    private Date checkOutDate;
    
    private String shippingAddress;    
    
    private List<CheckOutItemDTO> checkOutItems;
    
    public CheckOutDTO(CheckOutEntity entity) {
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
        
        return entity;
    }      

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
