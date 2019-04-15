package rc.bootsecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by lenovo on 15/04/2019.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication().withUser("admin").password(passwordEncoder()
                            .encode("admin")).roles("ADMIN")
                .and()
                .withUser("profile").password(passwordEncoder().
                    encode("profile")).roles("PROFILE")
                .and()
                .withUser("MANAGEMENT").password(passwordEncoder()
                    .encode("MANAGEMENT")).roles("MANAGEMENT");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/index.html").permitAll()
                .antMatchers("/profile/index").authenticated()
                .antMatchers("/admin/index").hasRole("ADMIN")
                .antMatchers("/management/index").hasAnyRole("ADMIN","MANAGEMENT")
                .antMatchers("/api/public/**").hasRole("ADMIN")
                .and()
                .httpBasic();
    }

    @Bean
    PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
}
