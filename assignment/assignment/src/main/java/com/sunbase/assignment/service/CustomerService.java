package com.sunbase.assignment.service;



import com.sunbase.assignment.RemoteApiResponse.RemoteResponse;
import com.sunbase.assignment.dto.CustomerReqDto;
import com.sunbase.assignment.dto.FetchAllReqDto;
import com.sunbase.assignment.dto.ResponseDto;
import com.sunbase.assignment.dto.UpdateReqDto;

public interface CustomerService {

	public ResponseDto add(CustomerReqDto customerReqDto);
	public ResponseDto fetch(String id);
	public ResponseDto update(UpdateReqDto updateReqDto);
	public ResponseDto delete(String phone);
	public ResponseDto fetchAll(FetchAllReqDto fetchAllReqDto);
	public ResponseDto addCustomerFromRemote();
	public ResponseDto convertRemoteResponseToCustomer(RemoteResponse remoteResponse);
}
