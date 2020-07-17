package com.stanfan.StartupAuctionV3.model;

import org.springframework.stereotype.Component;

@Component
public interface LotDAO {

	public void lotBid(Lot lot);
	public void clearLot(int lotId);
	public Bid getBidByLotId(int lotId);
	

}
