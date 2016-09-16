/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.persistence;

import co.edu.uniandes.csw.artwork.entities.ArtistLikeEntity;
import co.edu.uniandes.csw.crud.spi.persistence.CrudPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author le.florez602
 */
@Stateless
public class ArtistLikePersistance extends CrudPersistence<ArtistLikeEntity>{
    
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
    protected Class<ArtistLikeEntity> getEntityClass() {
        return ArtistLikeEntity.class;
    }

    public List<ArtistLikeEntity> findAll(Long artistid) {
        TypedQuery<ArtistLikeEntity> q = em.createQuery("select p from ArtistLikeEntity p where (p.artist.id = :artistid)", ArtistLikeEntity.class);
        q.setParameter("artistid", artistid);
        return q.getResultList();
    }
    
}
