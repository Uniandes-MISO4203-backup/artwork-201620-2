/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.test.logic;

import co.edu.uniandes.csw.artwork.api.ICreditCardLogic;
import co.edu.uniandes.csw.artwork.ejbs.CreditCardLogic;
import co.edu.uniandes.csw.artwork.entities.ClientEntity;
import co.edu.uniandes.csw.artwork.entities.CreditCardEntity;
import co.edu.uniandes.csw.artwork.entities.ItemEntity;
import co.edu.uniandes.csw.artwork.entities.MessageEntity;
import co.edu.uniandes.csw.artwork.persistence.CreditCardPersistence;
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
public class CreditCardLogicTest {

    ClientEntity fatherEntity;

    private PodamFactory factory = new PodamFactoryImpl();
    
    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<CreditCardEntity> data = new ArrayList<CreditCardEntity>();
    
    @Inject
    private ICreditCardLogic creditCardLogic;    
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CreditCardEntity.class.getPackage())
                .addPackage(CreditCardLogic.class.getPackage())
                .addPackage(ICreditCardLogic.class.getPackage())
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
        em.createQuery("delete from CreditCardEntity").executeUpdate();
        em.createQuery("delete from ClientEntity").executeUpdate();
    }    
    
    private void insertData() {
        fatherEntity = factory.manufacturePojo(ClientEntity.class);
        fatherEntity.setId(1L);
        em.persist(fatherEntity);
        
        for (int i = 0; i < 3; i++) {
            CreditCardEntity entity = factory.manufacturePojo(CreditCardEntity.class);
            entity.setClient(fatherEntity);

            em.persist(entity);
            data.add(entity);
        }           
    }
    
    
   /**
     * Prueba para crear una tarjeta de credito
     *
     * @generated
     */
    @Test
    public void createCreditCardTest() {
        CreditCardEntity newEntity = factory.manufacturePojo(CreditCardEntity.class);
        CreditCardEntity result = creditCardLogic.createItem(fatherEntity.getId(), newEntity);
        Assert.assertNotNull(result);
        
        CreditCardEntity entity = em.find(CreditCardEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getName(), entity.getName());
        Assert.assertEquals(newEntity.getExpirationMonth(), entity.getExpirationMonth());
        Assert.assertEquals(newEntity.getExpirationYear(), entity.getExpirationYear());        
        Assert.assertEquals(newEntity.getNumber(), entity.getNumber());                
        Assert.assertEquals(newEntity.getType(), entity.getType());                        
    }
    
    /**
     * Prueba para consultar la lista
     *
     * @generated
     */
    @Test
    public void getCreditCardsTest() {
        List<CreditCardEntity> list = creditCardLogic.getItems(fatherEntity.getId());
        Assert.assertEquals(data.size(), list.size());
        for (CreditCardEntity entity : list) {
            boolean found = false;
            for (CreditCardEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }    
    
    /**
     * Prueba para consultar un Item
     *
     * @generated
     */
    @Test
    public void getCreditCardTest() {
        CreditCardEntity entity = data.get(0);
        CreditCardEntity resultEntity = creditCardLogic.getItem(entity.getId());
        Assert.assertNotNull(resultEntity);
        
        Assert.assertEquals(resultEntity.getId(), entity.getId());
        Assert.assertEquals(resultEntity.getName(), entity.getName());
        Assert.assertEquals(resultEntity.getExpirationMonth(), entity.getExpirationMonth());
        Assert.assertEquals(resultEntity.getExpirationYear(), entity.getExpirationYear());        
        Assert.assertEquals(resultEntity.getNumber(), entity.getNumber());                
        Assert.assertEquals(resultEntity.getType(), entity.getType());    
    }       
    
    /**
     * Prueba para eliminar un Item
     *
     * @generated
     */
    @Test
    public void deleteCreditCardTest() {
        CreditCardEntity entity = data.get(1);
        creditCardLogic.deleteItem(entity.getId());
        CreditCardEntity deleted = em.find(CreditCardEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }      
    
    /**
     * Prueba para actualizar un Item
     *
     * @generated
     */
    @Test
    public void updateMessageTest() {
        CreditCardEntity entity = data.get(0);
        CreditCardEntity pojoEntity = factory.manufacturePojo(CreditCardEntity.class);

        pojoEntity.setId(entity.getId());
        creditCardLogic.updateItem(fatherEntity.getId(), pojoEntity);

        CreditCardEntity resp = em.find(CreditCardEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getName(), resp.getName());
        Assert.assertEquals(pojoEntity.getExpirationMonth(), resp.getExpirationMonth());
        Assert.assertEquals(pojoEntity.getExpirationYear(), resp.getExpirationYear());        
        Assert.assertEquals(pojoEntity.getNumber(), resp.getNumber());                
        Assert.assertEquals(pojoEntity.getType(), resp.getType());   
    }       
}
