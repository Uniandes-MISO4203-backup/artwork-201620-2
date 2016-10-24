/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.tests.dto;

import co.edu.uniandes.csw.artwork.dtos.detail.CategoryDetailDTO;
import co.edu.uniandes.csw.artwork.dtos.minimum.CategoryDTO;
import co.edu.uniandes.csw.artwork.entities.CategoryEntity;
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
public class CategoryDTOTest {
    PodamFactory factory = new PodamFactoryImpl();

    private static CategoryEntity categoryEntity;
    
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
                .addPackage(CategoryDetailDTO.class.getPackage())
                .addPackage(CategoryDTO.class.getPackage())
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
        em.createQuery("delete from CategoryEntity").executeUpdate();
        categoryEntity = null;
    }
    
    public void insertData() {
        categoryEntity = factory.manufacturePojo(CategoryEntity.class);
        categoryEntity.setId(1L);
        em.persist(categoryEntity);
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
        CategoryDTO category = factory.manufacturePojo(CategoryDTO.class);
        
        CategoryDTO newCategory = new CategoryDTO();
        newCategory.setId(category.getId());
        
        Assert.assertEquals(category.getId(), newCategory.getId());
    }
    
    @Test
    public void setgetName() {
        CategoryDTO category = factory.manufacturePojo(CategoryDTO.class);
        
        CategoryDTO newCategory = new CategoryDTO();
        newCategory.setName(category.getName());
        
        Assert.assertEquals(category.getName(), newCategory.getName());
    }
    
    @Test
    public void entity2DTO() {
        CategoryDTO artist = new CategoryDTO(categoryEntity);
        
        Assert.assertEquals(artist.getName(), categoryEntity.getName());
        Assert.assertEquals(artist.getId(), categoryEntity.getId());
    }
}
