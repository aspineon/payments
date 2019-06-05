package pl.training.payments;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountsMapper {

    @Mapping(source = "balance", target = "b")
    AccountTransferObject toAccountTransferObject(Account account);

    @IterableMapping(elementTargetType = AccountTransferObject.class)
    List<AccountTransferObject> toAccountTransferObjects(List<Account> accounts);

    PageTransferObject<AccountTransferObject> toAccountTransferObjectsPage(ResultPage<Account> accountResultPage);

}
