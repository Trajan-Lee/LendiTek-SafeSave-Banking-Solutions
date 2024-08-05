
public class Customer {
	private String name;
	private String customerId;
	private String address;
	private String phone;
	
	public Customer(String customerId, String name, String address, String phone) {
		this.customerId = customerId;
		this.name = name;
		this.address = address;
		this.phone = phone;
	}
	
	//No setters as all are set in the constructor
	
	public String getName() {
		return name;
	}
	
	public String getCustomerId() {
		return customerId;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String getPhone() {
		return phone;
	}
}
