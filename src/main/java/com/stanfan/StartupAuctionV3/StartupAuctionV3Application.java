package com.stanfan.StartupAuctionV3;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;




@SpringBootApplication
public class StartupAuctionV3Application {
	
	
	public static void main(String[] args) {
		
		SpringApplication.run(StartupAuctionV3Application.class, args);
		
		//app.build();
	}


	public StartupAuctionV3Application(){
		// Create an instance of the SampleDAO
		//playerDAO = new JDBCPlayerDAO(dataSource);
	}




}
