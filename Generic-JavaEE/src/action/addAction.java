package action;


import service.basicService;
import domain.Address;

public class addAction {
	private basicService<Address> service;
	
	public void Address(){
		Address ad = new Address("11","22");
		service.Add(ad);
	}

	public basicService<Address> getService() {
		return service;
	}

	public void setService(basicService<Address> service) {
		this.service = service;
	}
	
	
}
