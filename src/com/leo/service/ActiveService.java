package com.leo.service;

import java.sql.SQLException;

import com.leo.dao.ActiveDao;

public class ActiveService {

	public boolean active(String activeCode) {
		ActiveDao ad = new ActiveDao();
		try {
			return ad.active(activeCode) > 0 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
