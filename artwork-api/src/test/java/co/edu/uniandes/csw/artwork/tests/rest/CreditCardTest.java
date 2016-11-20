/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.tests.rest;

import co.edu.uniandes.csw.artwork.dtos.minimum.CreditCardDTO;
import co.edu.uniandes.csw.artwork.dtos.minimum.ItemDTO;
import co.edu.uniandes.csw.artwork.dtos.minimum.MessageDTO;
import co.edu.uniandes.csw.artwork.entities.ClientEntity;
import co.edu.uniandes.csw.artwork.entities.CreditCardEntity;
import co.edu.uniandes.csw.artwork.entities.ItemEntity;
import co.edu.uniandes.csw.artwork.resources.CreditCardResource;
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
import javax.ws.rs.core.Response.Status;
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
public class CreditCardTest {
    
    private WebTarget target;
    private final String apiPath = Utils.apiPath;
    private final String username = Utils.username;
    private final String password = Utils.password;    
    
    @ArquillianResource
    private URL deploymentURL;    
    PodamFactory factory = new PodamFactoryImpl();
    
    private final static List<CreditCardEntity> oraculo = new ArrayList<>();
    private final int Ok = Response.Status.OK.getStatusCode();
    private final int Created = Status.CREATED.getStatusCode();
    private final int OkWithoutContent = Status.NO_CONTENT.getStatusCode();    
    
    ClientEntity fatherClientEntity;
    
    private final String clientPath = "clients";
    private final String itemPath = "creditCard";    
    
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

    private void clearData() {
        em.createQuery("delete from CreditCardEntity").executeUpdate();
        em.createQuery("delete from ClientEntity").executeUpdate();
        oraculo.clear();
    }    
    
   /**
     * Datos iniciales para el correcto funcionamiento de las pruebas.
     *
     * @generated
     */
    public void insertData() {
        em.createNativeQuery("INSERT INTO ClientEntity (ID, NAME, AGE) VALUES(99999, 'Test User', 35)").executeUpdate();
        fatherClientEntity = em.getReference(ClientEntity.class, 99999L);
        em.persist(fatherClientEntity);

        for (int i = 0; i < 3; i++) {            
            CreditCardEntity item = factory.manufacturePojo(CreditCardEntity.class);
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
                .path(clientPath)
                .path(fatherClientEntity.getId().toString())
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
    public void createCreditCardTest() throws IOException {
        CreditCardDTO item = factory.manufacturePojo(CreditCardDTO.class);
        Cookie cookieSessionId = login(username, password);

        Response response = target
            .request().cookie(cookieSessionId)
            .post(Entity.entity(item, MediaType.APPLICATION_JSON));

        CreditCardDTO itemTest = (CreditCardDTO)response.readEntity(CreditCardDTO.class);

        Assert.assertEquals(Created, response.getStatus());
        Assert.assertEquals(item.getExpirationMonth(), itemTest.getExpirationMonth());
        Assert.assertEquals(item.getExpirationYear(), itemTest.getExpirationYear());        
        Assert.assertEquals(item.getNumber(), itemTest.getNumber());                
        Assert.assertEquals(item.getType(), itemTest.getType());          
        
        CreditCardEntity entity = em.find(CreditCardEntity.class, itemTest.getId());
        Assert.assertNotNull(entity);

        Assert.assertEquals(itemTest.getId(), entity.getId());
        Assert.assertEquals(item.getExpirationMonth(), entity.getExpirationMonth());
        Assert.assertEquals(item.getExpirationYear(), entity.getExpirationYear());        
        Assert.assertEquals(item.getNumber(), entity.getNumber());                
        Assert.assertEquals(item.getType(), entity.getType());           
    }

    /**
     * Prueba para consultar un Item
     *
     * @generated
     */
    @Test
    public void getCreditCardByIdTest() {
        Cookie cookieSessionId = login(username, password);

        CreditCardDTO itemTest = target
            .path(oraculo.get(0).getId().toString())
            .request().cookie(cookieSessionId).get(CreditCardDTO.class);
        
        Assert.assertEquals(itemTest.getId(), oraculo.get(0).getId());
        Assert.assertEquals(itemTest.getExpirationMonth(), oraculo.get(0).getExpirationMonth());
        Assert.assertEquals(itemTest.getExpirationYear(), oraculo.get(0).getExpirationYear());        
        Assert.assertEquals(itemTest.getNumber(), oraculo.get(0).getNumber());                
        Assert.assertEquals(itemTest.getType(), oraculo.get(0).getType());   
    }    
    
    
    /**
     * Prueba para consultar la lista de Items
     *
     * @generated
     */
    @Test
    public void listCreditCardTest() throws IOException {
        Cookie cookieSessionId = login(username, password);

        Response response = target
            .request().cookie(cookieSessionId).get();

        String listItem = response.readEntity(String.class);
        List<CreditCardDTO> listItemTest = new ObjectMapper().readValue(listItem, List.class);
        Assert.assertEquals(Ok, response.getStatus());
        Assert.assertEquals(3, listItemTest.size());
    }    
    
    /**
     * Prueba para consultar la lista de Items
     *
     * @generated
     */
    @Test
    public void listCreditCardUserTest() throws IOException {
        Cookie cookieSessionId = login(username, password);

        target = createWebTarget()
                .path(clientPath)
                .path(itemPath);
        
        Response response = target
            .request().cookie(cookieSessionId).get();

        String listItem = response.readEntity(String.class);
        List<CreditCardDTO> listItemTest = new ObjectMapper().readValue(listItem, List.class);
        Assert.assertEquals(Ok, response.getStatus());
        Assert.assertEquals(3, listItemTest.size());
    }
    
    /**
     * Prueba para actualizar un Item
     *
     * @generated
     */
    @Test
    public void updateCreditCardTest() throws IOException {
        Cookie cookieSessionId = login(username, password);
        CreditCardDTO item = new CreditCardDTO(oraculo.get(0));

        CreditCardDTO itemChanged = factory.manufacturePojo(CreditCardDTO.class);

        item.setExpirationMonth(itemChanged.getExpirationMonth());
        item.setExpirationYear(itemChanged.getExpirationYear());
        item.setNumber(itemChanged.getNumber());
        item.setType(itemChanged.getType());

        Response response = target
            .path(item.getId().toString())
            .request().cookie(cookieSessionId)
            .put(Entity.entity(item, MediaType.APPLICATION_JSON));

        CreditCardDTO itemTest = (CreditCardDTO)response.readEntity(CreditCardDTO.class);

        Assert.assertEquals(Ok, response.getStatus());
        Assert.assertEquals(item.getId(), itemTest.getId());
        Assert.assertEquals(item.getExpirationMonth(), itemTest.getExpirationMonth());
        Assert.assertEquals(item.getExpirationYear(), itemTest.getExpirationYear());        
        Assert.assertEquals(item.getNumber(), itemTest.getNumber());                
        Assert.assertEquals(item.getType(), itemTest.getType());  
    }    
    
    /**
     * Prueba para eliminar un Item
     *
     * @generated
     */
    @Test
    public void deleteCreditCardTest() {
        Cookie cookieSessionId = login(username, password);
        CreditCardDTO item = new CreditCardDTO(oraculo.get(0));
        Response response = target
            .path(item.getId().toString())
            .request().cookie(cookieSessionId).delete();

        Assert.assertEquals(OkWithoutContent, response.getStatus());
    }    
}
