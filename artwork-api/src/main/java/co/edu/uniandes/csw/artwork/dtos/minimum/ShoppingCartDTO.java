/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artwork.dtos.minimum;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author juan
 */
@XmlRootElement
public class ShoppingCartDTO {
 
    List<ShoppingCartItemDTO> cartItems;
    
    private Long total;
    
    /**
     * @generated
     */
    public ShoppingCartDTO() {
        /// Constructor vacio usado por el serializador        
    }
    
    public void setCartItems(List<ShoppingCartItemDTO> cartItems) {
        this.cartItems = cartItems;
        this.getTotal();
    }    
    
    public List<ShoppingCartItemDTO> getCartItems() {
        return this.cartItems;
    }       
    
    private Long getTotal() {
        if (this.cartItems == null) {
            this.total = 0L;
        }
        else
        {
            this.total = this.cartItems.stream().mapToLong(i -> i.getArtwork().getPrice() * i.getQuantity()).sum();
        }

        return this.total;
    }
}
