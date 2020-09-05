package com.app.utb;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class MySecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	
	
	@Autowired
	private DataSource dataSource;
	
	/*
	 * @Override protected void configure(AuthenticationManagerBuilder auth) throws
	 * Exception { auth.inMemoryAuthentication() .withUser("user") .password("pass")
	 * .roles("USER") .and() .withUser("admin") .password("admin") .roles("ADMIN");
	 * }
	 */
	
	
	
	//By default the spring boot uses default schema for H2 database, if nothing is configured.
	/*
	 * @Override protected void configure(AuthenticationManagerBuilder auth) throws
	 * Exception {
	 * 
	 * auth.jdbcAuthentication() .dataSource(dataSource) .withDefaultSchema()
	 * .withUser( User.withUsername("yasar") .password("pass") .roles("USER") )
	 * .withUser( User.withUsername("iftekhar") .password("pass") .roles("ADMIN") );
	 * }
	 */
	
	//https://docs.spring.io/spring-security/site/docs/current/reference/html5/#user-schema
	//The above url is used to get schema for a particular database.
	// The two methods usersByUsernameQuery() and authoritiesByUsernameQuery()
	//are used to get the data from predefined tables in the database. 
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.jdbcAuthentication()
			.dataSource(dataSource)
			.usersByUsernameQuery("select username, password, enabled"
					+ " from users "
					+ "where username=?")
			.authoritiesByUsernameQuery("select username, authority "
					+ " from authorities "
					+ "where username=?");
	}
	
	
	 @Override
	protected void configure(HttpSecurity http) throws Exception {
		 http.authorizeRequests()
		 	.antMatchers("/admin").hasRole("ADMIN");
		 
		 http.authorizeRequests()
		 	.antMatchers("/user").hasAnyRole("ADMIN", "USER");
		 
		 http.authorizeRequests()
		 	.antMatchers("/").permitAll()
		 	.and()
		 	.formLogin();
	}
	
	
	
	
	
	@Bean
	public PasswordEncoder myPasswordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

}
