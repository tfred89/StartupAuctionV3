package com.stanfan.StartupAuctionV3.model;

public class Bid {
	
	private int bidId;
	private int bidLength;
	private int bidSalary;
	private int playerId;
	private String bidder;
	private String expires;
	
	public Bid(String bidder, int playerId, int bidLength, int bidSalary) {
		this.bidder = bidder;
		this.playerId = playerId;
		this.bidLength = bidLength;
		this.bidSalary = bidSalary;
	}
	public Bid() {
		
	}
	
	public int getContractVal() {
		return (bidLength * 5) + bidSalary;
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

	public String getBidder() {
		return bidder;
	}

	public void setBidder(String bidder) {
		this.bidder = bidder;
	}

	
	
}
