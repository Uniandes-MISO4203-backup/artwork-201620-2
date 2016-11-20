/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.entities;

import co.edu.uniandes.csw.crud.spi.entity.BaseEntity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author juan
 */
@Entity
public class MessageEntity extends BaseEntity implements Serializable {
   
    private String subject;
    
    private String body;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date sentDate;

    @PodamExclude
    @ManyToOne    
    private ClientEntity client;    
    
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    public ClientEntity getClient() {
        return this.client;
    }
    
    public void setClient(ClientEntity client) {
        this.client = client;
    }         
}