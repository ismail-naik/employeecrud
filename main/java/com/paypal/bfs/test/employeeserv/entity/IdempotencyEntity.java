package com.paypal.bfs.test.employeeserv.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "idempotency")
public class IdempotencyEntity {
	
	@Id
	Long idempotencyKey;
	
	public IdempotencyEntity() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the idempotencyKey
	 */
	public Long getIdempotencyKey() {
		return idempotencyKey;
	}

	/**
	 * @param idempotencyKey the idempotencyKey to set
	 */
	public void setIdempotencyKey(Long idempotencyKey) {
		this.idempotencyKey = idempotencyKey;
	}

	public IdempotencyEntity(Long idempotencyKey) {
		super();
		this.idempotencyKey = idempotencyKey;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idempotencyKey == null) ? 0 : idempotencyKey.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IdempotencyEntity other = (IdempotencyEntity) obj;
		if (idempotencyKey == null) {
			if (other.idempotencyKey != null)
				return false;
		} else if (!idempotencyKey.equals(other.idempotencyKey))
			return false;
		return true;
	}

	
	

}
