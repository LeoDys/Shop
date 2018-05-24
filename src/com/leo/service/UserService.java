package com.leo.service;

import java.sql.SQLException;

import com.leo.dao.UserDao;
import com.leo.domain.User;

public class UserService {
	public boolean register(User user) {
		UserDao userDao = new UserDao();
		int result = 0;
		try {
			result = userDao.registe(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result > 0 ? true : false;
	}
	
	public boolean checkUsername(String username) {
		UserDao u = new UserDao();
		Long is = 0L;
		try {
			is = u.checkUsername(username);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(is > 0) {
			return true;
		} else {
			return false;
		}
	
	}
}
