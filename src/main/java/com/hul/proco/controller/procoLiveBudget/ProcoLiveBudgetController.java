package com.hul.proco.controller.procoLiveBudget;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hul.launch.web.util.CommonPropUtils;
import com.hul.launch.web.util.CommonUtils;
import com.hul.launch.web.util.FilePaths;
import com.hul.launch.web.util.UploadUtil;
import com.hul.proco.controller.promocr.PromoCrJsonObject;
import com.hul.proco.excelreader.exom.ExOM;
import org.apache.commons.io.IOUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;


/*
 *  Change name: Creating new file for budget details in proco
 *  Description: This controller will handle all the budget request 
 *  Developer: Kavitha Dayyala
 *  Date: 29/03/2023 
 */
@Controller
public class ProcoLiveBudgetController {
	
	@Autowired
	public ProcoLiveBudgetService procoLiveBudgetService;
	
	static Logger logger=Logger.getLogger(ProcoLiveBudgetController.class);
	
	
	@GetMapping(value="procoBudget.htm")
	public ModelAndView mainPageForm(Model modelObj) {
		return new ModelAndView("proco/proco_budget");
	}
	
	@GetMapping(value="procoBudgetTme.htm")
	public ModelAndView mainPageFormTme(Model modelObj) {
		return new ModelAndView("proco/proco_budget_tme");
	}
	
	
	@RequestMapping(value = "procoLiveBudgetUpload.htm", method = RequestMethod.POST)
	public @ResponseBody String procoLiveBudgetUpload(@ModelAttribute("BudgetHolderBean") BudgetHolderBean budgetHolderBean,
			Model model, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws IOException{
		String save_data = null;
		String userID = (String) httpServletRequest.getSession().getAttribute("UserID");
		CommonPropUtils commUtils = CommonPropUtils.getInstance();
		MultipartFile file = budgetHolderBean.getFile();
		String fileName = file.getOriginalFilename();
		BudgetHolderBean[] beanArray = null;
		String filepath = FilePaths.FILE_TEMPUPLOAD_PATH;
		fileName = filepath + fileName;
		try {
			if (!CommonUtils.isFileEmpty(file)) {
				if (CommonUtils.isFileProcoSizeExceeds(file)) {
					model.addAttribute("FILE_STAUS", "FILE_SIZE_EXCEED");
					return "FILE_SIZE_EXCEED";
				} else if (UploadUtil.movefile(file, fileName)) {
					Map<String, List<Object>> map = null;
					map = ExOM.mapFromExcel(new File(fileName)).to(BudgetHolderBean.class).map(24, false, null);
					if (map.isEmpty()) {
						model.addAttribute("FILE_STAUS", "FILE_EMPTY");
						return "FILE_EMPTY";
					}
					if (map.containsKey("ERROR")) {
						model.addAttribute("FILE_STAUS", "CHECK_COL_MISMATCH");

						return "CHECK_COL_MISMATCH";
					} else if (map.containsKey("DATA")) {
						List<?> datafromexcel = map.get("DATA");
						beanArray = (BudgetHolderBean[]) datafromexcel
								.toArray(new BudgetHolderBean[datafromexcel.size()]);
						
						save_data = procoLiveBudgetService.budgetHolderData(beanArray, userID);
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
				save_data="EXCEL_UPLOADED";
			} else {
				model.addAttribute("FILE_STAUS", "EXCEL_NOT_UPLOADED");
				save_data = "EXCEL_NOT_UPLOADED";
			}

		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		} catch (Throwable e) {
			logger.error(e);
			e.printStackTrace();
		}
		return save_data;
		}
	
	
	@GetMapping(value="procoLiveBudgetDownload.htm")
	public @ResponseBody ModelAndView procoLiveBudgetDownload(
			@ModelAttribute("BudgetHolderBean") BudgetHolderBean budgetHolderBean,
			Model model, HttpServletRequest httpServletRequest,
			HttpServletResponse response)  {
		InputStream is;
		String downloadLink = "", absoluteFilePath = "";
		List<ArrayList<String>> downloadedData = null;
		

			
			absoluteFilePath = FilePaths.FILE_TEMPDOWNLOAD_PATH;
			String fileName = UploadUtil.getFileName("PPM.COE.REMARKS.file", "",
					CommonUtils.getCurrDateTime_YYYY_MM_DD_HHMMSS());
			String downloadFileName = absoluteFilePath + fileName;
			ArrayList<String> headerDetails = procoLiveBudgetService.procoLiveBudgetDownloadHeaderList();

			 downloadedData = procoLiveBudgetService.procoLiveBudgetDownload(headerDetails);

		try {
			 if (downloadedData != null) {
				UploadUtil.writeXLSXFile(downloadFileName, downloadedData, null,".xlsx");
				downloadLink = downloadFileName + ".xlsx";
				is = new FileInputStream(new File(downloadLink));
				response.setContentType("application/force-download");
				response.setHeader("Content-Disposition", "attachment; filename=PROCO_LIVE_BUDGET_DOWNLOADFILE"
						+ CommonUtils.getCurrDateTime_YYYY_MM_DD_HH_MM_SS_WithOutA() + ".xlsx");
				IOUtils.copy(is, response.getOutputStream());
				response.flushBuffer();
			}} catch (FileNotFoundException e) {
				logger.error("Exception: ", e);
				return null;
			} catch (IOException e) {
				logger.error("Exception: ", e);
				return null;
		
			}
		return null;
		
	}
	@RequestMapping(value = "procoLiveBudgetPagination.htm", method = RequestMethod.GET, produces = "application/json", headers = "Accept=*/*")
	public @ResponseBody String promoListingPagination(HttpServletRequest request) {
		String roleId = (String) request.getSession().getAttribute("roleId");
		String userId = (String) request.getSession().getAttribute("UserID");
		Integer pageDisplayStart = Integer.valueOf(request.getParameter("iDisplayStart"));
		Integer pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));
		Integer pageNumber = (pageDisplayStart / pageDisplayLength) + 1;
		String searchParameter = request.getParameter("sSearch");
		int rowCount = procoLiveBudgetService.getProcoBudgetRowCount(searchParameter);
		List<BudgetHolderBean> promoList = procoLiveBudgetService.getProcoBudgetTableList((pageDisplayStart + 1),(pageNumber * pageDisplayLength),searchParameter);
		ProcoJsonObject jsonObj = new ProcoJsonObject();
		jsonObj.setJsonBean(promoList);
		jsonObj.setiTotalDisplayRecords(rowCount);
		jsonObj.setiTotalRecords(rowCount);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(jsonObj);
		return json;
	}
		
	
}
	
	

	
	

	
	


