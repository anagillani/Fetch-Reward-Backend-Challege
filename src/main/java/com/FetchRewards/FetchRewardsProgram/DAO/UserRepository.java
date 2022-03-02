package com.FetchRewards.FetchRewardsProgram.DAO;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.FetchRewards.FetchRewardsProgram.Entities.User;

public interface UserRepository extends CrudRepository<User, Long> {
	
	public User findByName(String name);
	

}
