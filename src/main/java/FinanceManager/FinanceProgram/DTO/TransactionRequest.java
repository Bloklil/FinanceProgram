package FinanceManager.FinanceProgram.DTO;

import FinanceManager.FinanceProgram.TransactionType;

public class TransactionRequest {
    private Long accountId;
    private Long categoryId;
    private double amount;
    private TransactionType type;

    public Long getAccountId() { return accountId; }
    public void setAccountId(Long accountId) { this.accountId = accountId; }

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public TransactionType getType() { return type; }
    public void setType(TransactionType type) { this.type = type; }
}