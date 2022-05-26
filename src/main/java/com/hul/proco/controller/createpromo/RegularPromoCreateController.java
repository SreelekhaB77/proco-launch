package com.hul.proco.controller.createpromo;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.hul.launch.web.util.CommonPropUtils;
import com.hul.launch.web.util.CommonUtils;
import com.hul.launch.web.util.FilePaths;
import com.hul.launch.web.util.UploadUtil;
import com.hul.proco.excelreader.exom.ExOM;

/*
 *  Change name: Creating new file for CR promo upload
 *  Description: This controller will handle all the new template request 
 *  Developer: Mayur Chavhan
 *  Date: 19/05/2022 
 */
@Controller
public class RegularPromoCreateController {
	@Autowired
	RegularPromoService createCRPromo;

	@RequestMapping(value = "createCRBean.htm", method = RequestMethod.POST)
	public @ResponseBody String uploadCRFile(@ModelAttribute("CreateBeanRegular") CreateBeanRegular createCRBean,
			@RequestParam(name = "template") String template, Model model, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		String save_data = null;

		CommonPropUtils commUtils = CommonPropUtils.getInstance();
		String userId = (String) httpServletRequest.getSession().getAttribute("UserID");
		MultipartFile file = createCRBean.getFile();
		String fileName = file.getOriginalFilename();
		CreateBeanRegular[] beanArray = null;
		String filepath = FilePaths.FILE_TEMPUPLOAD_PATH;
		fileName = filepath + fileName;
		try {
			if (!CommonUtils.isFileEmpty(file)) {
				if (CommonUtils.isFileSizeExceeds(file)) {
					model.addAttribute("FILE_STAUS", "FILE_SIZE_EXCEED");
				} else if (UploadUtil.movefile(file, fileName)) {
					Map<String, List<Object>> map = null;
					if (template.equalsIgnoreCase("regular")) {
						map = ExOM.mapFromExcel(new File(fileName)).to(CreateBeanRegular.class).map(16, false, null);
					} else if (template.equalsIgnoreCase("new")) {
						map = ExOM.mapFromExcel(new File(fileName)).to(CreateBeanRegular.class).map(17, false, null);
					}

					if (map.isEmpty()) {
						model.addAttribute("FILE_STAUS", "FILE_EMPTY");
					}
					if (map.containsKey("ERROR")) {
						model.addAttribute("FILE_STAUS", "CHECK_COL_MISMATCH");
					} else if (map.containsKey("DATA")) {
						List<?> datafromexcel = map.get("DATA");
						beanArray = (CreateBeanRegular[]) datafromexcel
								.toArray(new CreateBeanRegular[datafromexcel.size()]);
						save_data = createCRPromo.createCRPromo(beanArray, userId, template);

					}

				} else {
					System.out.println("Can't copy file, file should not open/use by other application.");
				}
			} else
				model.addAttribute("FILE_STAUS", "FILE_EMPTY");
			if (save_data.equals("EXCEL_UPLOADED")) {
				model.addAttribute("FILE_STAUS", "EXCEL_UPLOADED");
			} else {
				model.addAttribute("FILE_STAUS", "EXCEL_NOT_UPLOADED");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return save_data;

	}
}
