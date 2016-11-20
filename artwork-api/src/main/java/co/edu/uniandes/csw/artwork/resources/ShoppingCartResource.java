/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.resources;

import co.edu.uniandes.csw.artwork.api.IShoppingCartItemLogic;
import co.edu.uniandes.csw.artwork.dtos.minimum.ShoppingCartDTO;
import co.edu.uniandes.csw.artwork.dtos.minimum.ShoppingCartItemDTO;
import co.edu.uniandes.csw.artwork.entities.ShoppingCartItemEntity;
import co.edu.uniandes.csw.auth.provider.StatusCreated;
import co.edu.uniandes.csw.auth.stormpath.Utils;
import com.stormpath.sdk.account.Account;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * URI: clients/{shoppingCart: \\d+}/shoppingCart/
  * @author juan
 */
@Path("/shoppingCart")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ShoppingCartResource {
    
    private final static String CLIENT_ID = "client_id";
    
    @Inject private IShoppingCartItemLogic itemLogic;
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
    private List<ShoppingCartItemDTO> listEntity2DTO(List<ShoppingCartItemEntity> entityList){
        List<ShoppingCartItemDTO> list = new ArrayList<>();
        for (ShoppingCartItemEntity entity : entityList) {
            list.add(new ShoppingCartItemDTO(entity));
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
    public ShoppingCartDTO getShoppingCart() {
        
        ShoppingCartDTO shoppingCart = new ShoppingCartDTO();
        String accountHref = request.getRemoteUser();
        if (accountHref == null) {
            return shoppingCart;
        }
        
        Account account = Utils.getClient().getResource(accountHref, Account.class);
        int clientId = (int)account.getCustomData().get(CLIENT_ID);
        Long clientsId = new Long(clientId);
        if (page != null && maxRecords != null) {
            this.response.setIntHeader("X-Total-Count", itemLogic.countItems());
            shoppingCart.setCartItems(listEntity2DTO(itemLogic.getItems(page, maxRecords, clientsId, null)));
        }
        else {
            shoppingCart.setCartItems(listEntity2DTO(itemLogic.getItems(clientsId)));
        }
        
        return shoppingCart;
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
    public ShoppingCartItemDTO createItem(ShoppingCartItemDTO dto) {
        String accountHref = request.getRemoteUser();
        if (accountHref == null) {
            return null;
        }
        
        Account account = Utils.getClient().getResource(accountHref, Account.class);
        int clientId = (int) account.getCustomData().get(CLIENT_ID);
        Long clientsId = new Long(clientId);
        
        return new ShoppingCartItemDTO(itemLogic.addItem(clientsId, dto.toEntity()));
    }    
    
    
    /**
     * Elimina una instancia de Item de la base de datos.
     *
     * @param itemId Identificador de la instancia a eliminar.
     * @generated
     */
    @DELETE
    @Path("{itemId: \\d+}")
    public void deleteItem(@PathParam("itemId") Long itemId) {
        itemLogic.removeItem(itemId);
    }    
    
}
