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
package co.edu.uniandes.csw.artwork.test.persistence;
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
public class QualifyPersistenceTest {

    /**
     * @generated
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(QualifyEntity.class.getPackage())
                .addPackage(QualifyPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * @generated
     */

    /**
     * @generated
     */
    @Inject
    private QualifyPersistence qualifyPersistence;

    /**
     * @generated
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * @generated
     */
    @Inject
    UserTransaction utx;

    /**
     * Configuración inicial de la prueba.
     *
     * @generated
     */
    @Before
    public void configTest() {
        try {
            utx.begin();
            em.joinTransaction();
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
        em.createQuery("delete from ArtworkEntity").executeUpdate();
        em.createQuery("delete from QualifyEntity").executeUpdate();
        em.createQuery("delete from ClientEntity").executeUpdate();
    }

    /**
     * @generated
     */
    private List<QualifyEntity> data = new ArrayList<QualifyEntity>();

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
     *
     * @generated
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            QualifyEntity entity = factory.manufacturePojo(QualifyEntity.class);
            
            em.persist(entity);
            data.add(entity);
        }
    }
    /**
     * Prueba para crear una nationality.
     *
     * @generated
     */
    @Test
    public void createQualifyTest() {
        PodamFactory factory = new PodamFactoryImpl();
        QualifyEntity newEntity = factory.manufacturePojo(QualifyEntity.class);
        QualifyEntity result = qualifyPersistence.create(newEntity);

        Assert.assertNotNull(result);

        QualifyEntity entity = em.find(QualifyEntity.class, result.getId());

        Assert.assertEquals(newEntity.getName(), entity.getName());
    }

    /**
     * Prueba para consultar la lista de Nationalities.
     *
     * @generated
     */
    @Test
    public void getQualifiesTest() {
        List<QualifyEntity> list = qualifyPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (QualifyEntity ent : list) {
            boolean found = false;
            for (QualifyEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    
    /**
     * Prueba para consultar un Qualify.
     *
     * @generated
     */
    @Test
    public void getQualifyTest() {
        QualifyEntity entity = data.get(0);
        QualifyEntity newEntity = qualifyPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getScore(), newEntity.getScore());
    }

    /**
     * Prueba para eliminar una Qualify.
     *
     * @generated
     */
    @Test
    public void deleteQualifyTest() {
        QualifyEntity entity = data.get(0);
        qualifyPersistence.delete(entity.getId());
        QualifyEntity deleted = em.find(QualifyEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar una Qualify.
     *
     * @generated
     */
    @Test
    public void updateQualifyTest() {
        QualifyEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        QualifyEntity newEntity = factory.manufacturePojo(QualifyEntity.class);

        newEntity.setId(entity.getId());

        qualifyPersistence.update(newEntity);

        QualifyEntity resp = em.find(QualifyEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getName(), resp.getName());
    }
}