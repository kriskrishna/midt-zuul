package com.boeing.zuul.sample;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateCustomizer;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.implicit.ImplicitAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;

import com.boeing.zuul.sample.security.IdTokenToOAuthAdditionalInfo;
import com.boeing.zuul.sample.security.OAuthAdditionalInfo;
import com.boeing.zuul.sample.security.OpenIDTokenProvider;

@SpringBootApplication
@EnableZuulProxy
@EnableOAuth2Sso
public class MidtZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(MidtZuulApplication.class, args);
	}
	
	@Bean
    public UserInfoRestTemplateCustomizer userInfoOAuth2TemplateCustomizer() {
        return (oauth2RestTemplate) -> {
            oauth2RestTemplate.setAccessTokenProvider(accessTokenProviderChain());
        };
    }
	
	@Bean
    public AccessTokenProvider accessTokenProviderChain() {
        return new AccessTokenProviderChain(Arrays.<AccessTokenProvider> asList(
            new OpenIDTokenProvider(idTokenToOAuthConverter()),
            new AuthorizationCodeAccessTokenProvider(), new ImplicitAccessTokenProvider(),
            new ResourceOwnerPasswordAccessTokenProvider(), new ClientCredentialsAccessTokenProvider()));
    }
	
	@Bean
	public IdTokenToOAuthAdditionalInfo idTokenToOAuthConverter() {
		return new IdTokenToOAuthAdditionalInfo();
	}
}
