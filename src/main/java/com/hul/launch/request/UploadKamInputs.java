package com.hul.launch.request;

public class UploadKamInputs {
	private String Req_Id;
	private String Launch_Name;
	private String Launch_MOC;
	private String Account;
	private String Name;
	private String Changes_Requested;
	private String Kam_Remarks;
	private String Requested_Date;
	private String Action_To_Take;
	private String Tme_Remarks;

	public String getTme_Remarks() {
		return Tme_Remarks;
	}

	public void setTme_Remarks(String tme_Remarks) {
		Tme_Remarks = tme_Remarks;
	}

	public String getAction_To_Take() {
		return Action_To_Take;
	}

	public void setAction_To_Take(String action_To_Take) {
		Action_To_Take = action_To_Take;
	}

	public String getReq_Id() {
		return Req_Id;
	}

	public void setReq_Id(String req_Id) {
		Req_Id = req_Id;
	}

	public String getLaunch_Name() {
		return Launch_Name;
	}

	public void setLaunch_Name(String launch_Name) {
		Launch_Name = launch_Name;
	}

	public String getLaunch_MOC() {
		return Launch_MOC;
	}

	public void setLaunch_MOC(String launch_MOC) {
		Launch_MOC = launch_MOC;
	}

	public String getAccount() {
		return Account;
	}

	public void setAccount(String account) {
		Account = account;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getChanges_Requested() {
		return Changes_Requested;
	}

	public void setChanges_Requested(String changes_Requested) {
		Changes_Requested = changes_Requested;
	}

	public String getKam_Remarks() {
		return Kam_Remarks;
	}

	public void setKam_Remarks(String kam_Remarks) {
		Kam_Remarks = kam_Remarks;
	}

	public String getRequested_Date() {
		return Requested_Date;
	}

	public void setRequested_Date(String requested_Date) {
		Requested_Date = requested_Date;
	}

}