/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.test.logic;

import co.edu.uniandes.csw.artwork.api.ICommentLogic;
import co.edu.uniandes.csw.artwork.ejbs.CommentLogic;
import co.edu.uniandes.csw.artwork.entities.ArtworkEntity;
import co.edu.uniandes.csw.artwork.entities.CommentEntity;
import co.edu.uniandes.csw.artwork.persistence.CommentPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.PersistenceContext;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author cf.agudelo12
 */
@RunWith(Arquillian.class)
public class CommentLogicTest {

    ArtworkEntity fatherEntity;

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private ICommentLogic commentLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<CommentEntity> data = new ArrayList<CommentEntity>();

    private List<ArtworkEntity> artistData = new ArrayList<>();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CommentEntity.class.getPackage())
                .addPackage(CommentLogic.class.getPackage())
                .addPackage(ICommentLogic.class.getPackage())
                .addPackage(CommentPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

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

    private void clearData() {
        em.createQuery("delete from CommentEntity").executeUpdate();
        em.createQuery("delete from ArtworkEntity").executeUpdate();
    }


    private void insertData() {
        fatherEntity = factory.manufacturePojo(ArtworkEntity.class);
        fatherEntity.setId(1L);
        em.persist(fatherEntity);
        for (int i = 0; i < 3; i++) {
            CommentEntity entity = factory.manufacturePojo(CommentEntity.class);
            entity.setArtwork(fatherEntity);
            em.persist(entity);
            data.add(entity);
        }
    }

    @Test
    public void createCommentTest() {
        CommentEntity newEntity = factory.manufacturePojo(CommentEntity.class);
        CommentEntity result = commentLogic.createComment(fatherEntity.getId(), newEntity);
        Assert.assertNotNull(result);
        CommentEntity entity = em.find(CommentEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getName(), entity.getName());
        Assert.assertEquals(newEntity.getMessage(), entity.getMessage());
        Assert.assertEquals(newEntity.getArtwork().getId(), entity.getArtwork().getId());
    }

    @Test
    public void getCommentsTest() {
        List<CommentEntity> list = commentLogic.getComments(fatherEntity.getId());
        Assert.assertEquals(data.size(), list.size());
        for (CommentEntity entity : list) {
            boolean found = false;
            for (CommentEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    @Test
    public void getCommentTest() {
        CommentEntity entity = data.get(0);
        CommentEntity resultEntity = commentLogic.getComment(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getName(), resultEntity.getName());
        Assert.assertEquals(entity.getMessage(), resultEntity.getMessage());
        Assert.assertEquals(entity.getArtwork().getId(), resultEntity.getArtwork().getId());
    }

    @Test
    public void deleteCommentTest() {
        CommentEntity entity = data.get(1);
        commentLogic.deleteComment(entity.getId());
        CommentEntity deleted = em.find(CommentEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
