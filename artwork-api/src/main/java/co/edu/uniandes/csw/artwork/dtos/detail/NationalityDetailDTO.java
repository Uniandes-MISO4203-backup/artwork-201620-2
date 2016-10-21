/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.dtos.detail;

import co.edu.uniandes.csw.artwork.dtos.minimum.NationalityDTO;
import co.edu.uniandes.csw.artwork.entities.NationalityEntity;

/**
 *
 * @author am.osorio
 */
public class NationalityDetailDTO extends NationalityDTO {

    public NationalityDetailDTO() {
        super();
    }
    
    public NationalityDetailDTO(NationalityEntity entity) {
        super(entity);
    }
}
