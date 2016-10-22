/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.test.logic;

import co.edu.uniandes.csw.artwork.api.IMessageLogic;
import co.edu.uniandes.csw.artwork.ejbs.MessageLogic;
import co.edu.uniandes.csw.artwork.entities.ClientEntity;
import co.edu.uniandes.csw.artwork.entities.CreditCardEntity;
import co.edu.uniandes.csw.artwork.entities.ItemEntity;
import co.edu.uniandes.csw.artwork.entities.MessageEntity;
import co.edu.uniandes.csw.artwork.persistence.MessagePersistence;
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
public class MessageLogicTest {
    
    ClientEntity fatherEntity;
    Long notExistId = 999L;

    private PodamFactory factory = new PodamFactoryImpl();
    
    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<MessageEntity> data = new ArrayList<MessageEntity>();
    
    @Inject
    private IMessageLogic messageLogic;        
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(MessageEntity.class.getPackage())
                .addPackage(MessageLogic.class.getPackage())
                .addPackage(IMessageLogic.class.getPackage())
                .addPackage(MessagePersistence.class.getPackage())
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
        em.createQuery("delete from MessageEntity").executeUpdate();
        em.createQuery("delete from ClientEntity").executeUpdate();
    }      
    
    private void insertData() {
        fatherEntity = factory.manufacturePojo(ClientEntity.class);
        fatherEntity.setId(1L);
        em.persist(fatherEntity);
        /// ThreadLocalRandom.current().nextLong(m, n);
        
        for (int i = 0; i < 3; i++) {
            MessageEntity entity = factory.manufacturePojo(MessageEntity.class);
            entity.setClient(fatherEntity);

            em.persist(entity);
            data.add(entity);
            
            while (Objects.equals(entity.getId(), notExistId)) {
                notExistId = ThreadLocalRandom.current().nextLong();
            }
        }        
    }

       /**
     * Prueba para crear un mesaje
     *
     * @generated
     */
    @Test
    public void createMessageTest() {
        MessageEntity newEntity = factory.manufacturePojo(MessageEntity.class);
        MessageEntity result = messageLogic.createMessage(fatherEntity.getId(), newEntity);
        Assert.assertNotNull(result);
        
        MessageEntity entity = em.find(MessageEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getSentDate(), entity.getSentDate());
        Assert.assertEquals(newEntity.getSubject(), entity.getSubject());
        Assert.assertEquals(newEntity.getBody(), entity.getBody());        
    }  
    
    /**
     * Prueba para consultar la lista
     *
     * @generated
     */
    @Test
    public void getMessagesTest() {
        List<MessageEntity> list = messageLogic.getMessages();
        Assert.assertEquals(data.size(), list.size());
        for (MessageEntity entity : list) {
            boolean found = false;
            for (MessageEntity storedEntity : data) {
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
    public void getMessagesPaginatedTest() {
        List<MessageEntity> list = messageLogic.getMessages(1, 10);
        Assert.assertEquals(data.size(), list.size());
        for (MessageEntity entity : list) {
            boolean found = false;
            for (MessageEntity storedEntity : data) {
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
    public void getMessagesPaginatedByClientTest() {
        List<MessageEntity> list = messageLogic.getMessages(1, 10, fatherEntity.getId());
        Assert.assertEquals(data.size(), list.size());
        for (MessageEntity entity : list) {
            boolean found = false;
            for (MessageEntity storedEntity : data) {
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
    public void getMessagesByClientTest() {
        List<MessageEntity> list = messageLogic.getMessages(fatherEntity.getId());
        Assert.assertEquals(data.size(), list.size());
        for (MessageEntity entity : list) {
            boolean found = false;
            for (MessageEntity storedEntity : data) {
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
    public void getMessageTest() {
        MessageEntity entity = data.get(0);
        MessageEntity resultEntity = messageLogic.getMessage(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(resultEntity.getId(), entity.getId());
        Assert.assertEquals(resultEntity.getSentDate(), entity.getSentDate());
        Assert.assertEquals(resultEntity.getSubject(), entity.getSubject());
        Assert.assertEquals(resultEntity.getBody(), entity.getBody());  
    }    
    
    /**
     * Prueba para consultar un Item
     *
     * @generated
     */
    @Test()
    public void getMessageDoesNotExistTest() {
        MessageEntity resultEntity = messageLogic.getMessage(notExistId);
        Assert.assertNull(resultEntity);
    }       
    
    /**
     * Prueba para eliminar un Item
     *
     * @generated
     */
    @Test
    public void deleteMessageTest() {
        MessageEntity entity = data.get(1);
        messageLogic.deleteMessage(entity.getId());
        MessageEntity deleted = em.find(MessageEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }    
    
    /**
     * Prueba para actualizar un Item
     *
     * @generated
     */
    @Test
    public void updateMessageTest() {
        MessageEntity entity = data.get(0);
        MessageEntity pojoEntity = factory.manufacturePojo(MessageEntity.class);

        pojoEntity.setId(entity.getId());
        messageLogic.updateMessage(fatherEntity.getId(), pojoEntity);

        MessageEntity resp = em.find(MessageEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getSentDate(), resp.getSentDate());
        Assert.assertEquals(pojoEntity.getSubject(), resp.getSubject());
        Assert.assertEquals(pojoEntity.getBody(), resp.getBody());  
    }    
    
    /**
     * Prueba para contar items.
     */
    @Test
    public void countMessagesByClientTest() {
        int countRecords = messageLogic.countItems(fatherEntity.getId());
        Assert.assertEquals(countRecords, data.size());
    }       
    
    /**
     * Prueba para contar items.
     */
    @Test
    public void countMessagesTest() {
        int countRecords = messageLogic.countItems();
        Assert.assertEquals(countRecords, data.size());
    }       
    
}
