package kr.co.businesspeople.mobile.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.businesspeople.mobile.dto.User;

@Mapper
public interface UserMapper {
	public User readUser(@Param("username") String username);
	public List<String> readAuthority(@Param("username")String username);
}
