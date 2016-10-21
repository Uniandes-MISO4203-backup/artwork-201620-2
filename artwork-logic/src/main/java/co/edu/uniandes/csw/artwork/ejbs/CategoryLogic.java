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
package co.edu.uniandes.csw.artwork.ejbs;

import co.edu.uniandes.csw.artwork.api.ICategoryLogic;
import co.edu.uniandes.csw.artwork.entities.CategoryEntity;
import co.edu.uniandes.csw.artwork.persistence.CategoryPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @generated
 */
@Stateless
public class CategoryLogic implements ICategoryLogic {

    @Inject private CategoryPersistence persistence;


    /**
     * Obtiene el número de registros de Category.
     *
     * @return Número de registros de Category.
     * @generated
     */
    @Override
    public int countCategorys() {
        return persistence.count();
    }

    /**
     * Obtiene la lista de los registros de Category.
     *
     * @return Colección de objetos de CategoryEntity.
     * @generated
     */
    @Override
    public List<CategoryEntity> getCategorys() {
        return persistence.findAll();
    }

    /**
     * Obtiene la lista de los registros de Category indicando los datos para la paginación.
     *
     * @param page Número de página.
     * @param maxRecords Número de registros que se mostraran en cada página.
     * @return Colección de objetos de CategoryEntity.
     * @generated
     */
    @Override
    public List<CategoryEntity> getCategorys(Integer page, Integer maxRecords) {
        return persistence.findAll(page, maxRecords);
    }
    
     /**
     * Obtiene la lista de los registros de Categorys padres.
     *
     * @return Colección de objetos de CategoryEntity.
     * @generated
     */
    @Override
    public List<CategoryEntity> getParentsCategory() {
        return persistence.getParentsCategory();
    }
    
    /**
     * Obtiene la lista de los registros de Category indicando su padre.
     *
     * * @param parentCategoryid Categoria padre.
     * @param parentCategoryid
     * @return Colección de objetos de CategoryEntity.
     * @generated
     */
    @Override
    public List<CategoryEntity> getCategoryByParent(Long parentCategoryid) {
        return persistence.getCategoryByParent(parentCategoryid);
    }

    /**
     * Obtiene los datos de una instancia de Category a partir de su ID.
     *
     * @param id Identificador de la instancia a consultar
     * @return Instancia de CategoryEntity con los datos del Category consultado.
     * @generated
     */
    @Override
    public CategoryEntity getCategory(Long id) {
        return persistence.find(id);
    }

    /**
     * Se encarga de crear un Category en la base de datos.
     *
     * @param entity Objeto de CategoryEntity con los datos nuevos
     * @return Objeto de CategoryEntity con los datos nuevos y su ID.
     * @generated
     */
    @Override
    public CategoryEntity createCategory(CategoryEntity entity) {
        persistence.create(entity);
        return entity;
    }

    /**
     * Actualiza la información de una instancia de Category.
     *
     * @param entity Instancia de CategoryEntity con los nuevos datos.
     * @return Instancia de CategoryEntity con los datos actualizados.
     * @generated
     */
    @Override
    public CategoryEntity updateCategory(CategoryEntity entity) {
        return persistence.update(entity);
    }

    /**
     * Elimina una instancia de Category de la base de datos.
     *
     * @param id Identificador de la instancia a eliminar.
     * @generated
     */
    @Override
    public void deleteCategory(Long id) {
        persistence.delete(id);
    }
  
}
