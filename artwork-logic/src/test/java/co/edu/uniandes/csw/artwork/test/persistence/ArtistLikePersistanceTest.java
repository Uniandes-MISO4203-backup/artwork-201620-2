/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.test.persistence;

import co.edu.uniandes.csw.artwork.entities.ArtistLikeEntity;
import co.edu.uniandes.csw.artwork.persistence.ArtistLikePersistance;
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
 *
 * @author le.florez602
 */
@RunWith(Arquillian.class)
public class ArtistLikePersistanceTest {
    /**
     * @generated
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ArtistLikeEntity.class.getPackage())
                .addPackage(ArtistLikePersistance.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * @generated
     */
    @Inject
    private ArtistLikePersistance artistLikePersistance;

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
        em.createQuery("delete from ArtistEntity").executeUpdate();
        em.createQuery("delete from ArtistLikeEntity").executeUpdate();
        em.createQuery("delete from ClientEntity").executeUpdate();
    }

    /**
     * @generated
     */
    private List<ArtistLikeEntity> data = new ArrayList<ArtistLikeEntity>();

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
     *
     * @generated
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            ArtistLikeEntity entity = factory.manufacturePojo(ArtistLikeEntity.class);
            
            em.persist(entity);
            data.add(entity);
        }
    }
    
     /**
     * Prueba para crear una like
     *
     * @generated
     */
    @Test
    public void createArtistLikeTest() {
        PodamFactory factory = new PodamFactoryImpl();
        ArtistLikeEntity newEntity = factory.manufacturePojo(ArtistLikeEntity.class);
        ArtistLikeEntity result = artistLikePersistance.create(newEntity);

        Assert.assertNotNull(result);

        ArtistLikeEntity entity = em.find(ArtistLikeEntity.class, result.getId());

        Assert.assertEquals(newEntity.getName(), entity.getName());
    }
    
    /**
     * Prueba para consultar los likes
     *
     * @generated
     */
    @Test
    public void getArtistLikesTest() {
        List<ArtistLikeEntity> list = artistLikePersistance.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (ArtistLikeEntity ent : list) {
            boolean found = false;
            for (ArtistLikeEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    /**
     * Prueba para consultar un like.
     *
     * @generated
     */
    @Test
    public void getArtistLikeTest() {
        ArtistLikeEntity entity = data.get(0);
        ArtistLikeEntity newEntity = artistLikePersistance.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(newEntity.getName(), entity.getName());
    }
    
    /**
     * Prueba para eliminar una like.
     *
     * @generated
     */
    @Test
    public void deleteArtistLikeTest() {
        ArtistLikeEntity entity = data.get(0);
        artistLikePersistance.delete(entity.getId());
        ArtistLikeEntity deleted = em.find(ArtistLikeEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
      /**
     * Prueba para actualizar una like.
     *
     * @generated
     */
    @Test
    public void updateArtistLikeTest() {
        ArtistLikeEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        ArtistLikeEntity newEntity = factory.manufacturePojo(ArtistLikeEntity.class);

        newEntity.setId(entity.getId());

        artistLikePersistance.update(newEntity);

        ArtistLikeEntity resp = em.find(ArtistLikeEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getName(), resp.getName());
    }
}
