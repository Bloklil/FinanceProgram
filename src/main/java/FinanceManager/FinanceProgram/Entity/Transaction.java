package FinanceManager.FinanceProgram.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Account account;

    private LocalDate date = LocalDate.now();

    public Transaction() {}

    public Transaction(double amount, Category category, Account account, TransactionType type) {
        this.amount = amount;
        this.category = category;
        this.account = account;
        this.type = type;
    }

    public Long getId() { return id; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public TransactionType getType() { return type; }
    public void setType(TransactionType type) { this.type = type; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public Account getAccount() { return account; }
    public void setAccount(Account account) { this.account = account; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
}