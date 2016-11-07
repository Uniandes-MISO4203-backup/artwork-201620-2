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
public class OtherImageDTO implements Serializable{
    private String url;
    
    public OtherImageDTO(){
        //Constructor vacio
    }
    public OtherImageDTO(String url){
        this.url=url;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }
}
