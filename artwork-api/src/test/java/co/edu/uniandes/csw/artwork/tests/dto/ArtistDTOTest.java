/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.tests.dto;

import co.edu.uniandes.csw.artwork.dtos.detail.ArtistDetailDTO;
import co.edu.uniandes.csw.artwork.dtos.minimum.ArtistDTO;
import co.edu.uniandes.csw.artwork.entities.ArtistEntity;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
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
public class ArtistDTOTest {
    
    PodamFactory factory = new PodamFactoryImpl();

    private static ArtistEntity artistEntity;
    
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
                .addPackage(ArtistDetailDTO.class.getPackage())
                .addPackage(ArtistDTO.class.getPackage())
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
        em.createQuery("delete from ArtistEntity").executeUpdate();
        artistEntity = null;
    }
    
    public void insertData() {
        artistEntity = factory.manufacturePojo(ArtistEntity.class);
        artistEntity.setId(1L);
        em.persist(artistEntity);
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
        ArtistDTO artist = factory.manufacturePojo(ArtistDTO.class);
        
        ArtistDTO newArtist = new ArtistDTO();
        newArtist.setId(artist.getId());
        
        Assert.assertEquals(artist.getId(), newArtist.getId());
    }
    
    @Test
    public void setgetName() {
        ArtistDTO artist = factory.manufacturePojo(ArtistDTO.class);
        
        ArtistDTO newArtist = new ArtistDTO();
        newArtist.setName(artist.getName());
        
        Assert.assertEquals(artist.getName(), newArtist.getName());
    }
    
    @Test
    public void setgetNationality() {
        ArtistDTO artist = factory.manufacturePojo(ArtistDTO.class);
        
        ArtistDTO newArtist = new ArtistDTO();
        newArtist.setNationality(artist.getNationality());
        
        Assert.assertEquals(artist.getNationality(), newArtist.getNationality());
    }
    
    @Test
    public void setgetScore() {
        ArtistDTO artist = factory.manufacturePojo(ArtistDTO.class);
        
        ArtistDTO newArtist = new ArtistDTO();
        newArtist.setScore(artist.getScore());
        
        Assert.assertEquals(artist.getScore(), newArtist.getScore());
    }
    
    @Test
    public void entity2DTO() {
        ArtistDTO artist = new ArtistDTO(artistEntity);
        
        Assert.assertEquals(artist.getName(), artistEntity.getName());
        Assert.assertEquals(artist.getId(), artistEntity.getId());
        Assert.assertEquals(artist.getNationality(), artistEntity.getNationality());
        Assert.assertEquals(artist.getScore(), artistEntity.getScore());
    }
}
