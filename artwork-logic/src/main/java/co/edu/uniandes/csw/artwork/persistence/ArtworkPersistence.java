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
package co.edu.uniandes.csw.artwork.persistence;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import co.edu.uniandes.csw.artwork.entities.ArtworkEntity;
import co.edu.uniandes.csw.crud.spi.persistence.CrudPersistence;
import java.util.List;
import javax.persistence.TypedQuery;

/**
 * @generated
 */
@Stateless
public class ArtworkPersistence extends CrudPersistence<ArtworkEntity> {

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
    protected Class<ArtworkEntity> getEntityClass() {
        return ArtworkEntity.class;
    }

    public ArtworkEntity find(Long artistid, Long artworkid) {
        TypedQuery<ArtworkEntity> q = em.createQuery("select p from ArtworkEntity p where (p.artist.id = :artistid) and (p.id = :artworkid)", ArtworkEntity.class);
        q.setParameter("artistid", artistid);
        q.setParameter("artworkid", artworkid);
        return q.getSingleResult();
    }
    
    public List<ArtworkEntity> findAll(Integer page, Integer maxRecords, Long artistid) {
        TypedQuery<ArtworkEntity> q = em.createQuery("select p from ArtworkEntity p where (p.artist.id = :artistid)", ArtworkEntity.class);
        q.setParameter("artistid", artistid);
        if (page != null && maxRecords != null) {
            q.setFirstResult((page - 1) * maxRecords);
            q.setMaxResults(maxRecords);
        }
        return q.getResultList();
    }
    
    public List<ArtworkEntity> getArtworkByCategory(Integer page, Integer maxRecords, Long categoryid, String artistName) {
        TypedQuery<ArtworkEntity> q;
        if(categoryid == null){
            q = em.createQuery("select p from ArtworkEntity p where p.artist.name LIKE :artistname", ArtworkEntity.class);
        }
        else {
            q = em.createQuery("select p from ArtworkEntity p join  p.category i where i.id = :categoryid AND p.artist.name LIKE :artistname", ArtworkEntity.class);
            q.setParameter("categoryid", categoryid);
        }

        q.setParameter("artistname", "%" + artistName + "%");
        
        if (page != null && maxRecords != null) {
            q.setFirstResult((page - 1) * maxRecords);
            q.setMaxResults(maxRecords);
        }
        return q.getResultList();
    }
    
    public List<ArtworkEntity> getArtworksNewAdquisitions() {
        TypedQuery<ArtworkEntity> q = em.createQuery(
            "select p from ArtworkEntity p ORDER BY p.creationDate DESC", ArtworkEntity.class);
        q.setMaxResults(5);
        return q.getResultList();
    }

}
