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
import co.edu.uniandes.csw.artwork.dtos.minimum.ArtworkDTO;
import java.util.ArrayList;
import java.util.List;
import co.edu.uniandes.csw.artwork.entities.QualifyEntity;
import co.edu.uniandes.csw.auth.stormpath.Utils;
import com.stormpath.sdk.account.Account;
import javax.servlet.http.HttpServletRequest;

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

    @Context private HttpServletRequest req;
    /**
     * Creates a new instance of CreditCardResource
     */
    public QualifyResource() {
    }
    
     public static QualifyDetailDTO basicEntity2DTO(QualifyEntity entity) {
         if (entity != null) {
            QualifyDetailDTO dto = new QualifyDetailDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setArtwork(new ArtworkDTO(entity.getArtwork()));
            dto.setScore(entity.getScore());
            dto.setMessage(entity.getMessage());
            return dto;
        } else {
            return null;
        }
    }

      public static QualifyEntity basicDTO2Entity(QualifyDetailDTO dto) {
        if (dto != null) {
            QualifyEntity entity = new QualifyEntity();
            entity.setId(dto.getId());
            entity.setName(dto.getName());
            entity.setScore(dto.getScore());
            entity.setMessage(dto.getMessage());

            return entity;
        } else {
            return null;
        }
    }
     public static List<QualifyDetailDTO> listEntity2DTO(List<QualifyEntity> entities) {
        List<QualifyDetailDTO> dtos = new ArrayList<>();
        if (entities != null) {
            for (QualifyEntity entity : entities) {
                dtos.add(new QualifyDetailDTO(entity));
            }
        }
        return dtos;
    }

    public static List<QualifyEntity> listDTO2Entity(List<QualifyDetailDTO> dtos) {
        List<QualifyEntity> entities = new ArrayList<>();
        if (dtos != null) {
            for (QualifyDetailDTO dto : dtos) {
                entities.add(basicDTO2Entity(dto));
            }
        }
        return entities;
    }
    /**
     * Retrieves representation of an instance of co.edu.uniandes.csw.artwork.resources.QualifyResource
     * @param artworksId
     * @return an instance of Long
     */
    
    public List<QualifyDetailDTO>getQualifys(Long artworksId) {
        List<QualifyDetailDTO> qualifies = listEntity2DTO(qualifyLogic.getQualifys(artworksId));
        return qualifies;
    } 
    
    /* Retrieves representation of an instance of co.edu.uniandes.csw.artwork.resources.QualifyResource
     * @param artworksId
     * @return an instance of Long
     */
    @GET
    @Path("{artworksId: \\d+}")
    public List<QualifyDetailDTO> getQualifies(@PathParam("artworksId") Long artworksId) {  
        Long fullScore=0l;
        List<QualifyDetailDTO> qualiefies = getQualifys(artworksId);
        return qualiefies;
    } 
    
     /* Retrieves representation of an instance of co.edu.uniandes.csw.artwork.resources.QualifyResource
     * @param artworksId
     * @return an instance of Long
     */
    @GET
    @Path("{artworksId: \\d+}/score")
    public String getScore(@PathParam("artworksId") Long artworksId) {
        
        Long fullScore=0l;
        List<QualifyDetailDTO> scores = getQualifys(artworksId);
        
        for (QualifyDetailDTO qualify : scores) {
            fullScore+=qualify.getScore();
        }
        if(fullScore != 0l){
            fullScore = fullScore/scores.size();
        }
        return fullScore.toString();
    } 
   
    @POST
    @StatusCreated
    public QualifyDetailDTO createItem(QualifyDetailDTO dto) {
        
        String accountHref = req.getRemoteUser();
        if (accountHref == null) {
            return null;
        }        
       
        Account account = Utils.getClient().getResource(accountHref, Account.class);
        Integer client_id = (int) account.getCustomData().get("client_id");
        return new QualifyDetailDTO(qualifyLogic.addQualify(dto.getArtwork().getId(), client_id.longValue(), dto.toEntity()));
    }        
}
