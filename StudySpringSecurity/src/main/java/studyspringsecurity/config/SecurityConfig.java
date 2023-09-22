package studyspringsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// @Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // 密码加密
        String password = passwordEncoder.encode("321");

        auth.inMemoryAuthentication().withUser("321")
                .password(password)
                .roles("admin");
    }
    @Bean
    PasswordEncoder password(){
        return new BCryptPasswordEncoder();
    }
}
