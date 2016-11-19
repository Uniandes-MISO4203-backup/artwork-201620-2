/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.persistence;

import co.edu.uniandes.csw.artwork.entities.CheckOutItemEntity;
import co.edu.uniandes.csw.crud.spi.persistence.CrudPersistence;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author juan
 */
public class CheckOutItemPersistence  extends CrudPersistence<CheckOutItemEntity>{
    
    @PersistenceContext(unitName="ArtworkPU")
    protected EntityManager em;
    
    /**
     * @generated
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected Class<CheckOutItemEntity> getEntityClass() {
        return CheckOutItemEntity.class;
    }
}
