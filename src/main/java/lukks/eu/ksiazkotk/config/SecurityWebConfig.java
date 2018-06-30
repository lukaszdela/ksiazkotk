package lukks.eu.ksiazkotk.config;

import lukks.eu.ksiazkotk.service.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


@Configuration
@EnableWebSecurity
public class SecurityWebConfig  extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/")
                .authenticated()
                .antMatchers("/webjars/**")
                .permitAll()
                .antMatchers("/css/**")
                .permitAll()
                .antMatchers("/covers/**")
                .permitAll()
                .antMatchers("/avatars/**")
                .permitAll()
                .antMatchers("/images/**")
                .permitAll()
                .antMatchers("/addbook/**")
                .hasAnyRole("ADMIN", "USER")
                .antMatchers("/profile/**")
                .hasAnyRole("ADMIN", "USER")
                .antMatchers("/books/**")
                .hasAnyRole("ADMIN", "USER")
                .antMatchers("/user/**")
                .hasAnyRole("ADMIN", "USER")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

/*
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin")
                .roles("ADMIN", "USER")
                .build();

        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager(admin);

        PasswordEncoder passwordEncoder = passwordEncoder();
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("jkowalski")
                .password(passwordEncoder.encode("jkowalski"))
                .roles("USER")
                .build();

        inMemoryUserDetailsManager.createUser(user);

        UserDetails user2 = User.withDefaultPasswordEncoder()
                .username("mnowak")
                .password("mnowak")
                .roles("USER")
                .build();

        inMemoryUserDetailsManager.createUser(user2);

        return inMemoryUserDetailsManager;
    }*/

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Autowired
    private UserSecurityService userSecurityService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userSecurityService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        auth.authenticationProvider(daoAuthenticationProvider);
    }

}
