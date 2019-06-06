package pl.training.accounts;

import lombok.Data;

@Data
public class AccountTransferObject implements AccountTo {

    private long funds;

}
