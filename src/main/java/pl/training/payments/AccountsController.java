package pl.training.payments;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequestMapping("${api-version}/accounts")
@RestController
@RequiredArgsConstructor
public class AccountsController {

    @NonNull
    private final AccountsService accountsService;
    private final UriBuilder uriBuilder = new UriBuilder();

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Account> createAccount() {
        Account account = accountsService.create();
        URI uri = uriBuilder.requestUriWithId(account.getId());
        return ResponseEntity.created(uri)
                .body(account);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Account> getAccountById(@PathVariable("id") Long id) {
        Account account = accountsService.getById(id);
        return ResponseEntity.ok(account);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<ResultPage<Account>> getAccountsPage(
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "5") int pageSize) {
        ResultPage<Account> accountsPage = accountsService.get(pageNumber, pageSize);
        return ResponseEntity.ok(accountsPage);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteAccountById(@PathVariable("id") Long id) {
        accountsService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<Void> onAccountNotFound(AccountNotFoundException exception) {
        return ResponseEntity.notFound().build();
    }

}
