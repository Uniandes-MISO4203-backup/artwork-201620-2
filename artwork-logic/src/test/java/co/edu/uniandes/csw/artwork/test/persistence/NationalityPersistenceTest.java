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
import co.edu.uniandes.csw.artwork.entities.NationalityEntity;
import co.edu.uniandes.csw.artwork.persistence.NationalityPersistence;
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
public class NationalityPersistenceTest {

    /**
     * @generated
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(NationalityEntity.class.getPackage())
                .addPackage(NationalityPersistence.class.getPackage())
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
    private NationalityPersistence nationalityPersistence;

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
        em.createQuery("delete from ArtistEntity").executeUpdate();
        em.createQuery("delete from NationalityEntity").executeUpdate();
    }

    /**
     * @generated
     */
    private List<NationalityEntity> data = new ArrayList<NationalityEntity>();

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
     *
     * @generated
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            NationalityEntity entity = factory.manufacturePojo(NationalityEntity.class);
            
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
    public void createNationalityTest() {
        PodamFactory factory = new PodamFactoryImpl();
        NationalityEntity newEntity = factory.manufacturePojo(NationalityEntity.class);
        NationalityEntity result = nationalityPersistence.create(newEntity);

        Assert.assertNotNull(result);

        NationalityEntity entity = em.find(NationalityEntity.class, result.getId());

        Assert.assertEquals(newEntity.getName(), entity.getName());
    }

    /**
     * Prueba para consultar la lista de Nationalities.
     *
     * @generated
     */
    @Test
    public void getArtistsTest() {
        List<NationalityEntity> list = nationalityPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (NationalityEntity ent : list) {
            boolean found = false;
            for (NationalityEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    @Test
    public void findNationalityByNameTest() {
        for(NationalityEntity ent : data) {
            List<NationalityEntity> list = nationalityPersistence.findByName(ent.getName());
            Assert.assertTrue(list.size()>0);
            boolean found = false;
            for (NationalityEntity entity : list) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Nationality.
     *
     * @generated
     */
    @Test
    public void getNationalityTest() {
        NationalityEntity entity = data.get(0);
        NationalityEntity newEntity = nationalityPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getName(), newEntity.getName());
    }

    /**
     * Prueba para eliminar una Nationality.
     *
     * @generated
     */
    @Test
    public void deleteNationalityTest() {
        NationalityEntity entity = data.get(0);
        nationalityPersistence.delete(entity.getId());
        NationalityEntity deleted = em.find(NationalityEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar una Nationality.
     *
     * @generated
     */
    @Test
    public void updateNationalityTest() {
        NationalityEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        NationalityEntity newEntity = factory.manufacturePojo(NationalityEntity.class);

        newEntity.setId(entity.getId());

        nationalityPersistence.update(newEntity);

        NationalityEntity resp = em.find(NationalityEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getName(), resp.getName());
    }
}