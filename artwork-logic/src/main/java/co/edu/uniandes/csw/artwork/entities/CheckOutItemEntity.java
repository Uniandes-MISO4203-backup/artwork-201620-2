/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.entities;

import co.edu.uniandes.csw.crud.spi.entity.BaseEntity;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author juan
 */
@Entity
public class CheckOutItemEntity extends BaseEntity implements Serializable {
    
    private Long qty;
   
    @PodamExclude
    @ManyToOne
    private ArtworkEntity artwork;

    @PodamExclude
    @ManyToOne
    private CheckOutEntity checkOut;    
    
    
    /**
     * Obtiene el atributo artwork.
     *
     * @return atributo artwork.
     * @generated
     */
    public ArtworkEntity getArtwork() {
        return artwork;
    }    
    
    /**
     * Establece el valor del atributo qty.
     *
     * @param qty nuevo valor del atributo
     * @generated
     */
    public void setQty(Long qty){
        this.qty = qty;
    } 
    
    /**
     * Establece el valor del atributo artwork.
     *
     * @param artwork nuevo valor del atributo
     * @generated
     */
    public void setArtwork(ArtworkEntity artwork) {
        this.artwork = artwork;
    }    

   
        /**
     * Obtiene el atributo qty.
     *
     * @return atributo qty.
     * @generated
     */
    public Long getQty(){
        return qty;
    }    

    public CheckOutEntity getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(CheckOutEntity checkOut) {
        this.checkOut = checkOut;
    }
}
