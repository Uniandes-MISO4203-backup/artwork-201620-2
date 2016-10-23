/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.dtos.minimum;

import co.edu.uniandes.csw.artwork.entities.MessageEntity;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author juan
 */
@XmlRootElement
public class MessageDTO {
    
    private Long id;

    private String subject;
    
    private String body;
    
    private Date sentDate;
    
    private String clientName;
    
    @PodamExclude
    private ClientDTO client;    

    public MessageDTO() {
        /// Constructor vacio usado por el serializador        
    }    

    public MessageDTO(MessageEntity entity) {
        this.subject = entity.getSubject();
        this.body = entity.getBody();
        this.sentDate = entity.getSentDate();
        this.id = entity.getId();
        
        if (entity.getClient() != null) {
            this.setClient(new ClientDTO(entity.getClient()));
        }
    }    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    public void setBody(String body) {
        this.body = body;
    }
    
    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
        
        if (this.client != null) {
            this.clientName = this.client.getName();
        }
    }

    public String getClientName() {
        return this.clientName;
    }
    
    public MessageEntity toEntity() {
        MessageEntity entity = new MessageEntity();
        
        if (this.getClient() != null){
            entity.setClient(this.getClient().toEntity());
        }

        entity.setBody(this.getBody());
        entity.setSentDate(this.getSentDate());
        entity.setSubject(this.getSubject());
        entity.setId(this.getId());
        
        return entity;
    }    
}
