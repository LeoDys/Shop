package com.leo.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;

import com.leo.tool.DataSourceUtils;

public class ActiveDao {

	public int active(String activeCode) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update user set state = ? where code = ?";
		return qr.update(sql, 1, activeCode);
	}

}
