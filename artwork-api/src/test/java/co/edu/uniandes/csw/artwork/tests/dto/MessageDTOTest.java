/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.tests.dto;

import co.edu.uniandes.csw.artwork.dtos.minimum.MessageDTO;
import co.edu.uniandes.csw.artwork.entities.MessageEntity;
import java.io.File;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@RunWith(Arquillian.class)
public class MessageDTOTest {
    PodamFactory factory = new PodamFactoryImpl();

    private static MessageEntity messageEntity;
    
    @PersistenceContext(unitName = "ArtworkPU")
    private EntityManager em;

    @Inject
    private UserTransaction utx;
    
    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                // Se agrega las dependencias
                .addAsLibraries(Maven.resolver().loadPomFromFile("pom.xml")
                        .importRuntimeDependencies().resolve()
                        .withTransitivity().asFile())
                // Se agregan los compilados de los paquetes de servicios
                .addPackage(MessageDTO.class.getPackage())
                // El archivo que contiene la configuracion a la base de datos.
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                // El archivo beans.xml es necesario para injeccion de dependencias.
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/beans.xml"))
                // El archivo shiro.ini es necesario para injeccion de dependencias
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/shiro.ini"))
                // El archivo web.xml es necesario para el despliegue de los servlets
                .setWebXML(new File("src/main/webapp/WEB-INF/web.xml"));
    }
    
    private void clearData() {
        em.createQuery("delete from MessageEntity").executeUpdate();
        messageEntity = null;
    }
    
    public void insertData() {
        messageEntity = factory.manufacturePojo(MessageEntity.class);
        messageEntity.setId(1L);
        em.persist(messageEntity);
    }
    
    @Before
    public void setUpTest() {
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
    
    @Test
    public void setgetId() {
        MessageDTO item = factory.manufacturePojo(MessageDTO.class);
        
        MessageDTO newItem = new MessageDTO();
        newItem.setId(item.getId());
        
        Assert.assertEquals(item.getId(), newItem.getId());
    }
    
    @Test
    public void setgetBody() {
        MessageDTO message = factory.manufacturePojo(MessageDTO.class);
        
        MessageDTO newMessage = new MessageDTO();
        newMessage.setBody(message.getBody());
        
        Assert.assertEquals(message.getBody(), newMessage.getBody());
    }
    
    @Test
    public void setgetSubject() {
        MessageDTO message = factory.manufacturePojo(MessageDTO.class);
        
        MessageDTO newMessage = new MessageDTO();
        newMessage.setSubject(message.getSubject());
        
        Assert.assertEquals(message.getSubject(), newMessage.getSubject());
    }
    
    @Test
    public void setgetSentDate() {
        MessageDTO message = factory.manufacturePojo(MessageDTO.class);
        
        MessageDTO newMessage = new MessageDTO();
        newMessage.setSentDate(message.getSentDate());

        Assert.assertEquals(message.getSentDate(), newMessage.getSentDate());
    }
    
    @Test
    public void setgetClient() {
        MessageDTO message = factory.manufacturePojo(MessageDTO.class);
        
        MessageDTO newMessage = new MessageDTO();
        newMessage.setClient(message.getClient());

        Assert.assertEquals(message.getClient(), newMessage.getClient());
    }
    
    @Test
    public void entity2DTO() {
        MessageDTO artist = new MessageDTO(messageEntity);
        
        Assert.assertEquals(artist.getBody(), messageEntity.getBody());
        Assert.assertEquals(artist.getId(), messageEntity.getId());
        Assert.assertEquals(artist.getSubject(), messageEntity.getSubject());
        Assert.assertEquals(artist.getSentDate(), messageEntity.getSentDate());
    }
}
