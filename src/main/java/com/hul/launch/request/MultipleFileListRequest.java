package com.hul.launch.request;

import java.util.List;

/**
 * 
 * @author anshuman.shrivastava
 *
 **/
public class MultipleFileListRequest {
	private List<MultipleFileListRequest> file;

	public List<MultipleFileListRequest> getFile() {
		return file;
	}

	public void setFile(List<MultipleFileListRequest> file) {
		this.file = file;
	}

}