package es.raulsanmartin.postit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http)
            throws Exception {
        /*
         * Any user can access the /login URL and any URL under /public.
         * Access to any other resource is premitted only for authenticated users.
         */
        http
            .authorizeRequests()
                .antMatchers("/login", "/register", "/assets/**", "/public/**").permitAll()
                .anyRequest().authenticated()
                .and()
            .rememberMe().rememberMeParameter("rememberme")
                .key("uniqueAndSecret")
                .tokenValiditySeconds(86400)
                .userDetailsService(userDetailsService)
                .and()
            .formLogin().loginPage("/login").permitAll()
                .and()
            .logout().permitAll();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

}