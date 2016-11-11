/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.tests.rest;

import co.edu.uniandes.csw.artwork.dtos.minimum.ArtworkDTO;
import co.edu.uniandes.csw.artwork.dtos.minimum.ClientDTO;
import co.edu.uniandes.csw.artwork.dtos.minimum.MessageDTO;
import co.edu.uniandes.csw.artwork.dtos.minimum.ShoppingCartDTO;
import co.edu.uniandes.csw.artwork.dtos.minimum.ShoppingCartItemDTO;
import co.edu.uniandes.csw.artwork.entities.ArtworkEntity;
import co.edu.uniandes.csw.artwork.entities.ClientEntity;
import co.edu.uniandes.csw.artwork.entities.ShoppingCartItemEntity;
import co.edu.uniandes.csw.artwork.resources.CreditCardResource;
import co.edu.uniandes.csw.artwork.tests.Utils;
import co.edu.uniandes.csw.auth.model.UserDTO;
import co.edu.uniandes.csw.auth.security.JWT;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
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
import org.codehaus.jackson.map.ObjectMapper;
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
public class ShoppingCartTest {
    
    ArtworkEntity artwork;
    ArtworkEntity artwork2;    
    ClientEntity fatherEntity;
    Long notExistId = 999L;
    private List<ShoppingCartItemEntity> data = new ArrayList<ShoppingCartItemEntity>();
    
    private WebTarget target;
    private final String apiPath = Utils.apiPath;
    private final String username = Utils.username;
    private final String password = Utils.password;        
    
    private final int Ok = Response.Status.OK.getStatusCode();
    private final int Created = Response.Status.CREATED.getStatusCode();
    private final int OkWithoutContent = Response.Status.NO_CONTENT.getStatusCode();    
    
    private final String itemPath = "shoppingCart";    

    @ArquillianResource
    private URL deploymentURL;    
    PodamFactory factory = new PodamFactoryImpl();    
    
    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                // Se agrega las dependencias
                .addAsLibraries(Maven.resolver().loadPomFromFile("pom.xml")
                        .importRuntimeDependencies().resolve()
                        .withTransitivity().asFile())
                // Se agregan los compilados de los paquetes de servicios
                .addPackage(CreditCardResource.class.getPackage())
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
        em.createNativeQuery("INSERT INTO ClientEntity (ID, NAME, AGE) VALUES(99999, 'Test User', 35)").executeUpdate();
        fatherEntity = em.getReference(ClientEntity.class, 99999L);
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
                .path(itemPath);
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
    public void createShoppingCartItemTest() throws IOException {
        ShoppingCartItemDTO item = factory.manufacturePojo(ShoppingCartItemDTO.class);
        item.setArtwork(new ArtworkDTO(artwork2));
        item.setClient(new ClientDTO(fatherEntity));
        
        Cookie cookieSessionId = login(username, password);

        Response response = target
            .request().cookie(cookieSessionId)
            .post(Entity.entity(item, MediaType.APPLICATION_JSON));

        ShoppingCartItemDTO itemTest = (ShoppingCartItemDTO)response.readEntity(ShoppingCartItemDTO.class);
        Assert.assertEquals(Created, response.getStatus());
        Assert.assertEquals(item.getQuantity(), itemTest.getQuantity());
        Assert.assertEquals(item.getArtwork().getId(), itemTest.getArtwork().getId());
        
        ShoppingCartItemEntity entity = em.find(ShoppingCartItemEntity.class, itemTest.getId());
        Assert.assertNotNull(entity);

        Assert.assertEquals(itemTest.getId(), entity.getId());
        Assert.assertEquals(item.getQuantity(), entity.getQty());
        Assert.assertEquals(item.getArtwork().getId(), entity.getArtwork().getId());
    }    
    
    
    /**
     * Prueba para crear un Item
     *
     * @generated
     */
    @Test
    public void increaseShoppingCartItemTest() throws IOException {
        ShoppingCartItemDTO item = factory.manufacturePojo(ShoppingCartItemDTO.class);
        item.setArtwork(new ArtworkDTO(artwork2));
        item.setClient(new ClientDTO(fatherEntity));
        Long expectedQty = item.getQuantity() + 1;
        
        Cookie cookieSessionId = login(username, password);

        Response response = target
            .request().cookie(cookieSessionId)
            .post(Entity.entity(item, MediaType.APPLICATION_JSON));

        response.readEntity(ShoppingCartItemDTO.class);
        Assert.assertEquals(Created, response.getStatus());

        response = target
            .request().cookie(cookieSessionId)
            .post(Entity.entity(item, MediaType.APPLICATION_JSON));        
        
        ShoppingCartItemDTO itemTest = (ShoppingCartItemDTO)response.readEntity(ShoppingCartItemDTO.class);        
        Assert.assertEquals(Created, response.getStatus());
        Assert.assertEquals(expectedQty, itemTest.getQuantity());
        Assert.assertEquals(item.getArtwork().getId(), itemTest.getArtwork().getId());
        Assert.assertEquals(item.getClient().getId(), itemTest.getClient().getId());          
        
        ShoppingCartItemEntity entity = em.find(ShoppingCartItemEntity.class, itemTest.getId());
        Assert.assertNotNull(entity);

        Assert.assertEquals(itemTest.getId(), entity.getId());
        Assert.assertEquals(expectedQty, entity.getQty());
        Assert.assertEquals(item.getArtwork().getId(), entity.getArtwork().getId());
        Assert.assertEquals(item.getClient().getId(), entity.getClient().getId());        
    }      
    
    
    /**
     * Prueba para consultar la lista de Items
     *
     * @generated
     */
    @Test
    public void getShoppingCartTest() throws IOException {
        Cookie cookieSessionId = login(username, password);

        Response response = target
            .request().cookie(cookieSessionId).get();

        ShoppingCartDTO itemTest = (ShoppingCartDTO)response.readEntity(ShoppingCartDTO.class);
        Assert.assertEquals(Ok, response.getStatus());
        Assert.assertEquals(3, itemTest.getCartItems().size());
    }        
    
    /**
     * Prueba para consultar un Item
     *
     * @generated
     */
    @Test
    public void getShoppingCartPagedTest() throws IOException {
        Cookie cookieSessionId = login(username, password);

        Response response = target
                .queryParam("page", "1")                
                .queryParam("limit", "10")
                .request()
                .cookie(cookieSessionId).get();

        ShoppingCartDTO itemTest = (ShoppingCartDTO)response.readEntity(ShoppingCartDTO.class);
        Assert.assertEquals(Ok, response.getStatus());
        Assert.assertEquals(3, itemTest.getCartItems().size());
    }       
    
    /**
     * Prueba para eliminar un Item
     *
     * @generated
     */
    @Test
    public void deleteShoppingCartItemTest() {
        Cookie cookieSessionId = login(username, password);
        ShoppingCartItemDTO item = new ShoppingCartItemDTO(data.get(0));
        Response response = target
            .path(item.getId().toString())
            .request().cookie(cookieSessionId).delete();

        Assert.assertEquals(OkWithoutContent, response.getStatus());
    }       
}
