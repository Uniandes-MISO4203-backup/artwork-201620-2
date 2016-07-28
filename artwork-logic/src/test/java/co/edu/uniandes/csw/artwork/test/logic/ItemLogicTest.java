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

import co.edu.uniandes.csw.artwork.ejbs.ItemLogic;
import co.edu.uniandes.csw.artwork.api.IItemLogic;
import co.edu.uniandes.csw.artwork.entities.ItemEntity;
import co.edu.uniandes.csw.artwork.entities.ClientEntity;
import co.edu.uniandes.csw.artwork.persistence.ItemPersistence;
import co.edu.uniandes.csw.artwork.entities.ArtworkEntity;
import co.edu.uniandes.csw.artwork.entities.ClientEntity;
import co.edu.uniandes.csw.artwork.entities.ProductEntity;
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
public class ItemLogicTest {

    /**
     * @generated
     */
    ClientEntity fatherEntity;

    /**
     * @generated
     */
    private PodamFactory factory = new PodamFactoryImpl();

    /**
     * @generated
     */
    @Inject
    private IItemLogic itemLogic;

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
    private List<ItemEntity> data = new ArrayList<ItemEntity>();

    /**
     * @generated
     */
    private List<ArtworkEntity> artworkData = new ArrayList<>();

    /**
     * @generated
     */
    private List<ClientEntity> clientData = new ArrayList<>();

    /**
     * @generated
     */
    private List<ProductEntity> productData = new ArrayList<>();

    /**
     * @generated
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ItemEntity.class.getPackage())
                .addPackage(ItemLogic.class.getPackage())
                .addPackage(IItemLogic.class.getPackage())
                .addPackage(ItemPersistence.class.getPackage())
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
        em.createQuery("delete from ItemEntity").executeUpdate();
        em.createQuery("delete from ArtworkEntity").executeUpdate();
        em.createQuery("delete from ClientEntity").executeUpdate();
        em.createQuery("delete from ProductEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
     *
     * @generated
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ArtworkEntity artwork = factory.manufacturePojo(ArtworkEntity.class);
            em.persist(artwork);
            artworkData.add(artwork);
        }
        for (int i = 0; i < 3; i++) {
            ProductEntity product = factory.manufacturePojo(ProductEntity.class);
            em.persist(product);
            productData.add(product);
        }

        fatherEntity = factory.manufacturePojo(ClientEntity.class);
        fatherEntity.setId(1L);
        em.persist(fatherEntity);
        for (int i = 0; i < 3; i++) {
            ItemEntity entity = factory.manufacturePojo(ItemEntity.class);
            entity.setClient(fatherEntity);

            entity.setArtwork(artworkData.get(0));
            entity.setProduct(productData.get(0));

            em.persist(entity);
            data.add(entity);
        }
    }
   /**
     * Prueba para crear un Item
     *
     * @generated
     */
    @Test
    public void createItemTest() {
        ItemEntity newEntity = factory.manufacturePojo(ItemEntity.class);
        ItemEntity result = itemLogic.createItem(fatherEntity.getId(), newEntity);
        Assert.assertNotNull(result);
        ItemEntity entity = em.find(ItemEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getName(), entity.getName());
        Assert.assertEquals(newEntity.getQty(), entity.getQty());
    }

    /**
     * Prueba para consultar la lista de Items
     *
     * @generated
     */
    @Test
    public void getItemsTest() {
        List<ItemEntity> list = itemLogic.getItems(fatherEntity.getId());
        Assert.assertEquals(data.size(), list.size());
        for (ItemEntity entity : list) {
            boolean found = false;
            for (ItemEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    
    /**
     * Prueba para consultar un Item
     *
     * @generated
     */
    @Test
    public void getItemTest() {
        ItemEntity entity = data.get(0);
        ItemEntity resultEntity = itemLogic.getItem(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getName(), resultEntity.getName());
        Assert.assertEquals(entity.getQty(), resultEntity.getQty());
    }

    /**
     * Prueba para eliminar un Item
     *
     * @generated
     */
    @Test
    public void deleteItemTest() {
        ItemEntity entity = data.get(1);
        itemLogic.deleteItem(entity.getId());
        ItemEntity deleted = em.find(ItemEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar un Item
     *
     * @generated
     */
    @Test
    public void updateItemTest() {
        ItemEntity entity = data.get(0);
        ItemEntity pojoEntity = factory.manufacturePojo(ItemEntity.class);

        pojoEntity.setId(entity.getId());

        itemLogic.updateItem(fatherEntity.getId(), pojoEntity);

        ItemEntity resp = em.find(ItemEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getName(), resp.getName());
        Assert.assertEquals(pojoEntity.getQty(), resp.getQty());
    }
}

