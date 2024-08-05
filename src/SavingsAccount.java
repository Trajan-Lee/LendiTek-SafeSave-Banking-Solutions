
public class SavingsAccount extends Account {
	private double interestRate;
	
	public SavingsAccount(String accountNumber, Customer customer, double balance, double interestRate) {
		super(accountNumber, customer, balance, "Savings");
		this.interestRate = interestRate;
	}
	
	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
	
	//overriding
	
	@Override
	public void deposit(double amount) {
		setBalance(getBalance() + amount);
	}
	@Override
	public void withdraw(double amount) {
		if (getBalance() >= amount) {
			setBalance(getBalance() + amount);
		} else {
			System.out.println("Insufficient funds");
		}
	}
	
	
}
