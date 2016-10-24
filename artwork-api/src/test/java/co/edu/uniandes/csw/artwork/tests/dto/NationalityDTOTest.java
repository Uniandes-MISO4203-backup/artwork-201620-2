/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.tests.dto;

import co.edu.uniandes.csw.artwork.dtos.minimum.NationalityDTO;
import co.edu.uniandes.csw.artwork.entities.NationalityEntity;
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
public class NationalityDTOTest {
    PodamFactory factory = new PodamFactoryImpl();

    private static NationalityEntity itemEntity;
    
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
                .addPackage(NationalityDTO.class.getPackage())
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
        em.createQuery("delete from NationalityEntity").executeUpdate();
        itemEntity = null;
    }
    
    public void insertData() {
        itemEntity = factory.manufacturePojo(NationalityEntity.class);
        itemEntity.setId(1L);
        em.persist(itemEntity);
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
        NationalityDTO nationality = factory.manufacturePojo(NationalityDTO.class);
        
        NationalityDTO newNationality = new NationalityDTO();
        newNationality.setId(nationality.getId());
        
        Assert.assertEquals(nationality.getId(), newNationality.getId());
    }
    
    @Test
    public void setgetName() {
        NationalityDTO nationality = factory.manufacturePojo(NationalityDTO.class);
        
        NationalityDTO newNationality = new NationalityDTO();
        newNationality.setName(nationality.getName());
        
        Assert.assertEquals(nationality.getName(), newNationality.getName());
    }
    
    @Test
    public void setgetDescription() {
        NationalityDTO nationality = factory.manufacturePojo(NationalityDTO.class);
        
        NationalityDTO newNationality = new NationalityDTO();
        newNationality.setDescription(nationality.getDescription());
        
        Assert.assertEquals(nationality.getDescription(), newNationality.getDescription());
    }
    
    @Test
    public void entity2DTO() {
        NationalityDTO artist = new NationalityDTO(itemEntity);
        
        Assert.assertEquals(artist.getName(), itemEntity.getName());
        Assert.assertEquals(artist.getId(), itemEntity.getId());
        Assert.assertEquals(artist.getDescription(), itemEntity.getDescription());
    }
}
