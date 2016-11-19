/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.ejbs;

import co.edu.uniandes.csw.artwork.api.ICheckOutLogic;
import co.edu.uniandes.csw.artwork.api.IClientLogic;
import co.edu.uniandes.csw.artwork.entities.CheckOutEntity;
import co.edu.uniandes.csw.artwork.entities.CheckOutItemEntity;
import co.edu.uniandes.csw.artwork.entities.ClientEntity;
import co.edu.uniandes.csw.artwork.entities.ShoppingCartItemEntity;
import co.edu.uniandes.csw.artwork.persistence.CheckOutPersistence;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author juan
 */
@Stateless
public class CheckOutLogic implements ICheckOutLogic {

    @Inject 
    private CheckOutPersistence persistence;
    
    @Inject
    private IClientLogic clientLogic;
    
    @Override
    public int countItems() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CheckOutEntity> getItems(Long clientid) {
        return persistence.findAll(null, null, clientid);
    }

    @Override
    public List<CheckOutEntity> getItems(Integer page, Integer maxRecords, Long clientid) {
        return persistence.findAll(page, maxRecords, clientid);
    }

    @Override
    public CheckOutEntity addItem(Long clientId, CheckOutEntity entity) {

        ClientEntity client = clientLogic.getClient(clientId);
        List<ShoppingCartItemEntity> shoppingCart = client.getShoppingCart();
        List<CheckOutItemEntity> checkOutList = new ArrayList<>();
        long total = 0L;
        
        for(ShoppingCartItemEntity item : shoppingCart) {
            CheckOutItemEntity checkOutItem = new CheckOutItemEntity();
            checkOutItem.setArtwork(item.getArtwork());
            checkOutItem.setQty(item.getQty());
            checkOutList.add(checkOutItem);
            total += item.getArtwork().getPrice() * item.getQty();
        }
        
        entity.setTotal(total);
        entity.setCheckOutDate(new Date());
        entity.setCheckOutItemsList(checkOutList);
        entity.setClient(client);

        return persistence.create(entity);
    }
}