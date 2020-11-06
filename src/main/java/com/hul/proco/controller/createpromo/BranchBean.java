package com.hul.proco.controller.createpromo;

import java.util.ArrayList;
import java.util.List;

public class BranchBean {

	private List<ClusterBean> subs = new ArrayList<ClusterBean>();

	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<ClusterBean> getSubs() {
		return subs;
	}

	public void setSubs(List<ClusterBean> subs) {
		this.subs = subs;
	}

}
