package com.FetchRewards.FetchRewardsProgram.Entities;


import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Transactions")
public class Transaction implements Comparable<Transaction>{
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private long transaction_id;
	
	@Column(name="points")
	private int points;
	
	@Column(name="timestamp")
	private Timestamp timestamp;
	
	@Column(name="payer")
	private String payer;
	
	@ManyToOne(cascade= {CascadeType.REFRESH, CascadeType.PERSIST}, fetch= FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;

	public Transaction() {
		
	}
	
	public Transaction(long transaction_id, int points, Timestamp timestamp, String payer) {
		super();
		this.transaction_id = transaction_id;
		this.points = points;
		this.timestamp = timestamp;
		this.payer = payer;
	}

	public long getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(long transaction_id) {
		this.transaction_id = transaction_id;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp=Timestamp.valueOf(timestamp);
	}

	public String getPayer() {
		return payer;
	}

	public void setPayer(String payer) {
		this.payer = payer;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int compareTo(Transaction o) {
		return this.getTimestamp().compareTo(o.getTimestamp());
	}
	
	
	
	
}
