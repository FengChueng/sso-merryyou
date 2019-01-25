package cn.merryyou.sso.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * Created on 2017/12/26.
 *
 * @author zlf
 * @since 1.0
 */
@Configuration
@EnableAuthorizationServer
public class SsoAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    /**
     * 客户端一些配置
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("merryyou1")
                .secret("merryyousecrect1")
                .authorizedGrantTypes("authorization_code","client_credentials", "refresh_token")
                .scopes("all","read","write")
                .autoApprove(true)
                .and()
                .withClient("merryyou2")
                .secret("merryyousecrect2")
                .authorizedGrantTypes("authorization_code","password", "refresh_token")
                .scopes("all","read","write")
//                .autoApprove(true)
        ;
    }

    /**
     * 配置jwttokenStore
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(jwtTokenStore()).accessTokenConverter(jwtAccessTokenConverter());
//        endpoints.approvalStore(new TokenApprovalStore());
//        endpoints.userApprovalHandler(new TokenStoreUserApprovalHandler());
//        endpoints.pathMapping()
//        endpoints.approvalStore();
//        endpoints.authenticationManager();
//        endpoints.authorizationCodeServices();
//        endpoints.prefix();
//        endpoints.setClientDetailsService();
//        endpoints.tokenEnhancer();
//        endpoints.tokenGranter();
//        endpoints.tokenStore();
//        endpoints.tokenServices();
//        endpoints.userApprovalHandler(null);
//        endpoints.userDetailsService(null);

    }

    /**
     * springSecurity 授权表达式，访问merryyou tokenkey时需要经过认证
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("isAuthenticated()");
//        security.accessDeniedHandler();
//        security.addTokenEndpointAuthenticationFilter();
//        security.authenticationEntryPoint();
//        security.checkTokenAccess();
//        security.passwordEncoder();
    }

    /**
     * JWTtokenStore
     * @return
     */
    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * 生成JTW token
     * @return
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("merryyou");
        return converter;
    }
}
