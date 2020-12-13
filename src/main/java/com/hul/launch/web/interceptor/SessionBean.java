package com.hul.launch.web.interceptor;

import org.apache.log4j.Logger;

public class SessionBean {
	static Logger	logger	= Logger.getLogger(SessionBean.class);
	String			sessionId;
	String			userId;
	String			requestURI;
	String			remoteAddr;
	String			randomPageIndex;
	
	public String getSessionId() {
		return sessionId;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public String getRequestURI() {
		return requestURI;
	}
	
	public String getRemoteAddr() {
		return remoteAddr;
	}
	
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	public String getRandomPageIndex() {
		return randomPageIndex;
	}
	
	public void setRandomPageIndex(String randomPageIndex) {
		this.randomPageIndex = randomPageIndex;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public void setRequestURI(String requestURI) {
		this.requestURI = requestURI;
	}
	
	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}
	
	@Override
	public String toString() {
		return "SessionBean [sessionId=" + sessionId + ", userId=" + userId
				+ ", requestURI=" + requestURI + ", remoteAddr=" + remoteAddr
				+ ", randomPageIndex=" + randomPageIndex + "]";
	}
	
}
