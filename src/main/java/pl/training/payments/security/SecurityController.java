package pl.training.payments.security;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("${api-version}/users/active")
@RestController
@RequiredArgsConstructor
public class SecurityController {

    private static final String TOKEN_PREFIX = "bearer ";

    @NonNull
    private final TokenStore tokenStore;
    @NonNull
    private final PrincipalMapper principalMapper;

    @RequestMapping(method = RequestMethod.GET)
    public PrincipalTransferObject getActiveUser(Authentication authentication) {
        return principalMapper.toPrincipalTransferObject(authentication);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void logout(@RequestHeader("Authorization") String tokenHeader) {
        String tokenValue = tokenHeader.replace(TOKEN_PREFIX, "");
        OAuth2AccessToken token = tokenStore.readAccessToken(tokenValue);
        OAuth2RefreshToken refreshToken = token.getRefreshToken();
        if (refreshToken != null) {
            tokenStore.removeRefreshToken(refreshToken);
        }
        tokenStore.removeAccessToken(token);
    }

}
