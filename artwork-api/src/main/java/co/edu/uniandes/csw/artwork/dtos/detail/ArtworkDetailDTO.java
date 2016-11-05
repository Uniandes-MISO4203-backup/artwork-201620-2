/*
The MIT License (MIT)

Copyright (c) 2015 Los Andes University

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package co.edu.uniandes.csw.artwork.dtos.detail;

import co.edu.uniandes.csw.artwork.dtos.minimum.*;
import co.edu.uniandes.csw.artwork.entities.ArtworkEntity;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * @generated
 */
@XmlRootElement
public class ArtworkDetailDTO extends ArtworkDTO {

    @PodamExclude
    private ArtistDTO artist;

    private String description;

    private String video;

    @PodamExclude
    private List<OtherImageDTO> otherImages;

    @PodamExclude
    private List<AwardDTO> awards;

    @PodamExclude
    private List<PlaceDTO> places;

    /**
     * @generated
     */
    public ArtworkDetailDTO() {
        super();
    }

    /**
     * Crea un objeto ArtworkDetailDTO a partir de un objeto ArtworkEntity
     * incluyendo los atributos de ArtworkDTO.
     *
     * @param entity Entidad ArtworkEntity desde la cual se va a crear el nuevo
     * objeto.
     * @generated
     */
    public ArtworkDetailDTO(ArtworkEntity entity) {
        super(entity);
        if (entity.getArtist() != null) {
            this.artist = new ArtistDTO(entity.getArtist());

        }
        if(entity.getOtherImages() != null){
            String[] urlImages = entity.getOtherImages().split(",");
            this.otherImages =  new ArrayList<OtherImageDTO>();
            for (String urlImage : urlImages) {
                this.otherImages.add(new OtherImageDTO(urlImage));
            }
        }

        if(entity.getPlacesVisited() != null){
            String[] namePlaces = entity.getPlacesVisited().split(",");
            this.places =  new ArrayList<PlaceDTO>();
            for (String namePlace : namePlaces) {
                this.places.add(new PlaceDTO(namePlace));
            }
        }

        if(entity.getAwards() != null){
            String[] nameAwards = entity.getAwards().split(",");
            this.awards =  new ArrayList<AwardDTO>();
            for (String nameAward : nameAwards) {
                this.awards.add(new AwardDTO(nameAward));
            }
        }
        
        this.description = entity.getDescription();
        this.video = entity.getVideo();

    }

    /**
     * Convierte un objeto ArtworkDetailDTO a ArtworkEntity incluyendo los
     * atributos de ArtworkDTO.
     *
     * @return Nueva objeto ArtworkEntity.
     * @generated
     */
    @Override
    public ArtworkEntity toEntity() {
        ArtworkEntity entity = super.toEntity();
        if (this.getArtist() != null) {
            entity.setArtist(this.getArtist().toEntity());
        }
        
        if(this.getOtherImages()!= null){
            String urlImages = "";
            for (OtherImageDTO otherImage : this.getOtherImages()) {
                urlImages += otherImage.getUrl()+",";
            }
            entity.setOtherImages(urlImages);
        }
        
        if(this.getPlaces()!= null){
            String namePlaces = "";
            for (PlaceDTO place : this.getPlaces()) {
                namePlaces += place.getName()+",";
            }
            entity.setPlacesVisited(namePlaces);
        }
        
        if(this.getAwards()!= null){
            String nameAwards = "";
            for (AwardDTO award : this.getAwards()) {
                nameAwards += award.getName()+",";
            }
            entity.setAwards(nameAwards);
        }
        
        entity.setDescription(this.description);
        entity.setVideo(this.video);
        
        return entity;
    }

    /**
     * Obtiene el atributo artist.
     *
     * @return atributo artist.
     * @generated
     */
    public ArtistDTO getArtist() {
        return artist;
    }

    /**
     * Establece el valor del atributo artist.
     *
     * @param artist nuevo valor del atributo
     * @generated
     */
    public void setArtist(ArtistDTO artist) {
        this.artist = artist;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the video
     */
    public String getVideo() {
        return video;
    }

    /**
     * @param video the video to set
     */
    public void setVideo(String video) {
        this.video = video;
    }

    /**
     * @return the otherImages
     */
    public List<OtherImageDTO> getOtherImages() {
        return otherImages;
    }

    /**
     * @param otherImages the otherImages to set
     */
    public void setOtherImages(List<OtherImageDTO> otherImages) {
        this.otherImages = otherImages;
    }

    /**
     * @return the awards
     */
    public List<AwardDTO> getAwards() {
        return awards;
    }

    /**
     * @param awards the awards to set
     */
    public void setAwards(List<AwardDTO> awards) {
        this.awards = awards;
    }

    /**
     * @return the places
     */
    public List<PlaceDTO> getPlaces() {
        return places;
    }

    /**
     * @param places the places to set
     */
    public void setPlaces(List<PlaceDTO> places) {
        this.places = places;
    }

}
