package com.stanfan.StartupAuctionV3.model.jdbc;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.stanfan.StartupAuctionV3.model.Bid;
import com.stanfan.StartupAuctionV3.model.BidDAO;



@Service
public class JDBCBidDAO implements BidDAO{
	
	private JdbcTemplate jdbcTemplate;

	public JDBCBidDAO(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void addBid(Bid legalBid) {
		String sqlAddBid = "INSERT INTO bidLedger (playerId, ownerId, bidLength, bidSalary)" +
							"VALUES (?, ?, ?, ?)";
		jdbcTemplate.update(sqlAddBid, legalBid.getPlayerId(), legalBid.getBidderId(), legalBid.getBidLength(), legalBid.getBidSalary());
	}

	
	
}
