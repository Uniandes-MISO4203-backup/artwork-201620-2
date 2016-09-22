/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.dtos.minimum;

import co.edu.uniandes.csw.artwork.entities.CommentEntity;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class CommentDTO implements Serializable {
    private Long id;
    private String name;
    private String message;
    
    public CommentDTO() {
        //Constructor vacio
    }
    
    public CommentDTO(CommentEntity entity) {
        if(entity!=null) {
            this.id = entity.getId();
            this.name = entity.getName();
            this.message = entity.getMessage();
        }
    }
    
    public CommentEntity toEntity() {
        CommentEntity entity = new CommentEntity();
        entity.setId(id);
        entity.setName(name);
        entity.setMessage(message);
        return entity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
