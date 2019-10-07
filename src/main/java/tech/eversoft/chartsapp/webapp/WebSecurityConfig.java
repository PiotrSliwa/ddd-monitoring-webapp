package tech.eversoft.chartsapp.webapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/presentation/dayByDay").hasRole("DAY_BY_DAY_VIEWER")
                .antMatchers("/presentation/singleDay").hasRole("SINGLE_DAY_VIEWER")
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            .logout()
                .permitAll()
                .and().cors().and().csrf().disable();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        var userA =
             User.withDefaultPasswordEncoder()
                .username("userA")
                .password("password")
                .roles("SINGLE_DAY_VIEWER")
                .build();

        var userB =
                User.withDefaultPasswordEncoder()
                        .username("userB")
                        .password("password")
                        .roles("DAY_BY_DAY_VIEWER")
                        .build();

        var userC =
                User.withDefaultPasswordEncoder()
                        .username("userC")
                        .password("password")
                        .roles("SINGLE_DAY_VIEWER", "DAY_BY_DAY_VIEWER")
                        .build();

        return new InMemoryUserDetailsManager(userA, userB, userC);
    }
}