package com.hul.proco.controller.promostatustracker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
@Controller
@EnableScheduling
public class ProcoCustomerPortalController {
	
	private Logger logger=Logger.getLogger(ProcoStatusTrackerController.class);
	@Autowired
	private ProcoStatusTrackerService procoStatusTrackerService;
	
	//@Scheduled(fixedRate = 50000)
	@Scheduled(cron="0 0 5 * * *")
	public void PromotionStatusExportCron() {
		try {
			Calendar calendar = Calendar.getInstance();
			int year = calendar.get(Calendar.YEAR);
			int Month = calendar.get(Calendar.MONTH);
			//int day   = calendar.get(Calendar.DATE);
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
			
			
			MocWhr += "";
			YerWhr += "";
			
			ArrayList<String> headerList = procoStatusTrackerService.getHeaderListForPromoStatusTracker("2", true);
			List<ArrayList<String>> downloadedData = procoStatusTrackerService.getPromotionStatusTrackerCustomerPortal(headerList, "all", "all", "all", "all",
					"all", "all", "all", "all", YerWhr, MocWhr, "2", 1,"all");
			
			
			if (downloadedData != null) {
				downloadedData.remove(0);
				procoStatusTrackerService.batchCustomerStatusTrackerReport(downloadedData);

				
			}
		}
		catch (Exception e) {
			logger.debug("Exception: ", e);
		}
	}
	
}
