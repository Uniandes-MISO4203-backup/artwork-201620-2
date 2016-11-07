/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.ejbs;

import co.edu.uniandes.csw.artwork.api.IArtworkLogic;
import co.edu.uniandes.csw.artwork.api.IClientLogic;
import co.edu.uniandes.csw.artwork.api.IShoppingCartItemLogic;
import co.edu.uniandes.csw.artwork.entities.ArtworkEntity;
import co.edu.uniandes.csw.artwork.entities.ClientEntity;
import co.edu.uniandes.csw.artwork.entities.ShoppingCartItemEntity;
import co.edu.uniandes.csw.artwork.persistence.ShoppingCartItemPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;

/**
 *
 * @author juan
 */
@Stateless
public class ShoppingCartItemLogic implements IShoppingCartItemLogic {
    
    @Inject 
    private ShoppingCartItemPersistence persistence;

    @Inject
    private IClientLogic clientLogic;
    
    @Inject
    private IArtworkLogic artWorkLogic;    
    
    /**
     * Obtiene el número de registros de Item.
     *
     * @return Número de registros de Item.
     * @generated
     */
    @Override
    public int countItems() {
        return persistence.count();
    }


     /**
     * Obtiene la lista de los registros de Item que pertenecen a un Client.
     *
     * @param clientid id del Client el cual es padre de los Items.
     * @return Colección de objetos de ItemEntity.
     * @generated
     */
    @Override
    public List<ShoppingCartItemEntity> getItems(Long clientid) {
        ClientEntity client = clientLogic.getClient(clientid);
        return client.getShoppingCart();
    }
    
    /**
     * Obtiene la lista de los registros de Item que pertenecen a un Client indicando los datos para la paginación.
     *
     * @param page Número de página.
     * @param maxRecords Número de registros que se mostraran en cada página.
     * @param clientid id del Client el cual es padre de los Items.
     * @return Colección de objetos de ItemEntity.
     * @generated
     */
    @Override
    public List<ShoppingCartItemEntity> getItems(Integer page, Integer maxRecords, Long clientid, Long artworkId) {
        return persistence.findAll(page, maxRecords, clientid, artworkId);
    }    
    
    /**
     * Se encarga de crear un Item en la base de datos.
     *
     * @param entity Objeto de ItemEntity con los datos nuevos
     * @param clientid id del Client el cual sera padre del nuevo Item.
     * @return Objeto de ItemEntity con los datos nuevos y su ID.
     * @generated
     */
    @Override
    public ShoppingCartItemEntity addItem(Long clientid, ShoppingCartItemEntity entity) {

        ShoppingCartItemEntity currentItem = this.getCurrentItem(clientid, entity.getArtwork().getId());
        
        if (currentItem != null) {
            currentItem.setQty(currentItem.getQty() + 1);
            persistence.update(currentItem);
            return currentItem;
        }

        ClientEntity client = clientLogic.getClient(clientid);
        entity.setClient(client);

        ArtworkEntity artWork = artWorkLogic.getArtwork(entity.getArtwork().getId());
        entity.setArtwork(artWork);

        return persistence.create(entity);
    }    

    private ShoppingCartItemEntity getCurrentItem(Long clientId, Long artworkId) {
        List<ShoppingCartItemEntity> items = persistence.findAll(1, 1, clientId, artworkId);
        
        if (items.size() > 0) {
            return items.get(0);
        }
            
        return null;
    }
    
    
    @Override
    public ShoppingCartItemEntity getItem(Long itemid) {
        try {
            return persistence.find(itemid);
        }catch(NoResultException e){
            throw new IllegalArgumentException("El Item no existe", e);
        }
    }    
    
    @Override
    public ShoppingCartItemEntity updateItem(Long clientid, ShoppingCartItemEntity entity) {
        ClientEntity client = clientLogic.getClient(clientid);
        entity.setClient(client);
        return persistence.update(entity);
    }        
    
    /**
     * Elimina una instancia de Item de la base de datos.
     *
     * @param id Identificador de la instancia a eliminar.
     * @generated
     */
    @Override
    public void removeItem(Long id) {
        ShoppingCartItemEntity currentItem = persistence.find(id);
        
        if (currentItem != null && currentItem.getQty() > 1) {
            currentItem.setQty(currentItem.getQty() - 1);
            persistence.update(currentItem);
        }        
        else if (currentItem != null) {
            persistence.delete(currentItem.getId());
        }
    }    
}
