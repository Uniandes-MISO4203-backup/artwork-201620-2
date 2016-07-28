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
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
import javax.ws.rs.WebApplicationException;

/**
 * URI: artists/{artworksId: \\d+}/artworks/
 * @generated
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ArtworkResource {

    @Inject private IArtworkLogic artworkLogic;
    @Context private HttpServletResponse response;
    @QueryParam("page") private Integer page;
    @QueryParam("limit") private Integer maxRecords;
    @PathParam("artistsId") private Long artistsId;

   
    /**
     * Convierte una lista de ArtworkEntity a una lista de ArtworkDetailDTO
     *
     * @param entityList Lista de ArtworkEntity a convertir
     * @return Lista de ArtworkDetailDTO convertida
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
     * Obtiene la lista de los registros de Artwork asociados a un Artist
     *
     * @return Colección de objetos de ArtworkDetailDTO
     * @generated
     */
    @GET
    public List<ArtworkDetailDTO> getArtworks() {
        if (page != null && maxRecords != null) {
            this.response.setIntHeader("X-Total-Count", artworkLogic.countArtworks());
            return listEntity2DTO(artworkLogic.getArtworks(page, maxRecords, artistsId));
        }
        return listEntity2DTO(artworkLogic.getArtworks(artistsId));
    }

    /**
     * Obtiene los datos de una instancia de Artwork a partir de su ID asociado a un Artist
     *
     * @param artworkId Identificador de la instancia a consultar
     * @return Instancia de ArtworkDetailDTO con los datos del Artwork consultado
     * @generated
     */
    @GET
    @Path("{artworkId: \\d+}")
    public ArtworkDetailDTO getArtwork(@PathParam("artworkId") Long artworkId) {
        ArtworkEntity entity = artworkLogic.getArtwork(artworkId);
        if (entity.getArtist() != null && !artistsId.equals(entity.getArtist().getId())) {
            throw new WebApplicationException(404);
        }
        return new ArtworkDetailDTO(entity);
    }

    /**
     * Asocia un Artwork existente a un Artist
     *
     * @param dto Objeto de ArtworkDetailDTO con los datos nuevos
     * @return Objeto de ArtworkDetailDTOcon los datos nuevos y su ID.
     * @generated
     */
    @POST
    @StatusCreated
    public ArtworkDetailDTO createArtwork(ArtworkDetailDTO dto) {
        return new ArtworkDetailDTO(artworkLogic.createArtwork(artistsId, dto.toEntity()));
    }

    /**
     * Actualiza la información de una instancia de Artwork.
     *
     * @param artworkId Identificador de la instancia de Artwork a modificar
     * @param dto Instancia de ArtworkDetailDTO con los nuevos datos.
     * @return Instancia de ArtworkDetailDTO con los datos actualizados.
     * @generated
     */
    @PUT
    @Path("{artworkId: \\d+}")
    public ArtworkDetailDTO updateArtwork(@PathParam("artworkId") Long artworkId, ArtworkDetailDTO dto) {
        ArtworkEntity entity = dto.toEntity();
        entity.setId(artworkId);
        ArtworkEntity oldEntity = artworkLogic.getArtwork(artworkId);
        entity.setCategory(oldEntity.getCategory());
        return new ArtworkDetailDTO(artworkLogic.updateArtwork(artistsId, entity));
    }

    /**
     * Elimina una instancia de Artwork de la base de datos.
     *
     * @param artworkId Identificador de la instancia a eliminar.
     * @generated
     */
    @DELETE
    @Path("{artworkId: \\d+}")
    public void deleteArtwork(@PathParam("artworkId") Long artworkId) {
        artworkLogic.deleteArtwork(artworkId);
    }
    public void existsArtwork(Long artworksId){
        ArtworkDetailDTO artwork = getArtwork(artworksId);
        if (artwork== null) {
            throw new WebApplicationException(404);
        }
    }
    
    
    @Path("{artworksId: \\d+}/category")
    public Class<ArtworkCategoryResource> getArtworkCategoryResource(@PathParam("artworksId") Long artworksId){
        existsArtwork(artworksId);
        return ArtworkCategoryResource.class;
    }
    
}
