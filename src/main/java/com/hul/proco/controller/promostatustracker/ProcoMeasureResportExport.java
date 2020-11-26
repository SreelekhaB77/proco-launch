package com.hul.proco.controller.promostatustracker;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.hul.launch.web.util.CommonUtils;

@Component("ProcoMeasureExpoExcelView")
public class ProcoMeasureResportExport  extends AbstractXlsView {
	static Logger logger = Logger.getLogger(ProcoMeasureResportExport.class);
	
	
	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			Workbook workbook, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String IsExpost = (String) model.get("IsExport");
		
		CellStyle headerStyle = workbook.createCellStyle();
		Font font = workbook.createFont();//Create font
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);//Make font bold
		font.setFontHeight((short)220);
		headerStyle.setFont(font);
		font.setFontName("Calibri");
		font.setColor(IndexedColors.WHITE.getIndex());
		headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		headerStyle.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
		headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		headerStyle.setWrapText(true);
		headerStyle.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		if(IsExpost != null && IsExpost.equals("yes")) {
			
			ArrayList<ArrayList<String>> HeaderMasters = (ArrayList<ArrayList<String>>) model.get("HeaderMasters");
			
			
			response.setContentType("application/vnd.ms-excel");
			
			response.setHeader("Content-Disposition", "attachment; filename=Proco_Measure_Report_"+CommonUtils.getCurrDateTime_YYYY_MM_DD_HHMMSS()+".xls");
			
			Sheet sheet = workbook.createSheet("Promo Measusre report");
			Row ValueArea = sheet.createRow(1);
			
			if(HeaderMasters != null) {
				Sheet Statussheet = workbook.createSheet("PROMOTION_STATUS_MASTER");
				if( HeaderMasters.get(0) != null ) {
					for( int i = 0; i < HeaderMasters.get(0).size(); i++ ) {
						Statussheet.createRow(i).createCell(0).setCellValue(HeaderMasters.get(0).get(i));
					}
				}
				//ValueArea.createCell(23).setCellValue("ANY VALUE FROM PROMOTION_STATUS_MASTER");
				
				Sheet Mechsheet = workbook.createSheet("MECHANICS_MASTER");
				if( HeaderMasters.get(1) != null ) {
					for( int i = 0; i < HeaderMasters.get(1).size(); i++ ) {
						Mechsheet.createRow(i).createCell(0).setCellValue(HeaderMasters.get(1).get(i));
					}
				}
				
				//ValueArea.createCell(11).setCellValue("MECHANICS_MASTER");
				
				Sheet InvTypesheet = workbook.createSheet("INVESTMENT_TYPE_MASTER");
				if( HeaderMasters.get(2) != null ) {
					for( int i = 0; i < HeaderMasters.get(2).size(); i++ ) {
						InvTypesheet.createRow(i).createCell(0).setCellValue(HeaderMasters.get(2).get(i));
					}
				}
				
				Sheet CrtByTypesheet = workbook.createSheet("CREATED_BY_MASTER");
				if( HeaderMasters.get(3) != null ) {
					for( int i = 0; i < HeaderMasters.get(3).size(); i++ ) {
						CrtByTypesheet.createRow(i).createCell(0).setCellValue(HeaderMasters.get(3).get(i));
					}
				}
				//ValueArea.createCell(24).setCellValue("ANY VALUE FROM INVESTMENT_TYPE_MASTER");
			}
				
			String[] ExlHeaders = {"Promotion ID","Promotion Name","Created By","Created On","Project Id","Project Name","Bundle Id","Bundle Name","Promotion Qualification","Promotion Objective","Marketing Objective","Promotion Mechanics","Promotion Start Date","Promotion End Date","Pre Dip Start Date","Post Dip End Date","Customer","Business","Division","Product                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                ","Category","Brand","Sub-Brand","Promotion Status","Investment Type","MOC","Submission Date","Approved Date","Modified Date","Promotion Type","Duration","Free Product Name","Price Off","Baseline Quantity","Baseline GSV","Baseline Turnover","Baseline Gross Profit","Promotion Volume Before","Promotion Volume During","Promotion Volume After","Planned GSV","Planned Turnover","Planned Investment Amount","Planned Uplift","Planned Incremental Gross Profit","Planned Gross Profit","Planned Incremental Turnover","Planned Customer ROI","Planned Cost Price Based ROI","Planned Promotion ROI","Actual Quantity","Actual GSV","Actual Turnover","Actual Investment Amount","Actual Uplift","Actual Incremental Gross Profit","Actual Gross Profit","Actual Incremental Turnover","Actual Customer ROI","Actual Cost Price Based ROI","Actual Promotion ROI","Upload Reference Number","Is Duplicate"};
			
			
			
			Row FirstRow = sheet.createRow(0);
			for (int i = 0; i < ExlHeaders.length; i++) {
				Cell cell = FirstRow.createCell(i);
				cell.setCellValue(ExlHeaders[i]);
				cell.setCellStyle(headerStyle);
				sheet.setColumnWidth(i, 5800);
			}
		} else {
			
			List<Object[]> Measurelist = (List<Object[]>) model.get("Measurelist");
			String MocYear = (String) model.get("MocYear");
			String MocMonth = (String) model.get("MocMonth");
			List<List<Object[]>> MeasurelistSplit = new ArrayList<List<Object[]>>();
			int CurrSheetIndx = 0;
			List<String> sheetNmList = new ArrayList<String>();
			for (Object[] msr : Measurelist) {
				String groupName = "";
				/* Get sheet name  */
				if(msr[(msr.length - 14)]!= null) {
					groupName = msr[(msr.length - 14)].toString();
				}
				if( msr[(msr.length - 1)].toString().equals("Financial Close") ) {
					/* If status is financial closed */
					if(sheetNmList.indexOf("DROPPED List") == -1) {
						sheetNmList.add("DROPPED List");
					}
					CurrSheetIndx = sheetNmList.indexOf("DROPPED List");
				} else if(!groupName.equals("")){
					/* Is Sheet name exist */
					if(sheetNmList.indexOf(groupName) == -1) {
						sheetNmList.add(groupName);
					}
					CurrSheetIndx = sheetNmList.indexOf(groupName);
				} else {
					/* If Sheet name not exist set as rest */
					if(sheetNmList.indexOf("Rest") == -1) {
						sheetNmList.add("Rest");
					}
					CurrSheetIndx = sheetNmList.indexOf("Rest");
				}
				/* */
				if(CurrSheetIndx >= MeasurelistSplit.size()) {
					MeasurelistSplit.add(new ArrayList<Object[]>());
				}
				/* Add data in corresponding sheet */
				MeasurelistSplit.get(CurrSheetIndx).add(msr);
			}
	
			response.setContentType("application/vnd.ms-excel");
			
			response.setHeader("Content-Disposition", "attachment; filename=MT_VAT_SIGNED_OPS_REPORT.xls");
			
			int SheetItr = 0;
			String[] header = {"Signed Ops Shared Date", " Promotion ID KA Code"," Account Name" , "Activity Details", "Scope / Geography", "Process", "Is it to be claimed", "Basepack", "Sub Category" , "Claim Value", " Promo Volume In Thousand", " Total Investment In Lacs", "Supporting Required", "Promotion Start Date", "Promotion End Date","Promotion Status"};
			
			for(List<Object[]> MeasureListLp : MeasurelistSplit) {
				Sheet sheet = workbook.createSheet(( sheetNmList.get(SheetItr)));
				
			
				
				
				/*
				Cell firstCell = sheet.createRow(0).createCell(0);
				firstCell.setCellValue("From Promo measure report");	
				CellStyle FrtSty = workbook.createCellStyle();
				FrtSty.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
				FrtSty.setFillPattern(CellStyle.SOLID_FOREGROUND);
				firstCell.setCellStyle(FrtSty);
				
				Cell secCell = sheet.createRow(1).createCell(0);
				secCell.setCellValue("Calculation Implemented");	
				CellStyle SecSty = workbook.createCellStyle();
				SecSty.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
				SecSty.setFillPattern(CellStyle.SOLID_FOREGROUND);
				secCell.setCellStyle(SecSty);
				
				Cell thirdCell = sheet.createRow(2).createCell(0);
				thirdCell.setCellValue("Default");	
				CellStyle thridSty = workbook.createCellStyle();
				thridSty.setFillForegroundColor(IndexedColors.ROSE.getIndex());
				thridSty.setFillPattern(CellStyle.SOLID_FOREGROUND);
				thirdCell.setCellStyle(thridSty);
				*/
				
				CellStyle borderStyle = workbook.createCellStyle();
				borderStyle.setBorderBottom(CellStyle.BORDER_THICK);
				borderStyle.setBorderLeft(CellStyle.BORDER_THICK);
				borderStyle.setBorderRight(CellStyle.BORDER_THICK);
				borderStyle.setBorderTop(CellStyle.BORDER_THICK);
				borderStyle.setAlignment(CellStyle.ALIGN_CENTER);
				Font Bfont = workbook.createFont();//Create font
				Bfont.setBoldweight(Font.BOLDWEIGHT_BOLD);//Make font bold
				Bfont.setFontHeight((short)240);
				borderStyle.setFont(Bfont);
				
				/**/
				Row MrgedHdr = sheet.createRow(0);
				for (int i = 0; i <= 15; ++i) {
					Cell cell = MrgedHdr.createCell(i);
				    cell.setCellStyle(borderStyle);
				    if (i == 0) {
				        cell.setCellValue(sheetNmList.get(SheetItr) + " Exclusive Promotional Plan " + MocMonth + "/" + MocYear);
				    } 
				}
				
				sheet.setColumnWidth(0, 5800);
				sheet.setColumnWidth(1, 5800);
				sheet.setColumnWidth(2, 5800);
				sheet.setColumnWidth(3, 15800);
				sheet.setColumnWidth(4, 5800);
				sheet.setColumnWidth(5, 5500);
				sheet.setColumnWidth(6, 5300);
				sheet.setColumnWidth(7, 5300);
				sheet.setColumnWidth(8, 5800);
				sheet.setColumnWidth(9, 5800);
				sheet.setColumnWidth(10, 5600);
				sheet.setColumnWidth(11, 5300);
				sheet.setColumnWidth(12, 5800);
				sheet.setColumnWidth(13, 5800);
				sheet.setColumnWidth(14, 5800);
				sheet.setColumnWidth(15, 5800);
				
				sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 12));
				/**/
				Row headerRow = sheet.createRow(2);
				for(int i = 0; i < header.length; i++ ) {
					Cell headCell = headerRow.createCell(i);
					headCell.setCellStyle(headerStyle);
					headCell.setCellValue(header[i]);
				}
				sheet.createFreezePane(0, 3);
				/***/
				
				//List<Object[]> Measurelist = procoStatusTrackerDao.getMeasureReportByMoc("2020", "MOC7");
				int intr = 3;
				
				
				/*
				CellStyle listGRowStyle = workbook.createCellStyle();
				listGRowStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				listGRowStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
				listGRowStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
				listGRowStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
				listGRowStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				listGRowStyle.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
				listGRowStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
				
				CellStyle listYRowStyle = workbook.createCellStyle();
				listYRowStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				listYRowStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
				listYRowStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
				listYRowStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
				listYRowStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				listYRowStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
				listYRowStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

				CellStyle listPRowStyle = workbook.createCellStyle();
				listPRowStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				listPRowStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
				listPRowStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
				listPRowStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
				listPRowStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				listPRowStyle.setFillForegroundColor(IndexedColors.ROSE.getIndex());
				listPRowStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
				*/
				
				
				for (Object[] msr : MeasureListLp) {
					Row ListRow = sheet.createRow(intr);
					for(int i = 0; i < msr.length; i++) {
						//if( ( msr.length - 1 ) != i && ( msr.length - 2 ) != i ) {
							Cell ListCell = ListRow.createCell(i);
							/*
							if(i == 0 || i == 3 || i == 7 || i == 9 || i == 10) {
								ListCell.setCellStyle(listGRowStyle);
							} else if(i == 1 || i == 2 || i == 6 || i == 8 || i == 11 || i == 12) {
								ListCell.setCellStyle(listYRowStyle);
							} else if(i == 4 || i == 5) {
								ListCell.setCellStyle(listPRowStyle);
							}*/
							ListCell.setCellValue((msr[i] != null ? msr[i].toString() : ""));
						//}
					}
					intr++;
				}
				
		
				intr++;
				CellStyle Sstyle = workbook.createCellStyle();
				Font Sfont = workbook.createFont();
				Sfont.setBoldweight(Font.BOLDWEIGHT_BOLD);
				Sstyle.setFont(Sfont);  
				Sstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			
				
				Cell appCell = sheet.createRow(intr).createCell(6);
				appCell.setCellStyle(Sstyle);
				appCell.setCellValue("Approved By");
				Cell BalCell = sheet.createRow(intr+1).createCell(6);
				BalCell.setCellStyle(Sstyle);
				BalCell.setCellValue("Mayur Mallecha");
				
				 InputStream inputStream;
				 inputStream = getServletContext().getResourceAsStream("/resources/images/proco/signature.png");
				
				   //Get the contents of an InputStream as a byte[].
				   byte[] bytes = IOUtils.toByteArray(inputStream);
				   //Adds a picture to the workbook
				   int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
				   //close the input stream
				   inputStream.close();
				   //Returns an object that handles instantiating concrete classes
				   CreationHelper helper = workbook.getCreationHelper();
				   //Creates the top-level drawing patriarch.
				   Drawing drawing = sheet.createDrawingPatriarch();
		
				   //Create an anchor that is attached to the worksheet
				   ClientAnchor anchor = helper.createClientAnchor();
		
				   //create an anchor with upper left cell _and_ bottom right cell
				   anchor.setCol1(6); //Column B
				   anchor.setRow1(intr+2); //Row 3
				   anchor.setCol2(7); //Column C
				   anchor.setRow2(intr+7); //Row 4
		
				   //Creates a picture
				   Picture pict = drawing.createPicture(anchor, pictureIdx);
				
				   
				intr = intr + 7;
				Cell onCell = sheet.createRow(intr).createCell(6);
				onCell.setCellStyle( Sstyle );
				onCell.setCellValue("On behalf of ");
				
				Cell DeerCell =  sheet.createRow(intr+1).createCell(6);
				DeerCell.setCellStyle(Sstyle);
				DeerCell.setCellValue("Dheeraj Arora ");
			
				Cell VpCell = sheet.createRow(intr+2).createCell(6);
				VpCell.setCellStyle(Sstyle);
				VpCell.setCellValue("VP-Moderntrade");
				
				
				intr = intr + 5;
				String[] GNotes = {"1. Claim Submission   All claim to be submitted within 2 months of the activity and latter than that, claim will not be considered",
								   "2. No activity to be run without Promotion (Sol)Code",
								   "3. One activity can be claimed only once ",
								   "4. Coupon Redemption  To be claimed  within 2 months from the last date of redemption date.  Claim will not be considered post 2 months.Coupon to be attached with claim.",
								   "5. Activity Period claimed should be same as Promo Period",
								   "6. Visibility Budget Amount should be Inclusive of GST",
								   "7. Claim Activity should exactly match the Activity Details mentioned above",
								   "8. All activities are period based and subject to maximum Promo Volume within the promo period. Claims will not be accepted for quantities exceeding the Promo Volume"};
				CellStyle Nstyle = workbook.createCellStyle();
				Font Nfont = workbook.createFont();
				Nfont.setBoldweight(Font.BOLDWEIGHT_BOLD);
				Nstyle.setFont(Nfont);
				for( int x = 0; x < GNotes.length; x++ ) {
					intr = intr+2;
					Cell Ncell = sheet.createRow(intr).createCell(1);
					Ncell.setCellStyle(Nstyle);
					Ncell.setCellValue(GNotes[x]);
				}	
				SheetItr++;
			}
		}
	}
}
