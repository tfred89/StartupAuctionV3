package com.stanfan.StartupAuctionV3.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.stanfan.StartupAuctionV3.model.EspnPlayer;
import com.stanfan.StartupAuctionV3.model.Player;
import com.stanfan.StartupAuctionV3.model.PlayerDAO;
import com.stanfan.StartupAuctionV3.model.jdbc.JDBCPlayerDAO;


@Component
public class InventoryManager {

	private final String url = "http://fantasy.espn.com/apis/v3/games/ffl/seasons/2020/players?scoringPeriodId=0&view=players_wl";
	private PlayerDAO playerDAO;
	RestTemplate restTemplate = new RestTemplate();
	
	
	public InventoryManager(DataSource dataSource){
		playerDAO = new JDBCPlayerDAO(dataSource);
	}
	
	@PostConstruct
	public void buildInventory() {
	
	
	EspnPlayer[] allEspnPlayers = null;
	try {
		allEspnPlayers = getAllPlayers();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	List<Player> auctionInventory = makePlayerInventory(allEspnPlayers);
	
	}
	public EspnPlayer[] getAllPlayers() throws Exception{
		
		   ResponseEntity<EspnPlayer[]> response = restTemplate.getForEntity(url, EspnPlayer[].class);
		   EspnPlayer[] playerInventory = response.getBody();
		   return playerInventory;  
		}
	
	public List<Player> makePlayerInventory(EspnPlayer[] espnPlayers) {
		List<Player> ourPlayers = new ArrayList<Player>();
		for (EspnPlayer player : espnPlayers) {
			if (player.getDefaultPositionId() < 5) {
				String firstName = player.getFirstName();
				String lastName = player.getLastName();
				int espnId = player.getId();
				String position = posToString(player.getDefaultPositionId());
				Player thisPlayer = new Player(espnId, firstName, lastName, position);
				if(!playerDAO.playerAlreadyListed(thisPlayer.getEspnId())) {
					thisPlayer = playerDAO.insertPlayer(thisPlayer);
					System.out.println("made a player..." + thisPlayer.getFirstName() + " " + thisPlayer.getLastName() + " " + thisPlayer.getPosition());
					ourPlayers.add(thisPlayer);
				}
				
			}
		}
		System.out.println("done building inventory.");
		return ourPlayers;
	}

	public String posToString(int posId) {
		if (posId == 1) {
			return "QB";
		}
		if (posId == 2) {
			return "RB";
		}
		if (posId == 3) {
			return "WR";
		}
		if (posId == 4) {
			return "TE";
		} else {
			return "NA";
		}

	}
	
}
