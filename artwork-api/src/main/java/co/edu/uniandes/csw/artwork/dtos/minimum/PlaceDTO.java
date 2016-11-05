/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.dtos.minimum;

import java.io.Serializable;

/**
 *
 * @author am.osorio
 */
public class PlaceDTO implements Serializable{

    private String name;
    
    public PlaceDTO(){
        //Constructor vacio
    }
    
    public PlaceDTO(String name){
        this.name=name;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
