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
    @NonNull
    private final AccountsMapper accountsMapper;
    private final UriBuilder uriBuilder = new UriBuilder();

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<AccountTo> createAccount() {
        Account account = accountsService.create();
        URI uri = uriBuilder.requestUriWithId(account.getId());
        return ResponseEntity.created(uri)
                .body(accountsMapper.toAccountTransferObject(account));
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<AccountTransferObject> getAccountById(@PathVariable("id") Long id) {
        Account account = accountsService.getById(id);
        return ResponseEntity.ok(accountsMapper.toAccountTransferObject(account));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<PageTransferObject<AccountTransferObject>> getAccountsPage(
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "5") int pageSize) {
        ResultPage<Account> accountsPage = accountsService.get(pageNumber, pageSize);
        return ResponseEntity.ok(accountsMapper.toAccountTransferObjectsPage(accountsPage));
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
