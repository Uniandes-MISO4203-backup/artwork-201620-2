/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.ejbs;

import co.edu.uniandes.csw.artwork.api.IArtistLikeLogic;
import co.edu.uniandes.csw.artwork.api.IArtistLogic;
import co.edu.uniandes.csw.artwork.api.IClientLogic;
import co.edu.uniandes.csw.artwork.entities.ArtistEntity;
import co.edu.uniandes.csw.artwork.entities.ArtistLikeEntity;
import co.edu.uniandes.csw.artwork.entities.ClientEntity;
import co.edu.uniandes.csw.artwork.persistence.ArtistLikePersistance;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author le.florez602
 */
@Stateless
public class ArtistLikeLogic implements IArtistLikeLogic{

    @Inject
    private ArtistLikePersistance persistance;
    
    @Inject
    private IArtistLogic artistLogic;
    
    @Override
    public Long getNumberOfLikes(Long artistId) {
        List<ArtistLikeEntity> likes = persistance.findAll(artistId);
        return new Long(likes.size());
    }

    @Override
    public ArtistLikeEntity addArtistLike(Long artistId, ArtistLikeEntity entity) {
        ArtistEntity artist = artistLogic.getArtist(artistId);
        entity.setArtist(artist);
        return persistance.create(entity);
    }
    
}
