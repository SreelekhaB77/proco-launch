package com.hul.launch.request;

import java.util.List;

/**
 * 
 * @author anshuman.shrivastava
 *
 **/
public class AccountRequestList {
	public List<AccountRequest> getListOfAccounts() {
		return listOfAccounts;
	}

	public void setListOfAccounts(List<AccountRequest> listOfAccounts) {
		this.listOfAccounts = listOfAccounts;
	}

	private List<AccountRequest> listOfAccounts;

}