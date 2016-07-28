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
import co.edu.uniandes.csw.artwork.entities.ProductEntity;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @generated
 */
@XmlRootElement
public class ProductDetailDTO extends ProductDTO{



    /**
     * @generated
     */
    public ProductDetailDTO() {
        super();
    }

    /**
     * Crea un objeto ProductDetailDTO a partir de un objeto ProductEntity incluyendo los atributos de ProductDTO.
     *
     * @param entity Entidad ProductEntity desde la cual se va a crear el nuevo objeto.
     * @generated
     */
    public ProductDetailDTO(ProductEntity entity) {
        super(entity);
        
    }

    /**
     * Convierte un objeto ProductDetailDTO a ProductEntity incluyendo los atributos de ProductDTO.
     *
     * @return Nueva objeto ProductEntity.
     * @generated
     */
    @Override
    public ProductEntity toEntity() {
        ProductEntity entity = super.toEntity();
        return entity;
    }

}
