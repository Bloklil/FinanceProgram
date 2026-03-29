package FinanceManager.FinanceProgram.Service;

import FinanceManager.FinanceProgram.DTO.*;
import FinanceManager.FinanceProgram.Entity.Account;
import FinanceManager.FinanceProgram.Entity.Category;
import FinanceManager.FinanceProgram.Entity.Transaction;
import FinanceManager.FinanceProgram.Repository.AccountRepository;
import FinanceManager.FinanceProgram.Repository.CategoryRepository;
import FinanceManager.FinanceProgram.Repository.TransactionRepository;
import FinanceManager.FinanceProgram.Entity.TransactionType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // Сумма расходов по категории
    public double getExpensesByCategory(Long categoryId) {
        return transactionRepo.findAll()
                .stream()
                .filter(t -> t.getCategory().getId().equals(categoryId) && t.getType() == TransactionType.EXPENSE)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    // Сумма доходов/расходов за период
    public double getTotalByTypeAndPeriod(TransactionType type, LocalDate from, LocalDate to) {
        return transactionRepo.findAll()
                .stream()
                .filter(t -> t.getType() == type && !t.getDate().isBefore(from) && !t.getDate().isAfter(to))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public void deleteAccount(Long id) {
        Account account = accountRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Аккаунт не найден"));

        accountRepo.delete(account);
    }

    public void deleteCategory(Long id) {
        Category category = categoryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Категория не найдена"));

        categoryRepo.delete(category);
    }

    public double getTotalBalance() {
        return accountRepo.findAll()
                .stream()
                .mapToDouble(Account::getBalance)
                .sum();
    }

    public double getMonthlyTotal(int year, int month, TransactionType type) {

        return transactionRepo.findAll()
                .stream()
                .filter(t -> t.getType() == type)
                .filter(t -> t.getDate().getYear() == year)
                .filter(t -> t.getDate().getMonthValue() == month)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public void deleteTransaction(Long id) {

        Transaction transaction = transactionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Транзакция не найдена"));

        Account account = transaction.getAccount();

        if (transaction.getType() == TransactionType.INCOME) {
            account.withdraw(transaction.getAmount());
        } else {
            account.deposit(transaction.getAmount());
        }

        accountRepo.save(account);
        transactionRepo.delete(transaction);
    }

    public List<MonthlyStats> getMonthlyStats() {

        List<Transaction> transactions = transactionRepo.findAll();

        Map<String, MonthlyStats> map = new HashMap<>();

        for (Transaction t : transactions) {

            String month = t.getDate().getMonth().toString();

            map.putIfAbsent(month, new MonthlyStats(month, 0, 0));

            MonthlyStats stats = map.get(month);

            if (t.getType() == TransactionType.INCOME) {
                stats = new MonthlyStats(month, stats.getIncome() + t.getAmount(), stats.getExpense());
            } else {
                stats = new MonthlyStats(month, stats.getIncome(), stats.getExpense() + t.getAmount());
            }

            map.put(month, stats);
        }

        return new ArrayList<>(map.values());
    }

    @Transactional
    public AccountResponse updateAccount(Long id, AccountRequest request) {
        Account account = accountRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Счет не найден"));
        account.setName(request.getName());
        account.setBalance(request.getBalance());
        return new AccountResponse(account.getId(), account.getName(), account.getBalance());
    }

    @Transactional
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Category category = categoryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Категория не найдена"));
        category.setName(request.getName());
        return new CategoryResponse(category.getId(), category.getName());
    }

    @Transactional
    public TransactionResponse updateTransaction(Long id, TransactionRequest request) {
        Transaction transaction = transactionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Транзакция не найдена"));

        Account oldAccount = transaction.getAccount();
        if (transaction.getType() == TransactionType.INCOME) {
            oldAccount.withdraw(transaction.getAmount());
        } else {
            oldAccount.deposit(transaction.getAmount());
        }
        accountRepo.save(oldAccount);

        Account newAccount = accountRepo.findById(request.getAccountId())
                .orElseThrow(() -> new RuntimeException("Аккаунт не найден"));
        Category newCategory = categoryRepo.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Категория не найдена"));

        transaction.setAmount(request.getAmount());
        transaction.setDate(request.getDate());
        transaction.setAccount(newAccount);
        transaction.setCategory(newCategory);
        transaction.setType(request.getType());

        if (request.getType() == TransactionType.INCOME) {
            newAccount.deposit(request.getAmount());
        } else {
            newAccount.withdraw(request.getAmount());
        }
        accountRepo.save(newAccount);

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

}