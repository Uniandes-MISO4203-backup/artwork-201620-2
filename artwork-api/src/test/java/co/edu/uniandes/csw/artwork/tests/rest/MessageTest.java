/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.tests.rest;

import co.edu.uniandes.csw.artwork.dtos.minimum.MessageDTO;
import co.edu.uniandes.csw.artwork.entities.ClientEntity;
import co.edu.uniandes.csw.artwork.entities.MessageEntity;
import co.edu.uniandes.csw.artwork.resources.MessageResource;
import co.edu.uniandes.csw.artwork.tests.Utils;
import co.edu.uniandes.csw.auth.model.UserDTO;
import co.edu.uniandes.csw.auth.security.JWT;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
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
public class MessageTest {
    
    private WebTarget target;
    private final String apiPath = Utils.apiPath;
    private final String username = Utils.username;
    private final String password = Utils.password;    
    
    @ArquillianResource
    private URL deploymentURL;    
    PodamFactory factory = new PodamFactoryImpl();
    
    private final static List<MessageEntity> oraculo = new ArrayList<>();
    private final int Ok = Response.Status.OK.getStatusCode();
    private final int Created = Response.Status.CREATED.getStatusCode();
    private final int OkWithoutContent = Response.Status.NO_CONTENT.getStatusCode();   
    
    ClientEntity fatherClientEntity;
    private final String messagePath = "messages";
    
    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                // Se agrega las dependencias
                .addAsLibraries(Maven.resolver().loadPomFromFile("pom.xml")
                        .importRuntimeDependencies().resolve()
                        .withTransitivity().asFile())
                // Se agregan los compilados de los paquetes de servicios
                .addPackage(MessageResource.class.getPackage())
                // El archivo que contiene la configuracion a la base de datos.
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                // El archivo beans.xml es necesario para injeccion de dependencias.
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/beans.xml"))
                // El archivo shiro.ini es necesario para injeccion de dependencias
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/shiro.ini"))
                // El archivo web.xml es necesario para el despliegue de los servlets
                .setWebXML(new File("src/main/webapp/WEB-INF/web.xml"));
    }    
    
    private WebTarget createWebTarget() {
        return ClientBuilder.newClient().target(deploymentURL.toString()).path(apiPath);
    }    
    
    @PersistenceContext(unitName = "ArtworkPU")
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private void clearData() {
        em.createQuery("delete from MessageEntity").executeUpdate();
        em.createQuery("delete from ClientEntity").executeUpdate();
        oraculo.clear();
    }    
    
   /**
     * Datos iniciales para el correcto funcionamiento de las pruebas.
     *
     * @generated
     */
    public void insertData() {
        fatherClientEntity = factory.manufacturePojo(ClientEntity.class);
        fatherClientEntity.setId(99999L);
        em.persist(fatherClientEntity);

        for (int i = 0; i < 3; i++) {            
            MessageEntity item = factory.manufacturePojo(MessageEntity.class);
            item.setId(i + 1L);
            item.setClient(fatherClientEntity);
            em.persist(item);
            oraculo.add(item);
        }
    }

    /**
     * Configuración inicial de la prueba.
     *
     * @generated
     */
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
        target = createWebTarget()
                .path(messagePath);
    }    
    
    /**
     * Login para poder consultar los diferentes servicios
     *
     * @param username Nombre de usuario
     * @param password Clave del usuario
     * @return Cookie con información de la sesión del usuario
     * @generated
     */
    public Cookie login(String username, String password) {
        UserDTO user = new UserDTO();
        user.setUserName(username);
        user.setPassword(password);
        user.setRememberMe(true);
        Response response = createWebTarget().path("users").path("login").request()
                .post(Entity.entity(user, MediaType.APPLICATION_JSON));
        if (response.getStatus() == Ok) {
            return response.getCookies().get(JWT.cookieName);
        } else {
            return null;
        }
    }    
    
    /**
     * Prueba para crear un Item
     *
     * @generated
     */
    @Test
    public void createMessageTest() throws IOException {
        MessageDTO item = factory.manufacturePojo(MessageDTO.class);
        Cookie cookieSessionId = login(username, password);

        Response response = target
            .request().cookie(cookieSessionId)
            .post(Entity.entity(item, MediaType.APPLICATION_JSON));

        MessageDTO itemTest = (MessageDTO)response.readEntity(MessageDTO.class);

        Assert.assertEquals(Created, response.getStatus());
        Assert.assertEquals(itemTest.getSubject(), item.getSubject());
        Assert.assertEquals(itemTest.getBody(), item.getBody());         
        
        MessageEntity entity = em.find(MessageEntity.class, itemTest.getId());
        Assert.assertNotNull(entity);
        Assert.assertEquals(entity.getSubject(), item.getSubject());
        Assert.assertEquals(entity.getBody(), item.getBody());         
    }    
    
    
    /**
     * Prueba para consultar un Item
     *
     * @generated
     */
    @Test
    public void getMessageByIdTest() {
        Cookie cookieSessionId = login(username, password);

        MessageDTO itemTest = target
            .path(oraculo.get(0).getId().toString())
            .request().cookie(cookieSessionId).get(MessageDTO.class);
        
        Assert.assertEquals(itemTest.getId(), oraculo.get(0).getId());
        Assert.assertEquals(itemTest.getSubject(), oraculo.get(0).getSubject());
        Assert.assertEquals(itemTest.getBody(), oraculo.get(0).getBody());  
    }    
}