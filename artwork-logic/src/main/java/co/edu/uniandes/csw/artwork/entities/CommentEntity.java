package co.edu.uniandes.csw.artwork.entities;

import co.edu.uniandes.csw.crud.spi.entity.BaseEntity;
import java.io.Serializable;
import javax.persistence.Entity;
import uk.co.jemos.podam.common.PodamExclude;
import javax.persistence.ManyToOne;

@Entity
public class CommentEntity extends BaseEntity implements Serializable {
    @PodamExclude
    @ManyToOne
    private ArtworkEntity artwork;
    
    private String message;

    public ArtworkEntity getArtwork() {
        return artwork;
    }

    public void setArtwork(ArtworkEntity artwork) {
        this.artwork = artwork;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
