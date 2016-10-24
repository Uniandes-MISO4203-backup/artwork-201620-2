/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.resources;

import co.edu.uniandes.csw.artwork.api.IMessageLogic;
import co.edu.uniandes.csw.artwork.dtos.minimum.MessageDTO;
import co.edu.uniandes.csw.artwork.entities.MessageEntity;
import co.edu.uniandes.csw.auth.provider.StatusCreated;
import co.edu.uniandes.csw.auth.stormpath.Utils;
import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.group.Group;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author juan
 */
@Path("/messages")
public class MessageResource {
    
    private static final String CLIENT_ID = "client_id";
    private static final String CLIENT_HREF = "https://api.stormpath.com/v1/groups/5xJfg140VZoCF2Ny36Y7k1";
    private static final String ADMIN_HREF = "https://api.stormpath.com/v1/groups/G6wKFbwsYpo7yFR3ziC4v";       
    
    @Inject private IMessageLogic messageLogic;
    @Context private HttpServletResponse response;
    @QueryParam("page") private Integer page;
    @QueryParam("limit") private Integer maxRecords;
    @Context private HttpServletRequest req;

    private List<MessageDTO> listEntity2DTO(List<MessageEntity> entityList){
        List<MessageDTO> list = new ArrayList<>();
        for (MessageEntity entity : entityList) {
            list.add(new MessageDTO(entity));
        }
        return list;
    }      
    
    /**
     * Retrieves representation of an instance of co.edu.uniandes.csw.artwork.resources.CreditCardResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<MessageDTO> getMessages() {
        String accountHref = req.getRemoteUser();
        Account account = Utils.getClient().getResource(accountHref, Account.class);
        
        for (Group gr : account.getGroups()) {
            
            Integer id = (int)account.getCustomData().get(CLIENT_ID);
            boolean paged = checkPaged();
            
            if (gr.getHref().equals(ADMIN_HREF))  {
                if (paged) {
                   return listEntity2DTO(messageLogic.getMessages(page, maxRecords));                    
                }
                return listEntity2DTO(messageLogic.getMessages());                
            }
            else {
                if (paged) {
                    return listEntity2DTO(messageLogic.getMessages(page, maxRecords, id.longValue()));
                }
                return listEntity2DTO(messageLogic.getMessages(id.longValue()));                
            }
        }        

        return new ArrayList<>();
    }
   
    @POST
    @StatusCreated
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public MessageDTO createMessage(MessageDTO dto) {
        String accountHref = req.getRemoteUser();
        Account account = Utils.getClient().getResource(accountHref, Account.class);
        Integer id = (int) account.getCustomData().get(CLIENT_ID);
        
        return new MessageDTO(messageLogic.createMessage(id.longValue(), dto.toEntity()));
    }
    
    @GET
    @Path("{messageId: \\d+}")
    @Produces(MediaType.APPLICATION_JSON)    
    public MessageDTO getMessage(@PathParam("messageId") Long itemId) {
        String accountHref = req.getRemoteUser();
        MessageEntity entity = messageLogic.getMessage(itemId);        
        Account account = Utils.getClient().getResource(accountHref, Account.class);
        for (Group gr : account.getGroups()) {
            Long clientId = new Long((int) account.getCustomData().get(CLIENT_ID));            
            
            if (gr.getHref().equals(CLIENT_HREF) && entity.getClient() != null && !clientId.equals(entity.getClient().getId())) {
                throw new WebApplicationException(404);                  
            }
            
            return new MessageDTO(entity);
        }

        throw new WebApplicationException(404);
    }
    
    @PUT
    @Path("{id: \\d+}")
    public MessageDTO updateMessage(@PathParam("id") Long id, MessageDTO dto) {
        String accountHref = req.getRemoteUser();
        Account account = Utils.getClient().getResource(accountHref, Account.class);
        Long clientId = new Long((int) account.getCustomData().get(CLIENT_ID));
        
        MessageEntity entity = dto.toEntity();
        entity.setId(id);
        
        return new MessageDTO(messageLogic.updateMessage(clientId, entity));
    } 
    
    @DELETE
    @Path("{id: \\d+}")
    public void deleteMessage(@PathParam("id") Long id) {
        messageLogic.deleteMessage(id);
    }
    
    private boolean checkPaged() {
        if (page != null && maxRecords != null) {
            this.response.setIntHeader("X-Total-Count", messageLogic.countItems());
            return true;
        }        
        return false;
    }
}
