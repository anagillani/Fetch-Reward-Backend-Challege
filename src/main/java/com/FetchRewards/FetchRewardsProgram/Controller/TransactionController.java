package com.FetchRewards.FetchRewardsProgram.Controller;

import java.util.ArrayList;
import java.util.Optional;
import java.util.PriorityQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.FetchRewards.FetchRewardsProgram.DAO.TransactionRepository;
import com.FetchRewards.FetchRewardsProgram.DAO.UserRepository;
import com.FetchRewards.FetchRewardsProgram.Entities.Transaction;
import com.FetchRewards.FetchRewardsProgram.Entities.User;

@Controller
@RequestMapping(value="/transaction")
public class TransactionController {
	
	@Autowired
	TransactionRepository transaction_repository;
	
	@Autowired
	UserRepository user_repository;
	
	@GetMapping(value="/add")
	public String Transactions(Model model) {
		Transaction transaction = new Transaction();
		model.addAttribute("transaction",transaction);
		return "transaction";
	}
	
	@PostMapping(value="/save")
	public String save(Transaction transaction ,Model model) throws Exception {
		
		User newUser = new User();
		User existingUser=user_repository.findByName(transaction.getPayer());

		if(user_repository.findByName(transaction.getPayer())==null) {
			//if a new user tries to add negative points it will throw exception
			if(transaction.getPoints()<0) 
				throw new Exception("New user can not have negative balance");
			
			newUser.setName(transaction.getPayer());
			newUser.setBalance(transaction.getPoints());	
			user_repository.save(newUser);
			transaction.setUser(newUser);
			transaction_repository.save(transaction);	
				
			
		}
			
		else {
			if(existingUser.getBalance()+transaction.getPoints()<0)
				throw new Exception("This transaction will make the balance negative.");
			existingUser.setBalance(existingUser.getBalance()+transaction.getPoints());
			transaction.setUser(existingUser);
	    	transaction_repository.save(transaction);
		}

		return "redirect:/transaction/add";
		
		
	}
	
	@GetMapping(value="/spend")
	public String spend(Model model) {
		Integer points=0;
		model.addAttribute("points", points);
		return "spendForm";
	}
	
	@GetMapping(value="/spendResult")
	public String spendResult(Integer points,Model model) {
		System.out.println(points.intValue());
		//The priority queue is used to store transaction in the timestamp order to always pick the oldest transaction
		PriorityQueue<Transaction> transactionPQ = new PriorityQueue<>();
		
		//This ArrayList will keep track pf the spenders 
		ArrayList<User> spenders = new ArrayList();
		
		//This object will be used to add up the points of spenders to keep track of how much each user spent.
		User spender = new User();
		
		//Copying the transactions from database to the PQ.
		Iterable<Transaction> all=transaction_repository.findAll();
		for(Transaction tran: all)
			transactionPQ.add(tran);
		int counter=0;
		
		while(counter!=points) {
			//This object will find the current transaction's user from the DB so we can modify it's balance
			Optional<User> currentTransactionUser =user_repository.findById(transactionPQ.peek().getUser().getUser_id());
				
			//Handling negative transaction
			if(transactionPQ.peek().getPoints()<0) {
				currentTransactionUser.get().setBalance(currentTransactionUser.get().getBalance()-transactionPQ.peek().getPoints());
				currentTransactionUser.get().setSpent(currentTransactionUser.get().getSpent()+transactionPQ.peek().getPoints());
				counter+=transactionPQ.peek().getPoints();
				transactionPQ.poll();

			}
				
			//For transaction points which are greater then the spending points
			else if((points-counter)<=transactionPQ.peek().getPoints()) {
					
				if(transactionPQ.peek().getPoints()>currentTransactionUser.get().getBalance())
					
				{
					counter+=currentTransactionUser.get().getBalance();
					currentTransactionUser.get().setBalance(currentTransactionUser.get().getBalance()-counter);	
					currentTransactionUser.get().setSpent(currentTransactionUser.get().getSpent()+(points-counter));

					user_repository.save(currentTransactionUser.get()); 
					
				}
				else 
				{
					currentTransactionUser.get().setBalance((currentTransactionUser.get().getBalance())-(points-counter));
					currentTransactionUser.get().setSpent(currentTransactionUser.get().getSpent()+(points-counter));
					user_repository.save(currentTransactionUser.get()); 
					counter=points;
				}
				
				//checking if the current spender has spent any points before
				if(!spenders.contains(currentTransactionUser.get()))
					spenders.add(currentTransactionUser.get());				
		
				transactionPQ.poll(); //removing the transaction to access the next one.
				}

			//For transaction points which are smaller then the spending points
			else				
				{
				if(transactionPQ.peek().getPoints()>currentTransactionUser.get().getBalance())
					{
					counter+=currentTransactionUser.get().getBalance();
					currentTransactionUser.get().setSpent(currentTransactionUser.get().getSpent()+currentTransactionUser.get().getBalance());
					currentTransactionUser.get().setBalance(0);	
					user_repository.save(currentTransactionUser.get()); 
					}
				else 
					{
					counter+=transactionPQ.peek().getPoints();
					currentTransactionUser.get().setBalance((currentTransactionUser.get().getBalance())-transactionPQ.peek().getPoints());
					currentTransactionUser.get().setSpent(currentTransactionUser.get().getSpent()+transactionPQ.peek().getPoints());
					user_repository.save(currentTransactionUser.get());
					}
				
				if(!spenders.contains(currentTransactionUser.get()))
					{
					spenders.add(currentTransactionUser.get());
					}
				
				else {
					spender.setUser_id(currentTransactionUser.get().getUser_id());
					spender.setName(currentTransactionUser.get().getName());
					spender.setSpent(spender.getSpent()+transactionPQ.peek().getPoints());
					spenders.set(spenders.indexOf(currentTransactionUser.get()), spender);
				}
				transactionPQ.poll();

			}
		}
		model.addAttribute("User",spenders);
		return "spend";
	}
}
