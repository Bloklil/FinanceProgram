package FinanceManager.FinanceProgram.DTO;

import FinanceManager.FinanceProgram.Entity.TransactionType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class TransactionRequest {
    private Long accountId;
    private Long categoryId;
    private double amount;
    private TransactionType type;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    public Long getAccountId() { return accountId; }
    public void setAccountId(Long accountId) { this.accountId = accountId; }

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public TransactionType getType() { return type; }
    public void setType(TransactionType type) { this.type = type; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
}