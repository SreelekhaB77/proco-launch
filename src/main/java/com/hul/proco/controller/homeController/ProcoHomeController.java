package com.hul.proco.controller.homeController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hul.proco.controller.createpromo.CreatePromoService;
import com.hul.proco.controller.createpromo.CreatePromotionBean;
import com.hul.proco.controller.disaggregatepromo.DisaggregationService;
import com.hul.proco.controller.listingPromo.PromoListingService;
import com.hul.proco.controller.promocr.PromoCrService;

@Controller
public class ProcoHomeController {

	@Autowired
	public CreatePromoService createPromoService;
	
	@Autowired 
	public PromoListingService promoListingService;
	
	@Autowired
	private PromoCrService promoCrService;
	
	@Autowired
	public DisaggregationService disaggregationService;
	

	/*@Autowired
	private CollaborationService collaborationService;*/

	static Logger logger = Logger.getLogger(ProcoHomeController.class);

	@RequestMapping(value = "procoHome.htm", method = RequestMethod.GET)
	public ModelAndView getProcoHomePage(HttpServletRequest request, Model model) {
		//Harsha's implementation for logintool
		String id=(String)request.getSession().getAttribute("UserID");
		String role=(String)request.getSession().getAttribute("roleId");
		promoCrService.insertToportalUsage(id, role, "PROCO");
		//Harsha's Logic End's here 
		String roleId = (String) request.getSession().getAttribute("roleId");
		model.addAttribute("roleId", roleId);
		return new ModelAndView("proco/proco_home");
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "promoListing.htm", method = RequestMethod.GET)
	public ModelAndView getProcoPromoListingPage(HttpServletRequest request, Model model) {
		String roleId = (String) request.getSession().getAttribute("roleId");
		String userId = (String) request.getSession().getAttribute("UserID");
		//Harsha's implementation for logintool
		promoCrService.insertToportalUsage(userId, roleId, "PROCO");
		//Harsha's Logic End's here 
		model.addAttribute("roleId", roleId);
		if(roleId.equalsIgnoreCase("KAM")){
			List<String> customerChainL1 = createPromoService.getCustomerChainL1(userId);
			model.addAttribute("customerChainL1", customerChainL1);
		}else{
			List<String> customerChainL1 = createPromoService.getCustomerChainL1();
			model.addAttribute("customerChainL1", customerChainL1);
		}
		List<String> offerTypes = createPromoService.getOfferTypes();
		Map<Integer, String> modality = createPromoService.getModality();
		Map<String, Object> yearAndMoc = createPromoService.getYearAndMoc(false);
		String geographyJson = createPromoService.getGeography(false);
		List<String> yearList = (List<String>) yearAndMoc.get("years");
		String mocJson = (String) yearAndMoc.get("moc");
		
		List<String> mocValue = promoListingService.getPromoMoc(); //Added by Kavitha D for promo listing MOC filter-SPRINT 9
		
		if(roleId.equalsIgnoreCase("TME") || roleId.equalsIgnoreCase("DP")){
			List<String> category = createPromoService.getAllCategories(userId);
			List<String> brand = createPromoService.getAllBrands(userId);
			List<String> basepacks = createPromoService.getAllBasepacks(userId);
			model.addAttribute("categories", category);
			model.addAttribute("brands", brand);
			model.addAttribute("basepacks", basepacks);
		}else {
			List<String> category = promoCrService.getAllCategories();
			List<String> brand = promoCrService.getAllBrands();
			List<String> basepacks = promoCrService.getAllBasepacks();
			model.addAttribute("categories", category);
			model.addAttribute("brands", brand);
			model.addAttribute("basepacks", basepacks);
		}
		model.addAttribute("geographyJson", geographyJson);
		//model.addAttribute("mocJson", mocJson);
		model.addAttribute("years", yearList);
		model.addAttribute("modality", modality);
		model.addAttribute("offerTypes", offerTypes);
		model.addAttribute("mocList", mocValue);

		return new ModelAndView("proco/proco_promo_listing");
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "promoVolumeUpload.htm", method = RequestMethod.GET)
	public ModelAndView getPromoVolumeUploadPage(HttpServletRequest request, Model model) {
		String roleId = (String) request.getSession().getAttribute("roleId");
		String userId = (String) request.getSession().getAttribute("UserID");
		//Harsha's implementation for logintool
				promoCrService.insertToportalUsage(userId, roleId, "PROCO");
		//Harsha's Logic End's here 
		model.addAttribute("roleId", roleId);
		List<String> customerChainL1 = createPromoService.getCustomerChainL1();
		List<String> offerTypes = createPromoService.getOfferTypes();
		Map<Integer, String> modality = createPromoService.getModality();
		Map<String, Object> yearAndMoc = createPromoService.getYearAndMoc(false);
		String geographyJson = createPromoService.getGeography(false);
		List<String> yearList = (List<String>) yearAndMoc.get("years");
		String mocJson = (String) yearAndMoc.get("moc");
		List<String> category = createPromoService.getAllCategories(userId);
		List<String> brand = createPromoService.getAllBrands(userId);
		List<String> basepacks = createPromoService.getAllBasepacks(userId);
		model.addAttribute("geographyJson", geographyJson);
		model.addAttribute("mocJson", mocJson);
		model.addAttribute("years", yearList);
		model.addAttribute("modality", modality);
		model.addAttribute("customerChainL1", customerChainL1);
		model.addAttribute("offerTypes", offerTypes);
		model.addAttribute("categories", category);
		model.addAttribute("brands", brand);
		model.addAttribute("basepacks", basepacks);
		return new ModelAndView("proco/proco_volume_upload");
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "promoCreation.htm", method = RequestMethod.GET)
	public ModelAndView getPromoCreatePage(
			@ModelAttribute("CreatePromotionBean") CreatePromotionBean createPromotionBean, HttpServletRequest request,
			Model model) {
		String roleId = (String) request.getSession().getAttribute("roleId");
		List<String> customerChainL1 = createPromoService.getCustomerChainL1();
		List<String> offerTypes = createPromoService.getOfferTypes();
		String geographyJson = createPromoService.getGeography(false);
		Map<Integer, String> modality = createPromoService.getModality();
		Map<String, Object> yearAndMoc = createPromoService.getYearAndMoc(true);
		List<String> reasonListForEdit = promoListingService.getReasonListForEdit();
		List<String> yearList = (List<String>) yearAndMoc.get("years");
		String mocJson = (String) yearAndMoc.get("moc");
		model.addAttribute("mocJson", mocJson);
		model.addAttribute("years", yearList);
		model.addAttribute("customerChainL1", customerChainL1);
		model.addAttribute("offerTypes", offerTypes);
		model.addAttribute("geographyJson", geographyJson);
		model.addAttribute("modality", modality);
		model.addAttribute("roleId", roleId);
		model.addAttribute("reasonList", reasonListForEdit);
		return new ModelAndView("proco/proco_create");
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "promoDisaggregation.htm", method = RequestMethod.GET)
	public ModelAndView getPromoDisaggregationPage(HttpServletRequest request, Model model) {
		String roleId = (String) request.getSession().getAttribute("roleId");
		String userId = (String) request.getSession().getAttribute("UserID");
		//Harsha's implementation for logintool
		promoCrService.insertToportalUsage(userId, roleId, "PROCO");
		//Harsha's Logic End's here
		int countofdisaggregation = disaggregationService.countofDisaggregation();
		List<String> customerChainL1 = createPromoService.getCustomerChainL1();
		List<String> offerTypes = createPromoService.getOfferTypes();
		Map<Integer, String> modality = createPromoService.getModality();
		Map<String, Object> yearAndMoc = createPromoService.getYearAndMoc(false);
		String geographyJson = createPromoService.getGeography(false);
		List<String> yearList = (List<String>) yearAndMoc.get("years");
		String mocJson = (String) yearAndMoc.get("moc");
		List<String> category = createPromoService.getAllCategories(userId);
		List<String> brand = createPromoService.getAllBrands(userId);
		List<String> basepacks = createPromoService.getAllBasepacks(userId);
		model.addAttribute("roleId", roleId);
		model.addAttribute("geographyJson", geographyJson);
		model.addAttribute("mocJson", mocJson);
		model.addAttribute("years", yearList);
		model.addAttribute("modality", modality);
		model.addAttribute("customerChainL1", customerChainL1);
		model.addAttribute("offerTypes", offerTypes);
		model.addAttribute("categories", category);
		model.addAttribute("brands", brand);
		model.addAttribute("basepacks", basepacks);
		//Added by Harsha for count
		model.addAttribute("countofdisaggregation", countofdisaggregation);
		
		return new ModelAndView("proco/proco_disaggregation");
	}

