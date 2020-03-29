package com.CAT.BuffetAPI;

import java.sql.SQLException;
import javax.activation.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sun.istack.NotNull;

import oracle.jdbc.pool.OracleDataSource;

@Configuration
@ConfigurationProperties("oracle")
public class OracleConfigurations {
	@NotNull
	String username;
	
	@NotNull 
	String password;
	
	@NotNull
	String url;
	
	
	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}




	@Bean
    DataSource dataSource() throws SQLException {
		
		
        OracleDataSource dataSource = new OracleDataSource();
      
		dataSource.setUser(username);
		dataSource.setPassword(password);        
		dataSource.setURL(url);
        dataSource.setImplicitCachingEnabled(true);
        dataSource.setFastConnectionFailoverEnabled(true);
        return (DataSource) dataSource;
    }
}
