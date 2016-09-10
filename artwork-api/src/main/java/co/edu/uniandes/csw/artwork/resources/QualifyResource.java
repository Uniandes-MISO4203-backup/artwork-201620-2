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
package co.edu.uniandes.csw.artwork.resources;

import co.edu.uniandes.csw.auth.provider.StatusCreated;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import co.edu.uniandes.csw.artwork.api.IQualifyLogic;
import co.edu.uniandes.csw.artwork.dtos.detail.QualifyDetailDTO;

/**
 * URI: artworks/{artwork: \\d+}/qualify/
  * @author adriana
 */
@Path("/qualifys")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class QualifyResource {  
    
    @Inject private IQualifyLogic qualifyLogic;
    @Context private HttpServletResponse response;
    @QueryParam("page") private Integer page;
    @QueryParam("limit") private Integer maxRecords;

    /**
     * Creates a new instance of CreditCardResource
     */
    public QualifyResource() {
    }

    /**
     * Retrieves representation of an instance of co.edu.uniandes.csw.artwork.resources.QualifyResource
     * @param artworksId
     * @return an instance of Long
     */
    @GET
    @Path("{artworkId: \\d+}")
    public Long getQualifys(@PathParam("artworksId") Long artworksId) {
        Long score = qualifyLogic.getQualifys(artworksId);
        return score;
    } 
    
    @GET
    public String getQualifys() {
        return "Hola";
    } 
   
    @POST
    @StatusCreated
    public QualifyDetailDTO createItem(QualifyDetailDTO dto) {
       
        return new QualifyDetailDTO(qualifyLogic.addQualify(dto.getClient().getId(),dto.getArtwork().getId(), dto.toEntity()));
    }        
}
