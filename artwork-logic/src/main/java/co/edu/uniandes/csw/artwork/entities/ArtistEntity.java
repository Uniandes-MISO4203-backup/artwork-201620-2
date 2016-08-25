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
package co.edu.uniandes.csw.artwork.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import co.edu.uniandes.csw.crud.spi.entity.BaseEntity;
import uk.co.jemos.podam.common.PodamExclude;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;

/**
 * @generated
 */
@Entity
public class ArtistEntity extends BaseEntity implements Serializable {

    @PodamExclude
    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArtworkEntity> artworks = new ArrayList<>();
    
    @PodamExclude
    @ManyToOne
    private NationalityEntity nationality;

    /**
     * Obtiene la colección de artworks.
     *
     * @return colección artworks.
     * @generated
     */
    public List<ArtworkEntity> getArtworks() {
        return artworks;
    }

    /**
     * Establece el valor de la colección de artworks.
     *
     * @param artworks nuevo valor de la colección.
     * @generated
     */
    public void setArtworks(List<ArtworkEntity> artworks) {
        this.artworks = artworks;
    }
    
        /**
     * @return the nationality
     */
    public NationalityEntity getNationality() {
        return nationality;
    }

    /**
     * @param nationality the nationality to set
     */
    public void setNationality(NationalityEntity nationality) {
        this.nationality = nationality;
    }
}
