package com.hul.proco.controller.promostatustracker;

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

import com.hul.launch.web.util.CommonPropUtils;
import com.hul.launch.web.util.CommonUtils;
import com.hul.launch.web.util.FilePaths;
import com.hul.launch.web.util.UploadUtil;
import com.hul.proco.controller.createpromo.CreateBeanRegular;
import com.hul.proco.controller.createpromo.RegularPromoCreateController;
import com.hul.proco.excelreader.exom.ExOM;

@Controller
public class PPMLinkageController {

	static Logger logger = Logger.getLogger(PPMLinkageController.class);
	String ModRes;
	
	@Autowired
	PPMLinkageService linkageService;

	@RequestMapping(value = "ppminkageupload.htm", method = RequestMethod.POST)
	public @ResponseBody String uploadPPMLinkageFile(@ModelAttribute("PPMLinkageBean") PPMLinkageBean ppmlinkageBean,
			Model model, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String save_data = null;
		CommonPropUtils commUtils = CommonPropUtils.getInstance();
		String userId = (String) httpServletRequest.getSession().getAttribute("UserID");
		MultipartFile file = ppmlinkageBean.getFile();
		String fileName = file.getOriginalFilename();
		PPMLinkageBean[] beanArray = null;
		String filepath = FilePaths.FILE_TEMPUPLOAD_PATH;
		fileName = filepath + fileName;
		try {
			if (!CommonUtils.isFileEmpty(file)) {
				if (CommonUtils.isMearsureReportFileSizeExceeds(file)) {
					model.addAttribute("errorMsg", commUtils.getProperty("File.Size.Exceeds"));
					model.addAttribute("ModRes", "File Size Exceeds");
					return "File Size Exceeds";
				} else {
					if (UploadUtil.movefile(file, fileName)) {
						int excelColumnCount = UploadUtil.readExcelCellCount(fileName);
						if (excelColumnCount == 27) {
							Map<String, List<Object>> map = null;
							map = ExOM.mapFromExcel(new File(fileName)).to(PPMLinkageBean.class).map(27, false, null);
							
							if (map.isEmpty()) {
								model.addAttribute("ModRes", "FILE_EMPTY");
								return "FILE_EMPTY";
							}
							if (map.containsKey("ERROR")) {
								model.addAttribute("ModRes", "CHECK_COL_MISMATCH");

								return "CHECK_COL_MISMATCH";
							} else if (map.containsKey("DATA")) {
								List<?> datafromexcel = map.get("DATA");
								beanArray = (PPMLinkageBean[]) datafromexcel
										.toArray(new PPMLinkageBean[datafromexcel.size()]);
								
								//save_data = createCRPromo.createCRPromo(beanArray, userId, template);
								save_data=linkageService.addTotempTable(beanArray, userId);
								
							}
							
							if(save_data.equals("EXCEL_UPLOADED"))
							{
								model.addAttribute("ModRes", "EXCEL_UPLOADED");
								return"EXCEL_UPLOADED";
							}else
							{
								model.addAttribute("ModRes", "EXCEL_NOT_UPLOADED");
								return"EXCEL_NOT_UPLOADED";
							}
							
							
						} else {
							model.addAttribute("ModRes", "Column count is not match with expected");
							return "Column count is not match with expected";
						}
					}
				}
			}
		}catch (Exception e) {
			logger.error(e);
		}
		catch (Throwable e) {
			logger.error(e);
		}
		return save_data;
	}

}