	@SuppressWarnings({ "unchecked"})
	@RequestMapping(value = "promoCollaboration.htm", method = RequestMethod.GET)
	public ModelAndView getPromoCollaborationPage(HttpServletRequest request, Model model) {
		String roleId = (String) request.getSession().getAttribute("roleId");
		String userId = (String) request.getSession().getAttribute("UserID");
		List<String> customerChainL1 = createPromoService.getCustomerChainL1(userId);
		List<String> offerTypes = createPromoService.getOfferTypes();
		Map<Integer, String> modality = createPromoService.getModality();
		Map<String, Object> yearAndMoc = createPromoService.getYearAndMoc(false);
		String geographyJson = createPromoService.getGeography(false);
		List<String> yearList = (List<String>) yearAndMoc.get("years");
		String mocJson = (String) yearAndMoc.get("moc");
		List<String> mocValue = promoListingService.getPromoMoc();
		List<String> category = promoCrService.getAllCategories();
		List<String> brand = promoCrService.getAllBrands();
		List<String> basepacks = promoCrService.getAllBasepacks();
		List<String> priChannelValue = promoListingService.getPromoPrimaryChannels();  //Added By Sarin - Sprint10
		model.addAttribute("roleId", roleId);
		model.addAttribute("geographyJson", geographyJson);
		model.addAttribute("mocJson", mocJson);
		model.addAttribute("years", yearList);
		model.addAttribute("modality", modality);
		model.addAttribute("customerChainL1", customerChainL1);
		model.addAttribute("offerTypes", offerTypes);
		model.addAttribute("categories", category);
		model.addAttribute("brands", brand);
		model.addAttribute("basepacks", basepacks);
		model.addAttribute("mocList",mocValue);
		model.addAttribute("primaryChannelList", priChannelValue);  //Added By Sarin - Sprint10
		return new ModelAndView("proco/promo_collaboration");
	}
	
