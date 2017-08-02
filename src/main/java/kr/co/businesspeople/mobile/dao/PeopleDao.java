package kr.co.businesspeople.mobile.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PeopleDao {
	public String getPeopleMail(@Param("cdPeople") String cdPeople);
}
