/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.tests.dto;

import co.edu.uniandes.csw.artwork.dtos.detail.ArtworkDetailDTO;
import co.edu.uniandes.csw.artwork.dtos.minimum.ArtworkDTO;
import co.edu.uniandes.csw.artwork.entities.ArtworkEntity;
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
public class ArtworkDTOTest {
    PodamFactory factory = new PodamFactoryImpl();

    private static ArtworkEntity artworkEntity;
    
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
                .addPackage(ArtworkDetailDTO.class.getPackage())
                .addPackage(ArtworkDTO.class.getPackage())
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
        em.createQuery("delete from ArtworkEntity").executeUpdate();
        artworkEntity = null;
    }
    
    public void insertData() {
        artworkEntity = factory.manufacturePojo(ArtworkEntity.class);
        artworkEntity.setId(1L);
        em.persist(artworkEntity);
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
        ArtworkDTO artwork = factory.manufacturePojo(ArtworkDTO.class);
        
        ArtworkDTO newArtwork = new ArtworkDTO();
        newArtwork.setId(artwork.getId());
        
        Assert.assertEquals(artwork.getId(), newArtwork.getId());
    }
    
    @Test
    public void setgetName() {
        ArtworkDTO artwork = factory.manufacturePojo(ArtworkDTO.class);
        
        ArtworkDTO newArtwork = new ArtworkDTO();
        newArtwork.setName(artwork.getName());
        
        Assert.assertEquals(artwork.getName(), newArtwork.getName());
    }
    
    @Test
    public void setgetImage() {
        ArtworkDTO artwork = factory.manufacturePojo(ArtworkDTO.class);
        
        ArtworkDTO newArtwork = new ArtworkDTO();
        newArtwork.setImage(artwork.getImage());
        
        Assert.assertEquals(artwork.getImage(), newArtwork.getImage());
    }
    
    @Test
    public void setgetPrice() {
        ArtworkDTO artwork = factory.manufacturePojo(ArtworkDTO.class);
        
        ArtworkDTO newArtwork = new ArtworkDTO();
        newArtwork.setPrice(artwork.getPrice());
        
        Assert.assertEquals(artwork.getPrice(), newArtwork.getPrice());
    }
    
    @Test
    public void entity2DTO() {
        ArtworkDTO artist = new ArtworkDTO(artworkEntity);
        
        Assert.assertEquals(artist.getName(), artworkEntity.getName());
        Assert.assertEquals(artist.getId(), artworkEntity.getId());
        Assert.assertEquals(artist.getImage(), artworkEntity.getImage());
        Assert.assertEquals(artist.getPrice(), artworkEntity.getPrice());
    }
}
