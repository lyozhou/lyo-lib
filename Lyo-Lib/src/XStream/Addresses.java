package XStream;

import java.util.List;

/*----------------------------------------------------------------
 *  Author:        Lyo Zhou
 *  Written:       2013Äê8ÔÂ29ÈÕ       
 *        
 * 
 *  Purpose: One person may have several addresses
 *  
 *
 *----------------------------------------------------------------*/

public class Addresses {
	private List<Address> listAdd;

	public Addresses(List<Address> listAdd) {
		this.listAdd = listAdd;
	}

	public List<Address> getListAdd() {
		return listAdd;
	}

	public void setListAdd(List<Address> listAdd) {
		this.listAdd = listAdd;
	}
}
