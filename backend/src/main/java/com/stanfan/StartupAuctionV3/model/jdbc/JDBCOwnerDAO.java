package com.stanfan.StartupAuctionV3.model.jdbc;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	public Owner getOwnerInfoByName(String ownerName) {
		Owner o = new Owner();
		String sqlOwner = "SELECT ownerid, ownername, caproom, yearsleft, password_hash FROM owner WHERE ownerName = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sqlOwner, ownerName);
		while(result.next()) {
			o = mapRowsToOwner(result);
		}
		return o;
	}
	@Override
	public void updateInfoAfterWin(Bid bid) {
		int salary = bid.getBidSalary();
		int length = bid.getBidLength();
		String ownername = bid.getBidder();
		String sqlPostWin = "UPDATE owner SET caproom = (caproom - ?), yearsleft = (yearsleft - ?) WHERE ownername = ?";
		jdbcTemplate.update(sqlPostWin, salary, length, ownername);
	}
	@Override
	public List<Owner> getAllOwners(){
		List<Owner> ownerList = new ArrayList<>();
		Owner o = new Owner();
		String sqlOwners = "SELECT ownerid, ownername, password_hash, caproom, yearsleft FROM owner";
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
		o.setPassword(rs.getString("password_hash"));
		o.setCapRoom(rs.getInt("caproom"));
		o.setYearsLeft(rs.getInt("yearsleft"));
		o.setActivated(true);
		o.setAuthorities("USER");
		return o;
	}
	public List<String> addPass(int playerId, String owner){
		List<String> owners = new ArrayList<String>();
		String sql = "INSERT INTO passLedger (playerId, ownername) VALUES (?, ?)";
		jdbcTemplate.update(sql, playerId, owner);
		
		String sqlList = "SELECT ownername FROM passledger WHERE playerId = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sqlList, playerId);
		
		while(result.next()) {
			owners.add(result.getString("ownername"));
		}
		return owners;
	}
	public List<String> getPassesById(int playerId){
		List<String> owners = new ArrayList<String>();
		String sqlList = "SELECT ownername FROM passledger WHERE playerId = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sqlList, playerId);
		while(result.next()) {
			owners.add(result.getString("ownername"));
		}
		return owners;
	}
	
	@Override
	public List<Owner> findAll() {
		List<Owner> users = new ArrayList<>();
        String sql = "select ownerid, ownername, password_hash, caproom, yearsleft from owner";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()) {
            Owner user = mapRowsToOwner(results);
            users.add(user);
        }
        return users;
	}
	@Override
	public Owner findByUsername(String username) throws UsernameNotFoundException {
		for (Owner owner : this.findAll()) {
            if( owner.getOwnerName().toLowerCase().equals(username.toLowerCase())) {
                return owner;
            }
        }
        throw new UsernameNotFoundException("User " + username + " was not found.");
    }

	@Override
	public boolean create(String username, String password) {
		boolean userCreated = false;
       

        // create user
        String insertUser = "insert into owner (ownername,password_hash, caproom, yearsleft) values(?,?, 500, 80)";
        String password_hash = new BCryptPasswordEncoder().encode(password);

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        String id_column = "ownerid";
        userCreated = jdbcTemplate.update(con -> {
                    PreparedStatement ps = con.prepareStatement(insertUser, new String[]{id_column});
                    ps.setString(1, username);
                    ps.setString(2,password_hash);
                    return ps;
                }
                , keyHolder) == 1;
        int newUserId = (int) keyHolder.getKeys().get(id_column);

       
        return userCreated;
	}
}

