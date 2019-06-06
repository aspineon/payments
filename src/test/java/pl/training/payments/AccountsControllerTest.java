package pl.training.payments;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = PaymentsApplication.class
)
@AutoConfigureMockMvc
//@WebMvcTest(AccountsController.class)
@ExtendWith(SpringExtension.class)
public class AccountsControllerTest {

    private static final long ACCOUNT_ID = 1;

    @Autowired
    private MockMvc mockMvc;
   /* @MockBean
    private AccountsService accountsService;*/
    @Value("/${api-version}/accounts")
    private String accountsUrl;

    /*@BeforeEach
    void init() {
        Account account = new Account();
        account.setId(ACCOUNT_ID);
        when(accountsService.create()).thenReturn(account);
    }*/

    @Test
    void shouldCreateAccount() throws Exception {
        mockMvc.perform(post(accountsUrl)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
              //  .andExpect(header().string("Location", "http://localhost" + accountsUrl + "/" + ACCOUNT_ID));
    }

}
