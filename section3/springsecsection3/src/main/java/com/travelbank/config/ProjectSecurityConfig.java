package com.travelbank.config;

<<<<<<< HEAD
=======
import static org.springframework.security.config.Customizer.withDefaults;

>>>>>>> feature-enhancements
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
<<<<<<< HEAD
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
=======
>>>>>>> feature-enhancements
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

<<<<<<< HEAD
import static org.springframework.security.config.Customizer.withDefaults;
=======
>>>>>>> feature-enhancements
@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        /*http.authorizeHttpRequests((requests) -> requests.anyRequest().permitAll());*/
        /*http.authorizeHttpRequests((requests) -> requests.anyRequest().denyAll());*/
        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards").authenticated()
                .requestMatchers("/notices", "/contact", "/error").permitAll());
//        http.formLogin(flc->flc.disable());
//        http.httpBasic(hbc->hbc.disable());
        http.formLogin(withDefaults());
        http.httpBasic(withDefaults());
        return http.build();
    }
<<<<<<< HEAD

  @Bean
  public UserDetailsService userDetailsService() {
      UserDetails user = User.withUsername("user").password("{noop}travelbank@54321").authorities("read").build();
      UserDetails admin = User.withUsername("admin").password("{bcrypt}$2a$12$9gEsKxWChX2Kv66yy5zVo.5vmw7ks/IuLwNei3mx1oc1Y3AHSRCQ.").authorities("admin").build();
return new InMemoryUserDetailsManager(user, admin);
}
  
 
  
  
  @Bean
  public PasswordEncoder passwordEncoder() {
      return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Bean
  public CompromisedPasswordChecker compromisedPasswordChecker() {
      return new HaveIBeenPwnedRestApiPasswordChecker();
  }
=======
    
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("user").password("{noop}travelbank@54321").authorities("read").build();
        UserDetails admin = User.withUsername("admin").password("{bcrypt}$2a$12$9gEsKxWChX2Kv66yy5zVo.5vmw7ks/IuLwNei3mx1oc1Y3AHSRCQ.").authorities("admin").build();
return new InMemoryUserDetailsManager(user, admin);
}
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }
>>>>>>> feature-enhancements
}
