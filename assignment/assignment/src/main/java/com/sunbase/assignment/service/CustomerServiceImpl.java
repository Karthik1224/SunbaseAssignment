package com.sunbase.assignment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sunbase.assignment.RemoteApiResponse.RemoteResponse;
import com.sunbase.assignment.RemoteApiResponse.Result;
import com.sunbase.assignment.Repository.CustomerRepository;
import com.sunbase.assignment.dto.CustomerReqDto;
import com.sunbase.assignment.dto.FetchAllReqDto;
import com.sunbase.assignment.dto.FetchAllRespDto;
import com.sunbase.assignment.dto.ResponseDto;
import com.sunbase.assignment.dto.UpdateReqDto;
import com.sunbase.assignment.modal.Customer;

@Service
public class CustomerServiceImpl implements CustomerService {

	
	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
	@Autowired
	private CustomerRepository customerRepo;

	@Autowired
	private RestTemplate restTemplate;

	//adding customer to the database
	@Override
	public ResponseDto add(CustomerReqDto customerReqDto) {

		logger.info("Inside CustomerServiceImpl : add");
		try {

			if (customerReqDto == null)
				return new ResponseDto("Required Customer Details", "Failed");
			UUID uuid = UUID.randomUUID();
			String id = uuid.toString();
			Customer customer = new Customer(id, customerReqDto.getFirstName(), customerReqDto.getLastName(),
					customerReqDto.getStreet(), customerReqDto.getAddress(), customerReqDto.getCity(),
					customerReqDto.getState(), customerReqDto.getEmail(), customerReqDto.getPhone());
			customerRepo.save(customer);
			return new ResponseDto("Customer Added Successfully", "Success");
		} catch (Exception e) {
			return new ResponseDto(e.getMessage(), "Exception");
		}
	}

	//fetch the customer based on user id if exists else some message
	@Override
	public ResponseDto fetch(String id) {
		logger.info("Inside CustomerServiceImpl : fetch");
		try {
			Customer customer = customerRepo.findById(id).orElse(null);
			if (customer == null) {
				return new ResponseDto("Customer does not exist", "Failed");
			}
			return new ResponseDto(customer, "Success");

		} catch (Exception e) {
			return new ResponseDto(e.getMessage(), "Exception");
		}

	}

	//Here we update the user data , if it exists in database
	@Override
	public ResponseDto update(UpdateReqDto updateReqDto) {
		logger.info("Inside CustomerServiceImpl : update");
		try {

			Customer customer = customerRepo.findByPhone(updateReqDto.getPhone());
			if (customer == null) {
				return new ResponseDto("Customer does not exist", "Failed");
			}
			customer.setFirstName(updateReqDto.getFirstName());
			customer.setLastName(updateReqDto.getLastName());
			customer.setStreet(updateReqDto.getStreet());
			customer.setAddress(updateReqDto.getAddress());
			customer.setCity(updateReqDto.getCity());
			customer.setState(updateReqDto.getState());
			customer.setEmail(updateReqDto.getEmail());
			customer.setPhone(updateReqDto.getPhone());
			customerRepo.save(customer);
			return new ResponseDto("Customer updated successfully", "Success");

		} catch (Exception e) {
			return new ResponseDto(e.getMessage(), "Exception");
		}
	}

	//Deleting the user based on the phone number
	@Override
	public ResponseDto delete(String phone) {
		logger.info("Inside CustomerServiceImpl : delete");
		try {
			customerRepo.deleteByPhone(phone);
			return new ResponseDto("Customer deleted successfully", "Success");

		} catch (Exception e) {
			return new ResponseDto(e.getMessage(), "Exception");
		}
	}

	//Here we will fetch all customers from the database in sorted order
	@Override
	public ResponseDto fetchAll(FetchAllReqDto fetchAllReqDto) {
		logger.info("Inside CustomerServiceImpl : fetchAll");
		try {
			Pageable pageable = PageRequest.of(fetchAllReqDto.getPageNo()-1, fetchAllReqDto.getPageSize(),
					Sort.by(fetchAllReqDto.getSortBy()).ascending());
			Page<String> pagedResult = customerRepo.fetchAllCustomers(pageable);
			List<FetchAllRespDto> listOfCustomers = new ArrayList<>();
			for (Object Obj : pagedResult.getContent()) {
				Object[] DetailsObject = (Object[]) Obj;
				String[] getValues = new String[DetailsObject.length];
				for (int i = 0; i < DetailsObject.length; i++) {
					if (DetailsObject[i] != null) {
						getValues[i] = String.valueOf(DetailsObject[i]);
					}
				}

				FetchAllRespDto fetchRespDto = new FetchAllRespDto(getValues[0], getValues[1], getValues[2],
						getValues[3], getValues[4], getValues[5], getValues[6]);
				listOfCustomers.add(fetchRespDto);
			}
			return new ResponseDto(listOfCustomers, "Success");

		} catch (Exception e) {
			return new ResponseDto(e.getMessage(), "Exception");
		}
	}

	//Here we collect random user data from remote and store it in database
	@Override
	public ResponseDto addCustomerFromRemote() {
		logger.info("Inside CustomerServiceImpl : addCustomerFromRemote");
		try {

			RemoteResponse remoteResponse = restTemplate.getForObject("https://randomuser.me/api",
					RemoteResponse.class);
			if (remoteResponse == null) {
				return new ResponseDto("Failed to consume data from remote api", "Failed");
			}
			return convertRemoteResponseToCustomer(remoteResponse);

		} catch (Exception e) {
			return new ResponseDto(e.getMessage(), "Exception");
		}
	}

	
	//Here we will convert the remote response to respective customer object and store in database
	@Override
	public ResponseDto convertRemoteResponseToCustomer(RemoteResponse remoteResponse) {
		logger.info("Inside CustomerServiceImpl : convertRemoteResponseToCustomer");
		Result result = remoteResponse.getResults().get(0);
		Customer customer = customerRepo.findById(result.getLogin().getUuid()).orElse(null);
		if (customer == null) {
			Customer customerObj = new Customer();
			customerObj.setId(result.getLogin().getUuid());
			customerObj.setFirstName(result.getName().getFirst());
			customerObj.setLastName(result.getName().getLast());
			customerObj.setStreet(String.valueOf(result.getLocation().getStreet().getNumber()));
			customerObj.setAddress(result.getLocation().getStreet().getName());
			customerObj.setCity(result.getLocation().getCity());
			customerObj.setState(result.getLocation().getState());
			customerObj.setEmail(result.getEmail());
			customerObj.setPhone(result.getPhone());
			customerRepo.save(customerObj);
			return new ResponseDto("Consumed data from remote api and saved into db", "Success");
			
		} else {

			customer.setFirstName(result.getName().getFirst());
			customer.setLastName(result.getName().getLast());
			customer.setStreet(String.valueOf(result.getLocation().getStreet().getNumber()));
			customer.setAddress(result.getLocation().getStreet().getName());
			customer.setCity(result.getLocation().getCity());
			customer.setState(result.getLocation().getState());
			customer.setEmail(result.getEmail());
			customer.setPhone(result.getPhone());
			customerRepo.save(customer);
			return new ResponseDto("Consumed data from remote api and updated in db", "Success");
		}

	}

}
