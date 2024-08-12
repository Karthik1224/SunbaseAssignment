package com.sunbase.assignment.RemoteApiResponse;

import java.util.ArrayList;

public class RemoteResponse {
	public ArrayList<Result> results;
	public Info info;
	
	public RemoteResponse() {
		super();
	}

	public RemoteResponse(ArrayList<Result> results, Info info) {
		super();
		this.results = results;
		this.info = info;
	}

	public ArrayList<Result> getResults() {
		return results;
	}

	public void setResults(ArrayList<Result> results) {
		this.results = results;
	}

	public Info getInfo() {
		return info;
	}

	public void setInfo(Info info) {
		this.info = info;
	}
	
	
}