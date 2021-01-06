package com.hul.proco.controller.promostatustracker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hul.proco.controller.createpromo.CreatePromoService;
import com.hul.proco.controller.listingPromo.PromoListingBean;
import com.hul.proco.controller.listingPromo.PromoListingJsonObject;
import com.hul.proco.controller.listingPromo.PromoMeasureReportBean;
import com.hul.proco.controller.promocr.PromoCrService;
import com.hul.launch.web.util.CommonPropUtils;
import com.hul.launch.web.util.CommonUtils;
import com.hul.launch.web.util.FilePaths;
import com.hul.launch.web.util.UploadUtil;


@Controller
public class ProcoStatusTrackerController {
	
	private Logger logger=Logger.getLogger(ProcoStatusTrackerController.class);
	
	@Autowired
	private ProcoStatusTrackerService procoStatusTrackerService;
	
	@Autowired
	private PromoCrService promoCrService;
	
	@Autowired
	private CreatePromoService createPromoService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "promoStatusTracker.htm", method = RequestMethod.GET)
	public ModelAndView getpromoStatusTrackerPage(HttpServletRequest request, Model model) {
		long startTime = System.currentTimeMillis();
		String roleId = (String) request.getSession().getAttribute("roleId");
		model.addAttribute("roleId", roleId);
		//Sarin Changes Performance - Commented and added below
		/*
		List<String> customerChainL1 = createPromoService.getCustomerChainL1();
		List<String> offerTypes = createPromoService.getOfferTypes();
		*/
		List<List<String>> lstPromoDetails = createPromoService.getPromoDetails();
		List<String> customerChainL1 = lstPromoDetails.get(0);
		List<String> offerTypes = lstPromoDetails.get(1);
		//Sarin Changes Performance - Ends
		Map<Integer, String> modality = createPromoService.getModality();
		Map<String, Object> yearAndMoc = createPromoService.getYearAndMoc(false);
		String geographyJson = createPromoService.getGeography(false);
		List<String> yearList = (List<String>) yearAndMoc.get("years");
		String mocJson = (String) yearAndMoc.get("moc");
		//Sarin Changes Performance - Commented and added below
		/*
		List<String> category = promoCrService.getAllCategories();
		List<String> brand = promoCrService.getAllBrands();
		List<String> basepacks = promoCrService.getAllBasepacks();
		List<String> promoIds = createPromoService.getPromoIds();
		*/
		//Sarin Changes Performance
		List<List<String>> lstProductMaster = promoCrService.getAllProductMaster();
		List<String> category = lstProductMaster.get(0);
		List<String> brand = lstProductMaster.get(1);
		List<String> basepacks = lstProductMaster.get(2);

		//List<String> category = lstPromoDetails.get(3);
		//List<String> brand = lstPromoDetails.get(4);
		//List<String> basepacks = lstPromoDetails.get(5);
		
		List<String> promoIds = lstPromoDetails.get(2);
		//Sarin Changes Performance - Ends
		long endTime = System.currentTimeMillis();
		logger.info("duration of Promo page intialization: "+(endTime-startTime));
		model.addAttribute("geographyJson", geographyJson);
		model.addAttribute("mocJson", mocJson);
		model.addAttribute("years", yearList);
		model.addAttribute("modality", modality);
		model.addAttribute("customerChainL1", customerChainL1);
		model.addAttribute("offerTypes", offerTypes);
		model.addAttribute("categories", category);
		model.addAttribute("brands", brand);
		model.addAttribute("basepacks", basepacks);
		model.addAttribute("promoIds", promoIds);
		return new ModelAndView("proco/proco_status_tracker");
	}
	
	
	@RequestMapping(value = "promoStatusPagination.htm", method = RequestMethod.GET, produces = "application/json", headers = "Accept=*/*")
	public @ResponseBody String promoListingPagination(@RequestParam("category") String cagetoryValue,
			@RequestParam("brand") String brandValue, @RequestParam("custChainL1") String custChainL1Value,
			@RequestParam("offerType") String offerTypeValue, @RequestParam("modality") String modalityValue,
			@RequestParam("year") String yearValue, @RequestParam("custChainL2") String custChainL2Value,
			@RequestParam("basepack") String basepackValue, @RequestParam("geography") String geographyValue,
			@RequestParam("moc") String mocValue, @RequestParam("promoId") String promoId,HttpServletRequest request) {

		long startTime = System.currentTimeMillis();
		String userId = (String) request.getSession().getAttribute("UserID");
		String searchParameter = request.getParameter("sSearch");
		Integer pageDisplayStart = Integer.valueOf(request.getParameter("iDisplayStart"));
		Integer pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));
		Integer pageNumber = (pageDisplayStart / pageDisplayLength) + 1;

		String cagetory = "", brand = "", basepack = "", custChainL1 = "", custChainL2 = "", geography = "";
		String offerType = "", modality = "", year = "", moc = "", promoIdVal="";

		if (cagetoryValue == null || cagetoryValue.isEmpty() || (cagetoryValue.equalsIgnoreCase("undefined"))
				|| (cagetoryValue.equalsIgnoreCase("ALL CATEGORIES"))) {
			cagetory = "all";
		} else {
			cagetory = cagetoryValue;
		}
		if (brandValue == null || brandValue.isEmpty() || (brandValue.equalsIgnoreCase("undefined"))
				|| (brandValue.equalsIgnoreCase("ALL BRANDS"))) {
			brand = "all";
		} else {
			brand = brandValue;
		}
		if (basepackValue == null || basepackValue.isEmpty() || (basepackValue.equalsIgnoreCase("undefined"))
				|| (basepackValue.equalsIgnoreCase("All"))) {
			basepack = "all";
		} else {
			basepack = basepackValue;
		}
		if (custChainL1Value == null || custChainL1Value.isEmpty() || (custChainL1Value.equalsIgnoreCase("undefined"))
				|| (custChainL1Value.equalsIgnoreCase("ALL CUSTOMERS")) || (custChainL1Value.equalsIgnoreCase("ALL"))) {
			custChainL1 = "all";
		} else {
			custChainL1 = custChainL1Value;
		}
		if (custChainL2Value == null || custChainL2Value.isEmpty() || (custChainL2Value.equalsIgnoreCase("undefined"))
				|| (custChainL2Value.equalsIgnoreCase("All CUSTOMERS")) || (custChainL2Value.equalsIgnoreCase("ALL"))) {
			custChainL2 = "all";
		} else {
			custChainL2 = custChainL2Value;
		}
		if (geographyValue == null || geographyValue.isEmpty() || (geographyValue.equalsIgnoreCase("undefined"))
				|| (geographyValue.equalsIgnoreCase("ALL INDIA"))) {
			geography = "all";
		} else {
			geography = geographyValue;
		}
		if (offerTypeValue == null || offerTypeValue.isEmpty() || (offerTypeValue.equalsIgnoreCase("undefined"))
				|| (offerTypeValue.equalsIgnoreCase("ALL TYPES"))) {
			offerType = "all";
		} else {
			offerType = offerTypeValue;
		}
		if (modalityValue == null || modalityValue.isEmpty() || (modalityValue.equalsIgnoreCase("undefined"))
				|| (modalityValue.equalsIgnoreCase("ALL MODALITIES"))) {
			modality = "all";
		} else {
			modality = modalityValue;
		}
		if (yearValue == null || yearValue.isEmpty() || (yearValue.equalsIgnoreCase("undefined"))
				|| (yearValue.equalsIgnoreCase("ALL YEARS"))) {
			year = "all";
		} else {
			year = yearValue;
		}
		if (mocValue == null || mocValue.isEmpty() || (mocValue.equalsIgnoreCase("undefined"))
				|| (mocValue.equalsIgnoreCase("FULL YEAR"))) {
			moc = "all";
		} else {
			moc = mocValue;
		}
		if (promoId == null || promoId.isEmpty() || (promoId.equalsIgnoreCase("undefined"))
				|| (promoId.equalsIgnoreCase("ALL PROMOS"))) {
			promoIdVal = "all";
		} else {
			promoIdVal = promoId;
		}

		int rowCount = procoStatusTrackerService.getPromoListRowCount(cagetory, brand, basepack, custChainL1, custChainL2,
				geography, offerType, modality, year, moc, userId, 1,promoIdVal);
		List<PromoListingBean> promoList = procoStatusTrackerService.getPromoTableList((pageDisplayStart + 1),
				(pageNumber * pageDisplayLength), cagetory, brand, basepack, custChainL1, custChainL2, geography,
				offerType, modality, year, moc, userId, 1,promoIdVal,searchParameter);

		long endTime = System.currentTimeMillis();
		logger.info("duration of Promo pagination: "+(endTime-startTime));
		
		PromoListingJsonObject jsonObj = new PromoListingJsonObject();
		jsonObj.setJsonBean(promoList);
		jsonObj.setiTotalDisplayRecords(rowCount);
		jsonObj.setiTotalRecords(rowCount);

		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(jsonObj);
		return json;
	}
	
	
	
	
	@SuppressWarnings({ "resource" })
	@RequestMapping(value = "downloadMeasureReport.htm", method = RequestMethod.POST)
	public ModelAndView downloadMeasureReport(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		logger.info("START downloadMeasureReport():");
		ModelAndView DownExport = getProcoMeasureReportUploadPage(request, model);
		DownExport.addObject("DownError", "Error");
		try {
			String MocYear = (String) request.getParameter("MocYear");
			String MocMonth = (String) request.getParameter("MocMonth");
			if(!MocYear.equals(null) && !MocMonth.equals(null) &&
			   !MocYear.equals("") && !MocMonth.equals("")) {
				DownExport = new ModelAndView("ProcoMeasureExpoExcelView");
				DownExport.addObject("MocMonth", MocMonth);
				DownExport.addObject("MocYear", MocYear);
				DownExport.addObject("Measurelist", procoStatusTrackerService.procoExportMeasureReport(MocYear, MocMonth));
			}
		}  catch (Exception e) {
			logger.debug("Exception: ", e);
		}
		return DownExport;
	}
	
	@SuppressWarnings({ "resource" })
	@RequestMapping(value = "downloadSampleMeasureReport.htm", method = RequestMethod.GET)
	public ModelAndView downloadSampleMeasureReport(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		ModelAndView Sampleimp = new ModelAndView("ProcoMeasureExpoExcelView");
		try {
			Sampleimp.addObject("HeaderMasters", procoStatusTrackerService.getProcoStatusMasterValues() );
			Sampleimp.addObject("IsExport", "yes");
		}  catch (Exception e) {
			logger.debug("Exception: ", e);
		}
		return Sampleimp;
	}
	
	@RequestMapping(value = "downloadPromoStatusTracker.htm", method = RequestMethod.POST)
	public ModelAndView downloadPromoStatusTracker(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		logger.info("START downloadPromoStatusTracker():");
		try {
			String categoryValue = (String) request.getParameter("category");
			String brandValue = (String) request.getParameter("brand");
			String custChainL1Value = "";
			String custChainL2Value = "";
			if (request.getParameterValues("customerChainL1") != null) {
				String[] custL1 = request.getParameterValues("customerChainL1");
				for (int i = 0; i < custL1.length; i++) {
					if (i < custL1.length - 1) {
						custChainL1Value = custChainL1Value + custL1[i] + ",";
					} else {
						custChainL1Value = custChainL1Value + custL1[i];
					}
				}
			} else {
				custChainL1Value = null;
			}

			if (request.getParameterValues("cust-chain") != null) {
				String[] custL2 = request.getParameterValues("cust-chain");
				for (int i = 0; i < custL2.length; i++) {
					if (i < custL2.length - 1) {
						custChainL2Value = custChainL2Value + custL2[i] + ",";
					} else {
						custChainL2Value = custChainL2Value + custL2[i];
					}
				}
			} else {
				custChainL2Value = null;
			}
			String basepackValue = (String) request.getParameter("promoBasepack");
			String offerTypeValue = (String) request.getParameter("offerType");
			String modalityValue = (String) request.getParameter("modality");
			String geographyValue = (String) request.getParameter("geography");
			String yearValue = (String) request.getParameter("year");
			String mocValue = (String) request.getParameter("moc");
			String promoIdValue = (String) request.getParameter("promoIds");

			InputStream is;
			String downloadLink = "", absoluteFilePath = "";
			List<ArrayList<String>> downloadedData = null;
			absoluteFilePath = FilePaths.FILE_TEMPDOWNLOAD_PATH;
			String fileName = UploadUtil.getFileName("Promotion.Error.file", "",
					CommonUtils.getCurrDateTime_YYYY_MM_DD_HHMMSS());
			String downloadFileName = absoluteFilePath + fileName;
			String userId = (String) request.getSession().getAttribute("UserID");
			String cagetory = "", brand = "", basepack = "", custChainL1 = "", custChainL2 = "", geography = "";
			String offerType = "", modality = "", year = "", moc = "", promoId;
			if (categoryValue == null || categoryValue.isEmpty() || (categoryValue.equalsIgnoreCase("undefined"))
					|| (categoryValue.equalsIgnoreCase("ALL CATEGORIES"))) {
				cagetory = "all";
			} else {
				cagetory = categoryValue;
			}
			if (brandValue == null || brandValue.isEmpty() || (brandValue.equalsIgnoreCase("undefined"))
					|| (brandValue.equalsIgnoreCase("ALL BRANDS"))) {
				brand = "all";
			} else {
				brand = brandValue;
			}
			if (basepackValue == null || basepackValue.isEmpty() || (basepackValue.equalsIgnoreCase("undefined"))
					|| (basepackValue.equalsIgnoreCase("All"))) {
				basepack = "all";
			} else {
				basepack = basepackValue;
			}
			if (custChainL1Value == null || custChainL1Value.isEmpty()
					|| (custChainL1Value.equalsIgnoreCase("undefined"))
					|| (custChainL1Value.equalsIgnoreCase("ALL CUSTOMERS"))) {
				custChainL1 = "all";
			} else {
				custChainL1 = custChainL1Value;
			}
			if (custChainL2Value == null || custChainL2Value.isEmpty()
					|| (custChainL2Value.equalsIgnoreCase("undefined"))
					|| (custChainL2Value.equalsIgnoreCase("All CUSTOMERS"))) {
				custChainL2 = "all";
			} else {
				custChainL2 = custChainL2Value;
			}
			if (geographyValue == null || geographyValue.isEmpty() || (geographyValue.equalsIgnoreCase("undefined"))
					|| (geographyValue.equalsIgnoreCase("ALL INDIA"))) {
				geography = "all";
			} else {
				geography = geographyValue;
			}
			if (offerTypeValue == null || offerTypeValue.isEmpty() || (offerTypeValue.equalsIgnoreCase("undefined"))
					|| (offerTypeValue.equalsIgnoreCase("ALL TYPES"))) {
				offerType = "all";
			} else {
				offerType = offerTypeValue;
			}
			if (modalityValue == null || modalityValue.isEmpty() || (modalityValue.equalsIgnoreCase("undefined"))
					|| (modalityValue.equalsIgnoreCase("ALL MODALITIES"))) {
				modality = "all";
			} else {
				modality = modalityValue;
			}
			if (yearValue == null || yearValue.isEmpty() || (yearValue.equalsIgnoreCase("undefined"))
					|| (yearValue.equalsIgnoreCase("ALL YEARS"))) {
				year = "all";
			} else {
				year = yearValue;
			}
			if (mocValue == null || mocValue.isEmpty() || (mocValue.equalsIgnoreCase("undefined"))
					|| (mocValue.equalsIgnoreCase("FULL YEAR"))) {
				moc = "all";
			} else {
				moc = mocValue;
			}
			if (promoIdValue == null || promoIdValue.isEmpty() || (promoIdValue.equalsIgnoreCase("undefined"))
					|| (promoIdValue.equalsIgnoreCase("ALL PROMOS"))) {
				promoId = "all";
			} else {
				promoId = promoIdValue;
			}
			ArrayList<String> headerList = procoStatusTrackerService.getHeaderListForPromoStatusTracker();
			downloadedData = procoStatusTrackerService.getPromotionStatusTracker(headerList, cagetory, brand, basepack, custChainL1,
					custChainL2, geography, offerType, modality, year, moc, userId, 1,promoId);
			if (downloadedData != null) {
				UploadUtil.writeXLSFile(downloadFileName, downloadedData, null,".xls");
				downloadLink = downloadFileName + ".xls";
				is = new FileInputStream(new File(downloadLink));
				// copy it to response's OutputStream
				response.setContentType("application/force-download");
				response.setHeader("Content-Disposition", "attachment; filename=PromoStatusTrackerFile"
						+ CommonUtils.getCurrDateTime_YYYY_MM_DD_HH_MM_SS_WithOutA() + ".xls");
				IOUtils.copy(is, response.getOutputStream());
				response.flushBuffer();
			}
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			return null;
		}
		return null;
	}
	
	
	@RequestMapping(value = "downloadCustomerPortalPromoStatusTracker.htm", method = RequestMethod.POST)
	public ModelAndView downloadCustomerportalStatusTracker(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		try {
			Calendar calendar = Calendar.getInstance();
			int year = calendar.get(Calendar.YEAR);
			int Month = calendar.get(Calendar.MONTH);
			int day   = calendar.get(Calendar.DATE);
			String MocWhr = "";
			String YerWhr = "";
			Month = Month + 1;
			int DecMonth = Month - 1;
			if( Month >= 10 ) {
				for( int i = DecMonth; i <= (Month + 4); i++ ) {
					if(i > 12) {
						if(!MocWhr.equals("")) {
							MocWhr += " OR ";
						}
						MocWhr += "(A.YEAR, A.MOC) = ('"+(year+1)+"', 'MOC"+( i - 12 )+"')";
					} else {
						if(!MocWhr.equals("")) {
							MocWhr += " OR ";
						}
						MocWhr += "(A.YEAR, A.MOC) = ('"+(year)+"', 'MOC"+i+"')";
					}
				}
			} else if ( Month <= 2) {
				for( int i = DecMonth; i <= (Month + 4); i++ ) {
					if(i < 1) {
						if(!MocWhr.equals("")) {
							MocWhr += " OR ";
						}
						MocWhr += "(A.YEAR, A.MOC) = ('"+(year-1)+"', 'MOC"+( 12 + i )+"')";
					} else {
						if(!MocWhr.equals("")) {
							MocWhr += " OR ";
						}
						MocWhr += "(A.YEAR, A.MOC) = ('"+(year)+"', 'MOC"+( i )+"')";
					}
				}
			} else {
				for( int i = DecMonth; i <= (Month + 4); i++ ) {
					if(!MocWhr.equals("")) {
						MocWhr += " OR ";
					}
					MocWhr += "(A.YEAR, A.MOC) = ('"+(year)+"', 'MOC"+( i )+"')";
				}
			}
			
			
			MocWhr += " ";
			String userId = (String) request.getSession().getAttribute("UserID");
			
			ArrayList<String> headerList = procoStatusTrackerService.getHeaderListForPromoStatusTracker(userId, false);
			List<ArrayList<String>> downloadedData = procoStatusTrackerService.getPromotionStatusTrackerCustomerPortal(headerList, "all", "all", "all", "all",
					"all", "all", "all", "all", YerWhr, MocWhr, userId, 1,"all");
			if (downloadedData != null) {
				response.setContentType("text/csv");
		        response.setHeader("Content-Disposition", "attachment; filename=COE_PROMO_DOWNLOAD_"+ CommonUtils.getCurrentDate_YYYY_MM_DD() +".csv;");
		        ServletOutputStream os = response.getOutputStream();
				/* Multi-cluster row starts here */
				//List<ArrayList<String>> downCusData = new ArrayList<ArrayList<String>>();
				boolean isNext = false;
				for( int v = 0; v < downloadedData.size(); v++ ) {
					ArrayList<String> downSingCusData = null;
					downSingCusData = downloadedData.get(v);
					
					
					//downCusData.add(downSingCusData);
					String str = "\"" + String.join("\",\"", downSingCusData) + "\"";
					os.write(str.getBytes("UTF-8"));  
					os.write("\r\n".getBytes("UTF-8"));
				}
				os.close();
				
				/* Multi-cluster row ends here */
				
				
		              
		        response.flushBuffer();
			}
		}
		catch (Exception e) {
			logger.debug("Exception: ", e);
			return null;
		}
		return null;
	}
	
	
	@RequestMapping(value = "uploadPromoStatusTracker.htm", method = RequestMethod.POST)
	public @ResponseBody String uploadFile(
			@ModelAttribute("promoListingBean") PromoListingBean promoListingBean, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		String savedData = null;
		CommonPropUtils commUtils = CommonPropUtils.getInstance();
//		String userId = (String) request.getSession().getAttribute("UserID");
		MultipartFile file = promoListingBean.getFile();
		String fileName = file.getOriginalFilename();
		PromoListingBean[] promoListingBeanArray = null;
		String filepath = FilePaths.FILE_TEMPUPLOAD_PATH;
		fileName = filepath + fileName;
		try {
			if (!CommonUtils.isFileEmpty(file)) {
				if (CommonUtils.isFileSizeExceeds(file)) {
					model.addAttribute("errorMsg", commUtils.getProperty("File.Size.Exceeds"));
					return"File Size Exceeds";
				} else {

					if (UploadUtil.movefile(file, fileName)) {

						int excelColumnCount = UploadUtil.readExcelCellCount(fileName);
						if(excelColumnCount == 31) {
							List<PromoListingBean> promoListingBeanList = procoStatusTrackerService.readProcoStatusTracker(fileName);
							promoListingBeanArray = promoListingBeanList.toArray(new PromoListingBean[promoListingBeanList.size()]);

							if (promoListingBeanArray.length == 0) {
								model.addAttribute("FILE_STATUS", "ERROR");
								model.addAttribute("errorMsg2", "File does not contain data");
								return "File does not contain relevant data";
							} else {
								savedData = procoStatusTrackerService.uploadPromoStatusTracker(promoListingBeanArray);
								if (savedData != null && savedData.equals("SUCCESS_FILE")) {
									model.addAttribute("success", commUtils.getProperty("File.Upload.Success"));
									return "File Upload is Successful";
								} /*else if(savedData.equals("")) {
									return "File contain the empty rows so please remove the empty rows"; 
								}*/
							}
						}else {
							return "Column count is not match with expected";
						}

					}else {
						return "File movement process is UnSuccessful";
					}
				}
			}
			else {
				model.addAttribute("message", commUtils.getProperty("File.Empty"));
				return "File is Empty";
			}
			/*if (savedData.equals("ERROR_FILE")) {
				model.addAttribute("FILE_STATUS", "ERROR_FILE");
				return "Error while uploading file";
			} else if (savedData.equals("ERROR")) {
				model.addAttribute("errorMsg", "File Upload is UnSuccessful.");
				return "Error while uploading file";
			} else {
				model.addAttribute("FILE_STATUS", "SUCCESS_FILE");
				return "File Upload is Successful.";
			}*/
			
			return "File Upload is UnSuccessful";

		} catch (Exception e) {
			logger.error("Exception: ", e);
			model.addAttribute("errorMsg", "Error while uploading file");
			return "Error while uploading file";
		} catch (Throwable e) {
			logger.error("Exception: ", e);
			model.addAttribute("errorMsg", "Error while uploading file");
			return "Error while uploading file";
		}
	}
	
	@RequestMapping(value = "ProcoMeasureReportUploadPage.htm", method = RequestMethod.GET)
	public ModelAndView getProcoMeasureReportUploadPage(HttpServletRequest request, Model model) {
		String roleId = (String) request.getSession().getAttribute("roleId");
		String MeasureUploadResponse = (String) request.getSession().getAttribute("MeasureUploadResponse");
		model.addAttribute("roleId", roleId);
		model.addAttribute("MeasureUploadResponse", MeasureUploadResponse);
		 request.getSession().removeAttribute("MeasureUploadResponse");
		
		model.addAttribute( "DownloadMocList", procoStatusTrackerService.getMocList());
		
		return new ModelAndView("proco/proco_measure_upload");
	}
	
	@RequestMapping(value = "uploadProcoMeasureReport.htm", method = RequestMethod.POST)
	public @ResponseBody ModelAndView uploadProcoMeasureReport(
			@ModelAttribute("promoMeasureReportBean") PromoMeasureReportBean promoMeasureReportBean, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		String savedData = null;
		CommonPropUtils commUtils = CommonPropUtils.getInstance();
//		String userId = (String) request.getSession().getAttribute("UserID");
		MultipartFile file = promoMeasureReportBean.getFile();
		String fileName = file.getOriginalFilename();
		PromoMeasureReportBean[] promoMeasureReportBeanArray = null;
		String filepath = FilePaths.FILE_TEMPUPLOAD_PATH;
		fileName = filepath + fileName;
		String ModRes = "";
		try {
			if (!CommonUtils.isFileEmpty(file)) {
				if (CommonUtils.isMearsureReportFileSizeExceeds(file)) {
					model.addAttribute("errorMsg", commUtils.getProperty("File.Size.Exceeds"));
					ModRes = "File Size Exceeds";
				} else {

					if (UploadUtil.movefile(file, fileName)) {
						int excelColumnCount = UploadUtil.readExcelCellCount(fileName);
						if(excelColumnCount == 63) {
							List<PromoMeasureReportBean> promoMeasureReportBeanList = procoStatusTrackerService.readPromoMeasureReport(fileName);
							promoMeasureReportBeanArray = promoMeasureReportBeanList.toArray(new PromoMeasureReportBean[promoMeasureReportBeanList.size()]);
							if (promoMeasureReportBeanArray.length == 0) {
								model.addAttribute("FILE_STATUS", "ERROR");
								model.addAttribute("errorMsg2", "File does not contain data");
								ModRes = "File does not contain relevant data";
							} else {
								savedData = procoStatusTrackerService.uploadPromoMeasureReport(promoMeasureReportBeanArray);
								if (savedData != null && savedData.equals("SUCCESS_FILE")) {
									model.addAttribute("success", commUtils.getProperty("File.Upload.Success"));
									ModRes = "File Upload is Successful";
								} /*else if(savedData.equals("")) {
									return "File contain the empty rows so please remove the empty rows"; 
								}*/
							}
						}else {
							ModRes = "Column count is not match with expected";
						}

					}else {
						ModRes = "File movement process is UnSuccessful";
					}
				}
			}
			else {
				model.addAttribute("message", commUtils.getProperty("File.Empty"));
				ModRes = "File is Empty";
			}
			if (savedData.equals("ERROR_FILE")) {
				model.addAttribute("FILE_STATUS", "ERROR_FILE");
				ModRes = "Error while uploading file";
			} 
			
			//ModRes = "File Upload is UnSuccessful";

		} catch (Exception e) {
			logger.error("Exception: ", e);
			model.addAttribute("errorMsg", "Error while uploading file");
			ModRes = "Error while uploading file";
		} catch (Throwable e) {
			logger.error("Exception: ", e);
			model.addAttribute("errorMsg", "Error while uploading file");
			ModRes = "Error while uploading file";
		}
		
		request.getSession().setAttribute("MeasureUploadResponse", ModRes);
		
		return getProcoMeasureReportUploadPage(request, model);
	}
	
	@RequestMapping(value = "downloadProcoMeasureReportErrorFile.htm", method = RequestMethod.GET)
	public @ResponseBody ModelAndView downloadProcoMeasureReportErrorFile(
			@ModelAttribute("promoMeasureReportBean") PromoMeasureReportBean promoMeasureReportBean, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		InputStream is;
		String downloadLink = "", absoluteFilePath = "";
		List<ArrayList<String>> downloadedData = null;
		String userId = (String) request.getSession().getAttribute("UserID");
		ArrayList<String> headerDetail = procoStatusTrackerService.getHeaderListForProcoMeasureReport();
		absoluteFilePath = FilePaths.FILE_TEMPDOWNLOAD_PATH;
		String fileName = UploadUtil.getFileName("ProcoMeasureReport.Error.file", "",
				CommonUtils.getCurrDateTime_YYYY_MM_DD_HHMMSS());
		String downloadFileName = absoluteFilePath + fileName;
		downloadedData = procoStatusTrackerService.getProcoMeasureReportErrorDetails(headerDetail, userId);
	
		try {
			UploadUtil.writeXLSFile(downloadFileName, downloadedData, null,".xls");
			downloadLink = downloadFileName + ".xls";
			is = new FileInputStream(new File(downloadLink));
			response.setContentType("application/force-download");
			response.setHeader("Content-Disposition", "attachment; filename=PromoMeasureReport_ErrorFile"
					+ CommonUtils.getCurrDateTime_YYYY_MM_DD_HH_MM_SS_WithOutA() + ".xls");
			IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();
		} catch (FileNotFoundException e) {
			logger.error("Exception: ", e);
			// e.printStackTrace();
			return null;
		} catch (IOException e) {
			logger.error("Exception: ", e);
			// e.printStackTrace();
			return null;
		}
		return null;
		// return new ModelAndView("productsPage");
	}
}


