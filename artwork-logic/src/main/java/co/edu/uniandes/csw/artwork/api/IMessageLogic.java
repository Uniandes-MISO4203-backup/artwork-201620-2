/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.api;

import co.edu.uniandes.csw.artwork.entities.MessageEntity;
import java.util.List;

/**
 *
 * @author juan
 */
public interface IMessageLogic {
    public int countItems();
    public int countItems(Long clientid);    
    public List<MessageEntity> getMessages();    
    public List<MessageEntity> getMessages(Long clientid);
    public List<MessageEntity> getMessages(Integer page, Integer maxRecords);   
    public List<MessageEntity> getMessages(Integer page, Integer maxRecords, Long clientId);
    public MessageEntity getMessage(Long itemid);
    public MessageEntity createMessage(Long clientid, MessageEntity entity);
    public MessageEntity updateMessage(Long clientid, MessageEntity entity);
    public void deleteMessage(Long id);       
}