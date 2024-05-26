package pl.hk.food.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/img/**", "/js/**").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/registration").permitAll()
                .antMatchers("/success").permitAll()
                .antMatchers("/loginSuccess").permitAll()
                .antMatchers("/dish/menu").permitAll()
                .antMatchers("/addRestaurant").permitAll()
                .antMatchers("/contact/**").permitAll()
                .antMatchers("/cart").authenticated()
                .antMatchers("/order/add").permitAll()
                .antMatchers("/restaurant/list").permitAll()
                .antMatchers("/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("username")
                .successHandler(new SavedRequestAwareAuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                        Authentication authentication) throws IOException, ServletException {
                        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                        String username = userDetails.getUsername();
                        System.out.println("The user " + username + " has logged in.");

                        // Przekierowanie z parametrem
                        response.sendRedirect("/login?loginSuccess=true");
                    }
                })
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/403")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .logoutSuccessUrl("/");
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/h2-console/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
