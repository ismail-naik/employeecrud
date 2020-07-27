package com.paypal.bfs.test.employeeserv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paypal.bfs.test.employeeserv.entity.IdempotencyEntity;

@Repository
public interface IdempotencyRepository extends JpaRepository<IdempotencyEntity, Long> {
	
	//public IdempotencyEntity findByidempotencyKey(Long idKey);

}
