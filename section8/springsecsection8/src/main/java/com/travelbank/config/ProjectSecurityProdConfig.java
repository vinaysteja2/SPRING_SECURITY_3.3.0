package com.travelbank.config;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
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
import com.travelbank.filter.CsrfCookieFilter;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@Profile("prod")
public class ProjectSecurityProdConfig {

	 @Bean
	    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
	        /*http.authorizeHttpRequests((requests) -> requests.anyRequest().permitAll());*/
	        /*http.authorizeHttpRequests((requests) -> requests.anyRequest().denyAll());*/
	    	 CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler = new CsrfTokenRequestAttributeHandler();
	         http.securityContext(contextConfig -> contextConfig.requireExplicitSave(false))
	                 .sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
	       .cors(corsConfig -> corsConfig.configurationSource(new CorsConfigurationSource() {
	           @Override
	           public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
	               CorsConfiguration config = new CorsConfiguration();
	               config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
	               config.setAllowedMethods(Collections.singletonList("*"));
	               config.setAllowCredentials(true);
	               config.setAllowedHeaders(Collections.singletonList("*"));
	               config.setMaxAge(3600L);
	               return config;
	           }
	       }))
	       .csrf(csrfConfig -> csrfConfig .csrfTokenRequestHandler(csrfTokenRequestAttributeHandler)
	    		   .ignoringRequestMatchers( "/contact","/register")
	    		   .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
	       .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
//	       .sessionManagement(smc -> smc.invalidSessionUrl("/invalidSession").maximumSessions(1).maxSessionsPreventsLogin(true))
	        .requiresChannel(rcc -> rcc.anyRequest().requiresInsecure()) // Only HTTP
//	        .csrf(csrfConfig -> csrfConfig.disable())
	        .authorizeHttpRequests((requests) -> requests
	                .requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards").authenticated()
	                .requestMatchers("/notices", "/contact", "/error","/register","/invalidSession").permitAll());
//	        http.formLogin(flc->flc.disable());
//	        http.httpBasic(hbc->hbc.disable());
	        http.formLogin(withDefaults());
	        http.httpBasic(hbc -> hbc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
//	        http.exceptionHandling(ehc -> ehc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
	       http.exceptionHandling(ehc -> ehc.accessDeniedHandler(new CustomAccessDeniedHandler()));
	        return http.build();
	    }
	    
//	    @Bean
//	    public UserDetailsService userDetailsService() {
//	        UserDetails user = User.withUsername("user").password("{noop}travelbank@54321").authorities("read").build();
//	        UserDetails admin = User.withUsername("admin").password("{bcrypt}$2a$12$9gEsKxWChX2Kv66yy5zVo.5vmw7ks/IuLwNei3mx1oc1Y3AHSRCQ.").authorities("admin").build();
	//return new InMemoryUserDetailsManager(user, admin);
	//}
	    
	    
//	    @Bean
//	    public UserDetailsService userDetailsService(DataSource dataSource) {
//	        return new JdbcUserDetailsManager(dataSource);
//	    }

	    
	    
	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	    }

	    @Bean
	    public CompromisedPasswordChecker compromisedPasswordChecker() {
	        return new HaveIBeenPwnedRestApiPasswordChecker();
	    }
}