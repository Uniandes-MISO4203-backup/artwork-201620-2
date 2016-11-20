/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.persistence;

import co.edu.uniandes.csw.artwork.entities.CheckOutEntity;
import co.edu.uniandes.csw.crud.spi.persistence.CrudPersistence;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author juan
 */
public class CheckOutPersistence extends CrudPersistence<CheckOutEntity>{
    
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
    protected Class<CheckOutEntity> getEntityClass() {
        return CheckOutEntity.class;
    }
    
    public List<CheckOutEntity> findAll(Integer page, Integer maxRecords, Long clientid) {

        TypedQuery<CheckOutEntity> q = em.createQuery("select p from CheckOutEntity p where (p.client.id = :clientid) order by p.checkOutDate DESC", CheckOutEntity.class);
        q.setParameter("clientid", clientid);
        
        if (page != null && maxRecords != null) {
            q.setFirstResult((page - 1) * maxRecords);
            q.setMaxResults(maxRecords);
        }
        
        return q.getResultList();
    }      
}
