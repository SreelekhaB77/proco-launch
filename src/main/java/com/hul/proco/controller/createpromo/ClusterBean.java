package com.hul.proco.controller.createpromo;

import java.util.ArrayList;
import java.util.List;

public class ClusterBean {

	private List<StateBean> subs = new ArrayList<StateBean>();

	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<StateBean> getSubs() {
		return subs;
	}

	public void setSubs(List<StateBean> subs) {
		this.subs = subs;
	}

}
