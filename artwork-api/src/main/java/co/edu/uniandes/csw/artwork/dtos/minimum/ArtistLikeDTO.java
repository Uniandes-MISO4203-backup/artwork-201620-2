/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.dtos.minimum;

import co.edu.uniandes.csw.artwork.entities.ArtistLikeEntity;
import java.io.Serializable;

/**
 *
 * @author le.florez602
 */
public class ArtistLikeDTO implements Serializable{
    private Long id;
    private String name;
    
    public ArtistLikeDTO(){
        //Constructor vacio
    }
    
    public ArtistLikeDTO(ArtistLikeEntity entity){
        if(entity != null){
            this.id = entity.getId();
            this.name = entity.getName();
        }
    } 
    
    public ArtistLikeEntity toEntity(){
        ArtistLikeEntity entity = new ArtistLikeEntity();
        entity.setId(this.getId());
        entity.setName(this.getName());
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
    
}
