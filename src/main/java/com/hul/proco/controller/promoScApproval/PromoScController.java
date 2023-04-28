package com.hul.proco.controller.promoScApproval;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hul.launch.web.util.CommonPropUtils;
import com.hul.launch.web.util.CommonUtils;
import com.hul.launch.web.util.FilePaths;
import com.hul.launch.web.util.UploadUtil;
import com.hul.proco.controller.createpromo.CreatePromoService;
import com.hul.proco.controller.listingPromo.PromoListingService;
import com.hul.proco.controller.promocr.PromoCrBean;
import com.hul.proco.controller.promocr.PromoCrJsonObject;
import com.hul.proco.controller.promocr.PromoCrService;
import com.hul.proco.excelreader.exom.ExOM;


/*
 *  Change name: Creating new file for promo approval in sc login
 *  Description: This controller will handle all the new template request 
 *  Developer: Kavitha Dayyala
 *  Date: 24/11/2022 
 */

@Controller
public class PromoScController {
	
	static Logger logger = Logger.getLogger(PromoScController.class);
	
	@Autowired
	public PromoApprovalService promoApprovalService;
	
	@Autowired 
	public PromoListingService promoListingService;
	
	@Autowired
	private CreatePromoService createPromoService;
	@Autowired
	private PromoCrService promoCrService;
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "promoApproveSc.htm", method = RequestMethod.GET)
	public ModelAndView getProcoPromoListingPage(HttpServletRequest request, Model model) {
		String roleId = (String) request.getSession().getAttribute("roleId");
				String id=(String)request.getSession().getAttribute("UserID");
				promoApprovalService.insertToportalUsage(id, roleId, "PROCO");
				List<String> mocValueCr = promoListingService.getPromoMoc(); 
				/**/ 
				List<String> customerChainL1 = createPromoService.getCustomerChainL1();
				List<String> offerTypes = createPromoService.getOfferTypes();
				Map<Integer, String> modality = createPromoService.getModality();
				Map<String, Object> yearAndMoc = createPromoService.getYearAndMoc(false);
				String geographyJson = createPromoService.getGeography(false);
				List<String> yearList = (List<String>) yearAndMoc.get("years");
				String mocJson = (String) yearAndMoc.get("moc");
				List<String> category = promoCrService.getAllCategories();
				List<String> brand = promoCrService.getAllBrands();
				List<String> basepacks = promoCrService.getAllBasepacks();
				model.addAttribute("geographyJson", geographyJson);
				model.addAttribute("mocJson", mocJson);
				model.addAttribute("years", yearList);
				model.addAttribute("modality", modality);
				model.addAttribute("customerChainL1", customerChainL1);
				model.addAttribute("offerTypes", offerTypes);
				model.addAttribute("categories", category);
				model.addAttribute("brands", brand);
				model.addAttribute("basepacks", basepacks);
				 /**/
				model.addAttribute("roleId", roleId);
				model.addAttribute("mocListCr", mocValueCr);
				return new ModelAndView("proco/proco_promo_sc");
	}
	
	
	
	@RequestMapping(value = "promoScPagination.htm", method = RequestMethod.GET, produces = "application/json", headers = "Accept=*/*")
	public @ResponseBody String promoScPagination(@RequestParam("moc") String mocValue, HttpServletRequest request) {
		String roleId = (String) request.getSession().getAttribute("roleId");
		String userId = (String) request.getSession().getAttribute("UserID");
		Integer pageDisplayStart = Integer.valueOf(request.getParameter("iDisplayStart"));
		Integer pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));
		Integer pageNumber = (pageDisplayStart / pageDisplayLength) + 1;
		String searchParameter = request.getParameter("sSearch");
		String moc=" ";
		if (mocValue == null || mocValue.isEmpty() || mocValue.equalsIgnoreCase("undefined")) {
			moc = "all";
		} else {
			moc = mocValue;
		}
		int rowCount = promoApprovalService.getPromoListScRowCount(userId,roleId,moc);
		List<PromoCrBean> promoList = promoApprovalService.getPromoScTableList((pageDisplayStart + 1),(pageNumber * pageDisplayLength),userId,roleId,moc,searchParameter);
		

		PromoCrJsonObject jsonObj = new PromoCrJsonObject();
		jsonObj.setJsonBean(promoList);
		jsonObj.setiTotalDisplayRecords(rowCount);
		jsonObj.setiTotalRecords(rowCount);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(jsonObj);
		return json;
	}
	
	
	@RequestMapping(value = "approvePromoSc.htm", method = RequestMethod.GET)
	public ModelAndView approvePromoSc(@RequestParam("promoid") String promoId, Model model,
			HttpServletRequest request) {
		String roleId = (String) request.getSession().getAttribute("roleId");
		String userId = (String) request.getSession().getAttribute("UserID");
		String res = promoApprovalService.approvePromoSc(promoId, userId, roleId);
		if(res!=null){
			model.addAttribute("success", "Promo's Approved Successfully.");
			model.addAttribute("FILE_STATUS", "SUCCESS_FILE");
		}else{
			model.addAttribute("errorMsg", "Failed to Approve Promo's.");
		}
		model.addAttribute("roleId", roleId);
		//setModelAttributes(model);
		return new ModelAndView("proco/proco_promo_sc");
	}
	
	//Added by kavitha D for promo approval download starts-SPRINT 10
		@RequestMapping(value = "{moc}/downloadScPromoListing.htm", method = RequestMethod.GET)
		public @ResponseBody String downloadScPromosForListing(@ModelAttribute("PromoCrBean") PromoCrBean promoCrBean,@PathVariable("moc") String moc,
				Model model,HttpServletRequest request, HttpServletResponse response) {
			logger.info("START downloadPromos for listing():");
			try {
				InputStream is;
				String roleId = (String) request.getSession().getAttribute("roleId");
				String downloadLink = "", absoluteFilePath = "";
				List<ArrayList<String>> downloadedData = null;
				absoluteFilePath = FilePaths.FILE_TEMPDOWNLOAD_PATH;
				String fileName = UploadUtil.getFileName("Promotion.Download.Template.file", "",
						CommonUtils.getCurrDateTime_YYYY_MM_DD_HHMMSS());
				String downloadFileName = absoluteFilePath + fileName;
				String userId = (String) request.getSession().getAttribute("UserID");
				
				ArrayList<String> headerList = promoApprovalService.getHeaderListForPromoDownloadScListing();
				downloadedData = promoApprovalService.getPromotionListingScDownload(headerList, userId,moc,roleId);
				if (downloadedData != null) {
					UploadUtil.writeDeletePromoXLSXFile(downloadFileName, downloadedData, null,".xlsx");
					downloadLink = downloadFileName + ".xlsx";
					is = new FileInputStream(new File(downloadLink));
					// copy it to response's OutputStream
					response.setContentType("application/force-download");
					response.setHeader("Content-Disposition", "attachment; filename=PromotionListingScApprovalDownloadFile"
							+ CommonUtils.getCurrDateTime_YYYY_MM_DD_HH_MM_SS_WithOutA() + ".xlsx");
					IOUtils.copy(is, response.getOutputStream());
					response.flushBuffer();
				}
			} catch (Exception e) {
				logger.debug("Exception: ", e);
				return null;
			}
			return null;
		}
		//Added by kavitha D for promo approval download ends-SPRINT 10
		
		
		//Added by kavitha D for promo approval upload starts-SPRINT 10
		@RequestMapping(value = "promoApprovalScUpload.htm", method = RequestMethod.POST)
		public @ResponseBody String promoApprovalUpload(@ModelAttribute("PromoCrBean") PromoCrBean promoCrBean,
				Model model, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)throws Exception {
			String save_data = null;

			CommonPropUtils commUtils = CommonPropUtils.getInstance();
			String userId = (String) httpServletRequest.getSession().getAttribute("UserID");
			MultipartFile file = promoCrBean.getFile();
			String fileName = file.getOriginalFilename();
			PromoCrBean[] beanArray = null;
			String filepath = FilePaths.FILE_TEMPUPLOAD_PATH;
			fileName = filepath + fileName;
			try {
				if (!CommonUtils.isFileEmpty(file)) {
					if (CommonUtils.isFileProcoSizeExceeds(file)) {
						model.addAttribute("FILE_STATUS", "FILE_SIZE_EXCEED");
						return "FILE_SIZE_EXCEED";
					} else if (UploadUtil.movefile(file, fileName)) {
						Map<String, List<Object>> map = ExOM.mapFromExcel(new File(fileName)).to(PromoCrBean.class).map(4, false, null);

						if (map.isEmpty()) {
							model.addAttribute("FILE_STAUS", "FILE_EMPTY");
							return "FILE_EMPTY";
						}
						if (map.containsKey("ERROR")) {
							model.addAttribute("FILE_STAUS", "CHECK_COL_MISMATCH");

							return "CHECK_COL_MISMATCH";
						} 
						//Added by Kajal G for empty row in excel
						else if (map.containsKey("EMPTY_ROW")) {
							model.addAttribute("FILE_STAUS", "EMPTY_ROW");
							return "EMPTY_ROW";
						}
						else if (map.containsKey("DATA")) {
							List<?> datafromexcel = map.get("DATA");
							beanArray = (PromoCrBean[]) datafromexcel.toArray(new PromoCrBean[datafromexcel.size()]);
							save_data = promoApprovalService.uploadScApprovalData(beanArray, userId);
						}
					} else {

						model.addAttribute("FILE_STAUS", "FILE_EMPTY");
						return "FILE_EMPTY";
					}
				} else {
					model.addAttribute("FILE_STAUS", "FILE_EMPTY");
					return "FILE_EMPTY";
				}

				if (save_data.equals("EXCEL_UPLOADED")) {
					model.addAttribute("FILE_STAUS", "EXCEL_UPLOADED");
				} else {
					model.addAttribute("FILE_STAUS", "EXCEL_NOT_UPLOADED");
					save_data = "EXCEL_NOT_UPLOADED";
				}
			} catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
			} catch (Throwable t) {
				logger.error(t);
				t.printStackTrace();
			}
			return save_data;
		}
		
		//Added by kavitha D for promo approval upload starts-SPRINT 10

	
	}

	
	
	
	


