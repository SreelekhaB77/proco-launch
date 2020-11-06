package com.hul.launch.response;

public class CoeDocDownloadResponse {
	private String launchName;
	private String annexureFileName;
	private String artworkPackshotsFileName;
	private String mdgDeckFileName;
	private String error;

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getLaunchName() {
		return launchName;
	}

	public void setLaunchName(String launchName) {
		this.launchName = launchName;
	}

	public String getAnnexureFileName() {
		return annexureFileName;
	}

	public void setAnnexureFileName(String annexureFileName) {
		this.annexureFileName = annexureFileName;
	}

	public String getArtworkPackshotsFileName() {
		return artworkPackshotsFileName;
	}

	public void setArtworkPackshotsFileName(String artworkPackshotsFileName) {
		this.artworkPackshotsFileName = artworkPackshotsFileName;
	}

	public String getMdgDeckFileName() {
		return mdgDeckFileName;
	}

	public void setMdgDeckFileName(String mdgDeckFileName) {
		this.mdgDeckFileName = mdgDeckFileName;
	}

}