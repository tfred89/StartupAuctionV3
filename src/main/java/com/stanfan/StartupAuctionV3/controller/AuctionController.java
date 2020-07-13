package com.stanfan.StartupAuctionV3.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.stanfan.StartupAuctionV3.model.Bid;
import com.stanfan.StartupAuctionV3.model.BidDAO;
import com.stanfan.StartupAuctionV3.model.Owner;
import com.stanfan.StartupAuctionV3.model.OwnerDAO;
import com.stanfan.StartupAuctionV3.model.Player;
import com.stanfan.StartupAuctionV3.model.PlayerDAO;



@RestController
public class AuctionController {
	private PlayerDAO playerDAO;
	private OwnerDAO ownerDAO;
	private BidDAO bidDAO;
	
	public AuctionController(PlayerDAO playerDAO, OwnerDAO ownerDAO, BidDAO bidDAO) {
		this.playerDAO = playerDAO;
		this.ownerDAO = ownerDAO;
		this.bidDAO = bidDAO;
	}
	
	//@Autowired
	//InventoryManager inventory = new InventoryManager();


	
	
	
	@RequestMapping(path = "/api/team/{ownerId}", method = RequestMethod.GET)
	public List<Player> getTeam(@PathVariable int ownerId) {
		List<Player> onTeam = playerDAO.getAllPlayersOnTeam(ownerId);
		return onTeam;
	}
	//add bid to bid list
	@CrossOrigin(origins = "http://localhost:8081")
	@RequestMapping(path = "/api/bid", method = RequestMethod.POST)
	public void addBid(@RequestBody Bid bid) {
		bidDAO.addBid(bid);
	}
	//add owner to player when lot is won
	@RequestMapping(path = "/api/win/{playerid}", method = RequestMethod.PUT)
	public void winAuction(@RequestBody @PathVariable int playerid, Owner winningOwner) {
		playerDAO.addOwnerToPlayer(playerid, winningOwner.getOwnerId());
	}
	
	@RequestMapping(path = "/api/auction/{playerid}", method = RequestMethod.GET)
	public Player getPlayerAuctionInfo(@PathVariable int playerid) {
		Player auctionPlayer = new Player();
		
		
		return auctionPlayer;
	}
	// get list of unowned players based off position
	@CrossOrigin(origins = "http://localhost:8081")
	@RequestMapping(path = "/api/players/{position}", method = RequestMethod.GET)
	public List<Player> getAvailablePlayersByPosition(@PathVariable String position){
		
		return playerDAO.getAvailablePlayersAtPosition(position);
	}
	

	@RequestMapping(value = "/api/players", method = RequestMethod.GET)
	public List<Player> getAllPlayers(){
		System.out.println("this worked.");
		return playerDAO.getAllPlayers();
	}
}
