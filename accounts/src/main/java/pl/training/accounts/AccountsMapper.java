package pl.training.accounts;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.training.common.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountsMapper {

    @Mapping(source = "balance", target = "funds")
    AccountTransferObject toAccountTransferObject(Account account);

    @IterableMapping(elementTargetType = AccountTransferObject.class)
    List<AccountTransferObject> toAccountTransferObjects(List<Account> accounts);

    PageTransferObject<AccountTransferObject> toAccountTransferObjectsPage(ResultPage<Account> accountResultPage);

}
