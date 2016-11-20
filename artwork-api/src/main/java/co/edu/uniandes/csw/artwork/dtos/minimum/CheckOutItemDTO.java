/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.dtos.minimum;

import co.edu.uniandes.csw.artwork.entities.CheckOutItemEntity;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author juan
 */
@XmlRootElement
public class CheckOutItemDTO implements Serializable {
    
    private Long id;

    private Long qty;

    @PodamExclude
    private ArtworkDTO artwork;    

    CheckOutItemDTO(CheckOutItemEntity entity) {
        this.setQty(entity.getQty());
        this.setArtwork(new ArtworkDTO(entity.getArtwork()));
    }

    public CheckOutItemDTO() {
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public ArtworkDTO getArtwork() {
        return artwork;
    }

    public void setArtwork(ArtworkDTO artwork) {
        this.artwork = artwork;
    }
    
    /**
     * Convierte un objeto ItemDTO a ItemEntity.
     *
     * @return Nueva objeto ItemEntity.
     * @generated
     */
    public CheckOutItemEntity toEntity() {
        CheckOutItemEntity entity = new CheckOutItemEntity();
        entity.setId(this.getId());
        entity.setQty(this.getQty());
        
        if (this.getArtwork()!=null){
            entity.setArtwork(this.getArtwork().toEntity());
        }
       
        return entity;
    }      
}
