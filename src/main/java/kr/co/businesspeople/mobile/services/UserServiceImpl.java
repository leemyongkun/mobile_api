package kr.co.businesspeople.mobile.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kr.co.businesspeople.mobile.dao.UserMapper;
import kr.co.businesspeople.mobile.dto.User;

@Service
public class UserServiceImpl implements IUserService{

	@Autowired
	UserMapper userMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userMapper.readUser(username);
		System.out.println("user " + user.getName());
		System.out.println("user " + user.getUsername());
		System.out.println("user " + user.getPassword());
		
		user.setAuthorities(getAuthorities(username));
		return user;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities(String username) {
		 List<String> string_authorities = userMapper.readAuthority(username);
         List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
         for (String authority : string_authorities) {
        	 	System.out.println("AUTH > " + authority);
              authorities.add(new SimpleGrantedAuthority(authority));
         }
         return authorities;

	}

}
