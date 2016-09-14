/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.resources;

import co.edu.uniandes.csw.artwork.api.IArtistLikeLogic;
import co.edu.uniandes.csw.artwork.dtos.detail.ArtistLikeDetailDTO;
import co.edu.uniandes.csw.artwork.entities.ArtistLikeEntity;
import co.edu.uniandes.csw.auth.provider.StatusCreated;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;

/**
 *
 * @author le.florez602
 */
@Path("/artistLike")
public class ArtistLikeResource {
    
    @Inject private IArtistLikeLogic artistLikeLogic;
    @Context private HttpServletResponse response;
        
      
    @GET
    @Path("{artistId: \\d+}")
    public Long getNumLikes(@PathParam("artistId") Long artistId) {
        Long numLikes = artistLikeLogic.getNumberOfLikes(artistId);
        return numLikes;
    } 
    
    @POST
    @StatusCreated
    public ArtistLikeDetailDTO createLike(ArtistLikeDetailDTO dto) {
        ArtistLikeEntity entity =  artistLikeLogic.addArtistLike(
                dto.getArtist().getId(),
                dto.toEntity());
        
        return new ArtistLikeDetailDTO(entity);    
    } 
    
    @GET
    public String getQualifys() {
        return "Hola";
    } 
}
