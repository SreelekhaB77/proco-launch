package com.hul.launch.web.interceptor;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hul.launch.web.util.CommonPropUtils;
import com.hul.visibility.models.User;
import com.mtapputil.PropertyUtil;

public class SessionInterceptor extends HandlerInterceptorAdapter {
	static Logger											logger			= Logger.getLogger(SessionInterceptor.class);
	private static final ArrayList<String>					allowedUriList	= new ArrayList<String>();
	public static final ConcurrentMap<String, SessionBean>	userDetailsMap	= new ConcurrentHashMap<String, SessionBean>();
	private long											currentTocken	= -1;
	static String											loggedUser		= null;
	
	@SuppressWarnings("null")
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		// displaySessionBean();
		String UserId = "";
		String rquestUri = request.getRequestURI();
		HttpSession session = request.getSession();
		String contextPath = CommonPropUtils.getInstance().getProperty(
				"application.root.context.path");
		
		String loadbalancerurl=PropertyUtil.getPropertyUtil().getProperty("loadbalancerip");
		User loginBean = null;
		
		allowedUriList.add(contextPath);
		allowedUriList.add(contextPath + "/loginForm.htm");
		allowedUriList.add(contextPath + "/loginRequest.htm");
		
		// 1 mt
		if (rquestUri != null && rquestUri.contains("OneMtLoginRequest.htm")) {
			return true;
		}
		if (rquestUri != null && rquestUri.contains("oneMtuserCreation.htm")) {
			return true;
		}
		if (rquestUri != null && rquestUri.contains("oneMtupdateProfile.htm")) {
			return true;
		}
		if (rquestUri != null && rquestUri.contains("oneMtChangePassword.htm")) {
			return true;
		}
		if (rquestUri != null && rquestUri.contains("oneMtdeleteProfile.htm")) {
			return true;
		}
		
		if (null == request.getSession(false)) {
			//response.sendRedirect("/jsp/404/errorPage.jsp");
			response.sendRedirect("http://"+loadbalancerurl+"/VisibilityAssetTracker/assets/js/errorPage.jsp");
			return false;
		}
		
		
		if (rquestUri != null && allowedUriList.contains(rquestUri)) {
			return true;
		}
		
		if (session != null && !(rquestUri.contains("loginForm.htm"))) {
			UserId = (String) request.getSession().getAttribute("UserID");
			loginBean = (User) request.getSession().getAttribute("loginBean");
		}
		if (loginBean != null && loginBean.getUserId().equalsIgnoreCase(UserId)) {
			loggedUser = loginBean.getUserId();
		}
		
		if (loginBean != null) {
			return true;
		}
		
		if (loggedUser != null) {
			try {
				request.setAttribute("loggedUser", loginBean.getUserId());
			} catch (Exception e) {
				logger.error("Error Occured ", e);
			}
		}
		/*request.getRequestDispatcher("/jsp/404/errorPage.jsp").forward(request,
				response);*/
		//response.sendRedirect("http://"+loadbalancerurl+"/VisibilityAssetTracker/assets/js/errorPage.jsp");
		return false;
	}
	
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		HttpSession session = request.getSession();
		
		String requestContentType = request.getContentType();
		String responseContentType = response.getContentType();
		boolean downloadFile = false;
		
		String requestUri = request.getRequestURI();
		
		if (responseContentType != null) {
			if (responseContentType.contains("application/force-download")
					|| responseContentType.contains("text/plain")
					|| responseContentType.contains("application/json")) {
				downloadFile = true;
			}
		}
		User loginBean = null;
		if (session != null && !(requestUri.contains("loginForm.htm"))) {
			if (session.getAttribute("loginBean") != null) {
				loginBean = (User) session.getAttribute("loginBean");
			}
		}
		
		if (requestContentType == null
				&& !(requestUri.contains("errorPage.jsp")
						|| requestUri.contains("error.jsp") || downloadFile)) {
			currentTocken = System.currentTimeMillis();
		} else if (requestContentType != null
				&& (!(requestContentType.contains("application/json") == true
						|| requestContentType.contains("multipart/form-data") == true || downloadFile))) {
			currentTocken = System.currentTimeMillis();
		}
		if (loginBean != null) {
			SessionBean sessionBean = new SessionBean();
			sessionBean.setUserId(loginBean.getUserId());
			sessionBean.setSessionId(session.getId());
			sessionBean.setRequestURI(request.getRequestURI());
			sessionBean.setRemoteAddr(request.getRemoteAddr());
			sessionBean.setRandomPageIndex(currentTocken + "");
			request.setAttribute("RandomPageIndex", currentTocken + "");
			
			userDetailsMap.put(loginBean.getUserId(), sessionBean);
		}
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception exception)
			throws Exception {
		displaySessionBean();
	}
	
	@SuppressWarnings("unused")
	public void displaySessionBean() {
		for (ConcurrentMap.Entry<String, SessionBean> entry : userDetailsMap.entrySet()) {
			SessionBean sessionBean = entry.getValue();
			String loginUserID = entry.getKey();
		}
	}
}
