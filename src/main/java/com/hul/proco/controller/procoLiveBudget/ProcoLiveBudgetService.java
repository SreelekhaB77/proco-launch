package com.hul.proco.controller.procoLiveBudget;

import java.util.ArrayList;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface ProcoLiveBudgetService {
	
public String budgetHolderData(BudgetHolderBean[] beanArray,String userID) throws Exception;

public ArrayList<String> procoLiveBudgetDownloadHeaderList();

public List<ArrayList<String>> procoLiveBudgetDownload(ArrayList<String> headerDetails);

public int getProcoBudgetRowCount(String searchParameter);

public List<BudgetHolderBean> getProcoBudgetTableList(int pageDisplayStart, int pageDisplayLength, String searchParameter);

}
