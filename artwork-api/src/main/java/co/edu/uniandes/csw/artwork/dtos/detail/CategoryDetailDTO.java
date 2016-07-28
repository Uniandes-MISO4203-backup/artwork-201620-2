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
import co.edu.uniandes.csw.artwork.entities.CategoryEntity;
import javax.xml.bind.annotation.XmlRootElement;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * @generated
 */
@XmlRootElement
public class CategoryDetailDTO extends CategoryDTO{


    @PodamExclude
    private CategoryDTO parentCategory;

    /**
     * @generated
     */
    public CategoryDetailDTO() {
        super();
    }

    /**
     * Crea un objeto CategoryDetailDTO a partir de un objeto CategoryEntity incluyendo los atributos de CategoryDTO.
     *
     * @param entity Entidad CategoryEntity desde la cual se va a crear el nuevo objeto.
     * @generated
     */
    public CategoryDetailDTO(CategoryEntity entity) {
        super(entity);
        if (entity.getParentCategory()!=null){
        this.parentCategory = new CategoryDTO(entity.getParentCategory());
        }
        
    }

    /**
     * Convierte un objeto CategoryDetailDTO a CategoryEntity incluyendo los atributos de CategoryDTO.
     *
     * @return Nueva objeto CategoryEntity.
     * @generated
     */
    @Override
    public CategoryEntity toEntity() {
        CategoryEntity entity = super.toEntity();
        if (this.getParentCategory()!=null){
        entity.setParentCategory(this.getParentCategory().toEntity());
        }
        return entity;
    }

    /**
     * Obtiene el atributo parentCategory.
     *
     * @return atributo parentCategory.
     * @generated
     */
    public CategoryDTO getParentCategory() {
        return parentCategory;
    }

    /**
     * Establece el valor del atributo parentCategory.
     *
     * @param parentCategory nuevo valor del atributo
     * @generated
     */
    public void setParentCategory(CategoryDTO parentcategory) {
        this.parentCategory = parentcategory;
    }

}
