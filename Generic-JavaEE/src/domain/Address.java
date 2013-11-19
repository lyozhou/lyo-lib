package domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;  

import org.hibernate.annotations.GenericGenerator;


/*----------------------------------------------------------------
 *  Author:        Lyo Zhou
 *  Written:       2013Äê8ÔÂ29ÈÕ       
 *        
 * 
 *  Purpose: add a class for Address
 *  
 *
 *----------------------------------------------------------------*/
@Entity
@Table(name = "address")
public class Address {
	private int id;
	
	private String addType; 
    private String place; 

    public Address(String addType, String place) { 
        this.addType = addType; 
        this.place = place; 
    } 

    @Column(name = "addType", nullable = false, length = 50)  
    public String getAddType() { 
        return addType; 
    } 

    public void setAddType(String addType) { 
        this.addType = addType; 
    } 

    @Column(name = "place", nullable = false, length = 50)  
    public String getPlace() { 
        return place; 
    } 

    public void setPlace(String place) { 
        this.place = place; 
    } 
    
    @GenericGenerator(name = "generator", strategy = "uuid")  
    @Id  
    @GeneratedValue(generator = "generator")  
    @Column(name = "addressId", unique = true, nullable = false, length = 50)  
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


}
 