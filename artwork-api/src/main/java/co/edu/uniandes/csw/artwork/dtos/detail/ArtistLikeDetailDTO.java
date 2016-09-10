/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.dtos.detail;

import co.edu.uniandes.csw.artwork.dtos.minimum.ArtistDTO;
import co.edu.uniandes.csw.artwork.dtos.minimum.ArtistLikeDTO;
import co.edu.uniandes.csw.artwork.dtos.minimum.ClientDTO;
import co.edu.uniandes.csw.artwork.entities.ArtistLikeEntity;
import javax.xml.bind.annotation.XmlRootElement;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author le.florez602
 */
@XmlRootElement
public class ArtistLikeDetailDTO extends ArtistLikeDTO{
    @PodamExclude
    private ClientDTO client;
    
    @PodamExclude
    private ArtistDTO artist;
    
    
    public ArtistLikeDetailDTO(){
        super();
    }
    
    public ArtistLikeDetailDTO(ArtistLikeEntity entity){
        super(entity);
        if(entity.getClient() != null){
            this.client = new ClientDTO(entity.getClient());
        }
        if(entity.getArtist()!= null){
            this.artist = new ArtistDTO(entity.getArtist());
        }
    }
    
    @Override
    public ArtistLikeEntity toEntity(){
        ArtistLikeEntity entity = super.toEntity();
        if(this.getClient() != null){
            entity.setClient(this.getClient().toEntity());
        }
        if(this.getArtist()!= null){
            entity.setArtist(this.getArtist().toEntity());
        }
        return entity;
    }

    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }

    public ArtistDTO getArtist() {
        return artist;
    }

    public void setArtist(ArtistDTO artist) {
        this.artist = artist;
    }
    
    
}
