package XStream;

/*----------------------------------------------------------------
 *  Author:        Lyo Zhou
 *  Written:       2013Äê8ÔÂ29ÈÕ       
 *        
 * 
 *  Purpose: add a class for Address
 *  
 *
 *----------------------------------------------------------------*/

public class Address {
	private String addType; 
    private String place; 

    public Address(String addType, String place) { 
        this.addType = addType; 
        this.place = place; 
    } 

    public String getAddType() { 
        return addType; 
    } 

    public void setAddType(String addType) { 
        this.addType = addType; 
    } 

    public String getPlace() { 
        return place; 
    } 

    public void setPlace(String place) { 
        this.place = place; 
    } 

}
 