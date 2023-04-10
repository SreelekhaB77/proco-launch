package com.hul.proco.controller.procoLiveBudget;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ProcoLiveBudgetService")
@Transactional
public class ProcoLiveBudgetServiceImpl implements ProcoLiveBudgetService {
	
	@Autowired
	public ProcoLiveBudgetDao procoLiveBudgetDao;

	public String budgetHolderData(BudgetHolderBean[] beanArray,String userID) throws Exception {
		return procoLiveBudgetDao.budgetHolderData(beanArray,userID);
	}

	
	public ArrayList<String> procoLiveBudgetDownloadHeaderList(){
		 {
				ArrayList<String> headerList=new ArrayList<String>();
				headerList.add("BUDGET_HOLDER");
				headerList.add("CATEGORY");
				headerList.add("PRODUCT");	
				headerList.add("CUSTOMER");	
				headerList.add("FUND_TYPE");
				headerList.add("ORIGINAL_AMOUNT");	
				headerList.add("ADJUSTED_AMOUNT");
				headerList.add("REVISED_AMOUNT");	
				headerList.add("UPDATE_AMOUNT");
				headerList.add("TRANSFER_IN");	
				headerList.add("TRANSFER_OUT");
				headerList.add("TRANSFER_PIPELINE");	
				headerList.add("TOTAL_AMOUNT");
				headerList.add("PIPELINE_AMOUNT");	
				headerList.add("COMMITMENT_AMOUNT");
				headerList.add("REMAINING_AMOUNT");	
				headerList.add("ACTUALS");
				headerList.add("ADJUSTMENT_AGAINST_ACTUALS");	
				headerList.add("USAGE");
				headerList.add("POST_CLOSE_ACTUAL_AMOUNT");	
				headerList.add("PAST_YEAR_CLOSED_PROMOTIONS_AMOUNT");
				headerList.add("TIME_PHASE");	
				headerList.add("REPORT_DOWNLOADED_BY");
				headerList.add("REPORT_DOWNLOADED_DATE");	
				headerList.add("COE_BUDGETREPORT_UPLOADED_TIMESTAMP");
				
				return headerList;
			}
		
	}

	public List<ArrayList<String>> procoLiveBudgetDownload(ArrayList<String> headerDetails){
		return procoLiveBudgetDao.procoLiveBudgetDownload(headerDetails);
	}


	@Override
	public int getProcoBudgetRowCount() {
		return procoLiveBudgetDao.getProcoBudgetRowCount();
	}


	@Override
	public List<BudgetHolderBean> getProcoBudgetTableList(int pageDisplayStart, int pageDisplayLength, String searchParameter) {
		// TODO Auto-generated method stub
		return procoLiveBudgetDao.getProcoBudgetTableList(pageDisplayStart,pageDisplayLength,searchParameter);
	}
	
}
