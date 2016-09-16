/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.test.persistence;

import co.edu.uniandes.csw.artwork.entities.ClientEntity;
import co.edu.uniandes.csw.artwork.entities.CreditCardEntity;
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
public class CreditCardPersistenceTest {
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CreditCardEntity.class.getPackage())
                .addPackage(CreditCardPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }        
    
    ClientEntity fatherEntity;

    private PodamFactory factory = new PodamFactoryImpl();
    
    private List<CreditCardEntity> data = new ArrayList<CreditCardEntity>();
    
    @Inject
    private CreditCardPersistence itemPersistence;
    
    @PersistenceContext
    private EntityManager em;

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
     * Prueba para crear un Item.
     */
    @Test
    public void createCreditCardTest() {
        CreditCardEntity newEntity = factory.manufacturePojo(CreditCardEntity.class);
        newEntity.setClient(fatherEntity);
        CreditCardEntity result = itemPersistence.create(newEntity);

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
     * Prueba para consultar la lista de Items.
     *
     * @generated
     */
    @Test
    public void getCreditCardsTest() {
        List<CreditCardEntity> list = itemPersistence.findAll(null, null, fatherEntity.getId());
        Assert.assertEquals(data.size(), list.size());
        for (CreditCardEntity ent : list) {
            boolean found = false;
            for (CreditCardEntity entity : data) {
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
    public void getCreditCardTest() {
        CreditCardEntity entity = data.get(0);
        CreditCardEntity newEntity = itemPersistence.find(entity.getClient().getId(), entity.getId());
        Assert.assertNotNull(newEntity);
        
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getName(), entity.getName());
        Assert.assertEquals(newEntity.getExpirationMonth(), entity.getExpirationMonth());
        Assert.assertEquals(newEntity.getExpirationYear(), entity.getExpirationYear());        
        Assert.assertEquals(newEntity.getNumber(), entity.getNumber());                
        Assert.assertEquals(newEntity.getType(), entity.getType());  
    }    
    
    /**
     * Prueba para eliminar un Item.
     */
    @Test
    public void deleteCreditCardTest() {
        CreditCardEntity entity = data.get(0);
        itemPersistence.delete(entity.getId());
        CreditCardEntity deleted = em.find(CreditCardEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }    
    
    /**
     * Prueba para actualizar un Item.
     */
    @Test
    public void updateCreditCardTest() {
        CreditCardEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        CreditCardEntity newEntity = factory.manufacturePojo(CreditCardEntity.class);

        newEntity.setId(entity.getId());

        itemPersistence.update(newEntity);
        CreditCardEntity resp = em.find(CreditCardEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getId(), resp.getId());
        Assert.assertEquals(newEntity.getName(), resp.getName());
        Assert.assertEquals(newEntity.getExpirationMonth(), resp.getExpirationMonth());
        Assert.assertEquals(newEntity.getExpirationYear(), resp.getExpirationYear());        
        Assert.assertEquals(newEntity.getNumber(), resp.getNumber());                
        Assert.assertEquals(newEntity.getType(), resp.getType()); 
    }    
}
