package com.FetchRewards.FetchRewardsProgram.Entities;

import java.util.List;
import java.util.PriorityQueue;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Users")
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long user_id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="balance")
	private int balance;
	
	@Column(name="spent")
	private int spent;
	
	@OneToMany(mappedBy="user")
	private List<Transaction> transaction;
	
    private PriorityQueue<Transaction> transactionsQueue;


	public User() {
		
	}
	
	public User(long user_id, String name, int balance) {
		super();
		this.user_id = user_id;
		this.name = name;
		this.balance = balance;
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int l) {
		this.balance = l;
	}
	

	public int getSpent() {
		return spent;
	}

	public void setSpent(int spent) {
		this.spent = spent;
	}

	public List<Transaction> getTransaction() {
		return transaction;
	}

	public void setTransaction(List<Transaction> transaction) {
		this.transaction = transaction;
	}


	
	
}
