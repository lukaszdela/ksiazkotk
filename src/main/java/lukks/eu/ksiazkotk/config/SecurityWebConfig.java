package lukks.eu.ksiazkotk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
                .antMatchers("/book/**")
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


    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin")
                .roles("ADMIN", "USER")
                .build();

        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager(admin);

        UserDetails user = User.withDefaultPasswordEncoder()
                .username("jkowalski")
                .password("jkowalski")
                .roles("USER")
                .build();

        inMemoryUserDetailsManager.createUser(user);

        return inMemoryUserDetailsManager;
    }

}
