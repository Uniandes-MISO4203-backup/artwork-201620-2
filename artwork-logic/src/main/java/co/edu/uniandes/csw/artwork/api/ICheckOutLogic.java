/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.api;

import co.edu.uniandes.csw.artwork.entities.CheckOutEntity;
import java.util.List;

/**
 *
 * @author juan
 */
public interface ICheckOutLogic {
    public int countItems();
    public List<CheckOutEntity> getItems(Long clientid);
    public List<CheckOutEntity> getItems(Integer page, Integer maxRecords, Long clientid);    
    public CheckOutEntity addItem(Long clientsId, CheckOutEntity toEntity);
}
