/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.resources;

import co.edu.uniandes.csw.artwork.api.ICheckOutLogic;
import co.edu.uniandes.csw.artwork.api.ICreditCardLogic;
import co.edu.uniandes.csw.artwork.dtos.minimum.CheckOutDTO;
import co.edu.uniandes.csw.artwork.dtos.minimum.CreditCardDTO;
import co.edu.uniandes.csw.artwork.entities.CheckOutEntity;
import co.edu.uniandes.csw.artwork.entities.CreditCardEntity;
import co.edu.uniandes.csw.auth.provider.StatusCreated;
import co.edu.uniandes.csw.auth.stormpath.Utils;
import com.stormpath.sdk.account.Account;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author juan
 */
@Path("/checkout")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CheckOutResource {
    
    private final static String CLIENT_ID = "client_id";
    
    @Inject private ICheckOutLogic itemLogic;
    @Inject private ICreditCardLogic creditCardLogic;
    
    @Context private HttpServletResponse response;
    @QueryParam("page") private Integer page;
    @QueryParam("limit") private Integer maxRecords;
    @Context private HttpServletRequest request;    
    
    /**
     * Convierte una lista de ItemEntity a una lista de ItemDetailDTO
     *
     * @param entityList Lista de ItemEntity a convertir
     * @return Lista de ItemDetailDTO convertida
     * @generated
     */
    private List<CheckOutDTO> listEntity2DTO(List<CheckOutEntity> entityList){
        List<CheckOutDTO> list = new ArrayList<>();
        for (CheckOutEntity entity : entityList) {
            list.add(new CheckOutDTO(entity));
        }
        
        return list;
    }      
    
    
   /**
     * Obtiene la lista de los registros de Item asociados a un Client
     *
     * @return Colección de objetos de ItemDetailDTO
     * @generated
     */
    @GET
    public List<CheckOutDTO> getCheckOuts() {

        String accountHref = request.getRemoteUser();
        if (accountHref == null) {
            return new ArrayList<>();
        }
        
        Account account = Utils.getClient().getResource(accountHref, Account.class);
        int clientId = (int)account.getCustomData().get(CLIENT_ID);
        Long clientsId = new Long(clientId);
        if (page != null && maxRecords != null) {
            this.response.setIntHeader("X-Total-Count", itemLogic.countItems());
            return listEntity2DTO(itemLogic.getItems(page, maxRecords, clientsId));
        }
            
        return listEntity2DTO(itemLogic.getItems(1, 10, clientsId));
    }      
    
    /**
     * Asocia un Item existente a un Client
     *
     * @param dto Objeto de ItemDetailDTO con los datos nuevos
     * @return Objeto de ItemDetailDTOcon los datos nuevos y su ID.
     * @generated
     */
    @POST
    @StatusCreated
    public CheckOutDTO createItem(CheckOutDTO dto) {
        String accountHref = request.getRemoteUser();
        if (accountHref == null) {
            return null;
        }
        
        Account account = Utils.getClient().getResource(accountHref, Account.class);
        int clientId = (int) account.getCustomData().get(CLIENT_ID);
        Long clientsId = new Long(clientId);
        
        CreditCardEntity creditCard = creditCardLogic.getItem(dto.getCreditCard().getId());
        dto.setCreditCard(new CreditCardDTO(creditCard));
        
        return new CheckOutDTO(itemLogic.addItem(clientsId, dto.toEntity()));
    }       
}
