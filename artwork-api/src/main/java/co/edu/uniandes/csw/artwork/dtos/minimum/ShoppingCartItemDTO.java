/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.dtos.minimum;

import co.edu.uniandes.csw.artwork.entities.ShoppingCartItemEntity;
import javax.xml.bind.annotation.XmlRootElement;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author juan
 */
@XmlRootElement
public class ShoppingCartItemDTO {
   
    private Long id;
    private Long quantity;    
    
    @PodamExclude
    private ArtworkDTO artwork;

    @PodamExclude
    private ClientDTO client;    
    
    /**
     * @generated
     */
    public ShoppingCartItemDTO() {
        /// Constructor vacio usado por el serializador        
    }    
    
    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }    

    public ArtworkDTO getArtwork() {
        return artwork;
    }

    public void setArtwork(ArtworkDTO artwork) {
        this.artwork = artwork;
    }       
    
    /**
     * Obtiene el atributo id.
     *
     * @return atributo id.
     * @generated
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el valor del atributo id.
     *
     * @param id nuevo valor del atributo
     * @generated
     */
    public void setId(Long id) {
        this.id = id;
    }    
    
    /**
     * Obtiene el atributo qty.
     *
     * @return atributo qty.
     * @generated
     */
    public Long getQuantity() {
        return quantity;
    }

    /**
     * Establece el valor del atributo qty.
     *
     * @param quantity nuevo valor del atributo
     * @generated
     */
    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    /**
     * Crea un objeto ItemDetailDTO a partir de un objeto ItemEntity incluyendo los atributos de ItemDTO.
     *
     * @param entity Entidad ItemEntity desde la cual se va a crear el nuevo objeto.
     * @generated
     */
    public ShoppingCartItemDTO(ShoppingCartItemEntity entity) {
        this.setQuantity(entity.getQty());
        this.setId(entity.getId());
        
        if (entity.getArtwork() != null){
            this.artwork = new ArtworkDTO(entity.getArtwork());
        }
        if (entity.getClient() != null){
            this.client = new ClientDTO(entity.getClient());
        }
    }    
    
    
    /**
     * Convierte un objeto ItemDTO a ItemEntity.
     *
     * @return Nueva objeto ItemEntity.
     * @generated
     */
    public ShoppingCartItemEntity toEntity() {
        ShoppingCartItemEntity entity = new ShoppingCartItemEntity();
        entity.setId(this.getId());
        entity.setQty(this.getQuantity());
        
        if (this.getArtwork()!=null){
            entity.setArtwork(this.getArtwork().toEntity());
        }
        if (this.getClient()!=null){
            entity.setClient(this.getClient().toEntity());
        }        
       
        return entity;
    }    
}
