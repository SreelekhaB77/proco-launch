package com.hul.launch.constants;

public class ResponseCodeConstants {
	private ResponseCodeConstants() {
	}

	public static final int STATUS_FILED_BLANK_LAUNCH_ID = -1000;
	public static final int STATUS_SUCCESS_STORE_DATA_ON_STORE = 1000;

	// COE screens Status codes

	// GetAllCompletedFinalLaunchData API
	public static final int STATUS_SUCCESS_COMPLETED_FINAL_LAUNCH_DATA = 1001;
	public static final int STATUS_FAILURE_COMPLETED_FINAL_LAUNCH_DATA = -1001;

	// GetAllCompletedListingTracker API
	public static final int STATUS_SUCCESS_COMPLETED_LISTING_TRACKER = 1002;
	public static final int STATUS_FAILURE_COMPLETED_LISTING_TRACKER = -1002;

	// getAllMstnClearance
	public static final int STATUS_SUCCESS_ALL_MSTN_CLEARANCE = 1003;
	public static final int STATUS_FAILURE_ALL_MSTN_CLEARANCE = -1003;

	// getCoeDocDownloadUrl
	public static final int STATUS_SUCCESS_GET_COE_DOWNLOAD = 1004;
	public static final int STATUS_FAILURE_GET_COE_DOWNLOAD = -1004;

	// getLaunchStoreList
	public static final int STATUS_SUCCESS_GET_LAUNCH_STORE_LIST = 1005;
	public static final int STATUS_FAILURE_GET_LAUNCH_STORE_LIST = -1005;

	// KAM screen Status codes
	public static final int STATUS_SUCCESS_GET_KAM_BASEPACK_DATA = 2000;
	public static final int STATUS_FAILURE_GET_KAM_BASEPACK_DATA = -2000;

	// getAllBasePackByLaunchIdsSc
	public static final int STATUS_SUCCESS_GET_SC_BASEPACK_DATA = 2001;
	public static final int STATUS_FAILURE_GET_SC_BASEPACK_DATA = -2001;

	// getAllFinalBuildUpByLaunchIdsSc
	public static final int STATUS_SUCCESS_GET_SC_FINAL_BUILDUP_DATA = 2002;
	public static final int STATUS_FAILURE_GET_SC_FINAL_BUILDUP_DATA = -2002;

	// getAllFinalBuildUpByLaunchIdsDp
	public static final int STATUS_SUCCESS_GET_DP_FINAL_BUILDUP_DATA = 2004;
	public static final int STATUS_FAILURE_GET_DP_FINAL_BUILDUP_DATA = -2004;

	// getAllBasePackByLaunchIdsDp
	public static final int STATUS_SUCCESS_GET_DP_BASEPACK_DATA = 2003;
	public static final int STATUS_FAILURE_GET_DP_BASEPACK_DATA = -2003;

	// KAM screen Status codes
	public static final int STATUS_SUCCESS_GET_MOC_DATA = 2005;
	public static final int STATUS_FAILURE_GET_MOC_DATA = -2005;

	// KAM screen Status codes
	public static final int STATUS_SUCCESS_REJECT_LAUNCH_KAM = 2006;
	public static final int STATUS_FAILURE_REJECT_LAUNCH_KAM = -2006;

	// KAM screen Status codes
	public static final int STATUS_SUCCESS_REQUEST_CHANGE_MOC_KAM = 2007;
	public static final int STATUS_FAILURE_REQUEST_CHANGE_MOC_KAM = -2007;

	// KAM screen Status codes
	public static final int STATUS_SUCCESS_REJECT_BASEPACK_KAM = 2008;
	public static final int STATUS_FAILURE_REJECT_BASEPACK_KAM = -2008;

	// KAM screen Status codes
	public static final int STATUS_SUCCESS_BUILD_UP_KAM = 2009;
	public static final int STATUS_FAILURE_BUILD_UP_KAM = -2009;

	// KAM screen Status codes
	public static final int STATUS_SUCCESS_VISI_LIST_KAM = 2010;
	public static final int STATUS_FAILURE_VISI_LIST_KAM = -2010;

	// KAM screen Status codes
	public static final int STATUS_SUCCESS_SAVE_VISI_LIST_KAM = 2011;
	public static final int STATUS_FAILURE_SAVE_VISI_LIST_KAM = -2011;

	// KAM screen Status codes
	public static final int STATUS_SUCCESS_LAUNCH_DOC_DETAILS_KAM = 2012;
	public static final int STATUS_FAILURE_LAUNCH_DOC_DETAILS_KAM = -2012;

	// KAM screen Status codes
	public static final int STATUS_SUCCESS_GET_APPROVAL_STATUS_KAM = 2013;
	public static final int STATUS_FAILURE_GET_APPROVAL_STATUS_KAM = -2013;

	// TME screen Status codes
	public static final int STATUS_SUCCESS_GET_BP_CLASSIFICATION_TME = 3000;
	public static final int STATUS_FAILURE_GET_BP_CLASSIFICATION_TME = -3000;

	// TME screen Status codes
	public static final int STATUS_SUCCESS_GET_LAUNCH_SELLIN_TME = 3001;
	public static final int STATUS_FAILURE_GET_LAUNCH_SELLIN_TME = -3001;

	// TME screen Status codes
	public static final int STATUS_SUCCESS_UPLOAD_LAUNCH_BASEPACK_TME = 3002;
	public static final int STATUS_FAILURE_UPLOAD_LAUNCH_BASEPACK_TME = -3002;

	public static final int STATUS_SUCCESS_LAUNCH_STORE_KAM = 4001;
	public static final int STATUS_FAILURE_LAUNCH_STORE_KAM = -4001;

	public static final int STATUS_SUCCESS_MSTN_CLEARED_SC = 4002;
	public static final int STATUS_FAILURE_MSTN_CLEARED_SC = -4002;

	public static final int STATUS_SUCCESS_MSTN_CLEARED_SAVE_SC = 4003;
	public static final int STATUS_FAILURE_MSTN_CLEARED_SAVE_SC = -4003;

	public static final int STATUS_SUCCESS_MSTN_CLEARED_GET_KAM = 4004;
	public static final int STATUS_FAILURE_MSTN_CLEARED_GET_KAM = -4004;

	public static final int STATUS_SUCCESS_MSTN_CLEARED_COE = 4005;
	public static final int STATUS_FAILURE_MSTN_CLEARED_COE = -4005;

	public static final int STATUS_SUCCESS_MSTN_CLEARED_TME = 4006;
	public static final int STATUS_FAILURE_MSTN_CLEARED_TME = -4006;

}