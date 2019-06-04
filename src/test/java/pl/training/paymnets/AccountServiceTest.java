package pl.training.paymnets;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.training.payments.Account;
import pl.training.payments.AccountNotFoundException;
import pl.training.payments.AccountsRepository;
import pl.training.payments.AccountsService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AccountServiceTest {

    private final long NON_EXISTING_ACCOUNT_ID = 100;
    private final long FUNDS = 1_000;
    private final AccountsRepository accountsRepository = mock(AccountsRepository.class);
    private final AccountsService accountsService = new AccountsService(accountsRepository);

    @BeforeEach
    void init() {
        when(accountsRepository.saveAndFlush(any(Account.class))).then(returnsFirstArg());
    }

    @DisplayName("Should save created account")
    @Test
    void shouldSaveCreatedAccount() {
        Account account = accountsService.create();
        verify(accountsRepository).saveAndFlush(account);
    }

    @Test
    void shouldThrowExceptionWhenDepositIsMadeOnNonExistingAccount() {
        assertThrows(AccountNotFoundException.class, () -> accountsService.deposit(NON_EXISTING_ACCOUNT_ID, FUNDS));
    }

}