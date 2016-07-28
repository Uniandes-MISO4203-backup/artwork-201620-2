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
import co.edu.uniandes.csw.artwork.entities.ItemEntity;
import javax.xml.bind.annotation.XmlRootElement;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * @generated
 */
@XmlRootElement
public class ItemDetailDTO extends ItemDTO{


    @PodamExclude
    private ArtworkDTO artwork;
    @PodamExclude
    private ClientDTO client;
    @PodamExclude
    private ProductDTO product;

    /**
     * @generated
     */
    public ItemDetailDTO() {
        super();
    }

    /**
     * Crea un objeto ItemDetailDTO a partir de un objeto ItemEntity incluyendo los atributos de ItemDTO.
     *
     * @param entity Entidad ItemEntity desde la cual se va a crear el nuevo objeto.
     * @generated
     */
    public ItemDetailDTO(ItemEntity entity) {
        super(entity);
        if (entity.getArtwork()!=null){
        this.artwork = new ArtworkDTO(entity.getArtwork());
        }
        if (entity.getClient()!=null){
        this.client = new ClientDTO(entity.getClient());
        }
        if (entity.getProduct()!=null){
        this.product = new ProductDTO(entity.getProduct());
        }
        
    }

    /**
     * Convierte un objeto ItemDetailDTO a ItemEntity incluyendo los atributos de ItemDTO.
     *
     * @return Nueva objeto ItemEntity.
     * @generated
     */
    @Override
    public ItemEntity toEntity() {
        ItemEntity entity = super.toEntity();
        if (this.getArtwork()!=null){
        entity.setArtwork(this.getArtwork().toEntity());
        }
        if (this.getClient()!=null){
        entity.setClient(this.getClient().toEntity());
        }
        if (this.getProduct()!=null){
        entity.setProduct(this.getProduct().toEntity());
        }
        return entity;
    }

    /**
     * Obtiene el atributo artwork.
     *
     * @return atributo artwork.
     * @generated
     */
    public ArtworkDTO getArtwork() {
        return artwork;
    }

    /**
     * Establece el valor del atributo artwork.
     *
     * @param artwork nuevo valor del atributo
     * @generated
     */
    public void setArtwork(ArtworkDTO artwork) {
        this.artwork = artwork;
    }

    /**
     * Obtiene el atributo client.
     *
     * @return atributo client.
     * @generated
     */
    public ClientDTO getClient() {
        return client;
    }

    /**
     * Establece el valor del atributo client.
     *
     * @param client nuevo valor del atributo
     * @generated
     */
    public void setClient(ClientDTO client) {
        this.client = client;
    }

    /**
     * Obtiene el atributo product.
     *
     * @return atributo product.
     * @generated
     */
    public ProductDTO getProduct() {
        return product;
    }

    /**
     * Establece el valor del atributo product.
     *
     * @param product nuevo valor del atributo
     * @generated
     */
    public void setProduct(ProductDTO product) {
        this.product = product;
    }

}
