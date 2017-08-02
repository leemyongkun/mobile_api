package kr.co.businesspeople.mobile.services;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {
	 Collection<GrantedAuthority> getAuthorities(String username);
}
