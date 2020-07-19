package com.stanfan.StartupAuctionV3.model.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.stanfan.StartupAuctionV3.model.Bid;
import com.stanfan.StartupAuctionV3.model.Owner;
import com.stanfan.StartupAuctionV3.model.OwnerDAO;



@Service
public class JDBCOwnerDAO implements OwnerDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCOwnerDAO(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	@Override
	public Owner getOwnerInfoById(int ownerId) {
		Owner o = new Owner();
		String sqlOwner = "SELECT ownerid, ownername, caproom, yearsleft FROM owner WHERE ownerid = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sqlOwner, ownerId);
		while(result.next()) {
			o = mapRowsToOwner(result);
		}
		return o;
	}
	@Override
	public void updateInfoAfterWin(Bid bid) {
		int salary = bid.getBidSalary();
		int length = bid.getBidLength();
		int ownerId = bid.getBidderId();
		String sqlPostWin = "UPDATE owner SET caproom = (caproom - ?), yearsleft = (yearsleft - ?) WHERE ownerId = ?";
		jdbcTemplate.update(sqlPostWin, salary, length, ownerId);
	}
	@Override
	public List<Owner> getAllOwners(){
		List<Owner> ownerList = new ArrayList<>();
		Owner o = new Owner();
		String sqlOwners = "SELECT ownerid, ownername, caproom, yearsleft FROM owner";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlOwners);
		while(results.next()) {
			o = mapRowsToOwner(results);
			ownerList.add(o);
		}
		return ownerList;
	}
	
	public Owner mapRowsToOwner(SqlRowSet rs) {
		Owner o = new Owner();
		o.setOwnerId(rs.getInt("ownerid"));
		o.setOwnerName(rs.getString("ownername"));
		o.setCapRoom(rs.getInt("caproom"));
		o.setYearsLeft(rs.getInt("yearsleft"));
		return o;
	}
}

