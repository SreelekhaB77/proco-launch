package com.hul.proco.controller.procoLiveBudget;

import org.springframework.web.multipart.MultipartFile;

import com.hul.proco.excelreader.exom.annotation.Column;

public class BudgetHolderBean {
	private MultipartFile file;
	
	@Column(name="BUDGET_HOLDER")
	private String budget_holder;
	
	@Column(name="CATEGORY")
	private String category;
	
	@Column(name="PRODUCT")
	private String product;
	
	@Column(name="CUSTOMER")
	private String Customer;
	
	@Column(name="FUND_TYPE")
	private String fund_type;
	
	@Column(name="ORIGINAL_AMOUNT")
	private String original_amount;
	
	@Column(name="ADJUSTED_AMOUNT")
	private String adjusted_amount;
	
	@Column(name="REVISED_AMOUNT")
	private String revised_amount;
	
	@Column(name="UPDATE_AMOUNT")
	private String update_amount;
	
	@Column(name="TRANSFER_IN")
	private String transfer_in;
	
	@Column(name="TRANSFER_OUT")
	private String transfer_out;
	
	@Column(name="TRANSFER_PIPELINE")
	private String transfer_pipeline;
	
	@Column(name="TOTAL_AMOUNT")
	private String total_amount;
	
	@Column(name="PIPELINE_AMOUNT")
	private String pipeline_amount;
	
	@Column(name="COMMITMENT_AMOUNT")
	private String commitment_amount;
	
	@Column(name="REMAINING_AMOUNT")
	private String remaining_amount;
	
	@Column(name="ACTUALS")
	private String actuals;
	
	@Column(name="ADJUSTMENT_AGAINST_ACTUALS")
	private String adjustment_against_actuals;
	
	@Column(name="USAGE")
	private String usage;
	
	@Column(name="POST_CLOSE_ACTUAL_AMOUNT")
	private String post_close_actual_amount;
	
	@Column(name="PAST_YEAR_CLOSED_PROMOTIONS_AMOUNT")
	private String past_year_closed_promotions_amount;
	
	@Column(name="TIME_PHASE")
	private String time_phase;
	
	@Column(name="REPORT_DOWNLOADED_BY")
	private String report_downloaded_by;
	
	@Column(name="REPORT_DOWNLOADED_DATE")
	private String report_downlaoded_date;
	
	private String uploaded_timestamp;
	
	private String userId;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getBudget_holder() {
		return budget_holder;
	}

	public void setBudget_holder(String budget_holder) {
		this.budget_holder = budget_holder;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getCustomer() {
		return Customer;
	}

	public void setCustomer(String customer) {
		Customer = customer;
	}

	public String getFund_type() {
		return fund_type;
	}

	public void setFund_type(String fund_type) {
		this.fund_type = fund_type;
	}

	public String getOriginal_amount() {
		return original_amount;
	}

	public void setOriginal_amount(String original_amount) {
		this.original_amount = original_amount;
	}

	public String getAdjusted_amount() {
		return adjusted_amount;
	}

	public void setAdjusted_amount(String adjusted_amount) {
		this.adjusted_amount = adjusted_amount;
	}

	public String getRevised_amount() {
		return revised_amount;
	}

	public void setRevised_amount(String revised_amount) {
		this.revised_amount = revised_amount;
	}

	public String getUpdate_amount() {
		return update_amount;
	}

	public void setUpdate_amount(String update_amount) {
		this.update_amount = update_amount;
	}

	public String getTransfer_in() {
		return transfer_in;
	}

	public void setTransfer_in(String transfer_in) {
		this.transfer_in = transfer_in;
	}

	public String getTransfer_out() {
		return transfer_out;
	}

	public void setTransfer_out(String transfer_out) {
		this.transfer_out = transfer_out;
	}

	public String getTransfer_pipeline() {
		return transfer_pipeline;
	}

	public void setTransfer_pipeline(String transfer_pipeline) {
		this.transfer_pipeline = transfer_pipeline;
	}

	public String getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(String total_amount) {
		this.total_amount = total_amount;
	}

	public String getPipeline_amount() {
		return pipeline_amount;
	}

	public void setPipeline_amount(String pipeline_amount) {
		this.pipeline_amount = pipeline_amount;
	}

	public String getCommitment_amount() {
		return commitment_amount;
	}

	public void setCommitment_amount(String commitment_amount) {
		this.commitment_amount = commitment_amount;
	}

	public String getRemaining_amount() {
		return remaining_amount;
	}

	public void setRemaining_amount(String remaining_amount) {
		this.remaining_amount = remaining_amount;
	}

	public String getActuals() {
		return actuals;
	}

	public void setActuals(String actuals) {
		this.actuals = actuals;
	}

	public String getAdjustment_against_actuals() {
		return adjustment_against_actuals;
	}

	public void setAdjustment_against_actuals(String adjustment_against_actuals) {
		this.adjustment_against_actuals = adjustment_against_actuals;
	}

	public String getUsage() {
		return usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

	public String getPost_close_actual_amount() {
		return post_close_actual_amount;
	}

	public void setPost_close_actual_amount(String post_close_actual_amount) {
		this.post_close_actual_amount = post_close_actual_amount;
	}

	public String getPast_year_closed_promotions_amount() {
		return past_year_closed_promotions_amount;
	}

	public void setPast_year_closed_promotions_amount(String past_year_closed_promotions_amount) {
		this.past_year_closed_promotions_amount = past_year_closed_promotions_amount;
	}

	public String getTime_phase() {
		return time_phase;
	}

	public void setTime_phase(String time_phase) {
		this.time_phase = time_phase;
	}

	public String getReport_downloaded_by() {
		return report_downloaded_by;
	}

	public void setReport_downloaded_by(String report_downloaded_by) {
		this.report_downloaded_by = report_downloaded_by;
	}

	public String getReport_downlaoded_date() {
		return report_downlaoded_date;
	}

	public void setReport_downlaoded_date(String report_downlaoded_date) {
		this.report_downlaoded_date = report_downlaoded_date;
	}

	public String getUploaded_timestamp() {
		return uploaded_timestamp;
	}

	public void setUploaded_timestamp(String uploaded_timestamp) {
		this.uploaded_timestamp = uploaded_timestamp;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "BudgetHolderBean [file=" + file + ", budget_holder=" + budget_holder + ", category=" + category
				+ ", product=" + product + ", Customer=" + Customer + ", fund_type=" + fund_type + ", original_amount="
				+ original_amount + ", adjusted_amount=" + adjusted_amount + ", revised_amount=" + revised_amount
				+ ", update_amount=" + update_amount + ", transfer_in=" + transfer_in + ", transfer_out=" + transfer_out
				+ ", transfer_pipeline=" + transfer_pipeline + ", total_amount=" + total_amount + ", pipeline_amount="
				+ pipeline_amount + ", commitment_amount=" + commitment_amount + ", remaining_amount="
				+ remaining_amount + ", actuals=" + actuals + ", adjustment_against_actuals="
				+ adjustment_against_actuals + ", usage=" + usage + ", post_close_actual_amount="
				+ post_close_actual_amount + ", past_year_closed_promotions_amount="
				+ past_year_closed_promotions_amount + ", time_phase=" + time_phase + ", report_downloaded_by="
				+ report_downloaded_by + ", report_downlaoded_date=" + report_downlaoded_date + ", uploaded_timestamp="
				+ uploaded_timestamp + ", userId=" + userId + "]";
	}

	
	}