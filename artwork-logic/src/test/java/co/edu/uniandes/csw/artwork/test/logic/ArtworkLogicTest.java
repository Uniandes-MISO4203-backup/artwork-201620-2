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

import co.edu.uniandes.csw.artwork.ejbs.ArtworkLogic;
import co.edu.uniandes.csw.artwork.api.IArtworkLogic;
import co.edu.uniandes.csw.artwork.entities.ArtworkEntity;
import co.edu.uniandes.csw.artwork.entities.ArtistEntity;
import co.edu.uniandes.csw.artwork.persistence.ArtworkPersistence;
import co.edu.uniandes.csw.artwork.entities.CategoryEntity;
import co.edu.uniandes.csw.artwork.entities.ArtistEntity;
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
public class ArtworkLogicTest {

    /**
     * @generated
     */
    ArtistEntity fatherEntity;

    /**
     * @generated
     */
    private PodamFactory factory = new PodamFactoryImpl();

    /**
     * @generated
     */
    @Inject
    private IArtworkLogic artworkLogic;

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
    private List<ArtworkEntity> data = new ArrayList<ArtworkEntity>();

    /**
     * @generated
     */
    private List<CategoryEntity> categoryData = new ArrayList<>();

    /**
     * @generated
     */
    private List<ArtistEntity> artistData = new ArrayList<>();

    /**
     * @generated
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ArtworkEntity.class.getPackage())
                .addPackage(ArtworkLogic.class.getPackage())
                .addPackage(IArtworkLogic.class.getPackage())
                .addPackage(ArtworkPersistence.class.getPackage())
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
        em.createQuery("delete from ArtworkEntity").executeUpdate();
        em.createQuery("delete from CategoryEntity").executeUpdate();
        em.createQuery("delete from ArtistEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
     *
     * @generated
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            CategoryEntity category = factory.manufacturePojo(CategoryEntity.class);
            em.persist(category);
            categoryData.add(category);
        }

        fatherEntity = factory.manufacturePojo(ArtistEntity.class);
        fatherEntity.setId(1L);
        em.persist(fatherEntity);
        for (int i = 0; i < 3; i++) {
            ArtworkEntity entity = factory.manufacturePojo(ArtworkEntity.class);
            entity.setArtist(fatherEntity);

            entity.getCategory().add(categoryData.get(0));

            em.persist(entity);
            data.add(entity);
        }
    }
   /**
     * Prueba para crear un Artwork
     *
     * @generated
     */
    @Test
    public void createArtworkTest() {
        ArtworkEntity newEntity = factory.manufacturePojo(ArtworkEntity.class);
        ArtworkEntity result = artworkLogic.createArtwork(fatherEntity.getId(), newEntity);
        Assert.assertNotNull(result);
        ArtworkEntity entity = em.find(ArtworkEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getName(), entity.getName());
        Assert.assertEquals(newEntity.getImage(), entity.getImage());
        Assert.assertEquals(newEntity.getPrice(), entity.getPrice());
    }

    /**
     * Prueba para consultar la lista de Artworks
     *
     * @generated
     */
    @Test
    public void getArtworksTest() {
        List<ArtworkEntity> list = artworkLogic.getArtworks(fatherEntity.getId());
        Assert.assertEquals(data.size(), list.size());
        for (ArtworkEntity entity : list) {
            boolean found = false;
            for (ArtworkEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    
    /**
     * Prueba para consultar un Artwork
     *
     * @generated
     */
    @Test
    public void getArtworkTest() {
        ArtworkEntity entity = data.get(0);
        ArtworkEntity resultEntity = artworkLogic.getArtwork(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getName(), resultEntity.getName());
        Assert.assertEquals(entity.getImage(), resultEntity.getImage());
        Assert.assertEquals(entity.getPrice(), resultEntity.getPrice());
    }

    /**
     * Prueba para eliminar un Artwork
     *
     * @generated
     */
    @Test
    public void deleteArtworkTest() {
        ArtworkEntity entity = data.get(1);
        artworkLogic.deleteArtwork(entity.getId());
        ArtworkEntity deleted = em.find(ArtworkEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar un Artwork
     *
     * @generated
     */
    @Test
    public void updateArtworkTest() {
        ArtworkEntity entity = data.get(0);
        ArtworkEntity pojoEntity = factory.manufacturePojo(ArtworkEntity.class);

        pojoEntity.setId(entity.getId());

        artworkLogic.updateArtwork(fatherEntity.getId(), pojoEntity);

        ArtworkEntity resp = em.find(ArtworkEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getName(), resp.getName());
        Assert.assertEquals(pojoEntity.getImage(), resp.getImage());
        Assert.assertEquals(pojoEntity.getPrice(), resp.getPrice());
    }

    /**
     * Prueba para obtener una instancia de Category asociada a una instancia Artwork
     *
     * @generated
     */
    @Test
    public void getCategoryTest() {
        ArtworkEntity entity = data.get(0);
        CategoryEntity categoryEntity = categoryData.get(0);
        CategoryEntity response = artworkLogic.getCategory(entity.getId(), categoryEntity.getId());

        Assert.assertEquals(categoryEntity.getId(), response.getId());
        Assert.assertEquals(categoryEntity.getName(), response.getName());
    }

    /**
     * Prueba para obtener una colección de instancias de Category asociadas a una instancia Artwork
     *
     * @generated
     */
    @Test
    public void listCategoryTest() {
        List<CategoryEntity> list = artworkLogic.listCategory(data.get(0).getId());
        Assert.assertEquals(1, list.size());
    }

    /**
     *Prueba para asociar un Category existente a un Artwork
     *
     * @generated
     */
    @Test
    public void addCategoryTest() {
        ArtworkEntity entity = data.get(0);
        CategoryEntity categoryEntity = categoryData.get(1);
        CategoryEntity response = artworkLogic.addCategory(entity.getId(), categoryEntity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(categoryEntity.getId(), response.getId());
    }

    /**
     * Prueba para remplazar las instancias de Category asociadas a una instancia de Artwork
     *
     * @generated
     */
    @Test
    public void replaceCategoryTest() {
        ArtworkEntity entity = data.get(0);
        List<CategoryEntity> list = categoryData.subList(1, 3);
        artworkLogic.replaceCategory(entity.getId(), list);

        entity = artworkLogic.getArtwork(entity.getId());
        Assert.assertFalse(entity.getCategory().contains(categoryData.get(0)));
        Assert.assertTrue(entity.getCategory().contains(categoryData.get(1)));
        Assert.assertTrue(entity.getCategory().contains(categoryData.get(2)));
    }

    /**
     * Prueba para desasociar un Category existente de un Artwork existente
     *
     * @generated
     */
    @Test
    public void removeCategoryTest() {
        artworkLogic.removeCategory(data.get(0).getId(), categoryData.get(0).getId());
        CategoryEntity response = artworkLogic.getCategory(data.get(0).getId(), categoryData.get(0).getId());
        Assert.assertNull(response);
    }
}

