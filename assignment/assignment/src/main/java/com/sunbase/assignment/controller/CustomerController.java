package com.sunbase.assignment.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sunbase.assignment.dto.CustomerReqDto;
import com.sunbase.assignment.dto.FetchAllReqDto;
import com.sunbase.assignment.dto.UpdateReqDto;
import com.sunbase.assignment.service.CustomerService;



@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {


	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
	@Autowired
	private CustomerService customerService;
	
	@PostMapping("/add")
	public ResponseEntity<?> add(@RequestBody CustomerReqDto customerReqDto)
	{
		logger.info("Inside CustomerController : add");
		try {
			return new ResponseEntity(customerService.add(customerReqDto),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/get")
	public ResponseEntity<?> fetch(@RequestParam String id)
	{
		logger.info("Inside CustomerController : fetch");
		try {
			return new ResponseEntity(customerService.fetch(id),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/update")
	public ResponseEntity<?> update(@RequestBody UpdateReqDto updateReqDto)
	{
		logger.info("Inside CustomerController : update");
		try {
			return new ResponseEntity(customerService.update(updateReqDto),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<?> delete(@RequestParam String phone)
	{
		logger.info("Inside CustomerController : delete");
		try {
			return new ResponseEntity(customerService.delete(phone),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/fetchAll")
	public ResponseEntity<?> fetchAll(@RequestBody FetchAllReqDto fetchAllReqDto)
	{
		logger.info("Inside CustomerController : fetchAll");
		try {
			return new ResponseEntity(customerService.fetchAll(fetchAllReqDto),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/sync")
	public ResponseEntity<?> addCustomerFromRemote()
	{
		logger.info("Inside CustomerController : addCustomerFromRemote");
		try {
			return new ResponseEntity(customerService.addCustomerFromRemote(),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
}
