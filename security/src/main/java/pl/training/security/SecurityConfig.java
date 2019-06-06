package pl.training.security;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.sql.DataSource;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Value("classpath:h2-token-store-schema.sql")
    @Setter
    private Resource schemaScript;
    @Value("${signing-key}")
    @Setter
    private String jwtSigningKey;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DatabasePopulator databasePopulator() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(schemaScript);
        return populator;
    }

    @Bean
    public DataSourceInitializer dataSourceInitializer(DataSource dataSource, DatabasePopulator databasePopulator) {
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(databasePopulator);
        return initializer;
    }

    @Bean
    public JWTTokenEnhancer jwtTokenEnhancer() {
        return new JWTTokenEnhancer();
    }

    /*@Bean
    public TokenStore tokenStore(DataSource dataSource) {
        return new JdbcTokenStore(dataSource);
    }*/

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(jwtSigningKey);
        return converter;
    }

    @Bean
    public TokenStore jwtTokenStore(JwtAccessTokenConverter jwtAccessTokenConverter) {
        return new JwtTokenStore(jwtAccessTokenConverter);
    }

}
