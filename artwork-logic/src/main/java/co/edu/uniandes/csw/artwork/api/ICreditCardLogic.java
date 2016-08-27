/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.api;

import co.edu.uniandes.csw.artwork.entities.CreditCardEntity;
import java.util.List;

/**
 *
 * @author juan
 */
public interface ICreditCardLogic {
    public int countItems();
    public List<CreditCardEntity> getItems(Long clientid);
    public List<CreditCardEntity> getItems(Integer page, Integer maxRecords, Long clientid);
    public CreditCardEntity getItem(Long itemid);
    public CreditCardEntity createItem(Long clientid, CreditCardEntity entity);
    public CreditCardEntity updateItem(Long clientid, CreditCardEntity entity);
    public void deleteItem(Long id);    
}
