/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.test.persistence;

import co.edu.uniandes.csw.artwork.entities.CheckOutEntity;
import co.edu.uniandes.csw.artwork.entities.ClientEntity;
import co.edu.uniandes.csw.artwork.entities.CreditCardEntity;
import co.edu.uniandes.csw.artwork.persistence.CheckOutPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
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
public class CheckOutPersistenceTest {
    
    ClientEntity fatherEntity;
    CreditCardEntity creditCard;
    
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
    private CheckOutPersistence checkOutPersistence;    
    
    private List<CheckOutEntity> data = new ArrayList<>();    
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CheckOutEntity.class.getPackage())
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
     * Prueba para consultar la lista de Items.
     *
     * @generated
     */
    @Test
    public void getCheckOutItemsTest() {
        List<CheckOutEntity> list = checkOutPersistence.findAll(null, null, fatherEntity.getId());
        Assert.assertEquals(data.size(), list.size());
        for (CheckOutEntity ent : list) {
            boolean found = false;
            for (CheckOutEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }      
    
    /**
     * Prueba para consultar la lista de Items.
     *
     * @generated
     */
    @Test
    public void getCheckOutItemsPagedTest() {
        List<CheckOutEntity> list = checkOutPersistence.findAll(1, 10, fatherEntity.getId());
        Assert.assertEquals(data.size(), list.size());
        for (CheckOutEntity ent : list) {
            boolean found = false;
            for (CheckOutEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }       

    /**
     * Prueba para crear un Item.
     */
    @Test
    public void createCheckOutItemsTest() throws NotSupportedException, SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
        CheckOutEntity newEntity = factory.manufacturePojo(CheckOutEntity.class);
        newEntity.setClient(fatherEntity);
        newEntity.setCreditCard(creditCard);
        newEntity.setCheckOutItemsList(null);
        
        utx.begin();
        em.joinTransaction();
        CheckOutEntity result = checkOutPersistence.create(newEntity);
        utx.commit();

        Assert.assertNotNull(result);
        CheckOutEntity entity = em.find(CheckOutEntity.class, result.getId());

        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getName(), entity.getName());
        Assert.assertEquals(newEntity.getShippingAddress(), entity.getShippingAddress());
        Assert.assertEquals(newEntity.getTotal(), entity.getTotal());        
    }     
}
