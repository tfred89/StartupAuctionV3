package com.stanfan.StartupAuctionV3.model;

import org.springframework.stereotype.Component;

@Component
public interface BidDAO {
	
	public Bid addBid(Bid legalBid);
	public Bid getBidById(int bidId);
}
