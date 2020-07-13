package com.stanfan.StartupAuctionV3;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.stanfan.StartupAuctionV3.model.jdbc.JDBCPlayerDAO;
import com.stanfan.StartupAuctionV3.service.InventoryManager;
import com.stanfan.StartupAuctionV3.model.EspnPlayer;
import com.stanfan.StartupAuctionV3.model.Player;
import com.stanfan.StartupAuctionV3.model.PlayerDAO;


//@ComponentScan(basePackages = {"com.stanfan.StartupAuctionV3.controller"})
@SpringBootApplication
public class StartupAuctionV3Application {
	
	
	public static void main(String[] args) {
		
		SpringApplication.run(StartupAuctionV3Application.class, args);
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/startuptest");
		dataSource.setUsername("student");
		
		
		InventoryManager inventory = new InventoryManager(dataSource);
		
		
		
		inventory.buildInventory();
		//app.build();
	}


	public StartupAuctionV3Application(){
		// Create an instance of the SampleDAO
		//playerDAO = new JDBCPlayerDAO(dataSource);
	}




}
