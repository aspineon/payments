package pl.training.payments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountsRepository extends JpaRepository<Account, Long> {

    List<Account> getByBalance(long value);

    @Query("select a from Account as a where a.balance > :balance")
    List<Account> getWithMinimumBalance(@Param("balance") long value);

}
