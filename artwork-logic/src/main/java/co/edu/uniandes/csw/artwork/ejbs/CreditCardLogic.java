/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.ejbs;

import co.edu.uniandes.csw.artwork.api.IClientLogic;
import co.edu.uniandes.csw.artwork.api.ICreditCardLogic;
import co.edu.uniandes.csw.artwork.entities.ClientEntity;
import co.edu.uniandes.csw.artwork.entities.CreditCardEntity;
import co.edu.uniandes.csw.artwork.persistence.CreditCardPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;

/**
 *
 * @author juan
 */
@Stateless
public class CreditCardLogic implements ICreditCardLogic {
    
    @Inject private CreditCardPersistence persistence;

    @Inject
    private IClientLogic clientLogic;    
    
    @Override
    public int countItems() {
        return persistence.count();
    }    
    
    @Override
    public List<CreditCardEntity> getItems(Long clientid) {
        ClientEntity client = clientLogic.getClient(clientid);
        return client.getCreditCards();
    }    
    
    @Override
    public List<CreditCardEntity> getItems(Integer page, Integer maxRecords, Long clientid) {
        return persistence.findAll(page, maxRecords, clientid);
    }    
    
    @Override
    public CreditCardEntity getItem(Long itemid) {
        try {
            return persistence.find(itemid);
        }catch(NoResultException e){
            throw new IllegalArgumentException("El Item no existe", e);
        }
    }
    
    @Override
    public CreditCardEntity createItem(Long clientid, CreditCardEntity entity) {
        ClientEntity client = clientLogic.getClient(clientid);
        entity.setClient(client);
        entity = persistence.create(entity);
        return entity;
    }    
    
    @Override
    public CreditCardEntity updateItem(Long clientid, CreditCardEntity entity) {
        ClientEntity client = clientLogic.getClient(clientid);
        entity.setClient(client);
        return persistence.update(entity);
    }    
    
    @Override
    public void deleteItem(Long id) {
        CreditCardEntity old = getItem(id);
        persistence.delete(old.getId());
    }    
}
