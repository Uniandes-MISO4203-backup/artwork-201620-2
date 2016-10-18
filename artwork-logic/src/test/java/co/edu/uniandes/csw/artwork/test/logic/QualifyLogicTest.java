/*
The MIT License (MIT)

Copyright (c) 2015 Los Andes University

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package co.edu.uniandes.csw.artwork.test.logic;

import co.edu.uniandes.csw.artwork.api.IQualifyLogic;
import co.edu.uniandes.csw.artwork.ejbs.QualifyLogic;
import co.edu.uniandes.csw.artwork.entities.ArtworkEntity;
import co.edu.uniandes.csw.artwork.entities.ClientEntity;
import co.edu.uniandes.csw.artwork.entities.QualifyEntity;
import co.edu.uniandes.csw.artwork.persistence.QualifyPersistence;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.junit.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class QualifyLogicTest {

    private ArtworkEntity fatherEntity1;
    private ClientEntity fatherClient;
    private List<ClientEntity> clients = new ArrayList<ClientEntity>();

    /**
     * @generated
     */
    private PodamFactory factory = new PodamFactoryImpl();

    /**
     * @generated
     */
    @Inject
    private IQualifyLogic qualifyLogic;

    /**
     * @generated
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * @generated
     */
    @Inject
    private UserTransaction utx;

    /**
     * @generated
     */
    private List<QualifyEntity> data = new ArrayList<QualifyEntity>();

    /**
     * @generated
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(QualifyEntity.class.getPackage())
                .addPackage(QualifyLogic.class.getPackage())
                .addPackage(IQualifyLogic.class.getPackage())
                .addPackage(QualifyPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Configuración inicial de la prueba.
     *
     * @generated
     */
    @Before
    public void configTest() {
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

    /**
     * Limpia las tablas que están implicadas en la prueba.
     *
     * @generated
     */
    private void clearData() {
        em.createQuery("delete from QualifyEntity").executeUpdate();
        em.createQuery("delete from ClientEntity").executeUpdate();
        em.createQuery("delete from ArtworkEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
     *
     * @generated
     */
    private void insertData() {
        
        fatherEntity1 = factory.manufacturePojo(ArtworkEntity.class);
        fatherEntity1.setId(1L);
        em.persist(fatherEntity1);
        
        fatherClient = factory.manufacturePojo(ClientEntity.class);
        fatherClient.setId(10L);
        em.persist(fatherClient);
              
        for (int i = 0; i < 3; i++) {
            ClientEntity client = factory.manufacturePojo(ClientEntity.class);
            client.setId(new Long(i+1));
            em.persist(client);
            
            QualifyEntity entity = factory.manufacturePojo(QualifyEntity.class);
            entity.setArtwork(fatherEntity1);
            entity.setClient(client);
            em.persist(entity);
            data.add(entity);
            clients.add(client);
        }
    }
    /**
     * Prueba para crear un Qualify
     *
     * @generated
     */
    @Test
    public void createQualifyTest() {
        QualifyEntity newEntity = factory.manufacturePojo(QualifyEntity.class);
        QualifyEntity result = qualifyLogic.addQualify(fatherEntity1.getId(), fatherClient.getId(), newEntity);
        Assert.assertNotNull(result);
        QualifyEntity entity = em.find(QualifyEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getName(), entity.getName());
        Assert.assertEquals(newEntity.getScore(), entity.getScore());
        Assert.assertEquals(newEntity.getMessage(), entity.getMessage());
    }

    /**
     * Prueba para consultar la lista de Calificaciones
     *
     * @generated
     */
    @Test
    public void getQualifiesTest() {
        List<QualifyEntity> score = qualifyLogic.getQualifys(fatherEntity1.getId());
        Assert.assertNotNull(score);
        
    }
}