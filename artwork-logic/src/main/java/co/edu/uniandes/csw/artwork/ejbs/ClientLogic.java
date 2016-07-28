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

import co.edu.uniandes.csw.artwork.api.IClientLogic;
import co.edu.uniandes.csw.artwork.entities.ClientEntity;
import co.edu.uniandes.csw.artwork.persistence.ClientPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;

/**
 * @generated
 */
@Stateless
public class ClientLogic implements IClientLogic {

    @Inject private ClientPersistence persistence;


    /**
     * Obtiene el número de registros de Client.
     *
     * @return Número de registros de Client.
     * @generated
     */
    public int countClients() {
        return persistence.count();
    }

    /**
     * Obtiene la lista de los registros de Client.
     *
     * @return Colección de objetos de ClientEntity.
     * @generated
     */
    @Override
    public List<ClientEntity> getClients() {
        return persistence.findAll();
    }

    /**
     * Obtiene la lista de los registros de Client indicando los datos para la paginación.
     *
     * @param page Número de página.
     * @param maxRecords Número de registros que se mostraran en cada página.
     * @return Colección de objetos de ClientEntity.
     * @generated
     */
    @Override
    public List<ClientEntity> getClients(Integer page, Integer maxRecords) {
        return persistence.findAll(page, maxRecords);
    }

    /**
     * Obtiene los datos de una instancia de Client a partir de su ID.
     *
     * @param id Identificador de la instancia a consultar
     * @return Instancia de ClientEntity con los datos del Client consultado.
     * @generated
     */
    public ClientEntity getClient(Long id) {
        return persistence.find(id);
    }

    /**
     * Se encarga de crear un Client en la base de datos.
     *
     * @param entity Objeto de ClientEntity con los datos nuevos
     * @return Objeto de ClientEntity con los datos nuevos y su ID.
     * @generated
     */
    @Override
    public ClientEntity createClient(ClientEntity entity) {
        persistence.create(entity);
        return entity;
    }

    /**
     * Actualiza la información de una instancia de Client.
     *
     * @param entity Instancia de ClientEntity con los nuevos datos.
     * @return Instancia de ClientEntity con los datos actualizados.
     * @generated
     */
    @Override
    public ClientEntity updateClient(ClientEntity entity) {
        return persistence.update(entity);
    }

    /**
     * Elimina una instancia de Client de la base de datos.
     *
     * @param id Identificador de la instancia a eliminar.
     * @generated
     */
    @Override
    public void deleteClient(Long id) {
        persistence.delete(id);
    }
  
}
