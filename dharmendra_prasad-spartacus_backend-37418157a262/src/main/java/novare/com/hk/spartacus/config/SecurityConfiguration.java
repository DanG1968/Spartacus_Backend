package novare.com.hk.spartacus.config;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import novare.com.hk.spartacus.service.wireless.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	DataSource datasource;

	@Autowired
	PasswordEncoder passwordEncoder;


	@Bean
	public PasswordEncoder passwordEncoder() {
		return new DoNothingPasswordEncoder();
	}


	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
		// auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN");
		
		auth.authenticationProvider(authenticationProvider());
//		auth.jdbcAuthentication().dataSource(datasource).usersByUsernameQuery("select email as username, password, true"
//				+ " from user where email=?")
//				.authoritiesByUsernameQuery("select email as username, role"
//						+ " from user where email=?");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// insecure page and resource acess.
		http.authorizeRequests().antMatchers("/login", "/**/images/*", "/**/*.css").permitAll();

		// role based authorization
		http.authorizeRequests()
				// admin urls to be accessed only by admins
				.antMatchers("/admin/**").hasRole("ADMIN")
				.and().exceptionHandling().authenticationEntryPoint(new MyAuthenticationEntryPoint()) //comment out this line to see form
				.and()
	            .formLogin()
	            .loginProcessingUrl("/api/authentication")
	            .successHandler(ajaxAuthenticationSuccessHandler())
	            .failureHandler(ajaxAuthenticationFailureHandler())
	            .usernameParameter("j_username")
	            .passwordParameter("j_password")
	            .and().authorizeRequests().antMatchers("/api/authenticate").permitAll()
	            .antMatchers("/api/**").authenticated();

		// Ensures that any request to our application requires the user to be
		// authenticated
		http.authorizeRequests().anyRequest().authenticated();

		// csrf disabled
		http.csrf().disable();

		// basic auth enabled
		http.httpBasic();

		http.cors();
	}
	
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
	    DaoAuthenticationProvider authProvider
	      = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService(userDetailsService);
	    authProvider.setPasswordEncoder(passwordEncoder());
	    return authProvider;
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		List<String> allowedMethods = new ArrayList<String>();
		allowedMethods.add("PUT");
		allowedMethods.add("GET");
		allowedMethods.add("POST");
		allowedMethods.add("DELETE");
		allowedMethods.add("HEAD");
		allowedMethods.add("OPTIONS");
		config.setAllowedMethods(allowedMethods);
		source.registerCorsConfiguration("/**", new CorsConfiguration(config).applyPermitDefaultValues());
		return source;
	}
	
	 @Bean
	    public AjaxAuthenticationSuccessHandler ajaxAuthenticationSuccessHandler() {
	        return new AjaxAuthenticationSuccessHandler();
	    }

	    @Bean
	    public AjaxAuthenticationFailureHandler ajaxAuthenticationFailureHandler() {
	        return new AjaxAuthenticationFailureHandler();
	    }
}
