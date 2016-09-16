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
import javax.persistence.NoResultException;

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
        try {
            return persistence.find(id);
        }catch(NoResultException e){
            throw new IllegalArgumentException("El Artwork no existe");
        }
    }

    @Override
    public CommentEntity createComment(Long artworkId, CommentEntity entity) {
        ArtworkEntity artwork = artworkLogic.getArtwork(artworkId);
        entity.setArtwork(artwork);
        entity = persistence.create(entity);
        return entity;
    }

    @Override
    public void deleteComment(Long id) {
        persistence.delete(id);
    }
}
