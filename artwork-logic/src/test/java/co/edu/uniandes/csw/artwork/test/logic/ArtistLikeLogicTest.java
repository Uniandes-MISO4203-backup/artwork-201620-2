/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.test.logic;


import co.edu.uniandes.csw.artwork.api.IArtistLikeLogic;
import co.edu.uniandes.csw.artwork.ejbs.ArtistLikeLogic;
import co.edu.uniandes.csw.artwork.entities.ArtistEntity;
import co.edu.uniandes.csw.artwork.entities.ArtistLikeEntity;
import co.edu.uniandes.csw.artwork.entities.ClientEntity;
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
public class ArtistLikeLogicTest {
    /**
     * @generated
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ArtistLikeEntity.class.getPackage())
                .addPackage(IArtistLikeLogic.class.getPackage())
                .addPackage(ArtistLikeLogic.class.getPackage())
                .addPackage(ArtistLikePersistance.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * @generated
     */
    private PodamFactory factory = new PodamFactoryImpl();
    /**
     * @generated
     */
    @Inject
    private IArtistLikeLogic artistLikeLogic;

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

    
    private ClientEntity clientEntity1;
    private ArtistEntity artistEntity1;
    
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
        clientEntity1 = factory.manufacturePojo(ClientEntity.class);
        clientEntity1.setId(1L);
        em.persist(clientEntity1);
        
        artistEntity1 = factory.manufacturePojo(ArtistEntity.class);
        artistEntity1.setId(2L);
        em.persist(artistEntity1);
        for (int i = 0; i < 3; i++) {
            ArtistLikeEntity entity = factory.manufacturePojo(ArtistLikeEntity.class);
            entity.setClient(clientEntity1);
            entity.setArtist(artistEntity1);
            em.persist(entity);
            data.add(entity);
        }
    }
    
    
    /**
     * Prueba para crear un like
     *
     * @generated
     */
    @Test
    public void createArtistLikeTest() {
        ArtistLikeEntity newEntity = factory.manufacturePojo(ArtistLikeEntity.class);
        ArtistLikeEntity result = artistLikeLogic.addArtistLike(artistEntity1.getId(), 
                clientEntity1.getId(), newEntity);
        Assert.assertNotNull(result);
        ArtistLikeEntity entity = em.find(ArtistLikeEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getName(), entity.getName());
    }

    /**
     * Prueba para consultar la lista de Nationalities
     *
     * @generated
     */
    @Test
    public void getQualifiesTest() {
        Long score = artistLikeLogic.getNumberOfLikes(clientEntity1.getId());
        Assert.assertNotEquals(0, score.longValue());
    }
}
