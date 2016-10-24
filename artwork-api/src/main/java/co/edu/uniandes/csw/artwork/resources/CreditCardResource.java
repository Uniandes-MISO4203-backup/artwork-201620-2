/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.resources;

import co.edu.uniandes.csw.artwork.api.ICreditCardLogic;
import co.edu.uniandes.csw.artwork.dtos.minimum.CreditCardDTO;
import co.edu.uniandes.csw.artwork.entities.CreditCardEntity;
import co.edu.uniandes.csw.auth.provider.StatusCreated;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 * URI: clients/{creditCard: \\d+}/creditCard/
  * @author juan
 */
@Path("generic")
public class CreditCardResource {

    @Inject private ICreditCardLogic creditCardLogic;
    @Context private HttpServletResponse response;
    @QueryParam("page") private Integer page;
    @QueryParam("limit") private Integer maxRecords;
    @PathParam("clientsId") private Long clientsId;

    /**
     * Retrieves representation of an instance of co.edu.uniandes.csw.artwork.resources.CreditCardResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<CreditCardDTO> getCreditCards() {
        List<CreditCardEntity> items = creditCardLogic.getItems(clientsId);
        return creditCardEntity2DTO(items);
    }    
   
    @POST
    @StatusCreated
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CreditCardDTO createItem(CreditCardDTO dto) {
        return new CreditCardDTO(creditCardLogic.createItem(clientsId, dto.toEntity()));
    }    
    
    @GET
    @Path("{creditCardId: \\d+}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)    
    public CreditCardDTO getItem(@PathParam("creditCardId") Long itemId) {
        CreditCardEntity entity = creditCardLogic.getItem(itemId);
        if (entity.getClient() != null && !clientsId.equals(entity.getClient().getId())) {
            throw new WebApplicationException(404);
        }
        return new CreditCardDTO(entity);
    }    
    
    private List<CreditCardDTO> creditCardEntity2DTO(List<CreditCardEntity> entityList){
        List<CreditCardDTO> list = new ArrayList<>();
        for (CreditCardEntity entity : entityList) {
            list.add(new CreditCardDTO(entity));
        }
        return list;
    }  
    
    @DELETE
    @Path("{creditCardId: \\d+}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteItem(@PathParam("creditCardId") Long itemId) {
        creditCardLogic.deleteItem(itemId);
    }    
    
    @PUT
    @Path("{creditCardId: \\d+}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)       
    public CreditCardDTO updateItem(@PathParam("creditCardId") Long itemId, CreditCardDTO dto) {
        CreditCardEntity entity = dto.toEntity();
        entity.setId(itemId);
        return new CreditCardDTO(creditCardLogic.updateItem(clientsId, entity));
    }    
}
