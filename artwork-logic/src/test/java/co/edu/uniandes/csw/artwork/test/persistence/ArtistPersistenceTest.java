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
import co.edu.uniandes.csw.artwork.entities.ArtistEntity;
import co.edu.uniandes.csw.artwork.persistence.ArtistPersistence;
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
public class ArtistPersistenceTest {

    /**
     * @generated
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ArtistEntity.class.getPackage())
                .addPackage(ArtistPersistence.class.getPackage())
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
    private ArtistPersistence artistPersistence;

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
        em.createQuery("delete from ArtistEntity").executeUpdate();
    }

    /**
     * @generated
     */
    private List<ArtistEntity> data = new ArrayList<ArtistEntity>();

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
     *
     * @generated
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            ArtistEntity entity = factory.manufacturePojo(ArtistEntity.class);
            
            em.persist(entity);
            data.add(entity);
        }
    }
    /**
     * Prueba para crear un Artist.
     *
     * @generated
     */
    @Test
    public void createArtistTest() {
        PodamFactory factory = new PodamFactoryImpl();
        ArtistEntity newEntity = factory.manufacturePojo(ArtistEntity.class);
        ArtistEntity result = artistPersistence.create(newEntity);

        Assert.assertNotNull(result);

        ArtistEntity entity = em.find(ArtistEntity.class, result.getId());

        Assert.assertEquals(newEntity.getName(), entity.getName());
    }

    /**
     * Prueba para consultar la lista de Artists.
     *
     * @generated
     */
    @Test
    public void getArtistsTest() {
        List<ArtistEntity> list = artistPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (ArtistEntity ent : list) {
            boolean found = false;
            for (ArtistEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    @Test
    public void findArtistsByNameTest() {
        for(ArtistEntity ent : data) {
            List<ArtistEntity> list = artistPersistence.findArtistsByName(ent.getName());
            Assert.assertTrue(list.size()>0);
            boolean found = false;
            for (ArtistEntity entity : list) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Artist.
     *
     * @generated
     */
    @Test
    public void getArtistTest() {
        ArtistEntity entity = data.get(0);
        ArtistEntity newEntity = artistPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getName(), newEntity.getName());
    }

    /**
     * Prueba para eliminar un Artist.
     *
     * @generated
     */
    @Test
    public void deleteArtistTest() {
        ArtistEntity entity = data.get(0);
        artistPersistence.delete(entity.getId());
        ArtistEntity deleted = em.find(ArtistEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar un Artist.
     *
     * @generated
     */
    @Test
    public void updateArtistTest() {
        ArtistEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        ArtistEntity newEntity = factory.manufacturePojo(ArtistEntity.class);

        newEntity.setId(entity.getId());

        artistPersistence.update(newEntity);

        ArtistEntity resp = em.find(ArtistEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getName(), resp.getName());
    }
}
