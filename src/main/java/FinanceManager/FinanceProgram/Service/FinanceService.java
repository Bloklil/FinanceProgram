package FinanceManager.FinanceProgram.Service;

import FinanceManager.FinanceProgram.DTO.*;
import FinanceManager.FinanceProgram.Entity.Account;
import FinanceManager.FinanceProgram.Entity.Category;
import FinanceManager.FinanceProgram.Entity.Transaction;
import FinanceManager.FinanceProgram.Repository.AccountRepository;
import FinanceManager.FinanceProgram.Repository.CategoryRepository;
import FinanceManager.FinanceProgram.Repository.TransactionRepository;
import FinanceManager.FinanceProgram.TransactionType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FinanceService {

    private final AccountRepository accountRepo;
    private final CategoryRepository categoryRepo;
    private final TransactionRepository transactionRepo;

    public FinanceService(AccountRepository accountRepo,
                          CategoryRepository categoryRepo,
                          TransactionRepository transactionRepo) {
        this.accountRepo = accountRepo;
        this.categoryRepo = categoryRepo;
        this.transactionRepo = transactionRepo;
    }

    public AccountResponse createAccount(AccountRequest request) {
        Account acc = new Account(request.getName(), request.getBalance());
        Account saved = accountRepo.save(acc);
        return new AccountResponse(saved.getId(), saved.getName(), saved.getBalance());
    }

    public List<AccountResponse> getAccounts() {
        return accountRepo.findAll()
                .stream()
                .map(a -> new AccountResponse(a.getId(), a.getName(), a.getBalance()))
                .toList();
    }

    public CategoryResponse createCategory(CategoryRequest request) {
        Category cat = new Category(request.getName());
        Category saved = categoryRepo.save(cat);
        return new CategoryResponse(saved.getId(), saved.getName());
    }

    public List<CategoryResponse> getCategories() {
        return categoryRepo.findAll()
                .stream()
                .map(c -> new CategoryResponse(c.getId(), c.getName()))
                .toList();
    }

    public TransactionResponse addTransaction(TransactionRequest request) {
        Account account = accountRepo.findById(request.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));
        Category category = categoryRepo.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        if (request.getType() == TransactionType.INCOME) {
            account.deposit(request.getAmount());
        } else {
            account.withdraw(request.getAmount());
        }

        accountRepo.save(account);

        Transaction transaction = new Transaction(
                request.getAmount(),
                category,
                account,
                request.getType()
        );

        Transaction saved = transactionRepo.save(transaction);

        return new TransactionResponse(
                saved.getId(),
                saved.getAmount(),
                saved.getType(),
                saved.getCategory().getName(),
                saved.getAccount().getName(),
                saved.getDate()
        );
    }

    public List<TransactionResponse> getTransactions() {
        return transactionRepo.findAll()
                .stream()
                .map(t -> new TransactionResponse(
                        t.getId(),
                        t.getAmount(),
                        t.getType(),
                        t.getCategory().getName(),
                        t.getAccount().getName(),
                        t.getDate()
                ))
                .toList();
    }
}