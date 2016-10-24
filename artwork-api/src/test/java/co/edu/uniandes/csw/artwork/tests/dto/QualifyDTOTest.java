/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.tests.dto;

import co.edu.uniandes.csw.artwork.dtos.detail.QualifyDetailDTO;
import co.edu.uniandes.csw.artwork.dtos.minimum.QualifyDTO;
import co.edu.uniandes.csw.artwork.entities.QualifyEntity;
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
public class QualifyDTOTest {
    PodamFactory factory = new PodamFactoryImpl();

    private static QualifyEntity productEntity;
    
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
                .addPackage(QualifyDetailDTO.class.getPackage())
                .addPackage(QualifyDTO.class.getPackage())
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
        em.createQuery("delete from QualifyEntity").executeUpdate();
        productEntity = null;
    }
    
    public void insertData() {
        productEntity = factory.manufacturePojo(QualifyEntity.class);
        productEntity.setId(1L);
        em.persist(productEntity);
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
        QualifyDTO product = factory.manufacturePojo(QualifyDTO.class);
        
        QualifyDTO newProduct = new QualifyDTO();
        newProduct.setId(product.getId());
        
        Assert.assertEquals(product.getId(), newProduct.getId());
    }
    
    @Test
    public void setgetName() {
        QualifyDTO product = factory.manufacturePojo(QualifyDTO.class);
        
        QualifyDTO newProduct = new QualifyDTO();
        newProduct.setName(product.getName());
        
        Assert.assertEquals(product.getName(), newProduct.getName());
    }
    
    @Test
    public void setgetMessage() {
        QualifyDTO product = factory.manufacturePojo(QualifyDTO.class);
        
        QualifyDTO newProduct = new QualifyDTO();
        newProduct.setMessage(product.getMessage());
        
        Assert.assertEquals(product.getMessage(), newProduct.getMessage());
    }
    
    @Test
    public void setgetScore() {
        QualifyDTO product = factory.manufacturePojo(QualifyDTO.class);
        
        QualifyDTO newProduct = new QualifyDTO();
        newProduct.setScore(product.getScore());
        
        Assert.assertEquals(product.getScore(), newProduct.getScore());
    }
    
    @Test
    public void entity2DTO() {
        QualifyDTO artist = new QualifyDTO(productEntity);
        
        Assert.assertEquals(artist.getName(), productEntity.getName());
        Assert.assertEquals(artist.getId(), productEntity.getId());
        Assert.assertEquals(artist.getMessage(), productEntity.getMessage());
        Assert.assertEquals(artist.getScore(), productEntity.getScore());
    }
}
