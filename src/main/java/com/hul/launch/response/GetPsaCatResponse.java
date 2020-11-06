package com.hul.launch.response;

import java.util.ArrayList;
import java.util.List;

import com.hul.launch.model.TblLaunchMaster;

public class GetPsaCatResponse {
	private List<TblLaunchMaster> liComProdMasters = new ArrayList<>();

	public List<TblLaunchMaster> getLiComProdMasters() {
		return liComProdMasters;
	}

	public void setLiComProdMasters(List<TblLaunchMaster> liComProdMasters) {
		this.liComProdMasters = liComProdMasters;
	}

}