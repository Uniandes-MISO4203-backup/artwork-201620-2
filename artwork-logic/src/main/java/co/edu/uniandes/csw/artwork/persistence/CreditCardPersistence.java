package co.edu.uniandes.csw.artwork.persistence;

import co.edu.uniandes.csw.artwork.entities.CreditCardEntity;
import co.edu.uniandes.csw.artwork.entities.ItemEntity;
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
public class CreditCardPersistence extends CrudPersistence<CreditCardEntity>{
    @PersistenceContext(unitName="ArtworkPU")
    protected EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected Class<CreditCardEntity> getEntityClass() {
        return CreditCardEntity.class;
    }    
    
    public List<CreditCardEntity> findAll(Integer page, Integer maxRecords, Long clientid) {
        TypedQuery<CreditCardEntity> q = em.createQuery("select p from CreditCardEntity p where (p.client.id = :clientid)", CreditCardEntity.class);
        q.setParameter("clientid", clientid);
        if (page != null && maxRecords != null) {
            q.setFirstResult((page - 1) * maxRecords);
            q.setMaxResults(maxRecords);
        }
        return q.getResultList();
    }    
    
    public CreditCardEntity find(Long clientid, Long creditCardId) {
        TypedQuery<CreditCardEntity> q = em.createQuery("select p from CreditCardEntity p where (p.client.id = :clientid) and (p.id = :ccid)", CreditCardEntity.class);
        q.setParameter("clientid", clientid);
        q.setParameter("ccid", creditCardId);
        return q.getSingleResult();
    }    
}
