
import java.time.LocalDateTime;

public class Transaction {
    private String transactionId;
    private Account account;
    private double amount;
    private LocalDateTime date;
    private String type; // deposit or withdrawal
    
    
    //constructor used when creating generic transaction
    public Transaction(String transactionId, Account account, double amount, LocalDateTime date, String type) {
        this.transactionId = transactionId;
        this.account = account;
        this.amount = amount;
        this.date = date;
        this.type = type;
    }
    
    
    public String getTransactionId() {
        return transactionId;
    }

    public Account getAccount() {
        return account;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getType() {
        return type;
    }
}