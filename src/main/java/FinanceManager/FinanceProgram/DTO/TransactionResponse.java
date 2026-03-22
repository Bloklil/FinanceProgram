package FinanceManager.FinanceProgram.DTO;

import FinanceManager.FinanceProgram.TransactionType;
import java.time.LocalDate;

public class TransactionResponse {
    private Long id;
    private double amount;
    private TransactionType type;
    private String categoryName;
    private String accountName;
    private LocalDate date;

    public TransactionResponse(Long id, double amount, TransactionType type,
                               String categoryName, String accountName, LocalDate date) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.categoryName = categoryName;
        this.accountName = accountName;
        this.date = date;
    }

    public Long getId() { return id; }
    public double getAmount() { return amount; }
    public TransactionType getType() { return type; }
    public String getCategoryName() { return categoryName; }
    public String getAccountName() { return accountName; }
    public LocalDate getDate() { return date; }
}