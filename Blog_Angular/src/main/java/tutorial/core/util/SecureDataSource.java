package tutorial.core.util;


import java.util.logging.Level;
import java.util.logging.Logger;

import org.h2.Driver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class SecureDataSource extends DriverManagerDataSource{

	private static Logger  logger=Logger.getLogger(SecureDataSource.class.getName());
	private String username;
	private String password;
	private String url;
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
		logger.log(Level.INFO, "Passowrd is correct");
		
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
	

}
