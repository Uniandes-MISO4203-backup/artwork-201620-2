/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.api;

import co.edu.uniandes.csw.artwork.entities.ShoppingCartItemEntity;
import java.util.List;

/**
 *
 * @author juan
 */
public interface IShoppingCartItemLogic {
    public int countItems();
    public List<ShoppingCartItemEntity> getItems(Long clientid);
    public List<ShoppingCartItemEntity> getItems(Integer page, Integer maxRecords, Long clientid, Long artworkId);
    public ShoppingCartItemEntity addItem(Long clientid, ShoppingCartItemEntity entity);
    public void removeItem(Long id);
    public ShoppingCartItemEntity updateItem(Long clientid, ShoppingCartItemEntity entity);
    public ShoppingCartItemEntity getItem(Long itemid);
}
