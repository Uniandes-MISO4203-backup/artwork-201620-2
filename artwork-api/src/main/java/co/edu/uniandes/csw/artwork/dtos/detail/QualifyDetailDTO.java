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
import co.edu.uniandes.csw.artwork.entities.QualifyEntity;
import javax.xml.bind.annotation.XmlRootElement;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * @generated
 */
@XmlRootElement
public class QualifyDetailDTO extends QualifyDTO{


    @PodamExclude
    private ArtworkDTO artwork;
    @PodamExclude
    private ClientDTO client;
    /**
     * @generated
     */
    public QualifyDetailDTO() {
        super();
    }

    /**
     * Crea un objeto ArtworkDetailDTO a partir de un objeto ArtworkEntity incluyendo los atributos de ArtworkDTO.
     *
     * @param entity Entidad ArtworkEntity desde la cual se va a crear el nuevo objeto.
     * @generated
     */
    public QualifyDetailDTO(QualifyEntity entity) {
        super(entity);
        if (entity.getArtwork()!=null){
        this.artwork = new ArtworkDTO(entity.getArtwork());
        }
        if (entity.getClient()!=null){
        this.client = new ClientDTO(entity.getClient());
        }
        
    }

    /**
     * Convierte un objeto QualifyDetailDTO a QualifyEntity incluyendo los atributos de QualifyDTO.
     *
     * @return Nueva objeto ArtworkEntity.
     * @generated
     */
    @Override
    public QualifyEntity toEntity() {
        QualifyEntity entity = super.toEntity();
        if (this.getArtwork()!=null){
        entity.setArtwork(this.getArtwork().toEntity());
        }
        if (this.getClient()!=null){
        entity.setClient(this.getClient().toEntity());
        }
        return entity;
    }

   
    /**
     * @return the artwork
     */
    public ArtworkDTO getArtwork() {
        return artwork;
    }

    /**
     * @param artwork the artwork to set
     */
    public void setArtwork(ArtworkDTO artwork) {
        this.artwork = artwork;
    }

    /**
     * @return the client
     */
    public ClientDTO getClient() {
        return client;
    }

    /**
     * @param client the client to set
     */
    public void setClient(ClientDTO client) {
        this.client = client;
    }

}
