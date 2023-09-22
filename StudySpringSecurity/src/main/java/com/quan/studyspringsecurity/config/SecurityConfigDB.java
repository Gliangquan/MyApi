package com.quan.studyspringsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServlet;

/**
 * 数据库配置
 */
@Configuration
public class SecurityConfigDB extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(password());
    }

    @Bean
    PasswordEncoder password() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.formLogin() // 自定义自己编写的登录页面
                .loginPage("/login.html") // 登录页面设置
                .loginProcessingUrl("/user/login") // 登录访问路径
                .defaultSuccessUrl("/test/index").permitAll() //登录成功以后，跳转的路径
                .and().authorizeRequests()
                .antMatchers("/", "test/hello", "/user/login").permitAll() // 设置那些路由不需要认证
                .anyRequest().authenticated()
                .and().csrf().disable(); // 关闭csrf防护
    }
}
