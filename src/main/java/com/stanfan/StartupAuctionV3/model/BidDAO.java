package com.stanfan.StartupAuctionV3.model;

import org.springframework.stereotype.Component;

@Component
public interface BidDAO {
	
	public void addBid(Bid legalBid);

}
