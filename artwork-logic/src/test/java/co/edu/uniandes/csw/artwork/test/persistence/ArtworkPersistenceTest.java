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
package co.edu.uniandes.csw.artwork.test.persistence;
import co.edu.uniandes.csw.artwork.entities.ArtistEntity;
import co.edu.uniandes.csw.artwork.entities.ArtworkEntity;
import co.edu.uniandes.csw.artwork.entities.CategoryEntity;
import co.edu.uniandes.csw.artwork.persistence.ArtworkPersistence;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.junit.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class ArtworkPersistenceTest {

    /**
     * @generated
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ArtworkEntity.class.getPackage())
                .addPackage(ArtworkPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * @generated
     */
    ArtistEntity fatherEntity;
    CategoryEntity categoryFatherEntity;
    /**
     * @generated
     */
    @Inject
    private ArtworkPersistence artworkPersistence;

    /**
     * @generated
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * @generated
     */
    @Inject
    UserTransaction utx;

    /**
     * Configuración inicial de la prueba.
     *
     * @generated
     */
    @Before
    public void configTest() {
        try {
            utx.begin();
            em.joinTransaction();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Limpia las tablas que están implicadas en la prueba.
     *
     * @generated
     */
    private void clearData() {
        em.createQuery("delete from ArtworkEntity").executeUpdate();
        em.createQuery("delete from ArtistEntity").executeUpdate();
        em.createQuery("delete from CategoryEntity").executeUpdate();
    }

    /**
     * @generated
     */
    private List<ArtworkEntity> data = new ArrayList<ArtworkEntity>();

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
     *
     * @generated
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
            fatherEntity = factory.manufacturePojo(ArtistEntity.class);
            fatherEntity.setId(1L);
            em.persist(fatherEntity);
            
            categoryFatherEntity = factory.manufacturePojo(CategoryEntity.class);
            categoryFatherEntity.setId(99L);
            em.persist(categoryFatherEntity);
            
        for (int i = 0; i < 3; i++) {
            ArtworkEntity entity = factory.manufacturePojo(ArtworkEntity.class);
            
            entity.setArtist(fatherEntity);
            entity.getCategory().add(categoryFatherEntity);
            em.persist(entity);
            data.add(entity);
        }
    }
    /**
     * Prueba para crear un Artwork.
     *
     * @generated
     */
    @Test
    public void createArtworkTest() {
        PodamFactory factory = new PodamFactoryImpl();
        ArtworkEntity newEntity = factory.manufacturePojo(ArtworkEntity.class);
        newEntity.setArtist(fatherEntity);
        ArtworkEntity result = artworkPersistence.create(newEntity);

        Assert.assertNotNull(result);

        ArtworkEntity entity = em.find(ArtworkEntity.class, result.getId());

        Assert.assertEquals(newEntity.getName(), entity.getName());
        Assert.assertEquals(newEntity.getImage(), entity.getImage());
        Assert.assertEquals(newEntity.getPrice(), entity.getPrice());
        Assert.assertEquals(newEntity.getDescription(), entity.getDescription());
        Assert.assertEquals(newEntity.getVideo(), entity.getVideo());
        Assert.assertEquals(newEntity.getAwards(), entity.getAwards());
        Assert.assertEquals(newEntity.getPlacesVisited(), entity.getPlacesVisited());
        Assert.assertEquals(newEntity.getOtherImages(), entity.getOtherImages());
        Assert.assertEquals(newEntity.getCreationDate().getTime(), entity.getCreationDate().getTime());
    }

    /**
     * Prueba para consultar la lista de Artworks.
     *
     * @generated
     */
    @Test
    public void getArtworksTest() {
        List<ArtworkEntity> list = artworkPersistence.findAll(null, null, fatherEntity.getId());
        Assert.assertEquals(data.size(), list.size());
        for (ArtworkEntity ent : list) {
            boolean found = false;
            for (ArtworkEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    @Test
    public void getArtworksByCategory(){
        List<ArtworkEntity> list = artworkPersistence.getArtworkByCategory(null, null, 
                categoryFatherEntity.getId(), data.get(0).getArtist().getName().substring(0,2));
        Assert.assertEquals(data.size(), list.size());
        for (ArtworkEntity ent : list) {
            boolean found = false;
            for (ArtworkEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    @Test
    public void getArtworksByNullCategory(){
        List<ArtworkEntity> list = artworkPersistence.getArtworkByCategory(null, null, 
                null, data.get(0).getArtist().getName().substring(0,2));
        Assert.assertEquals(data.size(), list.size());
        for (ArtworkEntity ent : list) {
            boolean found = false;
            for (ArtworkEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    @Test
    public void getArtworksNewAdquisition(){
        List<ArtworkEntity> list = artworkPersistence.getArtworksNewAdquisitions();
        Assert.assertTrue(list.size() <= 5);
        for (int i = 0; i < list.size() - 1; i ++) {
            Assert.assertTrue(list.get(i).getCreationDate().getTime() >=
                list.get(i+1).getCreationDate().getTime());
        }
    }
    
    /**
     * Prueba para consultar un Artwork.
     *
     * @generated
     */
    @Test
    public void getArtworkTest() {
        ArtworkEntity entity = data.get(0);
        ArtworkEntity newEntity = artworkPersistence.find(entity.getArtist().getId(), entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getName(), newEntity.getName());
        Assert.assertEquals(entity.getImage(), newEntity.getImage());
        Assert.assertEquals(entity.getPrice(), newEntity.getPrice());
        Assert.assertEquals(entity.getDescription(), newEntity.getDescription());
        Assert.assertEquals(entity.getVideo(), newEntity.getVideo());
        Assert.assertEquals(entity.getAwards(), newEntity.getAwards());
        Assert.assertEquals(entity.getPlacesVisited(), newEntity.getPlacesVisited());
        Assert.assertEquals(entity.getOtherImages(), newEntity.getOtherImages());
        Assert.assertEquals(entity.getCreationDate().getTime(), newEntity.getCreationDate().getTime());

    }

    /**
     * Prueba para eliminar un Artwork.
     *
     * @generated
     */
    @Test
    public void deleteArtworkTest() {
        ArtworkEntity entity = data.get(0);
        artworkPersistence.delete(entity.getId());
        ArtworkEntity deleted = em.find(ArtworkEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar un Artwork.
     *
     * @generated
     */
    @Test
    public void updateArtworkTest() {
        ArtworkEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        ArtworkEntity newEntity = factory.manufacturePojo(ArtworkEntity.class);

        newEntity.setId(entity.getId());

        artworkPersistence.update(newEntity);

        ArtworkEntity resp = em.find(ArtworkEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getName(), resp.getName());
        Assert.assertEquals(newEntity.getImage(), resp.getImage());
        Assert.assertEquals(newEntity.getPrice(), resp.getPrice());
        Assert.assertEquals(newEntity.getDescription(), resp.getDescription());
        Assert.assertEquals(newEntity.getVideo(), resp.getVideo());
        Assert.assertEquals(newEntity.getAwards(), resp.getAwards());
        Assert.assertEquals(newEntity.getPlacesVisited(), resp.getPlacesVisited());
        Assert.assertEquals(newEntity.getOtherImages(), resp.getOtherImages());
        Assert.assertEquals(newEntity.getCreationDate().getTime(), resp.getCreationDate().getTime());
    }
}
