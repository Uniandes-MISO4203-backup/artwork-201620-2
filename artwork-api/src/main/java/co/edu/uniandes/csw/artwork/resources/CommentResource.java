/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.resources;

import co.edu.uniandes.csw.artwork.api.ICommentLogic;
import co.edu.uniandes.csw.artwork.dtos.detail.CommentDetailDTO;
import co.edu.uniandes.csw.artwork.entities.CommentEntity;
import co.edu.uniandes.csw.auth.provider.StatusCreated;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CommentResource {
    
    @Inject private ICommentLogic commentLogic;
    @Context private HttpServletResponse response;
    @QueryParam("page") private Integer page;
    @QueryParam("limit") private Integer maxRecords;
    @PathParam("artworkId") private Long artworkId;
    
    private List<CommentDetailDTO> listEntity2DTO(List<CommentEntity> entityList){
        List<CommentDetailDTO> list = new ArrayList<>();
        for (CommentEntity entity : entityList) {
            list.add(new CommentDetailDTO(entity));
        }
        return list;
    }
    
    @GET
    public List<CommentDetailDTO> getComments() {
        if (page != null && maxRecords != null) {
            this.response.setIntHeader("X-Total-Count", commentLogic.countComments());
            return listEntity2DTO(commentLogic.getComments(page, maxRecords, artworkId));
        }
        return listEntity2DTO(commentLogic.getComments(artworkId));
    }
    
    @GET
    @Path("{commentId: \\d+}")
    public CommentDetailDTO getComment(@PathParam("commentId") Long commentId) {
        CommentEntity entity = commentLogic.getComment(commentId);
        if (entity.getArtwork()!= null && !artworkId.equals(entity.getArtwork().getId())) {
            throw new WebApplicationException(404);
        }
        return new CommentDetailDTO(entity);
    }
    
    @POST
    @StatusCreated
    public CommentDetailDTO createComment(CommentDetailDTO dto) {
        return new CommentDetailDTO(commentLogic.createComment(artworkId, dto.toEntity()));
    }
    
    @DELETE
    @Path("{commentId: \\d+}")
    public void deleteComment(@PathParam("commentId") Long commentId) {
        commentLogic.deleteComment(commentId);
    }
    
    public void existsComment(Long commentId){
        CommentDetailDTO comment = getComment(commentId);
        if (comment== null) {
            throw new WebApplicationException(404);
        }
    }
}
