/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.persistence;

import co.edu.uniandes.csw.artwork.entities.ArtworkEntity;
import co.edu.uniandes.csw.artwork.entities.CommentEntity;
import co.edu.uniandes.csw.crud.spi.persistence.CrudPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author cf.agudelo12
 */
@Stateless
public class CommentPersistence extends CrudPersistence<CommentEntity> {
    @PersistenceContext(unitName="ArtworkPU")
    protected EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public CommentEntity find(Long artworkId, Long commentId) {
        TypedQuery<CommentEntity> q = em.createQuery("SELECT c FROM CommentEntity c WHERE (c.artwork.id = :artworkId) and (c.id = :commentId)", CommentEntity.class);
        q.setParameter("artworkId", artworkId);
        q.setParameter("commentId", commentId);
        return q.getSingleResult();
    }
    
    public List<CommentEntity> findAll(Integer page, Integer maxRecords, Long artworkId) {
        TypedQuery<CommentEntity> q = em.createQuery("SELECT c FROM CommentEntity c WHERE (c.artwork.id = :artworkId)", CommentEntity.class);
        q.setParameter("artworkId", artworkId);
        if (page != null && maxRecords != null) {
            q.setFirstResult((page - 1) * maxRecords);
            q.setMaxResults(maxRecords);
        }
        return q.getResultList();
    }
    
    @Override
    protected Class<CommentEntity> getEntityClass() {
        return CommentEntity.class;
    }
}
