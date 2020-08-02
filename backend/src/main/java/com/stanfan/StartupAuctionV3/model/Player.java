package com.stanfan.StartupAuctionV3.model;

import java.util.ArrayList;
import java.util.List;



public class Player {

	private String firstName;
	private String lastName;
	private int salary;
	private int length;
	private String ownerName;
	private String position;
	private int ownerId;
	private int espnId;
	private int playerId;
	private int contractValue;
	
	public Player(int espnId, String firstName, String lastName, String position) {
		this.espnId = espnId;
		this.lastName = lastName;
		this.firstName = firstName;
		this.position = position;
		this.salary = 0;
		this.length = 0;
		this.contractValue = this.salary + (this.length * 5);
	}
	
	public Player() {
//		this.salary = 0;
//		this.length = 0;
//		this.contractValue = this.salary + (this.length * 5);
	}


	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public int getEspnId() {
		return espnId;
	}

	public void setEspnId(int espnId) {
		this.espnId = espnId;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public int getContractValue() {
		return contractValue;
	}

	public void setContractValue(int contractValue) {
		this.contractValue = contractValue;
	}
	
	
	//this is checked in the controller now.
//	public Player bid(Player bidPlayer, Bid thisBid, BidDAO bidDAO) {
//		int bidValue = thisBid.getBidSalary() + (thisBid.getBidLength() * 5);
//		if (thisBid.getBidSalary() < 1 || thisBid.getBidLength() < 1) {
//			return bidPlayer;
//		}
//		if (bidValue > bidPlayer.getContractValue()) {
//			bidPlayer.se(thisBid.getBidder());
//			bidPlayer.setSalary(thisBid.getBidSalary());
//			bidPlayer.setLength(thisBid.getBidLength());
//			bidPlayer.setContractValue(bidValue);
//			//need to pass in jdbc template to update the database
//			bidDAO.addBid(thisBid);
//			return bidPlayer;
//			
//		}
//		return bidPlayer;
//	}

	
}
