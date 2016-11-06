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

import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import co.edu.uniandes.csw.artwork.api.IArtworkLogic;
import co.edu.uniandes.csw.artwork.dtos.detail.ArtworkDetailDTO;
import co.edu.uniandes.csw.artwork.entities.ArtworkEntity;
import java.util.ArrayList;
import javax.ws.rs.DefaultValue;

/**
 * @generated
 */
@Path("/artworks")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RootArtworkResource {

    @Inject private IArtworkLogic ArtworkLogic;
    @Context private HttpServletResponse response;
    @QueryParam("page") private Integer page;
    @QueryParam("limit") private Integer maxRecords;

   
    /**
     * Convierte una lista de ArtworkEntity a una lista de ArtworkBasicDTO
     *
     * @param entityList Lista de ArtworkEntity a convertir
     * @return Lista de ArtworkBasicDTO convertida
     * @generated
     */
    private List<ArtworkDetailDTO> listEntity2DTO(List<ArtworkEntity> entityList){
        List<ArtworkDetailDTO> list = new ArrayList<>();
        for (ArtworkEntity entity : entityList) {
            list.add(new ArtworkDetailDTO(entity));
        }
        return list;
    }


    /**
     * Obtiene la lista de los registros de Artist
     *
     * @return Colección de objetos de ArtworkDetailDTO
     * @generated
     */
    @GET
    public List<ArtworkDetailDTO> getArtworks() {
        if (page != null && maxRecords != null) {
            this.response.setIntHeader("X-Total-Count", ArtworkLogic.countArtworks());
            return listEntity2DTO(ArtworkLogic.getArtworks(page, maxRecords,null));
        }
        return listEntity2DTO(ArtworkLogic.getArtworks(null,null,null));
    }
    
    /**
     * Obtiene la lista de los registros de Artwork por categoria.
     *
     * @param categoryid id de la categoria.
     * @return Colección de objetos de ArtworkDetailDTO.
     * @generated
     */
    @GET
    @Path("/filtered")
    public List<ArtworkDetailDTO> getArtworkByCategory(
            @DefaultValue("") @QueryParam("categoryid") String categoryidin,
            @DefaultValue("") @QueryParam("artistName") String artistName) 
    {   
        Long categoryid;
        if(categoryidin.equals("")){
            categoryid = null;
        }
        else{
            categoryid = new Long(categoryidin);
        }
        if (page != null && maxRecords != null) {
            this.response.setIntHeader("X-Total-Count", ArtworkLogic.countArtworks());
            return listEntity2DTO(ArtworkLogic.getArtworkByCategory(page, maxRecords,categoryid, artistName));
        }
        return listEntity2DTO(ArtworkLogic.getArtworkByCategory(null,null,categoryid, artistName));
    }
    
    @GET
    @Path("/recent")
    public List<ArtworkDetailDTO> getArtworksNewAdquisitionsTest() 
    {   
        return listEntity2DTO(ArtworkLogic.getArtworksNewAdquisitions());
    }
}
