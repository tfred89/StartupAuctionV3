package com.stanfan.StartupAuctionV3.model.jdbc;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.stanfan.StartupAuctionV3.model.Bid;
import com.stanfan.StartupAuctionV3.model.BidDAO;
import com.stanfan.StartupAuctionV3.model.Player;



@Service
public class JDBCBidDAO implements BidDAO{
	
	private JdbcTemplate jdbcTemplate;

	public JDBCBidDAO(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public Bid getBidById(int bidId) {
		Bid newBid = new Bid();
		String sqlAddBid = "SELECT bidid, playerId, ownerId, bidLength, bidSalary, expires FROM bidLedger WHERE bidid = ?";
		SqlRowSet bidResult = jdbcTemplate.queryForRowSet(sqlAddBid, bidId);
		while(bidResult.next()) {
		newBid = mapRowToBid(bidResult);
		}
		return newBid;
	}
	
	@Override
	public Bid addBid(Bid newBid) {
		
		String sqlAddBid = "INSERT INTO bidLedger (bidId, playerId, ownername, bidLength, bidSalary, expires)" +
							"VALUES (?, ?, ?, ?, ?, ?)";
		newBid.setBidId(getNextBidId());
		jdbcTemplate.update(sqlAddBid, newBid.getBidId(), newBid.getPlayerId(), newBid.getBidder(), 
				newBid.getBidLength(), newBid.getBidSalary(), newBid.getExpires());		
		return newBid;
	}
	@Override
	public Bid getHighestBid(int playerId) {
		Bid newBid = new Bid();
		String sql = "SELECT bidid, bidlength, bidsalary, playerId, ownername, expires "
				+ "FROM bidledger WHERE playerId = ? ORDER BY bidid DESC LIMIT 1";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, playerId);
		while (result.next()) {
			newBid = mapRowToBid(result);
		}
		return newBid;
	}
	
	private int getNextBidId() {
		SqlRowSet nextId = jdbcTemplate.queryForRowSet("SELECT nextval('bidledger_bidid_seq')");
		if(nextId.next()) {
			return nextId.getInt(1);
		} else {
			throw new RuntimeException("Error.  Unable to save your reservation. Please try again later.");
		}
	}
	
	
	public Bid mapRowToBid(SqlRowSet rs) {
		Bid p = new Bid();
		p.setBidId(rs.getInt("bidid"));
		p.setBidLength(rs.getInt("bidLength"));
		p.setBidSalary(rs.getInt("bidSalary"));
		p.setPlayerId(rs.getInt("playerId"));
		p.setBidder(rs.getString("ownername"));
		p.setExpires(rs.getString("expires"));
		return p;
		
	}
	
	
}
