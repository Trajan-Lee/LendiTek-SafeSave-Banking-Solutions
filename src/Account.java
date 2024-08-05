
public abstract class Account {
	private String accountId;
	private double balance;
	private Customer customer;
	private String type;
	
	public Account(String accountId, Customer customer, double balance, String type) {
		this.accountId = accountId;
		this.customer = customer;
		this.balance = balance;
		this.type = type;
	}
	
	public abstract void deposit(double amount);
	public abstract void withdraw(double amount);

	//Getters and Setters
	public String getAccountId() {
		return accountId;
	}
	
	public Customer getCustomer() {
		return customer;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public String getType() {
		return type;
	}
	
	protected void setBalance(double balance) {
		this.balance = balance;
	}
}
