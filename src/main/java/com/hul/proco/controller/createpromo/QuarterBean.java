package com.hul.proco.controller.createpromo;

import java.util.ArrayList;
import java.util.List;

public class QuarterBean {
	private String title;

	private List<MocBean> subs = new ArrayList<MocBean>();

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<MocBean> getSubs() {
		return subs;
	}

	public void setSubs(List<MocBean> subs) {
		this.subs = subs;
	}

}
