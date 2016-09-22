/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.ejbs;

import co.edu.uniandes.csw.artwork.api.IArtworkLogic;
import co.edu.uniandes.csw.artwork.api.ICommentLogic;
import co.edu.uniandes.csw.artwork.entities.ArtworkEntity;
import co.edu.uniandes.csw.artwork.entities.CommentEntity;
import co.edu.uniandes.csw.artwork.persistence.CommentPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class CommentLogic implements ICommentLogic {

    @Inject private CommentPersistence persistence;
     
    @Inject
    private IArtworkLogic artworkLogic;
    
    @Override
    public int countComments() {
        return persistence.count();
    }

    @Override
    public List<CommentEntity> getComments(Long artworkId) {
        ArtworkEntity artwork = artworkLogic.getArtwork(artworkId);
        return artwork.getComments();
    }

    @Override
    public List<CommentEntity> getComments(Integer page, Integer maxRecords, Long artworkId) {
        if (artworkId!=null){
            return persistence.findAll(page, maxRecords, artworkId);    
        }else{
            return persistence.findAll(page, maxRecords);    
        }
    }

    @Override
    public CommentEntity getComment(Long id) {
        return persistence.find(id);
    }

    @Override
    public CommentEntity createComment(Long artworkId, CommentEntity entity) {
        ArtworkEntity artwork = artworkLogic.getArtwork(artworkId);
        entity.setArtwork(artwork);
        CommentEntity entity2 = persistence.create(entity);
        return entity2;
    }

    @Override
    public void deleteComment(Long id) {
        persistence.delete(id);
    }
}
