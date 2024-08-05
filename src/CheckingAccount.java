
public class CheckingAccount extends Account {
	private double overdraftLimit;

	
	public CheckingAccount(String accountNumber, Customer customer, double balance, double overdraftLimit) {
		super(accountNumber, customer, balance, "Checking");
		this.overdraftLimit = overdraftLimit;
	}
	
	public double getOverdraftLimit() {
		return overdraftLimit;
	}
	
	public void setOverdraftLimit(double overdraftLimit) {
		this.overdraftLimit = overdraftLimit;
	}
	
	@Override
	public void deposit(double amount) {
		setBalance(getBalance() + amount);
	}
	
	@Override
	public void withdraw(double amount) {
		if (getBalance() + overdraftLimit >= amount) {
			setBalance(getBalance() + amount);
		} else {
			System.out.println("Insufficient funds");
		}
	}
}
