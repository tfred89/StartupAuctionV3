package com.stanfan.StartupAuctionV3.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.stanfan.StartupAuctionV3.model.Bid;
import com.stanfan.StartupAuctionV3.model.BidDAO;
import com.stanfan.StartupAuctionV3.model.Lot;
import com.stanfan.StartupAuctionV3.model.LotDAO;
import com.stanfan.StartupAuctionV3.model.Owner;
import com.stanfan.StartupAuctionV3.model.OwnerDAO;
import com.stanfan.StartupAuctionV3.model.Player;
import com.stanfan.StartupAuctionV3.model.PlayerDAO;


@CrossOrigin(origins = "http://localhost:8081")
@RestController
public class AuctionController {
	private PlayerDAO playerDAO;
	private OwnerDAO ownerDAO;
	private BidDAO bidDAO;
	private LotDAO lotDAO;
	
	public AuctionController(PlayerDAO playerDAO, OwnerDAO ownerDAO, BidDAO bidDAO, LotDAO lotDAO) {
		this.playerDAO = playerDAO;
		this.ownerDAO = ownerDAO;
		this.bidDAO = bidDAO;
		this.lotDAO = lotDAO;
	}

	@RequestMapping(path = "/api/players/{playerId}", method = RequestMethod.GET)
	public Player getPlayerById(@PathVariable int playerId) {
		Player player = playerDAO.getPlayerById(playerId);
		return player;
	}


	@RequestMapping(path = "/api/team/{ownerId}", method = RequestMethod.GET)
	public List<Player> getTeam(@PathVariable int ownerId) {
		List<Player> onTeam = playerDAO.getAllPlayersOnTeam(ownerId);
		return onTeam;
	}
	//add bid to bid list
	//@CrossOrigin(origins = "http://localhost:8081")
	@RequestMapping(path = "/api/bid", method = RequestMethod.POST)
	public Bid addBid(@RequestBody Bid bid) {
		System.out.println(bid.getExpires());
		return bidDAO.addBid(bid);
	}
	
	@RequestMapping(path = "/api/bid/{bidId}", method = RequestMethod.GET)
	public Bid getBid(@PathVariable int bidId) {
		return bidDAO.getBidById(bidId);
	}
	
	//add owner to player when lot is won
	@RequestMapping(path = "/api/win/{playerid}", method = RequestMethod.PUT)
	public void winAuction(@RequestBody @PathVariable int playerid, Owner winningOwner) {
		playerDAO.addOwnerToPlayer(playerid, winningOwner.getOwnerId());
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(path = "/api/nominate", method = RequestMethod.PUT)
	public void tempOwnerForNomination(@RequestBody Player player) {
		playerDAO.addOwnerToPlayer(player.getPlayerId(), player.getOwnerId());
	}
	
	@RequestMapping(path = "/api/auction/{playerid}", method = RequestMethod.GET)
	public Player getPlayerAuctionInfo(@PathVariable int playerid) {
		Player auctionPlayer = new Player();
		
		
		return auctionPlayer;
	}
	// get list of unowned players based off position
	//@CrossOrigin(origins = "http://localhost:8081")
	@RequestMapping(path = "/api/position/{position}", method = RequestMethod.GET)
	public List<Player> getAvailablePlayersByPosition(@PathVariable String position){
		
		return playerDAO.getAvailablePlayersAtPosition(position);
	}
	

	@RequestMapping(value = "/api/players", method = RequestMethod.GET)
	public List<Player> getAllPlayers(){
		System.out.println("this worked.");
		return playerDAO.getAllPlayers();
	}

	@RequestMapping(value = "/api/lot/{lotId}", method = RequestMethod.GET)
	public Bid getBidInfoForLotDisplay(@PathVariable int lotId) {
		Bid b = new Bid();
		b = lotDAO.getBidByLotId(lotId);
		return b;
	}
	
	@CrossOrigin(origins = "http://localhost:8081")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(path = "/api/lot", method = RequestMethod.PUT)
	public void updateLotWithBid(@RequestBody Lot lot) {
		Lot l = new Lot();
		l.setBidId(lot.getBidId());
		l.setLotId(lot.getLotId());
		System.out.println(l.getLotId());
		System.out.println(l.getBidId());
		lotDAO.lotBid(l);
	}
	
}
