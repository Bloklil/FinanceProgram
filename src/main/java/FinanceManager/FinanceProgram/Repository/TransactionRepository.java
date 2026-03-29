package FinanceManager.FinanceProgram.Repository;

import FinanceManager.FinanceProgram.Entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
