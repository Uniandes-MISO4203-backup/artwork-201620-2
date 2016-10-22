/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.tests.rest;

import co.edu.uniandes.csw.artwork.dtos.detail.ArtistLikeDetailDTO;
import co.edu.uniandes.csw.artwork.dtos.minimum.ArtistDTO;
import co.edu.uniandes.csw.artwork.dtos.minimum.ArtistLikeDTO;
import co.edu.uniandes.csw.artwork.dtos.minimum.CreditCardDTO;
import co.edu.uniandes.csw.artwork.entities.ArtistEntity;
import co.edu.uniandes.csw.artwork.entities.ArtistLikeEntity;
import co.edu.uniandes.csw.artwork.entities.ClientEntity;
import co.edu.uniandes.csw.artwork.entities.CreditCardEntity;
import co.edu.uniandes.csw.artwork.resources.CreditCardResource;
import co.edu.uniandes.csw.artwork.tests.Utils;
import co.edu.uniandes.csw.auth.model.UserDTO;
import co.edu.uniandes.csw.auth.security.JWT;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import javax.ws.rs.client.ClientBuilder;
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
public class ArtistLikeTest {
    
    private WebTarget target;
    private final String apiPath = Utils.apiPath;
    private final String username = Utils.username;
    private final String password = Utils.password;       

    @ArquillianResource
    private URL deploymentURL;
    private final static List<ArtistLikeEntity> oraculo = new ArrayList<>();
    
    PodamFactory factory = new PodamFactoryImpl();
    private final int Ok = Response.Status.OK.getStatusCode();
    private final int Created = Response.Status.CREATED.getStatusCode();
    
    @PersistenceContext(unitName = "ArtworkPU")
    private EntityManager em;    
    
    @Inject
    private UserTransaction utx;
    private final String resourcePath = "artistLike";
    
    ArtistEntity fatherEntity;
    ArtistDTO fatherDTO;
    
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
    
    private void clearData() {
        em.createQuery("delete from ArtistLikeEntity").executeUpdate();
        em.createQuery("delete from ArtistEntity").executeUpdate();
        oraculo.clear();
    }        
    
   /**
     * Datos iniciales para el correcto funcionamiento de las pruebas.
     *
     * @generated
     */
    public void insertData() {
        fatherEntity = factory.manufacturePojo(ArtistEntity.class);
        fatherEntity.setId(1L);
        em.persist(fatherEntity);
        
        fatherDTO = new ArtistDTO(fatherEntity);
        
        for (int i = 0; i < 3; i++) {            
            ArtistLikeEntity item = factory.manufacturePojo(ArtistLikeEntity.class);
            item.setId(i + 1L);
            item.setArtist(fatherEntity);
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
        
        target = createWebTarget().path(resourcePath);
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
                .post(javax.ws.rs.client.Entity.entity(user, MediaType.APPLICATION_JSON));
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
    public void createArtistLikeTest() {
        ArtistLikeDetailDTO item = factory.manufacturePojo(ArtistLikeDetailDTO.class);
        item.setArtist(fatherDTO);
        Cookie cookieSessionId = login(username, password);

        Response response = target
            .request().cookie(cookieSessionId)
            .post(javax.ws.rs.client.Entity.entity(item, MediaType.APPLICATION_JSON));

        ArtistLikeDetailDTO itemTest = (ArtistLikeDetailDTO)response.readEntity(ArtistLikeDetailDTO.class);

        Assert.assertEquals(Created, response.getStatus());
        Assert.assertEquals(item.getName(), itemTest.getName());
        
        ArtistLikeEntity entity = em.find(ArtistLikeEntity.class, itemTest.getId());
        Assert.assertNotNull(entity);

        Assert.assertEquals(itemTest.getId(), entity.getId());
        Assert.assertEquals(item.getName(), entity.getName());
    }
    
    
    /**
     * Prueba para consultar un Item
     *
     * @generated
     */
    @Test
    public void getArtistNumLikesTest() {
        Cookie cookieSessionId = login(username, password);

        Long numlikes = target
            .path(fatherEntity.getId().toString())
            .request().cookie(cookieSessionId).get(Long.class);
        
        Assert.assertEquals(numlikes, new Long(oraculo.size()));
    }       
}
