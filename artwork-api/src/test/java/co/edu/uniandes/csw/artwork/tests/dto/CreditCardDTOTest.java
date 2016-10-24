/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.tests.dto;

import co.edu.uniandes.csw.artwork.dtos.minimum.CreditCardDTO;
import co.edu.uniandes.csw.artwork.entities.CreditCardEntity;
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
public class CreditCardDTOTest {
    PodamFactory factory = new PodamFactoryImpl();

    private static CreditCardEntity creditCardEntity;
    
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
                .addPackage(CreditCardDTO.class.getPackage())
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
        em.createQuery("delete from CreditCardEntity").executeUpdate();
        creditCardEntity = null;
    }
    
    public void insertData() {
        creditCardEntity = factory.manufacturePojo(CreditCardEntity.class);
        creditCardEntity.setId(1L);
        em.persist(creditCardEntity);
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
        CreditCardDTO creditCard = factory.manufacturePojo(CreditCardDTO.class);
        
        CreditCardDTO newCreditCard = new CreditCardDTO();
        newCreditCard.setId(creditCard.getId());
        
        Assert.assertEquals(creditCard.getId(), newCreditCard.getId());
    }
    
    @Test
    public void setgetNumber() {
        CreditCardDTO creditCard = factory.manufacturePojo(CreditCardDTO.class);
        
        CreditCardDTO newCreditCard = new CreditCardDTO();
        newCreditCard.setNumber(creditCard.getNumber());
        
        Assert.assertEquals(creditCard.getNumber(), newCreditCard.getNumber());
    }
    
    @Test
    public void setgetType() {
        CreditCardDTO creditCard = factory.manufacturePojo(CreditCardDTO.class);
        
        CreditCardDTO newCreditCard = new CreditCardDTO();
        newCreditCard.setType(creditCard.getType());
        
        Assert.assertEquals(creditCard.getType(), newCreditCard.getType());
    }
    
    @Test
    public void setgetExpirationMonth() {
        CreditCardDTO creditCard = factory.manufacturePojo(CreditCardDTO.class);

        CreditCardDTO newCreditCard = new CreditCardDTO();
        newCreditCard.setExpirationMonth(creditCard.getExpirationMonth());
        
        Assert.assertEquals(creditCard.getExpirationMonth(), newCreditCard.getExpirationMonth());
    }
    
    @Test
    public void setgetExpirationYear() {
        CreditCardDTO creditCard = factory.manufacturePojo(CreditCardDTO.class);

        CreditCardDTO newCreditCard = new CreditCardDTO();
        newCreditCard.setExpirationYear(creditCard.getExpirationYear());
        
        Assert.assertEquals(creditCard.getExpirationYear(), newCreditCard.getExpirationYear());
    }
    
    @Test
    public void setgetClient() {
        CreditCardDTO creditCard = factory.manufacturePojo(CreditCardDTO.class);

        CreditCardDTO newCreditCard = new CreditCardDTO();
        newCreditCard.setClient(creditCard.getClient());
        
        Assert.assertEquals(creditCard.getClient(), newCreditCard.getClient());
    }
    
    @Test
    public void entity2DTO() {
        CreditCardDTO creditCard = new CreditCardDTO(creditCardEntity);

        Assert.assertEquals(creditCard.getId(), creditCardEntity.getId());
        Assert.assertEquals(creditCard.getExpirationMonth(), creditCardEntity.getExpirationMonth());
        Assert.assertEquals(creditCard.getExpirationYear(), creditCardEntity.getExpirationYear());
        Assert.assertEquals(creditCard.getNumber(), creditCardEntity.getNumber());
        Assert.assertEquals(creditCard.getType(), creditCardEntity.getType());
    }
}
