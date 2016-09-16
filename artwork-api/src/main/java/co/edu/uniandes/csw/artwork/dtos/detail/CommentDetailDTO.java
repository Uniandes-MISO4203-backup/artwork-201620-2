/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.dtos.detail;

import co.edu.uniandes.csw.artwork.dtos.minimum.ArtworkDTO;
import co.edu.uniandes.csw.artwork.dtos.minimum.CommentDTO;
import co.edu.uniandes.csw.artwork.entities.CommentEntity;
import javax.xml.bind.annotation.XmlRootElement;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author cf.agudelo12
 */
@XmlRootElement
public class CommentDetailDTO extends CommentDTO {
    @PodamExclude
    private ArtworkDTO artwork;
    
    public CommentDetailDTO() {
        super();
    }
    
    public CommentDetailDTO(CommentEntity entity) {
        super(entity);
        if (entity.getArtwork()!=null){
            this.artwork = new ArtworkDTO(entity.getArtwork());
        }
    }
    
    @Override
    public CommentEntity toEntity() {
        CommentEntity entity = super.toEntity();
        if (this.getArtwork()!=null){
            entity.setArtwork(this.getArtwork().toEntity());
        }
        return entity;
    }

    public ArtworkDTO getArtwork() {
        return artwork;
    }

    public void setArtwork(ArtworkDTO artwork) {
        this.artwork = artwork;
    }
}
