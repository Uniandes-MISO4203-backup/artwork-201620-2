/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.tests.dto;

import co.edu.uniandes.csw.artwork.dtos.detail.ProductDetailDTO;
import co.edu.uniandes.csw.artwork.dtos.minimum.ProductDTO;
import co.edu.uniandes.csw.artwork.entities.ProductEntity;
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
public class ProductDTOTest {
    PodamFactory factory = new PodamFactoryImpl();

    private static ProductEntity productEntity;
    
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
                .addPackage(ProductDetailDTO.class.getPackage())
                .addPackage(ProductDTO.class.getPackage())
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
        em.createQuery("delete from ProductEntity").executeUpdate();
        productEntity = null;
    }
    
    public void insertData() {
        productEntity = factory.manufacturePojo(ProductEntity.class);
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
        ProductDTO product = factory.manufacturePojo(ProductDTO.class);
        
        ProductDTO newProduct = new ProductDTO();
        newProduct.setId(product.getId());
        
        Assert.assertEquals(product.getId(), newProduct.getId());
    }
    
    @Test
    public void setgetName() {
        ProductDTO product = factory.manufacturePojo(ProductDTO.class);
        
        ProductDTO newProduct = new ProductDTO();
        newProduct.setName(product.getName());
        
        Assert.assertEquals(product.getName(), newProduct.getName());
    }
    
    @Test
    public void setgetPrice() {
        ProductDTO product = factory.manufacturePojo(ProductDTO.class);
        
        ProductDTO newProduct = new ProductDTO();
        newProduct.setPrice(product.getPrice());
        
        Assert.assertEquals(product.getPrice(), newProduct.getPrice());
    }
    
    @Test
    public void entity2DTO() {
        ProductDTO artist = new ProductDTO(productEntity);
        
        Assert.assertEquals(artist.getName(), productEntity.getName());
        Assert.assertEquals(artist.getId(), productEntity.getId());
        Assert.assertEquals(artist.getPrice(), productEntity.getPrice());
    }
}
