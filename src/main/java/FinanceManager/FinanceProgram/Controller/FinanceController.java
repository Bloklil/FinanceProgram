package FinanceManager.FinanceProgram.Controller;

import FinanceManager.FinanceProgram.DTO.*;
import FinanceManager.FinanceProgram.Entity.Account;
import FinanceManager.FinanceProgram.Entity.TransactionType;
import FinanceManager.FinanceProgram.Service.FinanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Финансы", description = "Управление счетами, категориями и транзакциями")
@RestController
@RequestMapping("/api")
public class FinanceController {

    private final FinanceService service;

    public FinanceController(FinanceService service) {
        this.service = service;
    }

    // ---------------- Accounts ----------------
    @Operation(summary = "Создать новый счет", description = "Создает новый счет для пользователя")
    @PostMapping("/accounts")
    public AccountResponse createAccount(
            @Valid
            @RequestBody AccountRequest request
    ) {
        return service.createAccount(request);
    }

    @Operation(summary = "Обновить счет")
    @PutMapping("/accounts/{id}")
    public AccountResponse updateAccount(
            @PathVariable Long id,
            @Valid @RequestBody AccountRequest request
    ) {
        return service.updateAccount(id, request);
    }

    @Operation(summary = "Получить все счета")
    @GetMapping("/accounts")
    public List<AccountResponse> getAccounts() {
        return service.getAccounts();
    }

    // ---------------- Categories ----------------
    @Operation(summary = "Создать категорию")
    @PostMapping("/categories")
    public CategoryResponse createCategory(
            @Valid
            @RequestBody CategoryRequest request
    ) {
        return service.createCategory(request);
    }

    @Operation(summary = "Получить все категории")
    @GetMapping("/categories")
    public List<CategoryResponse> getCategories() {
        return service.getCategories();
    }

    @Operation(summary = "Обновить категорию")
    @PutMapping("/categories/{id}")
    public CategoryResponse updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequest request
    ) {
        return service.updateCategory(id, request);
    }

    // ---------------- Transactions ----------------
    @Operation(summary = "Добавить транзакцию")
    @PostMapping("/transactions")
    public TransactionResponse addTransaction(
            @Valid
            @RequestBody TransactionRequest request
    ) {
        return service.addTransaction(request);
    }

    @Operation(summary = "Получить все транзакции")
    @GetMapping("/transactions")
    public List<TransactionResponse> getTransactions() {
        return service.getTransactions();
    }


    @GetMapping("/analytics/expenses")
    public double getExpenses(@RequestParam Long categoryId) {
        return service.getExpensesByCategory(categoryId);
    }

    @Operation(summary = "Удалить счет")
    @DeleteMapping("/accounts/{id}")
    public void deleteAccount(@PathVariable Long id) {
        service.deleteAccount(id);
    }

    @Operation(summary = "Удалить категорию")
    @DeleteMapping("/categories/{id}")
    public void deleteCategory(@PathVariable Long id) {
        service.deleteCategory(id);
    }

    @Operation(summary = "Общий баланс по всем счетам")
    @GetMapping("/analytics/balance")
    public double getTotalBalance() {
        return service.getTotalBalance();
    }

    @Operation(summary = "Аналитика по месяцам")
    @GetMapping("/analytics/month")
    public double getMonthlyAnalytics(
            @RequestParam int year,
            @RequestParam int month,
            @RequestParam TransactionType type
    ) {
        return service.getMonthlyTotal(year, month, type);
    }

    @Operation(summary = "Удалить транзакцию")
    @DeleteMapping("/transactions/{id}")
    public void deleteTransaction(@PathVariable Long id) {
        service.deleteTransaction(id);
    }

    @GetMapping("/analytics/monthly")
    public List<MonthlyStats> getMonthlyStats() {
        return service.getMonthlyStats();
    }

    @Operation(summary = "Обновить транзакцию", description = "Редактирует сумму, дату, счет, категорию и тип транзакции")
    @PutMapping("/transactions/{id}")
    public TransactionResponse updateTransaction(
            @PathVariable Long id,
            @Valid @RequestBody TransactionRequest request
    ) {
        return service.updateTransaction(id, request);
    }

}