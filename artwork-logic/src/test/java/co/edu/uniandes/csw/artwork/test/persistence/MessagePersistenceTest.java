/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.test.persistence;

import co.edu.uniandes.csw.artwork.entities.ClientEntity;
import co.edu.uniandes.csw.artwork.entities.MessageEntity;
import co.edu.uniandes.csw.artwork.persistence.MessagePersistence;
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
public class MessagePersistenceTest {
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(MessageEntity.class.getPackage())
                .addPackage(MessagePersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }        
    
    ClientEntity fatherEntity;
    
    private List<MessageEntity> data = new ArrayList<MessageEntity>();
    
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private MessagePersistence itemPersistence;
    
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
        em.createQuery("delete from MessageEntity").executeUpdate();
        em.createQuery("delete from ClientEntity").executeUpdate();
    }     

    private void insertData() {
        fatherEntity = factory.manufacturePojo(ClientEntity.class);
        fatherEntity.setId(1L);
        em.persist(fatherEntity);
        
        for (int i = 0; i < 3; i++) {
            MessageEntity entity = factory.manufacturePojo(MessageEntity.class);
            entity.setClient(fatherEntity);

            em.persist(entity);
            data.add(entity);
        }        
    }
    
    /**
     * Prueba para crear un Item.
     */
    @Test
    public void createMessageTest() {
        MessageEntity newEntity = factory.manufacturePojo(MessageEntity.class);
        newEntity.setClient(fatherEntity);

        MessageEntity result = itemPersistence.create(newEntity);
        Assert.assertNotNull(result);

        MessageEntity entity = em.find(MessageEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getSubject(), entity.getSubject());
        Assert.assertEquals(newEntity.getBody(), entity.getBody());  
    }      
    
    /**
     * Prueba para consultar la lista de Items.
     */
    @Test
    public void getMessagesTest() {
        List<MessageEntity> list = itemPersistence.findAll(null, null, fatherEntity.getId());
        Assert.assertEquals(data.size(), list.size());
        for (MessageEntity ent : list) {
            boolean found = false;
            for (MessageEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }    
    
    /**
     * Prueba para consultar la lista de Items paginada.
     */
    @Test
    public void getMessagesPaginatedTest() {
        List<MessageEntity> list = itemPersistence.findAll(1, 10, fatherEntity.getId());
        Assert.assertEquals(data.size(), list.size());
        for (MessageEntity ent : list) {
            boolean found = false;
            for (MessageEntity entity : data) {
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
    public void getMessageTest() {
        MessageEntity entity = data.get(0);
        MessageEntity newEntity = itemPersistence.find(entity.getClient().getId(), entity.getId());
        Assert.assertNotNull(newEntity);
        
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getSentDate(), entity.getSentDate());
        Assert.assertEquals(newEntity.getSubject(), entity.getSubject());
        Assert.assertEquals(newEntity.getBody(), entity.getBody()); 
    }       
    
    /**
     * Prueba para eliminar un Item.
     */
    @Test
    public void deleteMessageTest() {
        MessageEntity entity = data.get(0);
        itemPersistence.delete(entity.getId());
        MessageEntity deleted = em.find(MessageEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }       
    
    /**
     * Prueba para actualizar un Item.
     */
    @Test
    public void updateMessageTest() {
        MessageEntity entity = data.get(0);
        MessageEntity newEntity = factory.manufacturePojo(MessageEntity.class);

        newEntity.setId(entity.getId());
        itemPersistence.update(newEntity);
        MessageEntity resp = em.find(MessageEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getId(), resp.getId());
        Assert.assertEquals(newEntity.getSentDate(), resp.getSentDate());
        Assert.assertEquals(newEntity.getSubject(), resp.getSubject());
        Assert.assertEquals(newEntity.getBody(), resp.getBody());
    }        
    
    /**
     * Prueba para contar items.
     */
    @Test
    public void countMessagesTest() {
        int countRecords = itemPersistence.count(fatherEntity.getId());
        Assert.assertEquals(countRecords, data.size());
    }         
}
