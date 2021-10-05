package com.hul.proco.controller.disaggregatepromo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
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
public class DissagregationController {

	private Logger logger = Logger.getLogger(DissagregationController.class);

	@Autowired
	private DisaggregationService disaggregationService;

	@Autowired
	private CreatePromoService createPromoService;

	@RequestMapping(value = "disaggregationPagination.htm", method = RequestMethod.GET, produces = "application/json", headers = "Accept=*/*")
	public @ResponseBody String promoListingPagination(@RequestParam("category") String cagetoryValue,
			@RequestParam("brand") String brandValue, @RequestParam("custChainL1") String custChainL1Value,
			@RequestParam("offerType") String offerTypeValue, @RequestParam("modality") String modalityValue,
			@RequestParam("year") String yearValue, @RequestParam("custChainL2") String custChainL2Value,
			@RequestParam("basepack") String basepackValue, @RequestParam("moc") String mocValue,
			HttpServletRequest request) {

		String userId = (String) request.getSession().getAttribute("UserID");
		Integer pageDisplayStart = Integer.valueOf(request.getParameter("iDisplayStart"));
		Integer pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));
		Integer pageNumber = (pageDisplayStart / pageDisplayLength) + 1;

		String cagetory = "", brand = "", basepack = "", custChainL1 = "", custChainL2 = "";
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
		if (offerTypeValue.isEmpty() || null == offerTypeValue || (offerTypeValue.equalsIgnoreCase("undefined"))
				|| (offerTypeValue.equalsIgnoreCase("ALL TYPES"))) {
			offerType = "all";
		} else {
			offerType = offerTypeValue;
		}
		if (modalityValue.isEmpty() || null == modalityValue || (modalityValue.equalsIgnoreCase("undefined"))
				|| (modalityValue.equalsIgnoreCase("ALL MODALITIES"))) {
			modality = "all";
		} else {
			modality = modalityValue;
		}
		if (yearValue.isEmpty() || null == yearValue || (yearValue.equalsIgnoreCase("undefined"))
				|| (yearValue.equalsIgnoreCase("ALL YEAR"))) {
			year = "all";
		} else {
			year = yearValue;
		}
		if (mocValue.isEmpty() || null == mocValue || (mocValue.equalsIgnoreCase("undefined"))
				|| (mocValue.equalsIgnoreCase("FULL YEAR"))) {
			moc = "all";
		} else {
			moc = mocValue;
		}

		int rowCount = disaggregationService.getDisaggregationRowCount(cagetory, brand, basepack, custChainL1,
				custChainL2, offerType, modality, year, moc, userId);
		List<DisaggregationBean> promoList = disaggregationService.getDisaggregationTableList((pageDisplayStart + 1),
				(pageNumber * pageDisplayLength), cagetory, brand, basepack, custChainL1, custChainL2, offerType,
				modality, year, moc, userId);

		DissaggregationJsonObject jsonObj = new DissaggregationJsonObject();
		jsonObj.setAaData(promoList);
		jsonObj.setiTotalDisplayRecords(rowCount);
		jsonObj.setiTotalRecords(rowCount);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(jsonObj);
		return json;
	}

	@RequestMapping(value = "disaggregatePromos.htm", method = RequestMethod.POST)
	public ModelAndView disaggregatePromos(HttpServletRequest request, Model model) {
		String[] promoId = request.getParameterValues("promoId");
		String[] mocs = request.getParameterValues("mocs");
		String roleId = (String) request.getSession().getAttribute("roleId");
		String userId = (String) request.getSession().getAttribute("UserID");
		if (promoId == null || promoId.length == 0) {
			model.addAttribute("errorMsg", "Please select atleast 1 promo to disaggregate.");
			setModelAttributes(model,userId);
			return new ModelAndView("proco/proco_disaggregation");
		}
		String res = disaggregationService.disaggregatePromos(promoId, mocs,userId);
		if (res.equals("")) {
			model.addAttribute("success", promoId.length + " promos disaggregated successfully.");
		} else {
			model.addAttribute("errorMsg", res);
		}
		model.addAttribute("roleId", roleId);
		setModelAttributes(model,userId);
		return new ModelAndView("proco/proco_disaggregation");
	}
	
	// New implementation for SUBMIT TO KAM added by harsha
	@RequestMapping(value = "disagregatedPromoskamsubmission.htm", method = RequestMethod.POST)
	public @ResponseBody String disagregatedPromoskamsubmission(HttpServletRequest request, Model model) {
		String roleId = (String) request.getSession().getAttribute("roleId");
		String userId = (String) request.getSession().getAttribute("UserID");
		
		Gson gson=new Gson();
		
		String res = disaggregationService.disagregatedpromoskamsubmission();

		if (res.equals("")) {
			res = "Success";
		} else {
			res = "Error";
		}
		return gson.toJson(res);
	}
	

	@SuppressWarnings("unchecked")
	private void setModelAttributes(Model model,String userId) {
		List<String> customerChainL1 = createPromoService.getCustomerChainL1();
		List<String> offerTypes = createPromoService.getOfferTypes();
		String geographyJson = createPromoService.getGeography(false);
		Map<Integer, String> modality = createPromoService.getModality();
		Map<String, Object> yearAndMoc = createPromoService.getYearAndMoc(false);
		List<String> category = createPromoService.getAllCategories(userId);
		List<String> brand = createPromoService.getAllBrands(userId);
		List<String> basepacks = createPromoService.getAllBasepacks(userId);
		List<String> yearList = (List<String>) yearAndMoc.get("years");
		String mocJson = (String) yearAndMoc.get("moc");
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

	@RequestMapping(value = "addDepotPage.htm", method = RequestMethod.GET)
	private ModelAndView getAddDepotPage(Model modal) {
		return new ModelAndView("proco/adddepot");
	}

	@RequestMapping(value = "getDepotForAddDepot.htm", method = RequestMethod.POST)
	private @ResponseBody String getDepotForAddDepot(@RequestParam String promoId,@RequestParam String branch,@RequestParam String cluster) {
		List<String> depotList = new ArrayList<>();
		Gson gson=new Gson();
		try {
			depotList = disaggregationService.getDepotForAddDepot(promoId,branch,cluster);
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			return null;
		}
		return gson.toJson(depotList);
	}
	
	@RequestMapping(value = "getBranchForAddDepot.htm", method = RequestMethod.POST)
	private @ResponseBody String getBranchForAddDepot() {
		List<String> branchList = new ArrayList<>();
		Gson gson=new Gson();
		try {
			branchList = disaggregationService.getBranchForAddDepot();
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			return null;
		}
		return gson.toJson(branchList);
	}
	
	@RequestMapping(value = "getClusterForAddDepot.htm", method = RequestMethod.POST)
	private @ResponseBody String getClusterForAddDepot(@RequestParam String branch) {
		List<String> clusterList = new ArrayList<>();
		Gson gson=new Gson();
		try {
			clusterList = disaggregationService.getClusterForAddDepot(branch);
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			return null;
		}
		return gson.toJson(clusterList);
	}
	
	@RequestMapping(value = "saveDepotForAddDepot.htm", method = RequestMethod.POST)
	private @ResponseBody String saveDepotForAddDepot(HttpServletRequest request, @RequestParam String depot,@RequestParam String branch,@RequestParam String cluster,@RequestParam int quantity,@RequestParam String promoId) {
		String res = "";
		Gson gson=new Gson();
		String userId = (String) request.getSession().getAttribute("UserID");
		try {
			res = disaggregationService.saveDepotForAddDepot(promoId, branch, cluster, depot, quantity,userId);
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			return res;
		}
		return gson.toJson(res);
	}
}
