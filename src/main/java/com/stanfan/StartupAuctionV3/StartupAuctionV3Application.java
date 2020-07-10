package com.stanfan.StartupAuctionV3;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.stanfan.StartupAuctionV3.model.jdbc.JDBCPlayerDAO;
import com.stanfan.StartupAuctionV3.model.EspnPlayer;
import com.stanfan.StartupAuctionV3.model.Player;
import com.stanfan.StartupAuctionV3.model.PlayerDAO;


//@ComponentScan(basePackages = {"com.stanfan.StartupAuctionV3.controller"})
@SpringBootApplication
public class StartupAuctionV3Application {
	RestTemplate restTemplate = new RestTemplate();
	private PlayerDAO playerDAO;
	private final String url = "http://fantasy.espn.com/apis/v3/games/ffl/seasons/2020/players?scoringPeriodId=0&view=players_wl";

	public static void main(String[] args) {
		StartupAuctionV3Application app = new StartupAuctionV3Application();
		//app.build();
		SpringApplication.run(StartupAuctionV3Application.class, args);
	}


public StartupAuctionV3Application(){
	
//	BasicDataSource dataSource = new BasicDataSource();
//	dataSource.setUrl("jdbc:postgresql://localhost:5432/startuptest");
//	dataSource.setUsername("postgres");

	
	// Create an instance of the SampleDAO
	//playerDAO = new JDBCPlayerDAO(dataSource);

}
public void build() {
	
	EspnPlayer[] allEspnPlayers = null;
	try {
		allEspnPlayers = getAllPlayers();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	List<Player> auctionInventory = makePlayerInventory(allEspnPlayers);
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
			}
			ourPlayers.add(thisPlayer);
			
		}
	}
	return ourPlayers;
}

public EspnPlayer[] getAllPlayers() throws Exception{
	
	   ResponseEntity<EspnPlayer[]> response = restTemplate.getForEntity(url, EspnPlayer[].class);
	   EspnPlayer[] playerInventory = response.getBody();
	   return playerInventory;  
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
