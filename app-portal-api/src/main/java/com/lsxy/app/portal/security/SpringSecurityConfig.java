package com.lsxy.app.portal.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;

/**
 * Created by Tandy on 2016/6/7.
 */
@EnableWebSecurity
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    private static final Log logger = LogFactory.getLog(SpringSecurityConfig.class);

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        if(logger.isDebugEnabled()){
            logger.debug("初始化Spring Security安全框架");
        }


        http.authorizeRequests()
                .antMatchers("/*").permitAll()
                .antMatchers("/rest/**").access("hasRole('ROLE_TENANT_USER')")
                .and().httpBasic()
                .and().csrf().disable()
        ;

        http.addFilter(headerAuthenticationFilter());
    }

    @Bean
    public TokenPreAuthenticationFilter headerAuthenticationFilter() throws Exception {
        return new TokenPreAuthenticationFilter(authenticationManager());
    }


    private AuthenticationProvider preAuthenticationProvider() {
        PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
//        provider.setPreAuthenticatedUserDetailsService(new KanBanAuthenticationUserDetailsService());
        return provider;
    }

    /**
     * 配置加密机制,以及加密盐值
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        ReflectionSaltSource rss = new ReflectionSaltSource();
//        rss.setUserPropertyToUse("username");
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setSaltSource(rss);
//
//        provider.setUserDetailsService(userDetailsService);
//
//        provider.setPasswordEncoder(getPasswordEncode());
//
//        provider.setHideUserNotFoundExceptions(false);
//
//        auth.authenticationProvider(provider);
        auth.inMemoryAuthentication().withUser("user001").password("123").roles("TENANT_USER");

//        super.configure(auth);

    }

}


