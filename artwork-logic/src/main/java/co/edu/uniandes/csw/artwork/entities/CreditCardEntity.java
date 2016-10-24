package co.edu.uniandes.csw.artwork.entities;

import co.edu.uniandes.csw.crud.spi.entity.BaseEntity;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import uk.co.jemos.podam.common.PodamExclude;
/**
 *
 * @author juan
 */
@Entity
public class CreditCardEntity extends BaseEntity implements Serializable {

    private String number;
    
    private String type;    
    
    private int expirationYear;
    
    private int expirationMonth;
    
    @PodamExclude
    @ManyToOne    
    private ClientEntity client;
    
    public String getNumber() {
        return this.number;
    }
    
    public void setNumber(String number) {
        this.number = number;
    }
    
    public int getExpirationYear() {
        return this.expirationYear;
    }
    
    public void setExpirationYear(int expirationYear) {
        this.expirationYear = expirationYear;
    }
    
    public int getExpirationMonth() {
        return this.expirationMonth;
    }
    
    public void setExpirationMonth(int expirationMonth) {
        this.expirationMonth = expirationMonth;
    }      
    
    public ClientEntity getClient() {
        return this.client;
    }
    
    public void setClient(ClientEntity client) {
        this.client = client;
    }      

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
    
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
    
    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
