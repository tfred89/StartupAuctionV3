package com.stanfan.StartupAuctionV3.model;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public interface OwnerDAO {

	public Owner getOwnerInfoById(int ownerId);
	public void updateInfoAfterWin(Bid bid);
	public List<Owner> getAllOwners();
}
