/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.entities;

import co.edu.uniandes.csw.crud.spi.entity.BaseEntity;
import java.io.Serializable;
import javax.persistence.Entity;

/**
 *
 * @author am.osorio
 */
@Entity
public class NationalityEntity extends BaseEntity implements Serializable {
    private String description;

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }
}
