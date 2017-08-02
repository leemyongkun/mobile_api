package kr.co.businesspeople.mobile.controller;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web	.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.businesspeople.mobile.dao.PeopleDao;
import kr.co.businesspeople.mobile.dao.UserMapper;
import kr.co.businesspeople.mobile.dto.NameVO;
import kr.co.businesspeople.mobile.services.IDefaultService;

@RestController
@RequestMapping("/test")
public class HelloController {
	private static final Logger logger = Logger.getLogger(HelloController.class);

	@Autowired
	private Environment env;

	@Autowired
	IDefaultService defaultServiceImpl;

	@Autowired
	private PeopleDao peopleDao;

	@RequestMapping("/test")
	public String test() {
		System.out.println("TEST");
		return "TEST";
	}
	
	//@PreAuthorize("hasAnyRole('ADMIN')")
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping("/admin")
	public String admin() {
		System.out.println("ADMIN");
		
		return "ADMIN";
	}
	
	//@PreAuthorize("hasRole('USER') AND hasRole('ADMIN') ")
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	//@PreAuthorize("hasRole('ADMIN')")
	//@Secured("USER")
	@PreAuthorize("hasAuthority('USER')")
	@RequestMapping("/user")
	public String user() {
		return "USER";
	}
	
	//@Secured("USER")
	@PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
	@RequestMapping("/user/2")
	public String user2() {
		return "USER 2";
	}
		
	
	@RequestMapping("/")
	public Object index() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		return map;
	}
	
	//http://localhost/PP160825A00002
	@PreAuthorize("hasAuthority('USER') OR hasAuthority('ADMIN') ")
	@GetMapping("/{name}")
	public Object getName(@PathVariable String name) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("name", defaultServiceImpl.getPeopleMail(name));

		return map;
	}

	
	@RequestMapping("/{name}/{message}")
	public NameVO getName2(@PathVariable String name, @PathVariable String message) {
		NameVO home = new NameVO();
		home.setName(name);
		home.setMessage(message);

		return home;
	}

	
	@Autowired
	UserMapper userMapper;
	
	//http://localhost/auth/PP160825A00002
	@RequestMapping("/auth/{username}")
	public Object getAuth(@PathVariable String username) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("name", defaultServiceImpl.getPeopleMail(username)); //PP161114A00001
		return map;
	}
	
	
	@RequestMapping("/favicon.ico")
	public Object favicon(@PathVariable String username) {
		return null;
	}
}
