/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.resources;

import co.edu.uniandes.csw.artwork.api.IArtistLikeLogic;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
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
    public String getQualifys() {
        return "Hola";
    } 
}
