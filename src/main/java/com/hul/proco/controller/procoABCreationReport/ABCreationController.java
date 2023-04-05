package com.hul.proco.controller.procoABCreationReport;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.hul.launch.web.util.CommonPropUtils;
import com.hul.launch.web.util.CommonUtils;
import com.hul.launch.web.util.FilePaths;
import com.hul.launch.web.util.UploadUtil;
import com.hul.proco.excelreader.exom.ExOM;

//Kajal G Added for SPRINT 12	
@Controller
public class ABCreationController {

	@Autowired
	ABCreationService abCreation;
	
	static Logger logger = Logger.getLogger(ABCreationController.class);
	
	//Added by Kajal G-SPRINT 12
	@RequestMapping(value = "procoABCreationPage.htm", method = RequestMethod.GET)
	public ModelAndView getProcoABCreationUploadPage(HttpServletRequest request, Model model) {
		String roleId = (String) request.getSession().getAttribute("roleId");
		model.addAttribute("roleId", roleId);		
		return new ModelAndView("proco/ab_creation_upload");
	}
	
	//Kajal G Added for SPRINT 12
	@RequestMapping(value = "abCreationReportUpload.htm", method = RequestMethod.POST)
	public @ResponseBody String abCreationReportUploadFile(@ModelAttribute("ABCreationBean") ABCreationBean abCreationBean,
			Model model, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String save_data = null;

		CommonPropUtils commUtils = CommonPropUtils.getInstance();
		String userId = (String) httpServletRequest.getSession().getAttribute("UserID");
		MultipartFile file = abCreationBean.getFile();
		String fileName = file.getOriginalFilename();
		ABCreationBean[] beanArray = null;
		String filepath = FilePaths.FILE_TEMPUPLOAD_PATH;
		fileName = filepath + fileName;
		
		try {
			if (!CommonUtils.isFileEmpty(file)) {
				if (CommonUtils.isFileProcoSizeExceeds(file)) {
					model.addAttribute("FILE_STAUS", "FILE_SIZE_EXCEED");
					return "FILE_SIZE_EXCEED";
				} else if (UploadUtil.movefile(file, fileName)) {
					Map<String, List<Object>> map = null;
					map = ExOM.mapFromExcel(new File(fileName)).to(ABCreationBean.class).map(4, false, null);
					if (map.isEmpty()) {
						model.addAttribute("FILE_STAUS", "FILE_EMPTY");
						return "FILE_EMPTY";
					}
					if (map.containsKey("ERROR")) {
						model.addAttribute("FILE_STAUS", "CHECK_COL_MISMATCH");
						return "CHECK_COL_MISMATCH";
					} else if (map.containsKey("DATA")) {
						List<?> datafromexcel = map.get("DATA");
						beanArray = (ABCreationBean[]) datafromexcel
								.toArray(new ABCreationBean[datafromexcel.size()]);
						
						save_data = abCreation.uploadABCreationReport(beanArray, userId);
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
		} catch (Throwable e) {
			logger.error(e);
			e.printStackTrace();
		}
		
		return save_data;
	}
}
