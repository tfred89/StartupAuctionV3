package com.stanfan.StartupAuctionV3.model;

public class Bid {
	
	private int bidLength;
	private int bidSalary;
	private int playerId;
	private int bidderId;
	
	public Bid(int bidderId, int playerId, int bidLength, int bidSalary) {
		this.bidderId = bidderId;
		this.playerId = playerId;
		this.bidLength = bidLength;
		this.bidSalary = bidSalary;
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
