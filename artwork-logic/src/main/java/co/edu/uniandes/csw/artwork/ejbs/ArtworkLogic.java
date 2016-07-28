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

import co.edu.uniandes.csw.artwork.api.IArtworkLogic;
import co.edu.uniandes.csw.artwork.entities.ArtworkEntity;
import co.edu.uniandes.csw.artwork.persistence.ArtworkPersistence;
import co.edu.uniandes.csw.artwork.api.IArtistLogic;
import co.edu.uniandes.csw.artwork.entities.ArtistEntity;
import co.edu.uniandes.csw.artwork.entities.CategoryEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;

/**
 * @generated
 */
@Stateless
public class ArtworkLogic implements IArtworkLogic {

    @Inject private ArtworkPersistence persistence;

    @Inject
    private IArtistLogic artistLogic;

    /**
     * Obtiene el número de registros de Artwork.
     *
     * @return Número de registros de Artwork.
     * @generated
     */
    public int countArtworks() {
        return persistence.count();
    }

    /**
     * Obtiene la lista de los registros de Artwork que pertenecen a un Artist.
     *
     * @param artistid id del Artist el cual es padre de los Artworks.
     * @return Colección de objetos de ArtworkEntity.
     * @generated
     */
    @Override
    public List<ArtworkEntity> getArtworks(Long artistid) {
        ArtistEntity artist = artistLogic.getArtist(artistid);
        return artist.getArtworks();
    }

    /**
     * Obtiene la lista de los registros de Artwork que pertenecen a un Artist indicando los datos para la paginación.
     *
     * @param page Número de página.
     * @param maxRecords Número de registros que se mostraran en cada página.
     * @param artistid id del Artist el cual es padre de los Artworks.
     * @return Colección de objetos de ArtworkEntity.
     * @generated
     */
    @Override
    public List<ArtworkEntity> getArtworks(Integer page, Integer maxRecords, Long artistid) {
        if (artistid!=null){
        return persistence.findAll(page, maxRecords, artistid);    
        }else{
        return persistence.findAll(page, maxRecords);    
        }
    }
    
    /**
     * Obtiene la lista de los registros de Artwork por categoria.
     *
     * @param page Número de página.
     * @param maxRecords Número de registros que se mostraran en cada página.
     * @param categoryid id de la categoria.
     * @return Colección de objetos de ArtworkEntity.
     * @generated
     */
    @Override
    public List<ArtworkEntity> getArtworkByCategory(Integer page, Integer maxRecords, Long categoryid) {        
        return persistence.getArtworkByCategory(page, maxRecords,categoryid);  
        
    }

    /**
     * Obtiene los datos de una instancia de Artwork a partir de su ID.
     *
     * @pre La existencia del elemento padre Artist se debe garantizar.
     * @param artworkid) Identificador del Artwork a consultar
     * @return Instancia de ArtworkEntity con los datos del Artwork consultado.
     * @generated
     */
    @Override
    public ArtworkEntity getArtwork(Long artworkid) {
        try {
            return persistence.find(artworkid);
        }catch(NoResultException e){
            throw new IllegalArgumentException("El Artwork no existe");
        }
    }

    /**
     * Se encarga de crear un Artwork en la base de datos.
     *
     * @param entity Objeto de ArtworkEntity con los datos nuevos
     * @param artistid id del Artist el cual sera padre del nuevo Artwork.
     * @return Objeto de ArtworkEntity con los datos nuevos y su ID.
     * @generated
     */
    @Override
    public ArtworkEntity createArtwork(Long artistid, ArtworkEntity entity) {
        ArtistEntity artist = artistLogic.getArtist(artistid);
        entity.setArtist(artist);
        entity = persistence.create(entity);
        return entity;
    }

    /**
     * Actualiza la información de una instancia de Artwork.
     *
     * @param entity Instancia de ArtworkEntity con los nuevos datos.
     * @param artistid id del Artist el cual sera padre del Artwork actualizado.
     * @return Instancia de ArtworkEntity con los datos actualizados.
     * @generated
     */
    @Override
    public ArtworkEntity updateArtwork(Long artistid, ArtworkEntity entity) {
        ArtistEntity artist = artistLogic.getArtist(artistid);
        entity.setArtist(artist);
        return persistence.update(entity);
    }

    /**
     * Elimina una instancia de Artwork de la base de datos.
     *
     * @param id Identificador de la instancia a eliminar.
     * @param artistid id del Artist el cual es padre del Artwork.
     * @generated
     */
    @Override
    public void deleteArtwork(Long id) {
        ArtworkEntity old = getArtwork(id);
        persistence.delete(old.getId());
    }
  

    /**
     * Obtiene una colección de instancias de CategoryEntity asociadas a una
     * instancia de Artwork
     *
     * @param artworkId Identificador de la instancia de Artwork
     * @return Colección de instancias de CategoryEntity asociadas a la instancia de Artwork
     * @generated
     */
    @Override
    public List<CategoryEntity> listCategory(Long artworkId) {
        return getArtwork(artworkId).getCategory();
    }

    /**
     * Obtiene una instancia de CategoryEntity asociada a una instancia de Artwork
     *
     * @param artworkId Identificador de la instancia de Artwork
     * @param categoryId Identificador de la instancia de Category
     * @generated
     */
    @Override
    public CategoryEntity getCategory(Long artworkId, Long categoryId) {
        List<CategoryEntity> list = getArtwork(artworkId).getCategory();
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(categoryId);
        int index = list.indexOf(categoryEntity);
        if (index >= 0) {
            return list.get(index);
        }
        return null;
    }

    /**
     * Asocia un Category existente a un Artwork
     *
     * @param artworkId Identificador de la instancia de Artwork
     * @param categoryId Identificador de la instancia de Category
     * @return Instancia de CategoryEntity que fue asociada a Artwork
     * @generated
     */
    @Override
    public CategoryEntity addCategory(Long artworkId, Long categoryId) {
        ArtworkEntity artworkEntity = getArtwork(artworkId);
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(categoryId);
        artworkEntity.getCategory().add(categoryEntity);
        return getCategory(artworkId, categoryId);
    }

    /**
     * Remplaza las instancias de Category asociadas a una instancia de Artwork
     *
     * @param artworkId Identificador de la instancia de Artwork
     * @param list Colección de instancias de CategoryEntity a asociar a instancia de Artwork
     * @return Nueva colección de CategoryEntity asociada a la instancia de Artwork
     * @generated
     */
    @Override
    public List<CategoryEntity> replaceCategory(Long artworkId, List<CategoryEntity> list) {
        ArtworkEntity artworkEntity = getArtwork(artworkId);
        artworkEntity.setCategory(list);
        return artworkEntity.getCategory();
    }

    /**
     * Desasocia un Category existente de un Artwork existente
     *
     * @param artworkId Identificador de la instancia de Artwork
     * @param categoryId Identificador de la instancia de Category
     * @generated
     */
    @Override
    public void removeCategory(Long artworkId, Long categoryId) {
        ArtworkEntity entity = getArtwork(artworkId);
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(categoryId);
        entity.getCategory().remove(categoryEntity);
    }
}
