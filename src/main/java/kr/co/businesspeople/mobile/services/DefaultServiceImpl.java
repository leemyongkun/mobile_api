package kr.co.businesspeople.mobile.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.businesspeople.mobile.dao.PeopleDao;

@Service
public class DefaultServiceImpl implements IDefaultService {
	private static final Logger logger = Logger.getLogger(DefaultServiceImpl.class);
	
	@Autowired
	private PeopleDao peopleDao;
	
	@Override
	@Transactional
	public String getPeopleMail(String cdPeople) {
		//PeopleDao peopleDao = sqlSessionTemplate.getMapper(PeopleDao.class);
		String mail = peopleDao.getPeopleMail(cdPeople);
		logger.error(mail);
		
		return mail;
	}

}
