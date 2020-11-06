package com.hul.proco.controller.disaggregatepromo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DisaggregationL2Bean {

	private String CustomerChainL2;
	private double saleTotalQty;
	private double percentage;
	private double qty;
	private ConcurrentHashMap<String, DisaggregationQtyBean> depotMap;

	public String getCustomerChainL2() {
		return CustomerChainL2;
	}

	public void setCustomerChainL2(String customerChainL2) {
		CustomerChainL2 = customerChainL2;
	}

	public double getSaleTotalQty() {
		return saleTotalQty;
	}

	public void setSaleTotalQty(double saleTotalQty) {
		this.saleTotalQty = saleTotalQty;
	}

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	public double getQty() {
		return qty;
	}

	public void setQty(double qty) {
		this.qty = qty;
	}

	public Map<String, DisaggregationQtyBean> getDepotMap() {
		return depotMap;
	}

	public void setDepotMap(ConcurrentHashMap<String, DisaggregationQtyBean> depotMap) {
		this.depotMap = depotMap;
	}

	@Override
	public String toString() {
		return "DisaggregationL2Bean [CustomerChainL2=" + CustomerChainL2 + ", saleTotalQty=" + saleTotalQty
				+ ", percentage=" + percentage + ", qty=" + qty + ", depotMap=" + depotMap + "]";
	}

	
	
}
