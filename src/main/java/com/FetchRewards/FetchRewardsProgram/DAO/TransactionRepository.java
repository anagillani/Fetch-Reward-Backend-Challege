package com.FetchRewards.FetchRewardsProgram.DAO;

import org.springframework.data.repository.CrudRepository;

import com.FetchRewards.FetchRewardsProgram.Entities.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {

}
