package pl.training.payments.security;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.lang.model.type.ArrayType;
import javax.sql.DataSource;
import java.util.List;

@EnableAuthorizationServer
@Configuration
@RequiredArgsConstructor
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private static final String PASSWORD_GRANT_TYPE = "password";
    private static final String REFRESH_TOKEN_GRANT_TYPE = "refresh_token";
    private static final String DEFAULT_SCOPE = "default";

    @Value("${client-id}")
    @Setter
    private String clientId;

    @NonNull
    private final AuthenticationManager authenticationManagerBean;
    @NonNull
    private final TokenStore tokenStore;
    @NonNull
    private final DataSource dataSource;
    @NonNull
    private final PasswordEncoder passwordEncoder;
    @NonNull
    private final SecurityService securityService;
    @NonNull
    private final JWTTokenEnhancer jwtTokenEnhancer;
    @NonNull
    private final JwtAccessTokenConverter jwtAccessTokenConverter;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(List.of(jwtTokenEnhancer, jwtAccessTokenConverter));

        endpoints.userDetailsService(securityService)
                .authenticationManager(authenticationManagerBean)
                .accessTokenConverter(jwtAccessTokenConverter)
                .tokenEnhancer(tokenEnhancerChain)
                .tokenStore(tokenStore);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.allowFormAuthenticationForClients().passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        /*clients.inMemory()
                .withClient(clientId)
                .authorizedGrantTypes(PASSWORD_GRANT_TYPE)
                .scopes(DEFAULT_SCOPE);*/
        try {
            new JdbcClientDetailsService(dataSource).loadClientByClientId(clientId);
        } catch (Exception exception) {
            clients.jdbc(dataSource)
                    .withClient(clientId)
                    .authorizedGrantTypes(PASSWORD_GRANT_TYPE, REFRESH_TOKEN_GRANT_TYPE)
                    .scopes(DEFAULT_SCOPE)
                    .accessTokenValiditySeconds(10 * 60)
                    .refreshTokenValiditySeconds(20 * 60);
        }
    }

}
