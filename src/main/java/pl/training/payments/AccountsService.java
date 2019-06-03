package pl.training.payments;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class AccountsService {

    @NonNull
    private final AccountsRepository accountsRepository;

    public Account create() {
        Account account = new Account();
        return accountsRepository.saveAndFlush(account);
    }

    public void deposit(Long id, long funds) {
        process(id, account -> account.increaseBalance(funds));
    }

    public void witdraw(Long id, long funds) {
        process(id, account -> {
            if (funds > account.getBalance()) {
                throw new InsufficientFundsException();
            }
            account.decreaseBalance(funds);
        });
    }

    private void process(Long id, Consumer<Account> accountTask) {
        Account account = getById(id);
        accountTask.accept(account);
        accountsRepository.saveAndFlush(account);
    }

    public Account getById(Long id) {
        return accountsRepository.findById(id)
                .orElseThrow(AccountNotFoundException::new);
    }

    public ResultPage<Account> get(int pageNumber, int pageSize) {
        Page<Account> accountsPage = accountsRepository.findAll(PageRequest.of(pageNumber, pageSize));
        return new ResultPage<>(accountsPage.getContent(), pageNumber, accountsPage.getTotalPages());
    }

    public void deleteById(Long id) {
        Account account = getById(id);
        accountsRepository.delete(account);
    }
}
