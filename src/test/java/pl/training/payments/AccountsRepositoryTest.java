package pl.training.payments;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.training.payments.accounts.Account;
import pl.training.payments.accounts.AccountsRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class AccountsRepositoryTest {

    private static final long FUNDS = 1_000;

    @Autowired
    private AccountsRepository accountsRepository;
    @Autowired
    private TestEntityManager entityManager;
    private Account account = new Account();

    @BeforeEach
    public void setUp() {
        account.setBalance(FUNDS);
        entityManager.persist(account);
        entityManager.persist(new Account());
        entityManager.flush();
    }

    @Test
    public void shouldReturnAccountsWithExpectedBalance() {
        List<Account> accounts = accountsRepository.getByBalance(FUNDS);
        assertTrue(accounts.contains(account));
        assertEquals(1, accounts.size());
    }

}
