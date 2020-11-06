package com.hul.proco.controller.promocr;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hul.proco.controller.createpromo.CreatePromoService;

@Controller
public class PromoCrController {
	
	@Autowired
	private PromoCrService promoCrService;
	
	@Autowired
	private CreatePromoService createPromoService;
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "promoCr.htm", method = RequestMethod.GET)
	public ModelAndView getProcoPromoListingPage(HttpServletRequest request, Model model) {
		String roleId = (String) request.getSession().getAttribute("roleId");
		model.addAttribute("roleId", roleId);
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
		return new ModelAndView("proco/proco_promo_cr");
	}
	
	@RequestMapping(value = "promoCrPagination.htm", method = RequestMethod.GET, produces = "application/json", headers = "Accept=*/*")
	public @ResponseBody String promoListingPagination(@RequestParam("category") String cagetoryValue,
			@RequestParam("brand") String brandValue, @RequestParam("custChainL1") String custChainL1Value,
			@RequestParam("offerType") String offerTypeValue, @RequestParam("modality") String modalityValue,
			@RequestParam("year") String yearValue, @RequestParam("custChainL2") String custChainL2Value,
			@RequestParam("basepack") String basepackValue, @RequestParam("geography") String geographyValue,
			@RequestParam("moc") String mocValue, HttpServletRequest request) {
		String roleId = (String) request.getSession().getAttribute("roleId");
		String userId = (String) request.getSession().getAttribute("UserID");
		Integer pageDisplayStart = Integer.valueOf(request.getParameter("iDisplayStart"));
		Integer pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));
		Integer pageNumber = (pageDisplayStart / pageDisplayLength) + 1;

		String cagetory = "", brand = "", basepack = "", custChainL1 = "", custChainL2 = "", geography = "";
		String offerType = "", modality = "", year = "", moc = "";

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

		int rowCount = promoCrService.getPromoListRowCount(cagetory, brand, basepack, custChainL1, custChainL2,
				geography, offerType, modality, year, moc, userId, 1,roleId);
		List<PromoCrBean> promoList = promoCrService.getPromoTableList((pageDisplayStart + 1),
				(pageNumber * pageDisplayLength), cagetory, brand, basepack, custChainL1, custChainL2, geography,
				offerType, modality, year, moc, userId, 1,roleId);

		PromoCrJsonObject jsonObj = new PromoCrJsonObject();
		jsonObj.setJsonBean(promoList);
		jsonObj.setiTotalDisplayRecords(rowCount);
		jsonObj.setiTotalRecords(rowCount);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(jsonObj);
		return json;
	}
	
	@RequestMapping(value = "approveCr.htm", method = RequestMethod.GET)
	public ModelAndView approveCr(@RequestParam("promoid") String promoId, Model model,
			HttpServletRequest request) {
		String roleId = (String) request.getSession().getAttribute("roleId");
		String userId = (String) request.getSession().getAttribute("UserID");
		String res = promoCrService.approveCr(promoId, userId, roleId);
		if(res!=null){
			model.addAttribute("success", "CRs approved successfully.");
			model.addAttribute("FILE_STATUS", "SUCCESS_FILE");
		}else{
			model.addAttribute("errorMsg", "Failed to approve CRs.");
		}
		model.addAttribute("roleId", roleId);
		setModelAttributes(model);
		return new ModelAndView("proco/proco_promo_cr");
	}
	
	@RequestMapping(value = "rejectCr.htm", method = RequestMethod.POST)
	public ModelAndView rejectCr(Model model,HttpServletRequest request) {
		String roleId = (String) request.getSession().getAttribute("roleId");
		String userId = (String) request.getSession().getAttribute("UserID");
		String reason = request.getParameter("remark");
		String promo = request.getParameter("promoIdList");
		String rejectCr = promoCrService.rejectCr(promo, userId, roleId, reason);
		if(rejectCr!=null){
			model.addAttribute("success", "CRs rejected successfully.");
			model.addAttribute("FILE_STATUS", "SUCCESS_FILE");
		}else{
			model.addAttribute("errorMsg", "Failed to reject CRs.");
		}
		model.addAttribute("roleId", roleId);
		setModelAttributes(model);
		return new ModelAndView("proco/proco_promo_cr");
	}
	
	@SuppressWarnings("unchecked")
	private void setModelAttributes(Model model) {
		List<String> customerChainL1 = createPromoService.getCustomerChainL1();
		List<String> offerTypes = createPromoService.getOfferTypes();
		String geographyJson = createPromoService.getGeography(false);
		Map<Integer, String> modality = createPromoService.getModality();
		Map<String, Object> yearAndMoc = createPromoService.getYearAndMoc(false);
		List<String> yearList = (List<String>) yearAndMoc.get("years");
		String mocJson = (String) yearAndMoc.get("moc");
		List<String> category = promoCrService.getAllCategories();
		List<String> brand = promoCrService.getAllBrands();
		List<String> basepacks = promoCrService.getAllBasepacks();
		model.addAttribute("mocJson", mocJson);
		model.addAttribute("years", yearList);
		model.addAttribute("customerChainL1", customerChainL1);
		model.addAttribute("offerTypes", offerTypes);
		model.addAttribute("geographyJson", geographyJson);
		model.addAttribute("modality", modality);
		model.addAttribute("categories", category);
		model.addAttribute("brands", brand);
		model.addAttribute("basepacks", basepacks);
	}

}
