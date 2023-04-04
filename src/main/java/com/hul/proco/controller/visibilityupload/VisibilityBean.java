package com.hul.proco.controller.visibilityupload;

import org.springframework.web.multipart.MultipartFile;

import com.hul.proco.excelreader.exom.annotation.Column;

public class VisibilityBean {
	private MultipartFile file;
	
	@Column(name = "VISI REF NO.")
	private String visi_ref_no;
	
	@Column(name = "START DATE")
	private String start_date;
	
	@Column(name = "END DATE")
	private String end_date;
	
	@Column(name = "MOC")
	private String moc;
	
	@Column(name = "HFS CONNECTIVITY")
	private String hfs_connectivity;
	
	@Column(name = "NEW/CONTINUED")
	private String new_continued;
	
	@Column(name = "MADE BY")
	private String made_by;
	
	@Column(name = "ACCOUNT NAME")
	private String account_name;
	
	@Column(name = "SPLIT REQUIRE")
	private String split_require;
	
	@Column(name = "PPM ACCOUNT NAME - EXCEPT NMT/RC")
	private String ppm_account_name;
	
	@Column(name = "Description 1")
	private String desc_1;
	
	@Column(name = "PPM Desc")
	private String ppm_desc;
	
	@Column(name = "REGION")
	private String region;
	
	@Column(name = "STATE")
	private String state;
	
	@Column(name = "CITY")
	private String city;
	
	@Column(name = "BASEPACK")
	private String basepack;
	
	@Column(name = "BASEPACK DESC")
	private String basepack_desc;
	
	@Column(name = "VISIBILITY DESC")
	private String visibility_desc;
	
	@Column(name = "ASSET DESCRIPTION")
	private String asset_desc;
	
	@Column(name = "ASSET TYPE")
	private String asset_type;
	
	@Column(name = "ASSET REMARK")
	private String asset_remark;
	
	@Column(name = "POP-CLASS")
	private String pop_class;
	
	@Column(name = "UNIT PER STORE")
	private String unit_per_store;
	
	@Column(name = "NO. OF STORES")
	private String no_of_stores;
	
	@Column(name = "AMOUNT PER STORE PER MOC")
	private String amount_per_store_per_moc;
	
	@Column(name = "AMOUNT PER BASEPACK PER MOC")
	private String amount_per_basepack_per_moc;
	
	@Column(name = "COMMENTS")
	private String comments;
	
	@Column(name = "HHT TRACKING")
	private String hht_tracking;
	
	@Column(name = "CATEGORY")
	private String category;
	
	@Column(name = "MIGRATED CATEGORY")
	private String migrated_category;
	
	@Column(name = "SUB ELEMENTS")
	private String sub_elements;
	
	@Column(name = "MBQ")
	private String mbq;
	
	@Column(name = "BRAND")
	private String brand;
	
	@Column(name = "TOTAL No. OF ASSET")
	private String total_no_of_asset;
	
	@Column(name = "VISIBILITY AMOUNT")
	private String visibility_amount;
	
	@Column(name = "OUTLET CODE")
	private String outlet_code;
	
	@Column(name = "OUTLET NAME")
	private String outlet_name;
	
