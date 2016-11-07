/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.persistence;

import co.edu.uniandes.csw.artwork.entities.ShoppingCartItemEntity;
import co.edu.uniandes.csw.crud.spi.persistence.CrudPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author juan
 */
@Stateless
public class ShoppingCartItemPersistence  extends CrudPersistence<ShoppingCartItemEntity> {
    
    @PersistenceContext(unitName="ArtworkPU")
    protected EntityManager em;

        /**
     * @generated
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * @generated
     */
    @Override
    protected Class<ShoppingCartItemEntity> getEntityClass() {
        return ShoppingCartItemEntity.class;
    }
    
    public List<ShoppingCartItemEntity> findAll(Integer page, Integer maxRecords, Long clientid, Long artworkId) {
        String query = "select p from ShoppingCartItemEntity p where (p.client.id = :clientid";
        
        if (artworkId != null) {
            query += " AND p.artwork.id = :artworkid";
        }
        
        query += ")";
        
        TypedQuery<ShoppingCartItemEntity> q = em.createQuery(query, ShoppingCartItemEntity.class);
        q.setParameter("clientid", clientid);
        
        if (artworkId != null) {
            q.setParameter("artworkid", artworkId);
        }        
        
        if (page != null && maxRecords != null) {
            q.setFirstResult((page - 1) * maxRecords);
            q.setMaxResults(maxRecords);
        }
        return q.getResultList();
    }    
}
