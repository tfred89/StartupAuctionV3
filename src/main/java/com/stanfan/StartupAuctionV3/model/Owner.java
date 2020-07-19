package com.stanfan.StartupAuctionV3.model;

public class Owner {

	private int ownerId;
	private String ownerName;
	private int yearsLeft;
	private int capRoom;
	
	
	public Owner() {
		this.yearsLeft = 80;
		this.capRoom = 500;
	}
	
	
	public int getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public int getYearsLeft() {
		return yearsLeft;
	}
	public void setYearsLeft(int yearsLeft) {
		this.yearsLeft = yearsLeft;
	}
	public int getCapRoom() {
		return capRoom;
	}
	public void setCapRoom(int capRoom) {
		this.capRoom = capRoom;
	}
}
