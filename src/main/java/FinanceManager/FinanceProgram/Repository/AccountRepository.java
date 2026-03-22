package FinanceManager.FinanceProgram.Repository;

import FinanceManager.FinanceProgram.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}