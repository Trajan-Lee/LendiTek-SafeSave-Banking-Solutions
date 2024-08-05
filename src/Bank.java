import java.util.ArrayList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;

//using BufferedWriter/reader would increase speed in a real bank system containing 10000s of accounts

public class Bank {
	private List<Customer> customers;
	private List<Account> accounts;
	private List<Transaction> transactions;
	
	public Bank() {
		this.customers = new ArrayList<>();
		this.accounts = new ArrayList<>();
		this.transactions = new ArrayList<>();
		loadAll();
	}
	
	public void addCustomer(Customer customer) {
		customers.add(customer);
	}
	
	public void addAccount(Account account) {
		accounts.add(account);
	}
	
	public void addTransaction(Transaction transaction) {
		transactions.add(transaction);
	}
	
	
	//Methods to Get object from list by object.StringId
	public Customer getCustomerById(String customerId) {
        for (Customer customer : customers) {
        	if (customer.getCustomerId().equals(customerId)) {
        		return customer;
        	}
        }
        return null;
	}
	
    public Transaction getTransactionById(String transactionId) {
        for (Transaction transaction : transactions) {
            if (transaction.getTransactionId().equals(transactionId)) {
                return transaction;
            }
        }
        return null;
    }
	
    public Account getAccountById(String accountId) {
        for (Account account : accounts) {
            if (account.getAccountId().equals(accountId)) {
                return account;
            }
        }
        return null;
    }
    
    //methods to return next Id for new objects in list
    
    public String nextCustomerId() {
        if (!customers.isEmpty()) {
            // Gets last Id, converts to int
            int lastId = Integer.parseInt(customers.get(customers.size() - 1).getCustomerId());
            // Adds 1 to make new Id, then back to string
            int newId = lastId + 1;
            return String.valueOf(newId);
        } else {
            // Handle case where the customers list is empty
            return "1";
        }
    }
    
    public String nextAccountId() {
        if (!accounts.isEmpty()) {
            // Gets last Id, converts to int
            int lastId = Integer.parseInt(accounts.get(accounts.size() - 1).getAccountId());
            // Adds 1 to make new Id, then back to string
            int newId = lastId + 1;
            return String.valueOf(newId);
        } else {
            // Handle case where the accounts list is empty
            return "1";
        }
    }
    
    public String nextTransactionId() {
        if (!transactions.isEmpty()) {
            // Gets last Id, converts to int
            int lastId = Integer.parseInt(transactions.get(transactions.size() - 1).getTransactionId());
            // Adds 1 to make new Id, then back to string
            int newId = lastId + 1;
            return String.valueOf(newId);
        } else {
            // Handle case where the transactions list is empty
            return "1";
        }
    }
    
    // Returns a String Array of Ids from the given list
    public ArrayList<String> listCustomerIds() {
    	ArrayList<String> outList = new ArrayList<String>();
    	for(Customer customer : customers) {
    		outList.add(customer.getCustomerId());
    	}
    	return outList;
    }
    
    public ArrayList<String> listAccountIds() {
    	ArrayList<String> outList = new ArrayList<String>();
    	for(Account account : accounts) {
    		outList.add(account.getAccountId());
    	}
    	return outList;
    }
    
    public ArrayList<String> listTransactonIds() {
    	ArrayList<String> outList = new ArrayList<String>();
    	for(Transaction transaction : transactions) {
    		outList.add(transaction.getTransactionId());
    	}
    	return outList;
    }
    
    
    //method to create new transaction, accounts, or customers, auto-filling variables 
    // and running the constructor and Bank.List.add()
    
    public void newCustomer(String name, String address, String phone) {
    	String innerCustomerId = nextCustomerId();    	
    	Customer customer = new Customer(innerCustomerId, name, address, phone);
    	customers.add(customer);
    }
    
    public void newAccount(String customerId, String type) {
    	String innerAccountId = nextAccountId();
    	Customer innerCustomer = getCustomerById(customerId);
    	double innerBalance = 0;
    	Account account;

    	switch(type) { //Account type argument assigned in child constructor
    	case "Savings": //Savings account, using default interest rate of 1.02
        	account = new SavingsAccount(innerAccountId, innerCustomer, innerBalance, 1.02);
    		break;
    	case "Checking": //Checking account using default overdraft limit of $200
        	account = new CheckingAccount(innerAccountId, innerCustomer, innerBalance, 200);
    		break;
		default:
			throw new IllegalArgumentException("Invalid Account type");
    	}
    	
    	accounts.add(account);
    }
    

    //Overloading to account for programs that input a string or a double amount
    public void newTransaction(String accountId, double amount) {
    	String innerTransactionId = nextTransactionId();
    	Account innerAccount = getAccountById(accountId);
    	LocalDateTime date = LocalDateTime.now();
    	String innerType;
    	if (amount >= 0) {
    		innerType = "Deposit";
    		innerAccount.deposit(amount);
    	} else {
    		innerType = "Withdrawal";
    		innerAccount.withdraw(amount);
    	}
    	
    	Transaction transaction = new Transaction(innerTransactionId, innerAccount, amount, date, innerType);
    	transactions.add(transaction);
    	System.out.println(innerAccount.getBalance());
    }
    public void newTransaction(String accountId, String amount) {
    	String innerTransactionId = nextTransactionId();
    	Account innerAccount = getAccountById(accountId);
    	LocalDateTime date = LocalDateTime.now();
    	String innerType;
        double innerAmount = Double.parseDouble(amount);
    	if (innerAmount >= 0) {
    		innerType = "Deposit";
    		innerAccount.deposit(innerAmount);
    	} else {
    		innerType = "Withdrawal";
    		innerAccount.withdraw(innerAmount);
    	}
    	
    	Transaction transaction = new Transaction(innerTransactionId, innerAccount, innerAmount, date, innerType);
    	transactions.add(transaction);
    	System.out.println(innerAccount.getBalance());
    }
    
    
    // Sort functions for lists
    public void sortCustomers() {
        Collections.sort(customers, new Comparator<Customer>() {
            @Override
            public int compare(Customer c1, Customer c2) {
                return c1.getCustomerId().compareTo(c2.getCustomerId());
            }
        });
    }
    
