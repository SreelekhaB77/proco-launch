package com.hul.proco.controller.visibilityupload;

import java.io.File;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.util.SystemOutLogger;
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
public class VisibilityUploadController {
	
	@Autowired
	VisibilityUploadService visiUploadService;
	
	static Logger logger = Logger.getLogger(VisibilityUploadController.class);
	
	//Added by Kajal G-SPRINT 12
	@RequestMapping(value = "procovisibilityUpload.htm", method = RequestMethod.GET)
	public ModelAndView getProcoVisibilityUploadPage(HttpServletRequest request, Model model) {
		String roleId = (String) request.getSession().getAttribute("roleId");
		model.addAttribute("roleId", roleId);		
		return new ModelAndView("proco/visibility_upload");
	}
	
	//Kajal G Added for SPRINT 12
	@RequestMapping(value = "visibilityUpload.htm", method = RequestMethod.POST)
	public @ResponseBody String uploadVisibiltyFile(@ModelAttribute("VisibilityBean") VisibilityBean visiBean,
			Model model, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String save_data = null;

		CommonPropUtils commUtils = CommonPropUtils.getInstance();
		String userId = (String) httpServletRequest.getSession().getAttribute("UserID");
		MultipartFile file = visiBean.getFile();
		String fileName = file.getOriginalFilename();
		VisibilityBean[] beanArray = null;
		String filepath = FilePaths.FILE_TEMPUPLOAD_PATH;
		fileName = filepath + fileName;
		
		try {
			if (!CommonUtils.isFileEmpty(file)) {
				if (CommonUtils.isFileSizeExceeds(file)) {
					model.addAttribute("FILE_STAUS", "FILE_SIZE_EXCEED");
					return "FILE_SIZE_EXCEED";
				} else {

					if (UploadUtil.movefile(file, fileName)) {
						int excelColumnCount = UploadUtil.readExcelCellCount(fileName);
						if(excelColumnCount == 44) {
							List<VisibilityBean> visiBeanList = visiUploadService.readVisibilityBean(fileName);
							beanArray = visiBeanList.toArray(new VisibilityBean[visiBeanList.size()]);
							if (beanArray.length == 0) {
								model.addAttribute("FILE_STAUS", "FILE_EMPTY");
								return "FILE_EMPTY";
							} else {
								save_data = visiUploadService.uploadVisibilityData(beanArray, userId);
							}
						}else {
							model.addAttribute("FILE_STAUS", "CHECK_COL_MISMATCH");
							return "CHECK_COL_MISMATCH";
						}

					}else {
						model.addAttribute("FILE_STAUS", "FILE_EMPTY");
						return "FILE_EMPTY";
					}
				}
			}
			else {
				model.addAttribute("FILE_STAUS", "FILE_EMPTY");
				return "FILE_EMPTY";
			}
			if (save_data.equals("EXCEL_UPLOADED")) {
				model.addAttribute("FILE_STAUS", "EXCEL_UPLOADED");
			}else if(save_data.equals("START_DATE_AND_END_DATE_ERROR")) {
				model.addAttribute("FILE_STAUS", "START_DATE_AND_END_DATE_ERROR");
				save_data="START_DATE_AND_END_DATE_ERROR";
			}
			else {
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
}
