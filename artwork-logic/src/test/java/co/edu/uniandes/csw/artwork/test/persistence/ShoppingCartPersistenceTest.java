/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.test.persistence;

import co.edu.uniandes.csw.artwork.entities.ArtworkEntity;
import co.edu.uniandes.csw.artwork.entities.ClientEntity;
import co.edu.uniandes.csw.artwork.entities.ShoppingCartItemEntity;
import co.edu.uniandes.csw.artwork.persistence.CreditCardPersistence;
import co.edu.uniandes.csw.artwork.persistence.ShoppingCartItemPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author juan
 */
@RunWith(Arquillian.class)
public class ShoppingCartPersistenceTest {
    
    ArtworkEntity artwork;
    ClientEntity fatherEntity;

    private PodamFactory factory = new PodamFactoryImpl();
    
    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ShoppingCartItemEntity> data = new ArrayList<ShoppingCartItemEntity>();    
    
    @Inject
    private ShoppingCartItemPersistence itemPersistence;    
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ShoppingCartItemEntity.class.getPackage())
                .addPackage(CreditCardPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }    
    
    
    @Before
    public void configTest() {
        try {
            utx.begin();
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
        em.createQuery("delete from ShoppingCartItemEntity").executeUpdate();
        em.createQuery("delete from ArtworkEntity").executeUpdate();
        em.createQuery("delete from ClientEntity").executeUpdate();
    }    
    
    private void insertData() {
        fatherEntity = factory.manufacturePojo(ClientEntity.class);
        fatherEntity.setId(1L);
        em.persist(fatherEntity);
        
        artwork = factory.manufacturePojo(ArtworkEntity.class);
        artwork.setId(1L);
        em.persist(artwork);        
        
        for (int i = 0; i < 3; i++) {
            ShoppingCartItemEntity entity = factory.manufacturePojo(ShoppingCartItemEntity.class);
            entity.setClient(fatherEntity);
            entity.setArtwork(artwork);

            em.persist(entity);
            data.add(entity);
        }           
    }      
    
   /**
     * Prueba para crear un Item.
     */
    @Test
    public void createShoppingCartItemTest() {
        ShoppingCartItemEntity newEntity = factory.manufacturePojo(ShoppingCartItemEntity.class);
        newEntity.setClient(fatherEntity);
        newEntity.setArtwork(artwork);
        
        ShoppingCartItemEntity result = itemPersistence.create(newEntity);
        Assert.assertNotNull(result);

        ShoppingCartItemEntity entity = em.find(ShoppingCartItemEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getName(), entity.getName());
        Assert.assertEquals(newEntity.getQty(), entity.getQty());
        
        Assert.assertEquals(newEntity.getArtwork().getId(), entity.getArtwork().getId());
        Assert.assertEquals(newEntity.getClient().getId(), entity.getClient().getId());
    }        
    
    
    /**
     * Prueba para consultar la lista de Items.
     *
     * @generated
     */
    @Test
    public void getShoppingCartItemsTest() {
        List<ShoppingCartItemEntity> list = itemPersistence.findAll(null, null, fatherEntity.getId(), null);
        Assert.assertEquals(data.size(), list.size());
        for (ShoppingCartItemEntity ent : list) {
            boolean found = false;
            for (ShoppingCartItemEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }      
    
    /**
     * Prueba para consultar un Item.
     */
    @Test
    public void getShoppingCartItemTest() {
        ShoppingCartItemEntity entity = data.get(0);
        ShoppingCartItemEntity newEntity = itemPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getName(), entity.getName());
        Assert.assertEquals(newEntity.getQty(), entity.getQty());
        Assert.assertEquals(newEntity.getArtwork().getId(), entity.getArtwork().getId());
        Assert.assertEquals(newEntity.getClient().getId(), entity.getClient().getId());
    }     
    
    /**
     * Prueba para eliminar un Item.
     */
    @Test
    public void deleteShoppingCartItemTest() {
        ShoppingCartItemEntity entity = data.get(0);
        itemPersistence.delete(entity.getId());
        ShoppingCartItemEntity deleted = em.find(ShoppingCartItemEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }       
    
    /**
     * Prueba para actualizar un Item.
     */
    @Test
    public void updateShoppingCartItemTest() {
        ShoppingCartItemEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        ShoppingCartItemEntity newEntity = factory.manufacturePojo(ShoppingCartItemEntity.class);

        newEntity.setId(entity.getId());
        newEntity.setArtwork(artwork);
        newEntity.setClient(fatherEntity);

        itemPersistence.update(newEntity);
        ShoppingCartItemEntity resp = em.find(ShoppingCartItemEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getId(), resp.getId());
        Assert.assertEquals(newEntity.getName(), resp.getName());
        Assert.assertEquals(newEntity.getQty(), resp.getQty());
        Assert.assertEquals(newEntity.getArtwork().getId(), resp.getArtwork().getId());
        Assert.assertEquals(newEntity.getClient().getId(), resp.getClient().getId());
    }     
}