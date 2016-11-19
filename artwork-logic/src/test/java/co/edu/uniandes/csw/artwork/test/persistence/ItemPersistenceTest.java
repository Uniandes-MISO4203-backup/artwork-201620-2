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
import co.edu.uniandes.csw.artwork.entities.ArtworkEntity;
import co.edu.uniandes.csw.artwork.entities.ClientEntity;
import co.edu.uniandes.csw.artwork.entities.ItemEntity;
import co.edu.uniandes.csw.artwork.persistence.ItemPersistence;
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
public class ItemPersistenceTest {

    /**
     * @generated
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ItemEntity.class.getPackage())
                .addPackage(ItemPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * @generated
     */
    ClientEntity fatherEntity;

    /**
     * @generated
     */
    @Inject
    private ItemPersistence itemPersistence;

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
        em.createQuery("delete from ItemEntity").executeUpdate();
        em.createQuery("delete from ClientEntity").executeUpdate();
        em.createQuery("delete from ArtworkEntity").executeUpdate();
    }

    /**
     * @generated
     */
    private List<ItemEntity> data = new ArrayList<ItemEntity>();

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
     *
     * @generated
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        fatherEntity = factory.manufacturePojo(ClientEntity.class);
        fatherEntity.setId(1L);
        em.persist(fatherEntity);
        for (int i = 0; i < 3; i++) {
            ArtworkEntity artwork = factory.manufacturePojo(ArtworkEntity.class);
            em.persist(artwork);
            ItemEntity entity = factory.manufacturePojo(ItemEntity.class);
            entity.setArtwork(artwork);
            entity.setClient(fatherEntity);
            em.persist(entity);
            data.add(entity);
        }
    }
    /**
     * Prueba para crear un Item.
     *
     * @generated
     */
    @Test
    public void createItemTest() {
	PodamFactory factory = new PodamFactoryImpl();
        ItemEntity newEntity = factory.manufacturePojo(ItemEntity.class);
        newEntity.setClient(fatherEntity);
        ItemEntity result = itemPersistence.create(newEntity);

        Assert.assertNotNull(result);

        ItemEntity entity = em.find(ItemEntity.class, result.getId());

        Assert.assertEquals(newEntity.getName(), entity.getName());
        Assert.assertEquals(newEntity.getQty(), entity.getQty());
    }

    /**
     * Prueba para consultar la lista de Items.
     *
     * @generated
     */
    @Test
    public void getItemsTest() {
        List<ItemEntity> list = itemPersistence.findAll(null, null, fatherEntity.getId());
        Assert.assertEquals(data.size(), list.size());
        for (ItemEntity ent : list) {
            boolean found = false;
            for (ItemEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    @Test
    public void getItemsByArtworkTest() {
        ItemEntity entity = data.get(0);
        ItemEntity item = itemPersistence.findByArtwork(entity.getClient().getId(), entity.getArtwork().getId());
        Assert.assertNotNull(item);
        Assert.assertEquals(entity.getName(), item.getName());
        Assert.assertEquals(entity.getQty(), item.getQty());
    }

    /**
     * Prueba para consultar un Item.
     *
     * @generated
     */
    @Test
    public void getItemTest() {
        ItemEntity entity = data.get(0);
        ItemEntity newEntity = itemPersistence.find(entity.getClient().getId(), entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getName(), newEntity.getName());
        Assert.assertEquals(entity.getQty(), newEntity.getQty());
    }

    /**
     * Prueba para eliminar un Item.
     *
     * @generated
     */
    @Test
    public void deleteItemTest() {
        ItemEntity entity = data.get(0);
        itemPersistence.delete(entity.getId());
        ItemEntity deleted = em.find(ItemEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar un Item.
     *
     * @generated
     */
    @Test
    public void updateItemTest() {
        ItemEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        ItemEntity newEntity = factory.manufacturePojo(ItemEntity.class);

        newEntity.setId(entity.getId());

        itemPersistence.update(newEntity);

        ItemEntity resp = em.find(ItemEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getName(), resp.getName());
        Assert.assertEquals(newEntity.getQty(), resp.getQty());
    }
}
