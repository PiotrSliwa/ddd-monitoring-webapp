package tech.eversoft.chartsapp.webapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
//                .antMatchers("/presentation/dayByDay").hasRole("DAY_BY_DAY_VIEWER")
//                .antMatchers("/presentation/singleDay").hasRole("SINGLE_DAY_VIEWER")
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
        return new InMemoryUserDetailsManager(
                User.withDefaultPasswordEncoder()
                        .username("userXA")
                        .password("pass")
                        .authorities("SINGLE_DAY_VIEWER", "ACCESS_COMPANY_X")
                        .build(),
                User.withDefaultPasswordEncoder()
                        .username("userXB")
                        .password("pass")
                        .authorities("DAY_BY_DAY_VIEWER", "ACCESS_COMPANY_X")
                        .build(),
                User.withDefaultPasswordEncoder()
                        .username("userXC")
                        .password("pass")
                        .authorities("SINGLE_DAY_VIEWER", "DAY_BY_DAY_VIEWER", "ACCESS_COMPANY_X")
                        .build(),
                User.withDefaultPasswordEncoder()
                        .username("userYA")
                        .password("pass")
                        .authorities("SINGLE_DAY_VIEWER", "ACCESS_COMPANY_Y")
                        .build(),
                User.withDefaultPasswordEncoder()
                        .username("userYB")
                        .password("pass")
                        .authorities("DAY_BY_DAY_VIEWER", "ACCESS_COMPANY_Y")
                        .build(),
                User.withDefaultPasswordEncoder()
                        .username("userYC")
                        .password("pass")
                        .authorities("SINGLE_DAY_VIEWER", "DAY_BY_DAY_VIEWER", "ACCESS_COMPANY_Y")
                        .build());
    }
}