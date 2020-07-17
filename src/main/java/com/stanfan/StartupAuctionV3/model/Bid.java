package com.stanfan.StartupAuctionV3.model;

public class Bid {
	
	private int bidId;
	private int bidLength;
	private int bidSalary;
	private int playerId;
	private int bidderId;
	private String expires;
	
	public Bid(int bidderId, int playerId, int bidLength, int bidSalary) {
		this.bidderId = bidderId;
		this.playerId = playerId;
		this.bidLength = bidLength;
		this.bidSalary = bidSalary;
	}
	public Bid() {
		
	}
	

	public String getExpires() {
		return expires;
	}
	public void setExpires(String expires) {
		this.expires = expires;
	}
	public int getBidId() {
		return bidId;
	}
	public void setBidId(int bidId) {
		this.bidId= bidId;
	}

	
	public int getBidLength() {
		return bidLength;
	}

	public void setBidLength(int bidLength) {
		this.bidLength = bidLength;
	}

	public int getBidSalary() {
		return bidSalary;
	}

	public void setBidSalary(int bidSalary) {
		this.bidSalary = bidSalary;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public int getBidderId() {
		return bidderId;
	}

	public void setBidderId(int bidderId) {
		this.bidderId = bidderId;
	}

	
	
}
