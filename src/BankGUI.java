import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class BankGUI extends JFrame{
	private Bank bank;
	
	public BankGUI(Bank bank) {
		this.bank = bank;


        setTitle("LendiTek SafeSave Banking Solutions");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create panels
        JPanel mainPanel = new JPanel(new CardLayout());
        JPanel menuPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel addCustomerPanel = new JPanel(new GridLayout(5, 2));
        JPanel addAccountPanel = new JPanel(new GridLayout(5, 2));
        JPanel transactionPanel = new JPanel(new GridLayout(6, 2));
        JPanel balancePanel = new JPanel(new GridLayout(3, 2));

        // Create menu buttons + action listeners
        JButton addCustomerButton = new JButton("Add Customer");
        JButton addAccountButton = new JButton("Add Account");
        JButton transactionButton = new JButton("Perform Transaction");
        JButton balanceButton = new JButton("View Balance");

        addCustomerButton.addActionListener(e -> {
            initAddCustomerPanel(addCustomerPanel);
        	switchPanel(mainPanel, "Add Customer");});
        addAccountButton.addActionListener(e -> {
            initAddAccountPanel(addAccountPanel);
        	switchPanel(mainPanel, "Add Account");});
        transactionButton.addActionListener(e -> {
            initTransactionPanel(transactionPanel);
        	switchPanel(mainPanel, "Transaction");});
        balanceButton.addActionListener(e -> {
            initBalancePanel(balancePanel);
            switchPanel(mainPanel, "Balance");});
        
        

        menuPanel.add(addCustomerButton);
        menuPanel.add(addAccountButton);
        menuPanel.add(transactionButton);
        menuPanel.add(balanceButton);


        // Add secondary panels to main panel with proper identifiers
        mainPanel.add(menuPanel, "Menu");
        mainPanel.add(addCustomerPanel, "Add Customer");
        mainPanel.add(addAccountPanel, "Add Account");
        mainPanel.add(transactionPanel, "Transaction");
        mainPanel.add(balancePanel, "Balance");

        // Add main panel to frame
        add(mainPanel, BorderLayout.CENTER);

        
        // THIS WAS CAUSING TRAUMA. MAKE THEM LOAD NEW EACH TIME
        /* Initialize individual panels
        initAddCustomerPanel(addCustomerPanel);
        initAddAccountPanel(addAccountPanel);
        initTransactionPanel(transactionPanel);
        initBalancePanel(balancePanel);
        */
        

		bank.loadAll();
		System.out.println("Current customers: " + bank.listCustomerIds());
    }

    private void switchPanel(JPanel mainPanel, String panelIdentifier) {
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, panelIdentifier);
    }
	
	private void initAddCustomerPanel(JPanel panel) {
		panel.removeAll();
		panel.updateUI();
		panel.setName("Add Customer");
		JLabel nameLabel = new JLabel("Name:");
		JTextField nameField = new JTextField();
		JLabel idLabel = new JLabel("CustomerId");
		JLabel idReturnLabel = new JLabel(bank.nextCustomerId());
		JLabel addressLabel = new JLabel("Address:");
		JTextField addressField = new JTextField();
		JLabel phoneLabel = new JLabel("Phone Number:");
		JTextField phoneField = new JTextField();
		JButton addButton = new JButton("Add Customer");
		
		JButton backButton = new JButton("Back");
		backButton.addActionListener(e -> switchPanel((JPanel) panel.getParent(), "Menu"));
		
		addButton.addActionListener(e -> {
			String name = nameField.getText();
			//customer autofilled in newCustomer()
			String address = addressField.getText();
			String phone = phoneField.getText();
			//create object and add to list
			bank.newCustomer(name, address, phone);
			bank.saveAll();
			JOptionPane.showMessageDialog(this, "Customer added");
			System.out.println("Current customers: " + bank.listCustomerIds());
			switchPanel((JPanel) panel.getParent(), "Menu");
		});
		
		panel.add(nameLabel);
		panel.add(nameField);
		panel.add(idLabel);
		panel.add(idReturnLabel);
		panel.add(addressLabel);
		panel.add(addressField);
		panel.add(phoneLabel);
		panel.add(phoneField);
		panel.add(addButton);
	    panel.add(backButton);
	}
	
	
	private void initAddAccountPanel(JPanel panel) {
		panel.removeAll();
		panel.updateUI();
		panel.setName("Add Account");
		
		JLabel customerIdLabel = new JLabel("Customer ID");
		JComboBox<String> customerIdComboBox = new JComboBox<>(bank.listCustomerIds().toArray(new String[0]));
		JLabel accountIdLabel = new JLabel("Account Number");
		JLabel returnIdLabel = new JLabel(bank.nextAccountId());
	    JLabel accountTypeLabel = new JLabel("Account Type:");
	    JComboBox<String> accountTypeComboBox = new JComboBox<>(new String[]{"Savings", "Checking"});
	    JButton addButton = new JButton("Add Account");
		JButton backButton = new JButton("Back");

		
		backButton.addActionListener(e -> switchPanel((JPanel) panel.getParent(), "Menu"));
	
	    addButton.addActionListener(e -> {
	    	String customerId = (String) customerIdComboBox.getSelectedItem();
	    	String accountType = (String) accountTypeComboBox.getSelectedItem();
	    	Customer customer = bank.getCustomerById(customerId);
	    	
	    	if (customer != null) {
    			bank.newAccount(customerId, accountType);
	    	}
			JOptionPane.showMessageDialog(this, "Account added");
			bank.saveAll();
			switchPanel((JPanel) panel.getParent(), "Menu");
	    });
	    
	    customerIdComboBox.addActionListener(e -> {
	    	returnIdLabel.setText(bank.nextAccountId());
	    });
	    
	    panel.add(customerIdLabel);
	    panel.add(customerIdComboBox);
	    panel.add(accountIdLabel);
	    panel.add(returnIdLabel);
	    panel.add(accountTypeLabel);
	    panel.add(accountTypeComboBox);
	    panel.add(addButton);
	    panel.add(backButton);
	}
	
	private void initTransactionPanel(JPanel panel) {
		panel.removeAll();
		panel.updateUI();
		panel.setName("Add Transaction");
		
		JLabel customerIdLabel = new JLabel("Customer ID");
		JComboBox<String> customerIdComboBox = new JComboBox<>(bank.listCustomerIds().toArray(new String[0]));
		JLabel accountIdLabel = new JLabel("Account ID");
		JComboBox<String> accountIdComboBox = new JComboBox<>();
		JLabel transactionIdLabel = new JLabel("Transaction Number");
		JLabel returnIdLabel = new JLabel(bank.nextAccountId());
		JLabel balanceLabel = new JLabel("Balance: ");
		JLabel balanceValueLabel = new JLabel();
		JLabel amountLabel = new JLabel("Transaction amount(negative{-} for withdrawal)");
		JTextField amountField = new JTextField();
		JButton transactionButton = new JButton("Perform Transaction");
		
		JButton backButton = new JButton("Back");
		
		backButton.addActionListener(e -> switchPanel((JPanel) panel.getParent(), "Menu"));
		
		
		//listener to update account dropdown
	    customerIdComboBox.addActionListener(e -> {
	        String selectedCustomerId = (String) customerIdComboBox.getSelectedItem();
	        updateAccountIdComboBox(accountIdComboBox, selectedCustomerId);
	        accountIdComboBox.setEnabled(selectedCustomerId != null && accountIdComboBox.getItemCount() > 0);
	        
	    });
	    //listener to update balance on account select
	    accountIdComboBox.addActionListener(e -> {
	        String selectedAccountId = (String) accountIdComboBox.getSelectedItem();
	        if (selectedAccountId != null) {
	            Account account = bank.getAccountById(selectedAccountId);
	            if (account != null) {
	                updateBalanceLabel(balanceValueLabel, account.getBalance());
	            }
	        } else {
	            updateBalanceLabel(balanceValueLabel, 0.00);
	        }
	    });
	    
	    transactionButton.addActionListener(e -> {
	    	String accountId = (String) accountIdComboBox.getSelectedItem();
	    	double amount = Double.parseDouble(amountField.getText());
	    	System.out.println(amount);
	    	bank.newTransaction(accountId, amount);
			JOptionPane.showMessageDialog(this, "Transaction complete");
			bank.saveAll();
			switchPanel((JPanel) panel.getParent(), "Menu");
	    });
	    // Initial population of the accountIdComboBox
	    updateAccountIdComboBox(accountIdComboBox, (String) customerIdComboBox.getSelectedItem());

	    panel.add(customerIdLabel);
	    panel.add(customerIdComboBox);
	    panel.add(accountIdLabel);
	    panel.add(accountIdComboBox);
	    panel.add(transactionIdLabel);
	    panel.add(returnIdLabel);
	    panel.add(balanceLabel);
	    panel.add(balanceValueLabel);
	    panel.add(amountLabel);
	    panel.add(amountField);
	    panel.add(transactionButton);
	    panel.add(backButton);
	}

	private void initBalancePanel(JPanel panel) {
		panel.removeAll();
		panel.updateUI();
	    panel.setName("Check Balance");
	    
	    JLabel customerIdLabel = new JLabel("Customer ID");
	    JComboBox<String> customerIdComboBox = new JComboBox<>(bank.listCustomerIds().toArray(new String[0]));
	    JLabel accountIdLabel = new JLabel("Account ID");
	    JComboBox<String> accountIdComboBox = new JComboBox<>();
		JLabel balanceLabel = new JLabel("Balance: ");
	    JLabel balanceValueLabel = new JLabel("Balance: $0.00");
		JButton backButton = new JButton("Back");
		
		backButton.addActionListener(e -> switchPanel((JPanel) panel.getParent(), "Menu"));

	    customerIdComboBox.addActionListener(e -> {
	        String selectedCustomerId = (String) customerIdComboBox.getSelectedItem();
	        updateAccountIdComboBox(accountIdComboBox, selectedCustomerId);
	        accountIdComboBox.setEnabled(selectedCustomerId != null && accountIdComboBox.getItemCount() > 0);
	        updateBalanceLabel(balanceValueLabel, 0.00);
	    });

	    accountIdComboBox.addActionListener(e -> {
	        String selectedAccountId = (String) accountIdComboBox.getSelectedItem();
	        if (selectedAccountId != null) {
	            Account account = bank.getAccountById(selectedAccountId);
	            if (account != null) {
	                updateBalanceLabel(balanceValueLabel, account.getBalance());
	            }
	        } else {
	            updateBalanceLabel(balanceValueLabel, 0.00);
	        }
	    });

	    // Initial population of the accountIdComboBox
	    updateAccountIdComboBox(accountIdComboBox, (String) customerIdComboBox.getSelectedItem());

	    panel.add(customerIdLabel);
	    panel.add(customerIdComboBox);
	    panel.add(accountIdLabel);
	    panel.add(accountIdComboBox);
	    panel.add(balanceLabel);
	    panel.add(balanceValueLabel);
	    panel.add(backButton);
	}
	private void updateCustomerIdComboBox(JComboBox<String> customerIdComboBox) {
	    customerIdComboBox.removeAllItems(); // Clear existing items

        ArrayList<String> customerIds = bank.listCustomerIds();
        for (String customerId : customerIds) {
            Customer customer = bank.getCustomerById(customerId);
            if (customer != null) {
                customerIdComboBox.addItem(customer.getCustomerId());
            }
        }
	} 
	
	private void updateAccountIdComboBox(JComboBox<String> accountIdComboBox, String customerId) {
	    accountIdComboBox.removeAllItems(); // Clear existing items

	    if (customerId != null) {
	        ArrayList<String> accountIds = bank.listAccountIds();
	        for (String accountId : accountIds) {
	            Account account = bank.getAccountById(accountId);
	            if (account != null && account.getCustomer().getCustomerId().equals(customerId)) {
	                accountIdComboBox.addItem(account.getAccountId());
	            }
	        }
	    } 
	}
	
	private void updateBalanceLabel(JLabel balanceLabel, double balance) {
	    balanceLabel.setText(String.format("%.2f", balance));
	}
	

    public static void main(String[] args) {
        Bank bank = new Bank();
        SwingUtilities.invokeLater(() -> {
            BankGUI gui = new BankGUI(bank);
            gui.setVisible(true);
        });
    }
}
