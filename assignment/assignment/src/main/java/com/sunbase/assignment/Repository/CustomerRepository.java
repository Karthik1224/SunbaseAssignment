package com.sunbase.assignment.Repository;




import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sunbase.assignment.modal.Customer;

import jakarta.transaction.Transactional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,String>{

	@Query(value = "select firstName, lastName, address, city, state, email,phone from sunbase.customers", nativeQuery = true)
	public Page<String> fetchAllCustomers(Pageable pageable);
	
	@Transactional
	@Modifying
	public void deleteByPhone(String phone);
	
	public Customer findByPhone(String phone);
	
}
