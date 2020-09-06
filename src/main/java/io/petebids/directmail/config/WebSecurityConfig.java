package io.petebids.directmail.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().fullyAuthenticated().and().httpBasic();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("Pete").password("{noop}password").roles("USER");
	}

//	  @Override
//	  public void configure(AuthenticationManagerBuilder auth) throws Exception {
//	    auth
//	      .ldapAuthentication()
//	        .userDnPatterns("uid={0},ou=people")
//	        .groupSearchBase("ou=groups")
//	        .contextSource()
//	          .url("ldap://localhost:389/dc=my-company,dc=com")
//	          .and()
//	        .passwordCompare()
//	          .passwordEncoder(new BCryptPasswordEncoder())
//          .passwordAttribute("userPassword");
//	  }

}
