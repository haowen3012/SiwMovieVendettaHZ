package it.uniroma3.siw.hz.authentication;


import it.uniroma3.siw.hz.repository.CredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;


import static it.uniroma3.siw.hz.model.Credentials.ADMIN_ROLE;

@Configuration
@EnableWebSecurity
public  class AuthConfiguration {


	@Autowired
	DataSource dataSource;



	@Autowired
	private CredentialsRepository credentialsRepository;  // questo lo toglieremo alla fine







	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.jdbcAuthentication()
				.dataSource(dataSource)
				.authoritiesByUsernameQuery("SELECT user_name, role from credentials WHERE user_name=?")
				.usersByUsernameQuery("SELECT user_name, password, 1 as enabled FROM credentials WHERE user_name=?");
	}

/*
   @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/resources/**");
    }*/


	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}


	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
		return authenticationConfiguration.getAuthenticationManager();
	}



	@Bean
	protected SecurityFilterChain configure(final HttpSecurity httpSecurity) throws Exception{

		httpSecurity
				.csrf().and().cors().disable()
				.authorizeHttpRequests()
				.requestMatchers("/**","/ws/**").permitAll()
				.requestMatchers("/oauth2/**").authenticated()
				.requestMatchers(HttpMethod.GET,"/","/index","/user/register").permitAll()
				.requestMatchers(HttpMethod.POST,"/user/register").permitAll()
				.requestMatchers(HttpMethod.GET,"/admin/**").hasAnyAuthority(ADMIN_ROLE)
				.requestMatchers(HttpMethod.POST,"/admin/**").hasAnyAuthority(ADMIN_ROLE)
				.anyRequest().authenticated()
				.and().formLogin()
				.loginPage("/login")
				.permitAll()
				.defaultSuccessUrl("/successful")
				.failureUrl("/login?error=true")
				.and().logout()
				.invalidateHttpSession(true)
				.clearAuthentication(true).permitAll()
				.and()
				.oauth2Login()
				.loginPage("/login");




		return httpSecurity.build();
	}



}