package kr.co.businesspeople.mobile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@PropertySources({
	@PropertySource("classpath:config/properties/dbconnection/DBConnectConfig.xml") 
})
public class ApiApplication{
	
	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	} 
}
