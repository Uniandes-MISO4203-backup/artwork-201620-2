/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.persistence;

import co.edu.uniandes.csw.artwork.entities.NationalityEntity;
import co.edu.uniandes.csw.crud.spi.persistence.CrudPersistence;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author am.osorio
 */
@Stateless
public class NationalityPersistence extends CrudPersistence<NationalityEntity>{

    @PersistenceContext(unitName="ArtworkPU")
    protected EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected Class<NationalityEntity> getEntityClass() {
        return NationalityEntity.class;
    }
    
}
