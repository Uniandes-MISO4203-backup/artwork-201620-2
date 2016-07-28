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

import co.edu.uniandes.csw.artwork.api.IArtistLogic;
import co.edu.uniandes.csw.artwork.entities.ArtistEntity;
import co.edu.uniandes.csw.artwork.persistence.ArtistPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;

/**
 * @generated
 */
@Stateless
public class ArtistLogic implements IArtistLogic {

    @Inject private ArtistPersistence persistence;


    /**
     * Obtiene el número de registros de Artist.
     *
     * @return Número de registros de Artist.
     * @generated
     */
    public int countArtists() {
        return persistence.count();
    }

    /**
     * Obtiene la lista de los registros de Artist.
     *
     * @return Colección de objetos de ArtistEntity.
     * @generated
     */
    @Override
    public List<ArtistEntity> getArtists() {
        return persistence.findAll();
    }

    /**
     * Obtiene la lista de los registros de Artist indicando los datos para la paginación.
     *
     * @param page Número de página.
     * @param maxRecords Número de registros que se mostraran en cada página.
     * @return Colección de objetos de ArtistEntity.
     * @generated
     */
    @Override
    public List<ArtistEntity> getArtists(Integer page, Integer maxRecords) {
        return persistence.findAll(page, maxRecords);
    }

    /**
     * Obtiene los datos de una instancia de Artist a partir de su ID.
     *
     * @param id Identificador de la instancia a consultar
     * @return Instancia de ArtistEntity con los datos del Artist consultado.
     * @generated
     */
    public ArtistEntity getArtist(Long id) {
        return persistence.find(id);
    }

    /**
     * Se encarga de crear un Artist en la base de datos.
     *
     * @param entity Objeto de ArtistEntity con los datos nuevos
     * @return Objeto de ArtistEntity con los datos nuevos y su ID.
     * @generated
     */
    @Override
    public ArtistEntity createArtist(ArtistEntity entity) {
        persistence.create(entity);
        return entity;
    }

    /**
     * Actualiza la información de una instancia de Artist.
     *
     * @param entity Instancia de ArtistEntity con los nuevos datos.
     * @return Instancia de ArtistEntity con los datos actualizados.
     * @generated
     */
    @Override
    public ArtistEntity updateArtist(ArtistEntity entity) {
        return persistence.update(entity);
    }

    /**
     * Elimina una instancia de Artist de la base de datos.
     *
     * @param id Identificador de la instancia a eliminar.
     * @generated
     */
    @Override
    public void deleteArtist(Long id) {
        persistence.delete(id);
    }
  
}
