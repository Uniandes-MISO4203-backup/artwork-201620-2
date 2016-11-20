/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.test.logic;

import co.edu.uniandes.csw.artwork.api.ICheckOutLogic;
import co.edu.uniandes.csw.artwork.ejbs.CheckOutLogic;
import co.edu.uniandes.csw.artwork.entities.ArtworkEntity;
import co.edu.uniandes.csw.artwork.entities.CheckOutEntity;
import co.edu.uniandes.csw.artwork.entities.CheckOutItemEntity;
import co.edu.uniandes.csw.artwork.entities.ClientEntity;
import co.edu.uniandes.csw.artwork.entities.CreditCardEntity;
import co.edu.uniandes.csw.artwork.entities.ShoppingCartItemEntity;
import co.edu.uniandes.csw.artwork.persistence.CheckOutPersistence;
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
public class CheckOutLogicTest {
    
    ClientEntity fatherEntity;
    CreditCardEntity creditCard;
    ArtworkEntity artwork;
    List<ShoppingCartItemEntity> shoppingCart = new ArrayList<>();    
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    /**
     * @generated
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * @generated
     */
    @Inject
    private UserTransaction utx;    
        
    @Inject
    private ICheckOutLogic checkOutLogic;    
    
    private List<CheckOutEntity> data = new ArrayList<>();
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CheckOutEntity.class.getPackage())
                .addPackage(CheckOutLogic.class.getPackage())
                .addPackage(ICheckOutLogic.class.getPackage())
                .addPackage(CheckOutPersistence.class.getPackage())                
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }  
    
    /**
     * Limpia las tablas que están implicadas en la prueba.
     *
     * @generated
     */
    private void clearData() {
        em.createQuery("delete from ShoppingCartItemEntity").executeUpdate();
        em.createQuery("delete from CheckOutItemEntity").executeUpdate();
        em.createQuery("delete from CheckOutEntity").executeUpdate();
        em.createQuery("delete from ArtworkEntity").executeUpdate();
        em.createQuery("delete from CreditCardEntity").executeUpdate();        
        em.createQuery("delete from ClientEntity").executeUpdate();        
    }    
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
     *
     * @generated
     */
    private void insertData() {

        fatherEntity = factory.manufacturePojo(ClientEntity.class);
        em.persist(fatherEntity);        
        
        artwork = factory.manufacturePojo(ArtworkEntity.class);
        em.persist(artwork);
        
        ShoppingCartItemEntity item = factory.manufacturePojo(ShoppingCartItemEntity.class);
        item.setClient(fatherEntity);
        item.setArtwork(artwork);
        em.persist(item);
        shoppingCart.add(item);
        
        creditCard = factory.manufacturePojo(CreditCardEntity.class);
        em.persist(creditCard);
        
        for (int i = 0; i < 3; i++) {
            CheckOutEntity entity = factory.manufacturePojo(CheckOutEntity.class);
            entity.setClient(fatherEntity);
            entity.setCreditCard(creditCard);            
            
            em.persist(entity);
            data.add(entity);
        }  
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
     * Prueba para consultar la lista
     *
     * @generated
     */
    @Test
    public void getCheckOutItemsTest() {
        List<CheckOutEntity> list = checkOutLogic.getItems(fatherEntity.getId());
        Assert.assertEquals(data.size(), list.size());
        for (CheckOutEntity entity : list) {
            boolean found = false;
            for (CheckOutEntity storedEntity : data) {
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
    public void getCheckOutPaginatedTest() {
        List<CheckOutEntity> list = checkOutLogic.getItems(1, 10, fatherEntity.getId());
        Assert.assertEquals(data.size(), list.size());
        for (CheckOutEntity entity : list) {
            boolean found = false;
            for (CheckOutEntity storedEntity : data) {
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
    public void getCountCheckOutTest() {
        int itemCount = checkOutLogic.countItems();
        Assert.assertEquals(data.size(), itemCount);
    }       
    
    
       /**
     * Prueba para crear un Item
     *
     * @generated
     */
    @Test
    public void createCheckOutTest() {
        CheckOutEntity newEntity = factory.manufacturePojo(CheckOutEntity.class);
        newEntity.setClient(fatherEntity);
        newEntity.setCreditCard(creditCard);
        newEntity.setCheckOutItemsList(new ArrayList<>());
        
        CheckOutEntity result = checkOutLogic.addItem(fatherEntity.getId(), newEntity);
        Assert.assertNotNull(result);
        
        ShoppingCartItemEntity shoppingItem = shoppingCart.get(0);
        
        CheckOutEntity entity = em.find(CheckOutEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getName(), entity.getName());
        Assert.assertEquals(newEntity.getShippingAddress(), entity.getShippingAddress());
        Assert.assertEquals(newEntity.getCheckOutDate(), entity.getCheckOutDate());
        Assert.assertEquals(newEntity.getClient().getId(), entity.getClient().getId());
        Assert.assertEquals(newEntity.getCreditCard().getNumber(), entity.getCreditCard().getNumber());
        
        Long total =  shoppingItem.getQty() *  shoppingItem.getArtwork().getPrice();
        Assert.assertEquals(newEntity.getTotal(), total);
        
        List<CheckOutItemEntity> items = newEntity.getCheckOutItemsList();
        Assert.assertEquals(newEntity.getCheckOutItemsList().size(), items.size());                
        
        CheckOutItemEntity checkOutItem = newEntity.getCheckOutItemsList().get(0);
        Assert.assertEquals(checkOutItem.getQty(), shoppingItem.getQty());
        Assert.assertEquals(checkOutItem.getArtwork().getId(), shoppingItem.getArtwork().getId());
        Assert.assertEquals(checkOutItem.getCheckOut().getId(), newEntity.getId());
    }
}
