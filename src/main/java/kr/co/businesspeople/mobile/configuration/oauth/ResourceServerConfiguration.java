package kr.co.businesspeople.mobile.configuration.oauth;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
//https://github.com/spring-projects/spring-security-oauth/blob/master/tests/annotation/jdbc/src/main/java/demo/Application.java
//https://github.com/spring-projects/spring-security-oauth/tree/master/samples/oauth2 : Sample
//http://blog.naver.com/wizardkyn/220650609325 : 참고사이트
//http://tugs.tistory.com/122 : proxyTargetClass

//curl -X POST -u "bpClient:bpPassword" -d "grant_type=password&username=leemyongkun@naver.com&password=f8810357b43260c862055b8f93e7232925d746fe69a8e78311aa67c96724733c" http://localhost/oauth/token
//curl -i -D "grant_type=refresh_token&client_id=bpClient&refresh_token=1d6c9a15-facb-4f7c-844e-78d20d682d33" http://localhost/oauth/token
//curl -i -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer 09f44982-94cc-4be0-b3b8-65578ffe0248" -X GET http://localhost/test/admin

@Configuration
@EnableAutoConfiguration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled=true)
public class ResourceServerConfiguration {

	@Autowired
	private DataSource log4jdbcProxyDataSource;

	/*
	 * API 서버
	 */
	@Configuration
	@EnableResourceServer
	protected static class ResourceServer extends ResourceServerConfigurerAdapter 
	{
/*
		@Autowired
		private TokenStore tokenStore;

		@Override
		public void configure(ResourceServerSecurityConfigurer resources)
				throws Exception {
			System.out.println("tokenStore");
			resources.tokenStore(tokenStore);
		}
*/
		@Override
		public void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests()
			.anyRequest().authenticated()
			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
			 // @formatter:off
			// @formatter:on	
		}
		
	    @Bean
	    public RemoteTokenServices remoteTokenServices() {
	    	System.out.println("remoteTokenServices");
	        final RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
	        // 경로 끝에 check_token/ 로 마무리 하면 안된다. 반드시 check_token 로 마무리 할 것
	        remoteTokenServices.setCheckTokenEndpointUrl("http://localhost:9900/oauth/check_token");
	        remoteTokenServices.setClientId("bpClient");
	        // 암호화 된게 저장되지만 암호화 된걸 secret에 넣으면 안된다. 500에러
	        //remoteTokenServices.setClientSecret("42ba60765d07876e8dcaf7727fb005d0e105955d0d7fc79513a1627b3faef5c4");
	        remoteTokenServices.setClientSecret("bpPassword");
	        return remoteTokenServices;
	    } 
	}
	 
	/*@Autowired
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		// @formatter:off
		//auth.jdbcAuthentication().dataSource(log4jdbcProxyDataSource).getUserDetailsService();//.withUser("kkuni").password("12345").roles("USER", "ACTUATOR");
		auth.jdbcAuthentication()
				.dataSource(log4jdbcProxyDataSource)
				.usersByUsernameQuery(getUserQuery())
				.authoritiesByUsernameQuery(getAuthoritiesQuery());
			// @formatter:on
	}

	 private String getUserQuery() {
        //return "SELECT username as principal, password as credentials, isEnabled FROM test_user WHERE username = ?";
		 return "SELECT "+ 
					"B.MAIL AS principal, "+
					"A.PWD AS credentials, "+
					"1 "+
				"FROM TBL_PEOPLE A JOIN TBL_PPMAIL B "+
				"ON A.CD_PEOPLE = B.CD_PEOPLE "+
				"WHERE B.MAIL = ? ";
    }*/
	 

	 
 /*   private String getAuthoritiesQuery() {
        return " SELECT username as principal, role FROM test_authority WHERE username = ? ";
    }*/

    
}