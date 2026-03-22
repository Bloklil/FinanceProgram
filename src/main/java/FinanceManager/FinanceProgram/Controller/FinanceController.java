package FinanceManager.FinanceProgram.Controller;

import FinanceManager.FinanceProgram.DTO.AccountRequest;
import FinanceManager.FinanceProgram.DTO.AccountResponse;
import FinanceManager.FinanceProgram.DTO.CategoryRequest;
import FinanceManager.FinanceProgram.DTO.CategoryResponse;
import FinanceManager.FinanceProgram.DTO.TransactionRequest;
import FinanceManager.FinanceProgram.DTO.TransactionResponse;
import FinanceManager.FinanceProgram.Service.FinanceService;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Финансы", description = "Операции управления финансами")
@RestController
@RequestMapping("/api")
public class FinanceController {

    private final FinanceService service;

    public FinanceController(FinanceService service) {
        this.service = service;
    }

    @Operation(summary = "Создать новый счет")
    @PostMapping("/accounts")
    public AccountResponse createAccount(@RequestBody AccountRequest request) {
        return service.createAccount(request);
    }

    @Operation(summary = "Получить все счета")
    @GetMapping("/accounts")
    public List<AccountResponse> getAccounts() {
        return service.getAccounts();
    }

    @Operation(summary = "Создать категорию")
    @PostMapping("/categories")
    public CategoryResponse createCategory(@RequestBody CategoryRequest request) {
        return service.createCategory(request);
    }

    @Operation(summary = "Получить все категории")
    @GetMapping("/categories")
    public List<CategoryResponse> getCategories() {
        return service.getCategories();
    }

    @Operation(summary = "Добавить транзакцию")
    @PostMapping("/transactions")
    public TransactionResponse addTransaction(@RequestBody TransactionRequest request) {
        return service.addTransaction(request);
    }

    @Operation(summary = "Получить все транзакции")
    @GetMapping("/transactions")
    public List<TransactionResponse> getTransactions() {
        return service.getTransactions();
    }
}