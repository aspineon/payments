package pl.training.payments.security;

import org.mapstruct.Mapper;
import org.springframework.security.core.Authentication;

@Mapper(componentModel = "spring")
public interface PrincipalMapper {

    PrincipalTransferObject toPrincipalTransferObject(Authentication authentication);

}
