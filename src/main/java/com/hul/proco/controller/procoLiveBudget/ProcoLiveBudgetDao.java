package com.hul.proco.controller.procoLiveBudget;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface ProcoLiveBudgetDao {

public String budgetHolderData(BudgetHolderBean[] beanArray,String userID) throws Exception;

public List<ArrayList<String>> procoLiveBudgetDownload(ArrayList<String> headerDetails);

public int getProcoBudgetRowCount(String searchParameter);

public List<BudgetHolderBean> getProcoBudgetTableList(int pageDisplayStart, int pageDisplayLength,
		String searchParameter);



}
