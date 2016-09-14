/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.api;

import co.edu.uniandes.csw.artwork.entities.ArtistLikeEntity;

/**
 *
 * @author le.florez602
 */
public interface IArtistLikeLogic {
    
    public Long getNumberOfLikes(Long artistId);
    public ArtistLikeEntity addArtistLike(Long artistId, ArtistLikeEntity entity);
    
}
