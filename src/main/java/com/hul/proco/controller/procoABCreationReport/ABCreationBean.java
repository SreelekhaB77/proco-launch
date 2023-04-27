package com.hul.proco.controller.procoABCreationReport;

import org.springframework.web.multipart.MultipartFile;

import com.hul.proco.excelreader.exom.annotation.Column;

public class ABCreationBean {

	private MultipartFile file;
	
	@Column(name = "ACTIVITY_CODE")
	private String activity_code;
	
	@Column(name = "STATUS_IN_CENTRAL_UNIFY")
	private String status_in_central_unify;
	
	@Column(name = "TME_SUBMIT_DATE")
	private String tme_submit_date;
	
	@Column(name = "AUDITOR_SUBMIT_DATE")
	private String auditor_submit_date;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getActivity_code() {
		return activity_code;
	}

	public void setActivity_code(String activity_code) {
		this.activity_code = activity_code;
	}

	public String getStatus_in_central_unify() {
		return status_in_central_unify;
	}

	public void setStatus_in_central_unify(String status_in_central_unify) {
		this.status_in_central_unify = status_in_central_unify;
	}

	public String getTme_submit_date() {
		return tme_submit_date;
	}

	public void setTme_submit_date(String tme_submit_date) {
		this.tme_submit_date = tme_submit_date;
	}

	public String getAuditor_submit_date() {
		return auditor_submit_date;
	}

	public void setAuditor_submit_date(String auditor_submit_date) {
		this.auditor_submit_date = auditor_submit_date;
	}
	
	@Override
	public String toString() {
		return "ABCreationBean [file=" + file + ", activity_code=" + activity_code + ", status_in_central_unify=" + status_in_central_unify + ","
				+ " tme_submit_date="+ tme_submit_date + ", auditor_submit_date=" + auditor_submit_date + "]";
	}
}
