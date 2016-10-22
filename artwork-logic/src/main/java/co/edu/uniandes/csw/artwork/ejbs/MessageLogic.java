/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.ejbs;

import co.edu.uniandes.csw.artwork.api.IClientLogic;
import co.edu.uniandes.csw.artwork.api.IMessageLogic;
import co.edu.uniandes.csw.artwork.entities.ClientEntity;
import co.edu.uniandes.csw.artwork.entities.MessageEntity;
import co.edu.uniandes.csw.artwork.persistence.MessagePersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;

/**
 *
 * @author juan
 */
@Stateless
public class MessageLogic implements IMessageLogic{
    
    @Inject private MessagePersistence persistence;

    @Inject
    private IClientLogic clientLogic;        
    
    @Override
    public int countItems() {
        return persistence.count();
    }    
    
    @Override
    public int countItems(Long clientid) {
        return persistence.count(clientid);
    }     

    @Override
    public List<MessageEntity> getMessages(Long clientid) {
        ClientEntity client = clientLogic.getClient(clientid);
        return client.getMessages();
    }      
    
    @Override
    public List<MessageEntity> getMessages() {
        return persistence.findAll();
    }       
    
    @Override
    public List<MessageEntity> getMessages(Integer page, Integer maxRecords, Long clientid) {
        return persistence.findAll(page, maxRecords, clientid);
    }        
    
    @Override
    public List<MessageEntity> getMessages(Integer page, Integer maxRecords) {
        return persistence.findAll(page, maxRecords);
    }      
    
    @Override
    public MessageEntity getMessage(Long itemid) {
        try {
            return persistence.find(itemid);
        }catch(NoResultException e){
            throw new IllegalArgumentException("El Mensaje no existe", e);
        }
    }    
    
    @Override
    public MessageEntity createMessage(Long clientid, MessageEntity entity) {
        ClientEntity client = clientLogic.getClient(clientid);
        entity.setClient(client);
        MessageEntity response = persistence.create(entity);
        return response;
    }      
    
    @Override
    public MessageEntity updateMessage(Long clientid, MessageEntity entity) {
        ClientEntity client = clientLogic.getClient(clientid);
        entity.setClient(client);
        return persistence.update(entity);
    }       
    
    @Override
    public void deleteMessage(Long id) {
        MessageEntity old = getMessage(id);
        persistence.delete(old.getId());
    }       
}
