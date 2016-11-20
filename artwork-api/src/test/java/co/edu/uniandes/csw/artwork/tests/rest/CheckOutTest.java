/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.tests.rest;

import co.edu.uniandes.csw.artwork.dtos.minimum.CheckOutDTO;
import co.edu.uniandes.csw.artwork.dtos.minimum.CheckOutItemDTO;
import co.edu.uniandes.csw.artwork.dtos.minimum.CreditCardDTO;
import co.edu.uniandes.csw.artwork.entities.ArtworkEntity;
import co.edu.uniandes.csw.artwork.entities.CheckOutEntity;
import co.edu.uniandes.csw.artwork.entities.ClientEntity;
import co.edu.uniandes.csw.artwork.entities.CreditCardEntity;
import co.edu.uniandes.csw.artwork.entities.ShoppingCartItemEntity;
import co.edu.uniandes.csw.artwork.resources.CheckOutResource;
import co.edu.uniandes.csw.artwork.tests.Utils;
import co.edu.uniandes.csw.auth.model.UserDTO;
import co.edu.uniandes.csw.auth.security.JWT;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import static java.util.Calendar.DAY_OF_YEAR;
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
public class CheckOutTest {
    
    ClientEntity fatherEntity;
    CreditCardEntity creditCard;
    ArtworkEntity artwork;
    List<ShoppingCartItemEntity> shoppingCart = new ArrayList<>();   
    private List<CheckOutEntity> data = new ArrayList<>();
    
    private WebTarget target;
    private final String apiPath = Utils.apiPath;
    private final String username = Utils.username;
    private final String password = Utils.password;        
    
    private final int Ok = Response.Status.OK.getStatusCode();
    private final int Created = Response.Status.CREATED.getStatusCode();
    private final int OkWithoutContent = Response.Status.NO_CONTENT.getStatusCode();    
    
    private final String itemPath = "checkout";    

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
                .addPackage(CheckOutResource.class.getPackage())
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

        em.createNativeQuery("INSERT INTO ClientEntity (ID, NAME, AGE) VALUES(99999, 'Test User', 35)").executeUpdate();
        fatherEntity = em.getReference(ClientEntity.class, 99999L);
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
    public void createCheckOutTest() throws IOException {
        CheckOutDTO item = factory.manufacturePojo(CheckOutDTO.class);
        item.setCreditCard(new CreditCardDTO(creditCard));
        Cookie cookieSessionId = login(username, password);

        Response response = target
            .request().cookie(cookieSessionId)
            .post(Entity.entity(item, MediaType.APPLICATION_JSON));

        CheckOutDTO result = (CheckOutDTO)response.readEntity(CheckOutDTO.class);
        Assert.assertNotNull(result);
        
        ShoppingCartItemEntity shoppingItem = shoppingCart.get(0);
        
        CheckOutEntity entity = em.find(CheckOutEntity.class, result.getId());
        Assert.assertEquals(result.getId(), entity.getId());
        Assert.assertEquals(result.getShippingAddress(), entity.getShippingAddress());
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(entity.getCheckOutDate());
        
        Calendar calresult = Calendar.getInstance();
        calresult.setTime(result.getCheckOutDate());
        
        Assert.assertEquals(cal.get(DAY_OF_YEAR), calresult.get(DAY_OF_YEAR));
        Assert.assertEquals(result.getCreditCard().getNumber(), entity.getCreditCard().getNumber());
        
        Long total =  shoppingItem.getQty() *  shoppingItem.getArtwork().getPrice();
        Assert.assertEquals(result.getTotal(), total);
        
        List<CheckOutItemDTO> items = result.getCheckOutItems();
        Assert.assertEquals(result.getCheckOutItems().size(), items.size());                
        
        CheckOutItemDTO checkOutItem = result.getCheckOutItems().get(0);
        Assert.assertEquals(checkOutItem.getQty(), shoppingItem.getQty());
        Assert.assertEquals(checkOutItem.getArtwork().getId(), shoppingItem.getArtwork().getId());        
    } 
    
        /**
     * Prueba para consultar la lista de Items
     *
     * @generated
     */
    @Test
    public void listCheckOutTest() throws IOException {
        Cookie cookieSessionId = login(username, password);

        Response response = target
            .request().cookie(cookieSessionId).get();

        String listItem = response.readEntity(String.class);
        List<CheckOutDTO> listItemTest = new ObjectMapper().readValue(listItem, List.class);
        Assert.assertEquals(Ok, response.getStatus());
        Assert.assertEquals(3, listItemTest.size());
    }
    
     /**
     * Prueba para consultar la lista de Items
     *
     * @generated
     */
    @Test
    public void listCheckOutPagedTest() throws IOException {
        Cookie cookieSessionId = login(username, password);

        Response response = target
                .queryParam("page", "1")                
                .queryParam("limit", "10")
                .request()
                .cookie(cookieSessionId).get();

        String listItem = response.readEntity(String.class);
        List<CheckOutDTO> listItemTest = new ObjectMapper().readValue(listItem, List.class);
        Assert.assertEquals(Ok, response.getStatus());
        Assert.assertEquals(3, listItemTest.size());
    }    
}
