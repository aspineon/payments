package pl.trainin.payments;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

}
