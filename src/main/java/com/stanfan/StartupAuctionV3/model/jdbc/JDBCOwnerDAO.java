package com.stanfan.StartupAuctionV3.model.jdbc;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.stanfan.StartupAuctionV3.model.OwnerDAO;



@Service
public class JDBCOwnerDAO implements OwnerDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCOwnerDAO(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
}

