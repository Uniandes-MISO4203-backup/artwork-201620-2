/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.persistence;

import co.edu.uniandes.csw.artwork.entities.MessageEntity;
import co.edu.uniandes.csw.crud.spi.persistence.CrudPersistence;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author juan
 */
@Stateless
public class MessagePersistence extends CrudPersistence<MessageEntity>{
    @PersistenceContext(unitName="ArtworkPU")
    protected EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected Class<MessageEntity> getEntityClass() {
        return MessageEntity.class;
    }    
    
    @Override
    public MessageEntity create(MessageEntity entity) {
        entity.setSentDate(new Date());
        return super.create(entity);
    }
    
    public int count(Long clientid) {
        Query q = em.createQuery("select count(p) from MessageEntity p where (p.client.id = :clientid)");
        q.setParameter("clientid", clientid);
        return Integer.parseInt(q.getSingleResult().toString());
    }      
    
    public List<MessageEntity> findAll(Integer page, Integer maxRecords, Long clientid) {
        TypedQuery<MessageEntity> q = em.createQuery("select p from MessageEntity p where (p.client.id = :clientid)", MessageEntity.class);
        q.setParameter("clientid", clientid);
        if (page != null && maxRecords != null) {
            q.setFirstResult((page - 1) * maxRecords);
            q.setMaxResults(maxRecords);
        }
        return q.getResultList();
    }    
    
    public MessageEntity find(Long clientid, Long messageid) {
        TypedQuery<MessageEntity> q = em.createQuery("select p from MessageEntity p where (p.client.id = :clientid) and (p.id = :messageid)", MessageEntity.class);
        q.setParameter("clientid", clientid);
        q.setParameter("messageid", messageid);
        return q.getSingleResult();
    }    
}