    public void sortAccounts() {
        Collections.sort(accounts, new Comparator<Account>() {
            @Override
            public int compare(Account a1, Account a2) {
                return a1.getAccountId().compareTo(a2.getAccountId());
            }
        });    
    }
    
    public void sortTransactions() {
        Collections.sort(transactions, new Comparator<Transaction>() {
            @Override
            public int compare(Transaction t1, Transaction t2) {
                return t1.getTransactionId().compareTo(t2.getTransactionId());
            }
        });
    }
	
    
    public String relativePathFile(String listType) {
    	Path currentPath = Paths.get("").toAbsolutePath();
    	String pathfilename = currentPath.toString();
    	pathfilename += "\\Resources\\" + listType + ".csv";
        
        return pathfilename;

    }
    
    
	// Ideally I'd be saving this to some sort of SQL database
	// Due to time constraints and portablility, I'm saving to CSV instead
	// the DB structure would be that Bank has child Customers,
	// Customers has child Accounts, Accounts has child Transactions
	
	//Save Customer info to a CSV
	public void saveCustomersToFile() {
		String filename = relativePathFile("customers");
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))){
			for (Customer customer : customers) {
				String line =  customer.getCustomerId() + "," + customer.getName() + ","+ customer.getAddress() + "," + customer.getPhone();
				writer.write(line);
				writer.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//load customer data from CSV
	public void loadCustomersFromFile() {
		String filename = relativePathFile("customers");
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))){
			String line;
			//check for end of line, line = null at end
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				String customerId = parts[0];
				String name = parts[1];
				String address = parts[2];
				String phone = parts[3];
				
				Customer customer = new Customer(customerId, name, address, phone);
				addCustomer (customer);
			}
			sortCustomers();
		} catch (IOException e){
			e.printStackTrace();
		}
	} 
	
	//Save Account info to a CSV
    public void saveAccountsToFile() {
		String filename = relativePathFile("accounts");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Account account : accounts) {
            	if (account.getType().equals("Savings")) {
            		SavingsAccount savingsAccount = (SavingsAccount) account;
	                String line = account.getAccountId() + "," + account.getCustomer().getCustomerId() + ","
	                		+ account.getBalance() + "," + account.getType() + "," + savingsAccount.getInterestRate();
	                writer.write(line);
	                writer.newLine();
            	} else if (account.getType().equals("Checking")) { //elif to allow for easy expansion when more account types
            		CheckingAccount checkingAccount = (CheckingAccount) account;
	                String line = account.getAccountId() + "," + account.getCustomer().getCustomerId() + ","
	                		+ account.getBalance() + "," + account.getType() + "," + checkingAccount.getOverdraftLimit();
	                writer.write(line);
	                writer.newLine();
            	}
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //load Account information from a CSV
    public void loadAccountsFromFile() {
		String filename = relativePathFile("accounts");
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String accountId = parts[0];
                String loadcustomerId = parts[1];
                double balance = Double.parseDouble(parts[2]);
                String type = parts[3];
                
                Customer loadcustomer = getCustomerById(loadcustomerId);
                if (loadcustomer != null) { // Check if a customer was found
                    if (type.equals("Savings")) { //Checks for account type and inits the proper subclass
                        double interestRate = Double.parseDouble(parts[4]);
                        Account account = new SavingsAccount(accountId, loadcustomer, balance, interestRate); // Adjust constructor if necessary
                        addAccount(account);
                    } else if (type.equals("Checking")) {
                        double overdraftLimit = Double.parseDouble(parts[4]);
                        Account account = new CheckingAccount(accountId, loadcustomer, balance, overdraftLimit); // Adjust constructor if necessary
                        addAccount(account);
                    }
                } else {
                    System.out.println("Customer with ID " + loadcustomerId + " not found.");
                }
            }
            sortAccounts();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Save Transactions
    public void saveTransactionsToFile() {
		String filename = relativePathFile("transactions");
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    	
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Transaction transaction : transactions) {
                String line = transaction.getTransactionId() + "," +
                              transaction.getAccount().getAccountId() + "," +
                              transaction.getAmount() + "," +
                              transaction.getDate().format(formatter) + "," +
                              transaction.getType();
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //load transactions
    public void loadTransactionsFromFile() {
		String filename = relativePathFile("transactions");
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    	
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String transactionId = parts[0];
                String accountId = parts[1];
                double amount = Double.parseDouble(parts[2]);
                LocalDateTime date = LocalDateTime.parse(parts[3], formatter);
                String type = parts[4];

                // Find the account by number
                Account account = getAccountById(accountId);
                if (account != null) {
                    Transaction transaction = new Transaction(transactionId, account, amount, date, type);
                    addTransaction(transaction);
                }
            }
            sortTransactions();
        } catch (IOException e) {
            e.printStackTrace();
        }
       
    }
    public void saveAll() {
    	saveCustomersToFile();
    	saveAccountsToFile();
    	saveTransactionsToFile();
    }
    
    public void loadAll() {
    	transactions.clear();
    	accounts.clear();
    	customers.clear();
    	loadCustomersFromFile();
    	loadAccountsFromFile();
    	loadTransactionsFromFile();
    }
}