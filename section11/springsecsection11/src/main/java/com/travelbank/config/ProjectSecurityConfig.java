package com.travelbank.config;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.travelbank.exceptionhandling.CustomAccessDeniedHandler;
import com.travelbank.exceptionhandling.CustomBasicAuthenticationEntryPoint;
import com.travelbank.filter.AuthoritiesLoggingAfterFilter;
import com.travelbank.filter.AuthoritiesLoggingAtFilter;
import com.travelbank.filter.CsrfCookieFilter;
import com.travelbank.filter.JWTTokenGeneratorFilter;
import com.travelbank.filter.JWTTokenValidatorFilter;
import com.travelbank.filter.RequestValidationBeforeFilter;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@Profile("!prod")
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        /*http.authorizeHttpRequests((requests) -> requests.anyRequest().permitAll());*/
        /*http.authorizeHttpRequests((requests) -> requests.anyRequest().denyAll());*/
    	 CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler = new CsrfTokenRequestAttributeHandler();
//         http.securityContext(contextConfig -> contextConfig.requireExplicitSave(false))
        //    .sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
           http.sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .cors(corsConfig -> corsConfig.configurationSource(new CorsConfigurationSource() {
           @Override
           public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
               CorsConfiguration config = new CorsConfiguration();
               config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
               config.setAllowedMethods(Collections.singletonList("*"));
               config.setAllowCredentials(true);
               config.setAllowedHeaders(Collections.singletonList("*"));
               config.setExposedHeaders(Arrays.asList("Authorization"));
               config.setMaxAge(3600L);
               return config;
           }
       }))
       .csrf(csrfConfig -> csrfConfig .csrfTokenRequestHandler(csrfTokenRequestAttributeHandler)
    		   .ignoringRequestMatchers( "/contact","/register","/apiLogin")
      .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
       .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
       .addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
       .addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
       .addFilterAt(new AuthoritiesLoggingAtFilter(), BasicAuthenticationFilter.class)
//       .sessionManagement(smc -> smc.invalidSessionUrl("/invalidSession").maximumSessions(1).maxSessionsPreventsLogin(true))
       .addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
       .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
        .requiresChannel(rcc -> rcc.anyRequest().requiresInsecure()) // Only HTTP
//        .csrf(csrfConfig -> csrfConfig.disable())
        .authorizeHttpRequests((requests) -> requests
//                .requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards","/user").authenticated()
//        		.requestMatchers("/myAccount").hasAuthority("VIEWACCOUNT")
//                .requestMatchers("/myBalance").hasAnyAuthority("VIEWBALANCE","VIEWACCOUNT")
//                .requestMatchers("/myLoans").hasAuthority("VIEWLOANS")
//                .requestMatchers("/myCards").hasAuthority("VIEWCARDS")
//                .requestMatchers("/user").authenticated()
//                .requestMatchers("/notices", "/contact", "/error","/register","/invalidSession").permitAll());
        		 .requestMatchers("/myAccount").hasRole("USER")
                 .requestMatchers("/myBalance").hasAnyRole("USER", "ADMIN")
                 .requestMatchers("/myLoans").hasRole("USER")
                 .requestMatchers("/myCards").hasRole("USER")
                 .requestMatchers("/user").authenticated()
                 .requestMatchers("/notices", "/contact", "/error","/register","/invalidSession","/apiLogin").permitAll());
//        http.formLogin(flc->flc.disable());
//        http.httpBasic(hbc->hbc.disable());
        http.formLogin(withDefaults());
        http.httpBasic(hbc -> hbc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
//        http.exceptionHandling(ehc -> ehc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
       http.exceptionHandling(ehc -> ehc.accessDeniedHandler(new CustomAccessDeniedHandler()));
        return http.build();
    }
    
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user = User.withUsername("user").password("{noop}travelbank@54321").authorities("read").build();
//        UserDetails admin = User.withUsername("admin").password("{bcrypt}$2a$12$9gEsKxWChX2Kv66yy5zVo.5vmw7ks/IuLwNei3mx1oc1Y3AHSRCQ.").authorities("admin").build();
//return new InMemoryUserDetailsManager(user, admin);
//}
    
    
//    @Bean
//    public UserDetailsService userDetailsService(DataSource dataSource) {
//        return new JdbcUserDetailsManager(dataSource);
//    }

    
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        TravelBankUsernamePwdAuthenticationProvider authenticationProvider =
                new TravelBankUsernamePwdAuthenticationProvider(userDetailsService, passwordEncoder);
        ProviderManager providerManager = new ProviderManager(authenticationProvider);
        providerManager.setEraseCredentialsAfterAuthentication(false);
        return  providerManager;
    }

}
