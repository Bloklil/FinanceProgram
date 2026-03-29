package FinanceManager.FinanceProgram.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class AccountRequest {
    @NotBlank(message = "Имя счета обязательно")
    private String name;

    @Min(value = 0, message = "Баланс не может быть отрицательным")
    private double balance;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
}