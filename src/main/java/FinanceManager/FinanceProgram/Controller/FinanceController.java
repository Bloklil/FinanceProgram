package FinanceManager.FinanceProgram.Controller;

import FinanceManager.FinanceProgram.DTO.*;
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
}