	/*priyanka*/
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "promoDeletion.htm", method = RequestMethod.GET)
	public ModelAndView getProcoPromoDeletionPage(HttpServletRequest request, Model model) {
		String roleId = (String) request.getSession().getAttribute("roleId");
		String userId = (String) request.getSession().getAttribute("UserID");
		//Harsha's implementation for logintool
		promoCrService.insertToportalUsage(userId, roleId, "PROCO");
		//Harsha's Logic End's here 
		model.addAttribute("roleId", roleId);
		if(roleId.equalsIgnoreCase("KAM")){
			List<String> customerChainL1 = createPromoService.getCustomerChainL1(userId);
			model.addAttribute("customerChainL1", customerChainL1);
		}else{
			List<String> customerChainL1 = createPromoService.getCustomerChainL1();
			model.addAttribute("customerChainL1", customerChainL1);
		}
		List<String> offerTypes = createPromoService.getOfferTypes();
		Map<Integer, String> modality = createPromoService.getModality();
		Map<String, Object> yearAndMoc = createPromoService.getYearAndMoc(false);
		String geographyJson = createPromoService.getGeography(false);
		List<String> yearList = (List<String>) yearAndMoc.get("years");
		String mocJson = (String) yearAndMoc.get("moc");
		List<String> mocValue = promoListingService.getPromoMoc();
		if(roleId.equalsIgnoreCase("TME") || roleId.equalsIgnoreCase("DP")){
			List<String> category = createPromoService.getAllCategories(userId);
			List<String> brand = createPromoService.getAllBrands(userId);
			List<String> basepacks = createPromoService.getAllBasepacks(userId);
			model.addAttribute("categories", category);
			model.addAttribute("brands", brand);
			model.addAttribute("basepacks", basepacks);
		}else {
			List<String> category = promoCrService.getAllCategories();
			List<String> brand = promoCrService.getAllBrands();
			List<String> basepacks = promoCrService.getAllBasepacks();
			model.addAttribute("categories", category);
			model.addAttribute("brands", brand);
			model.addAttribute("basepacks", basepacks);
		}
		model.addAttribute("geographyJson", geographyJson);
		model.addAttribute("mocJson", mocJson);
		model.addAttribute("years", yearList);
		model.addAttribute("modality", modality);
		model.addAttribute("offerTypes", offerTypes);
		model.addAttribute("mocList",mocValue);
		return new ModelAndView("proco/proco_promo_deletion");
	}

}
