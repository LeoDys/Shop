package com.leo.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.leo.domain.User;
import com.leo.tool.DataSourceUtils;

public class UserDao {

	public int registe(User user) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?)";
		int update = qr.update(sql, user.getUid(), user.getUsername(), user.getPassword(), user.getName(),
				user.getEmail(), user.getTelephone(), user.getBirthday(), user.getSex(), user.getState(),
				user.getCode());
		return update;
	}

	/**
	 * 查询用户是否存在
	 * 
	 * @param username
	 * @return >0表示存在
	 * @throws SQLException
	 */
	public Long checkUsername(String username) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select count(*) from user where username = ?";
		Long getInt = (Long) qr.query(sql, new ScalarHandler(), username);
		return getInt;
	}

}
