package pl.training.payments;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.training.payments.accounts.Account;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountTest {

    private static final long FUNDS = 1_000;

    private final Account account = new Account();
    private final long initialBalance = account.getBalance();

    @DisplayName("Should increase balance")
    @Test
    void shouldIncreaseBalance() {
        account.increaseBalance(FUNDS);
        assertEquals(initialBalance + FUNDS, account.getBalance());
    }

    @DisplayName("Should decrease balance")
    @Test
    void shouldDecreaseBalance() {
        account.decreaseBalance(FUNDS);
        assertEquals(initialBalance - FUNDS, account.getBalance());
    }

}
