package com.stanfan.StartupAuctionV3.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


public class Owner {

	private int ownerId;
	private String ownerName;
	private String password;
	private boolean activated;
	private Set<Authority> authorities = new HashSet<>();
	private int yearsLeft;
	private int capRoom;
	
	
	public Owner(int ownerId, String ownerName, String password, String authorities) {
		this.ownerId = ownerId;
		this.yearsLeft = 80;
		this.capRoom = 500;
	    this.ownerName = ownerName;
	    this.password = password;
	    this.activated = true;
	}
	
	public Owner() {
		this.activated = true;
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

	   public String getPassword() {
	      return password;
	   }

	   public void setPassword(String password) {
	      this.password = password;
	   }

	   public boolean isActivated() {
	      return activated;
	   }

	   public void setActivated(boolean activated) {
	      this.activated = activated;
	   }

	   public Set<Authority> getAuthorities() {
	      return authorities;
	   }

	   public void setAuthorities(Set<Authority> authorities) {
	      this.authorities = authorities;
	   }

	   public void setAuthorities(String authorities) {
	      String[] roles = authorities.split(",");
	      for(String role : roles) {
	         this.authorities.add(new Authority("ROLE_" + role));
	      }
	   }

	   @Override
	   public boolean equals(Object o) {
	      if (this == o) return true;
	      if (o == null || getClass() != o.getClass()) return false;
	      Owner user = (Owner) o;
	      return ownerId == user.ownerId &&
	              activated == user.activated &&
	              Objects.equals(ownerName, user.ownerName) &&
	              Objects.equals(password, user.password) &&
	              Objects.equals(authorities, user.authorities);
	   }

	   @Override
	   public int hashCode() {
	      return Objects.hash(ownerId, ownerName, password, activated, authorities);
	   }

	   @Override
	   public String toString() {
	      return "User{" +
	              "id=" + ownerId +
	              ", username='" + ownerName + '\'' +
	              ", activated=" + activated +
	              ", authorities=" + authorities +
	              '}';
	   }	
}
