/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.test.logic;

import co.edu.uniandes.csw.artwork.api.IShoppingCartItemLogic;
import co.edu.uniandes.csw.artwork.ejbs.ShoppingCartItemLogic;
import co.edu.uniandes.csw.artwork.entities.ArtworkEntity;
import co.edu.uniandes.csw.artwork.entities.ClientEntity;
import co.edu.uniandes.csw.artwork.entities.ShoppingCartItemEntity;
import co.edu.uniandes.csw.artwork.persistence.ShoppingCartItemPersistence;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
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
public class ShoppingCartLogicTest {
    
    ArtworkEntity artwork;
    ArtworkEntity artwork2;    
    ClientEntity fatherEntity;
    Long notExistId = 999L;

    private PodamFactory factory = new PodamFactoryImpl();
    
    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ShoppingCartItemEntity> data = new ArrayList<ShoppingCartItemEntity>();
    
    @Inject
    private IShoppingCartItemLogic shoppingCartItemLogic;
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ShoppingCartItemEntity.class.getPackage())
                .addPackage(ShoppingCartItemLogic.class.getPackage())
                .addPackage(IShoppingCartItemLogic.class.getPackage())
                .addPackage(ShoppingCartItemPersistence.class.getPackage())                
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
        
        artwork2 = factory.manufacturePojo(ArtworkEntity.class);
        artwork2.setId(2L);
        em.persist(artwork2);            
        
        for (int i = 0; i < 3; i++) {
            ShoppingCartItemEntity entity = factory.manufacturePojo(ShoppingCartItemEntity.class);
            entity.setClient(fatherEntity);
            entity.setArtwork(artwork);

            if (i == 0) {
                entity.setQty(1L);
            } 
            else if (i == 1) {
                entity.setQty(10L);
            } 
            
            em.persist(entity);
            data.add(entity);
            
            while (Objects.equals(entity.getId(), notExistId)) {
                notExistId = ThreadLocalRandom.current().nextLong();
            }            
        }           
    }    
    
    /**
     * Prueba para consultar un Item
     *
     * @generated
     */
    @Test
    public void getShoppingCartItemTest() {
        ShoppingCartItemEntity entity = data.get(0);
        ShoppingCartItemEntity resultEntity = shoppingCartItemLogic.getItem(entity.getId());
        Assert.assertNotNull(resultEntity);
        
        Assert.assertEquals(resultEntity.getId(), entity.getId());
        Assert.assertEquals(resultEntity.getName(), entity.getName());
        Assert.assertEquals(resultEntity.getQty(), entity.getQty());
        
        Assert.assertEquals(resultEntity.getArtwork().getId(), entity.getArtwork().getId());
        Assert.assertEquals(resultEntity.getClient().getId(), entity.getClient().getId());
    }     
    
    
    /**
     * Prueba para actualizar un Item
     *
     * @generated
     */
    @Test
    public void updateShoppingCartItemTest() {
        ShoppingCartItemEntity entity = data.get(0);
        ShoppingCartItemEntity pojoEntity = factory.manufacturePojo(ShoppingCartItemEntity.class);
        
        pojoEntity.setId(entity.getId());
        pojoEntity.setClient(fatherEntity);
        pojoEntity.setArtwork(artwork);
        
        shoppingCartItemLogic.updateItem(fatherEntity.getId(), pojoEntity);

        ShoppingCartItemEntity resp = em.find(ShoppingCartItemEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getName(), resp.getName());
        Assert.assertEquals(pojoEntity.getQty(), resp.getQty());
        
        Assert.assertEquals(pojoEntity.getArtwork().getId(), resp.getArtwork().getId());
        Assert.assertEquals(pojoEntity.getClient().getId(), resp.getClient().getId());
    } 
    
    
   /**
     * Prueba para crear un Item
     *
     * @generated
     */
    @Test
    public void increaseQtyShoppingCartItemTest() {
        ShoppingCartItemEntity newEntity = factory.manufacturePojo(ShoppingCartItemEntity.class);
        newEntity.setClient(fatherEntity);
        newEntity.setArtwork(artwork2);
        Long expectedQty = newEntity.getQty() + 1;
        
        ShoppingCartItemEntity newItem = shoppingCartItemLogic.addItem(fatherEntity.getId(), newEntity);
        Assert.assertNotNull(newItem);
        
        ShoppingCartItemEntity result = shoppingCartItemLogic.addItem(fatherEntity.getId(), newEntity);
        Assert.assertNotNull(newItem);        
        
        ShoppingCartItemEntity entity = em.find(ShoppingCartItemEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getName(), entity.getName());
        Assert.assertEquals(expectedQty, entity.getQty());
        
        Assert.assertEquals(newEntity.getArtwork().getId(), entity.getArtwork().getId());
        Assert.assertEquals(newEntity.getClient().getId(), entity.getClient().getId());
    }    
    
    
   /**
     * Prueba para crear un Item
     *
     * @generated
     */
    @Test
    public void createShoppingCartItemTest() {
        ShoppingCartItemEntity newEntity = factory.manufacturePojo(ShoppingCartItemEntity.class);
        newEntity.setClient(fatherEntity);
        newEntity.setArtwork(artwork2);
        
        ShoppingCartItemEntity result = shoppingCartItemLogic.addItem(fatherEntity.getId(), newEntity);
        Assert.assertNotNull(result);
        
        ShoppingCartItemEntity entity = em.find(ShoppingCartItemEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getName(), entity.getName());
        Assert.assertEquals(newEntity.getQty(), entity.getQty());
        
        Assert.assertEquals(newEntity.getArtwork().getId(), entity.getArtwork().getId());
        Assert.assertEquals(newEntity.getClient().getId(), entity.getClient().getId());
    }
    
    /**
     * Prueba para consultar la lista
     *
     * @generated
     */
    @Test
    public void getShoppingCartItemsTest() {
        List<ShoppingCartItemEntity> list = shoppingCartItemLogic.getItems(fatherEntity.getId());
        Assert.assertEquals(data.size(), list.size());
        for (ShoppingCartItemEntity entity : list) {
            boolean found = false;
            for (ShoppingCartItemEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }       
    
    /**
     * Prueba para consultar la lista
     *
     * @generated
     */
    @Test
    public void getShoppingCartItemPaginatedTest() {
        List<ShoppingCartItemEntity> list = shoppingCartItemLogic.getItems(1, 10, fatherEntity.getId(), null);
        Assert.assertEquals(data.size(), list.size());
        for (ShoppingCartItemEntity entity : list) {
            boolean found = false;
            for (ShoppingCartItemEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }      


    /**
     * Prueba para eliminar un Item
     *
     * @generated
     */
    @Test
    public void decreaseQtyShoppingCartItemTest() {
        ShoppingCartItemEntity entity = data.get(1);
        Long expectedQty = entity.getQty() - 1;
        
        shoppingCartItemLogic.removeItem(entity.getId());
        ShoppingCartItemEntity deleted = em.find(ShoppingCartItemEntity.class, entity.getId());
        Assert.assertNotNull(deleted);
        Assert.assertEquals(deleted.getQty(), expectedQty);
    }   
    
    /**
     * Prueba para eliminar un Item
     *
     * @generated
     */
    @Test
    public void deleteShoppingCartItemTest() {
        ShoppingCartItemEntity entity = data.get(0);
        shoppingCartItemLogic.removeItem(entity.getId());
        ShoppingCartItemEntity deleted = em.find(ShoppingCartItemEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }      
    
    
    /**
     * Prueba para contar items.
     */
    @Test
    public void countShoppingCartItemsTest() {
        int countRecords = shoppingCartItemLogic.countItems();
        Assert.assertEquals(countRecords, data.size());
    }         
}
