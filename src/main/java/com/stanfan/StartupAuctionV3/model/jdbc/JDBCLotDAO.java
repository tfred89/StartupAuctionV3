package com.stanfan.StartupAuctionV3.model.jdbc;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.stanfan.StartupAuctionV3.model.Bid;
import com.stanfan.StartupAuctionV3.model.Lot;
import com.stanfan.StartupAuctionV3.model.LotDAO;

@Service
public class JDBCLotDAO implements LotDAO {

	private JdbcTemplate jdbcTemplate;
	
	public JDBCLotDAO(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	

	
	@Override
	public void lotBid(Lot lot) {
		String sqlLotBid = "UPDATE lot SET bidid = ? WHERE lotid = ?";
		jdbcTemplate.update(sqlLotBid, lot.getBidId(), lot.getLotId());
		
	}
	
	@Override
	public void clearLot(int lotId) {
		String sqlClearBid = "UPDATE lot SET bidid = NULL WHERE lotId = ?";
		jdbcTemplate.update(sqlClearBid, lotId);
		
	}
	
	
	@Override
	public Bid getBidByLotId(int lotId) {
		Bid bid = new Bid();
		String sqlBidJoin = "SELECT lot.bidid, playerid, ownerid, bidlength, bidsalary, expires FROM " +
							"bidledger JOIN Lot ON lot.bidId = bidledger.bidid " +
							"WHERE lotId = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sqlBidJoin, lotId);
		while(result.next()) {
			bid = mapRowToBid(result);
		}
		return bid;
	}
	
	public Bid mapRowToBid(SqlRowSet rs) {
		Bid b = new Bid();
		b.setBidId(rs.getInt("bidid"));
		b.setPlayerId(rs.getInt("playerid"));
		b.setBidderId(rs.getInt("ownerid"));
		b.setBidLength(rs.getInt("bidlength"));
		b.setBidSalary(rs.getInt("bidSalary"));
		b.setExpires(rs.getString("expires"));
		return b;
	}
	
}