	@Column(name = "MAPPED POP-CLASS")
	private String mapped_pop_class;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "DATE OF CREATION")
	private String date_of_creation;
	
	@Column(name = "LAST EDITED")
	private String last_edited;
	
	@Column(name = "CLASSIFICATION")
	private String classification;
	
	@Column(name = "EDIT/DELETE REASON")
	private String edit_delete_reason;

	@Column(name = "VISIBILITY_PAYOUT_CODE")
	private String visibility_payout_code;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getVisi_ref_no() {
		return visi_ref_no;
	}

	public void setVisi_ref_no(String visi_ref_no) {
		this.visi_ref_no = visi_ref_no;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public String getMoc() {
		return moc;
	}

	public void setMoc(String moc) {
		this.moc = moc;
	}

	public String getHfs_connectivity() {
		return hfs_connectivity;
	}

	public void setHfs_connectivity(String hfs_connectivity) {
		this.hfs_connectivity = hfs_connectivity;
	}

	public String getNew_continued() {
		return new_continued;
	}

	public void setNew_continued(String new_continued) {
		this.new_continued = new_continued;
	}

	public String getMade_by() {
		return made_by;
	}

	public void setMade_by(String made_by) {
		this.made_by = made_by;
	}

	public String getAccount_name() {
		return account_name;
	}

	public void setAccount_name(String account_name) {
		this.account_name = account_name;
	}

	public String getSplit_require() {
		return split_require;
	}

	public void setSplit_require(String split_require) {
		this.split_require = split_require;
	}

	public String getPpm_account_name() {
		return ppm_account_name;
	}

	public void setPpm_account_name(String ppm_account_name) {
		this.ppm_account_name = ppm_account_name;
	}

	public String getDesc_1() {
		return desc_1;
	}

	public void setDesc_1(String desc_1) {
		this.desc_1 = desc_1;
	}

	public String getPpm_desc() {
		return ppm_desc;
	}

	public void setPpm_desc(String ppm_desc) {
		this.ppm_desc = ppm_desc;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getBasepack() {
		return basepack;
	}

	public void setBasepack(String basepack) {
		this.basepack = basepack;
	}

	public String getBasepack_desc() {
		return basepack_desc;
	}

	public void setBasepack_desc(String basepack_desc) {
		this.basepack_desc = basepack_desc;
	}

	public String getVisibility_desc() {
		return visibility_desc;
	}

	public void setVisibility_desc(String visibility_desc) {
		this.visibility_desc = visibility_desc;
	}

	public String getAsset_desc() {
		return asset_desc;
	}

	public void setAsset_desc(String asset_desc) {
		this.asset_desc = asset_desc;
	}

	public String getAsset_type() {
		return asset_type;
	}

	public void setAsset_type(String asset_type) {
		this.asset_type = asset_type;
	}

	public String getAsset_remark() {
		return asset_remark;
	}

	public void setAsset_remark(String asset_remark) {
		this.asset_remark = asset_remark;
	}

	public String getPop_class() {
		return pop_class;
	}

	public void setPop_class(String pop_class) {
		this.pop_class = pop_class;
	}

	public String getUnit_per_store() {
		return unit_per_store;
	}

	public void setUnit_per_store(String unit_per_store) {
		this.unit_per_store = unit_per_store;
	}

	public String getNo_of_stores() {
		return no_of_stores;
	}

	public void setNo_of_stores(String no_of_stores) {
		this.no_of_stores = no_of_stores;
	}

	public String getAmount_per_store_per_moc() {
		return amount_per_store_per_moc;
	}

	public void setAmount_per_store_per_moc(String amount_per_store_per_moc) {
		this.amount_per_store_per_moc = amount_per_store_per_moc;
	}

	public String getAmount_per_basepack_per_moc() {
		return amount_per_basepack_per_moc;
	}

	public void setAmount_per_basepack_per_moc(String amount_per_basepack_per_moc) {
		this.amount_per_basepack_per_moc = amount_per_basepack_per_moc;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getHht_tracking() {
		return hht_tracking;
	}

	public void setHht_tracking(String hht_tracking) {
		this.hht_tracking = hht_tracking;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getMigrated_category() {
		return migrated_category;
	}

	public void setMigrated_category(String migrated_category) {
		this.migrated_category = migrated_category;
	}

	public String getSub_elements() {
		return sub_elements;
	}

	public void setSub_elements(String sub_elements) {
		this.sub_elements = sub_elements;
	}

	public String getMbq() {
		return mbq;
	}

	public void setMbq(String mbq) {
		this.mbq = mbq;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getTotal_no_of_asset() {
		return total_no_of_asset;
	}

	public void setTotal_no_of_asset(String total_no_of_asset) {
		this.total_no_of_asset = total_no_of_asset;
	}

	public String getVisibility_amount() {
		return visibility_amount;
	}

	public void setVisibility_amount(String visibility_amount) {
		this.visibility_amount = visibility_amount;
	}

	public String getOutlet_code() {
		return outlet_code;
	}

	public void setOutlet_code(String outlet_code) {
		this.outlet_code = outlet_code;
	}

	public String getOutlet_name() {
		return outlet_name;
	}

	public void setOutlet_name(String outlet_name) {
		this.outlet_name = outlet_name;
	}

	public String getMapped_pop_class() {
		return mapped_pop_class;
	}

	public void setMapped_pop_class(String mapped_pop_class) {
		this.mapped_pop_class = mapped_pop_class;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDate_of_creation() {
		return date_of_creation;
	}

	public void setDate_of_creation(String date_of_creation) {
		this.date_of_creation = date_of_creation;
	}

	public String getLast_edited() {
		return last_edited;
	}

	public void setLast_edited(String last_edited) {
		this.last_edited = last_edited;
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	public String getEdit_delete_reason() {
		return edit_delete_reason;
	}

	public void setEdit_delete_reason(String edit_delete_reason) {
		this.edit_delete_reason = edit_delete_reason;
	}

	public String getVisibility_payout_code() {
		return visibility_payout_code;
	}

	public void setVisibility_payout_code(String visibility_payout_code) {
		this.visibility_payout_code = visibility_payout_code;
	}
	
	
	@Override
	public String toString() {
		return "VisibilityBean [file=" + file + ", visi_ref_no=" + visi_ref_no + ", start_date=" + start_date + ","
				+ " end_date="+ end_date + ", moc=" + moc + ", hfs_connectivity=" + hfs_connectivity+ ", new_continued=" 
				+ new_continued + ", made_by=" + made_by + ", account_name="+ account_name + ", split_require="
				+ split_require + ", ppm_account_name=" + ppm_account_name + ", desc_1="+ desc_1 + ", ppm_desc="
				+ ppm_desc + ", region=" + region + ", state=" + state+ ", city=" + city + ", basepack="
				+ basepack + ", basepack_desc=" + basepack_desc + ", visibility_desc=" + visibility_desc
				+ ", asset_desc=" + asset_desc + ", asset_type=" + asset_type + ", asset_remark="
				+ asset_remark + ", pop_class=" + pop_class + ", unit_per_store=" + unit_per_store + ", no_of_stores="
				+ no_of_stores + ", amount_per_store_per_moc=" + amount_per_store_per_moc + ", amount_per_basepack_per_moc="
				+ amount_per_basepack_per_moc + ", comments="+ comments + ", hht_tracking=" + hht_tracking + ", category="
				+ category + ", migrated_category="+ migrated_category + ", sub_elements=" + sub_elements + ", mbq="
				+ mbq + ", brand="+ brand + ", total_no_of_asset=" + total_no_of_asset + ",visibility_amount="
				+ visibility_amount+", outlet_code="+ outlet_code +",outlet_name="+ outlet_name +",mapped_pop_class="
				+ mapped_pop_class+", status="+status+",date_of_creation="+date_of_creation+",last_edited="+last_edited+""
				+ " classification="+classification+",edit_delete_reason="+edit_delete_reason+""
				+ "visibility_payout_code="+visibility_payout_code+"]";
	}
	
}
