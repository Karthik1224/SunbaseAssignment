package com.sunbase.assignment.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sunbase.assignment.modal.LoginDetails;

@Repository
public interface LoginDetailsRepository extends JpaRepository<LoginDetails, Integer>{

	public Optional<LoginDetails> findByEmail(String email);
}
