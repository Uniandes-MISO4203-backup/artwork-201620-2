/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.tests.rest;

import co.edu.uniandes.csw.artwork.dtos.minimum.ArtworkDTO;
import co.edu.uniandes.csw.artwork.entities.ArtistEntity;
import co.edu.uniandes.csw.artwork.entities.ArtworkEntity;
import co.edu.uniandes.csw.artwork.entities.CategoryEntity;
import co.edu.uniandes.csw.artwork.resources.ArtworkResource;
import co.edu.uniandes.csw.artwork.tests.Utils;
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
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Cookie;
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
public class RootArtworkTest {
    
    private WebTarget target;
    private final String apiPath = Utils.apiPath;
    PodamFactory factory = new PodamFactoryImpl();

    private final int Ok = Response.Status.OK.getStatusCode();
    private final static List<ArtworkEntity> oraculo = new ArrayList<>();
    private final String artworkPath = "artworks";

    ArtistEntity fatherArtistEntity;
    CategoryEntity fatherCategoryEntity;    
    
    @ArquillianResource
    private URL deploymentURL;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                // Se agrega las dependencias
                .addAsLibraries(Maven.resolver().loadPomFromFile("pom.xml")
                        .importRuntimeDependencies().resolve()
                        .withTransitivity().asFile())
                // Se agregan los compilados de los paquetes de servicios
                .addPackage(ArtworkResource.class.getPackage())
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
        em.createQuery("delete from ArtworkEntity").executeUpdate();
        oraculo.clear();
    }    
    
   /**
     * Datos iniciales para el correcto funcionamiento de las pruebas.
     *
     * @generated
     */
    public void insertData() {
        fatherArtistEntity = factory.manufacturePojo(ArtistEntity.class);
        fatherArtistEntity.setId(1L);
        em.persist(fatherArtistEntity);
        
        fatherCategoryEntity = factory.manufacturePojo(CategoryEntity.class);
        fatherCategoryEntity.setId(1L);
        em.persist(fatherCategoryEntity);        
        List<CategoryEntity> categoryList = new ArrayList<>();
        categoryList.add(fatherCategoryEntity);
                
        for (int i = 0; i < 3; i++) {            
            ArtworkEntity artwork = factory.manufacturePojo(ArtworkEntity.class);
            artwork.setId(i + 1L);
            artwork.setArtist(fatherArtistEntity);
            artwork.setCategory(categoryList);
            em.persist(artwork);
            oraculo.add(artwork);
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
        target = createWebTarget().path(artworkPath);
    }    
    
    
    /**
     * Prueba para consultar la lista de Artworks
     *
     * @throws java.io.IOException
     * @generated
     */
    @Test
    public void listArtworksAsGuestTest() throws IOException {

        Response response = target.request().get();

        String listArtwork = response.readEntity(String.class);
        List<ArtworkDTO> listArtworkTest = new ObjectMapper().readValue(listArtwork, List.class);
        Assert.assertEquals(Ok, response.getStatus());
        Assert.assertEquals(3, listArtworkTest.size());
    }    
    
    /**
     * Prueba para consultar la lista de Artworks
     *
     * @throws java.io.IOException
     * @generated
     */
    @Test
    public void listArtworksAsGuestPagedTest() throws IOException {

        Response response = target
            .queryParam("page", "1")                
            .queryParam("maxRecords", "10")                  
            .request().get();

        String listArtwork = response.readEntity(String.class);
        List<ArtworkDTO> listArtworkTest = new ObjectMapper().readValue(listArtwork, List.class);
        Assert.assertEquals(Ok, response.getStatus());
        Assert.assertEquals(3, listArtworkTest.size());
    }       
    
    
    /**
     * Prueba para consultar la lista de Artworks
     *
     * @throws java.io.IOException
     * @generated
     */
    @Test
    public void listArtworksAsGuestByArtistNameTest() throws IOException {

        Response response = target
                .path("filtered")
                .queryParam("artistName", fatherArtistEntity.getName())
                .request().get();

        String listArtwork = response.readEntity(String.class);
        List<ArtworkDTO> listArtworkTest = new ObjectMapper().readValue(listArtwork, List.class);
        Assert.assertEquals(Ok, response.getStatus());
        Assert.assertEquals(3, listArtworkTest.size());
    }      
    
    /**
     * Prueba para consultar la lista de Artworks
     *
     * @throws java.io.IOException
     * @generated
     */
    @Test
    public void listArtworksAsGuestByCategoryTest() throws IOException  {

        Response response = target
                .path("filtered")
                .queryParam("artistName", fatherArtistEntity.getName())                
                .queryParam("categoryid", fatherCategoryEntity.getId())
                .request().get();

        String listArtwork = response.readEntity(String.class);
        List<ArtworkDTO> listArtworkTest = new ObjectMapper().readValue(listArtwork, List.class);
        Assert.assertEquals(Ok, response.getStatus());
        Assert.assertEquals(3, listArtworkTest.size());
    }      
    
    
    /**
     * Prueba para consultar la lista de Artworks
     *
     * @throws java.io.IOException
     * @generated
     */
    @Test
    public void listArtworksAsGuestByCategoryPAgedTest() throws IOException  {

        Response response = target
                .path("filtered")
                .queryParam("page", "1")                
                .queryParam("maxRecords", "10")                    
                .queryParam("artistName", fatherArtistEntity.getName())                
                .queryParam("categoryid", fatherCategoryEntity.getId())
                .request().get();

        String listArtwork = response.readEntity(String.class);
        List<ArtworkDTO> listArtworkTest = new ObjectMapper().readValue(listArtwork, List.class);
        Assert.assertEquals(Ok, response.getStatus());
        Assert.assertEquals(3, listArtworkTest.size());
    }      
}
