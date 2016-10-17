/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.dtos.minimum;

import co.edu.uniandes.csw.artwork.entities.CreditCardEntity;
import javax.xml.bind.annotation.XmlRootElement;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author juan
 */
@XmlRootElement
public class CreditCardDTO {
    
    private Long id;
    
    private String number;
    
    private String type;    
    
    private int expirationYear;
    
    private int expirationMonth;
    
    @PodamExclude
    private ClientDTO client;    

    public CreditCardDTO(){
        /// Constructor vacio usado por el serializador
    }    
    
    public CreditCardDTO(CreditCardEntity entity) {
        this.expirationMonth = entity.getExpirationMonth();
        this.expirationYear = entity.getExpirationYear();
        this.number = entity.getNumber();
        this.type = entity.getType();
        this.id = entity.getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(int expirationYear) {
        this.expirationYear = expirationYear;
    }

    public int getExpirationMonth() {
        return expirationMonth;
    }

    public void setExpirationMonth(int expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }

    public CreditCardEntity toEntity() {
        CreditCardEntity entity = new CreditCardEntity();
        
        if (this.getClient() != null){
            entity.setClient(this.getClient().toEntity());
        }

        entity.setExpirationMonth(this.getExpirationMonth());
        entity.setExpirationYear(this.getExpirationYear());
        entity.setNumber(this.getNumber());
        entity.setType(this.getType());
        entity.setId(this.getId());
        
        return entity;
    }
}
