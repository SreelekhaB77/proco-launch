package com.hul.launch.seviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hul.launch.dao.LaunchBasePacksDao;
import com.hul.launch.dao.LaunchDao;
import com.hul.launch.exception.ParameterNotFoundException;
import com.hul.launch.model.LaunchBaseplan;
import com.hul.launch.model.LaunchClusterDataCustStoreForm;
import com.hul.launch.model.LaunchClusterDataStoreForm;
import com.hul.launch.model.TblLaunchBasebacks;
import com.hul.launch.model.TblLaunchMaster;
import com.hul.launch.request.AcceptByTmeRequest;
import com.hul.launch.request.AccountRequest;
import com.hul.launch.request.DownloadLaunchClusterRequest;
import com.hul.launch.request.RejectByTmeRequest;
import com.hul.launch.request.SaveLaunchBasepackRequest;
import com.hul.launch.request.SaveLaunchBasepacksListReq;
import com.hul.launch.request.SaveLaunchClustersRequest;
import com.hul.launch.request.SaveLaunchMasterRequest;
import com.hul.launch.request.UploadKamInputs;
import com.hul.launch.response.LaunchBasePackResponse;
import com.hul.launch.response.LaunchDataResponse;
import com.hul.launch.response.ProcoCustResponse;
import com.hul.launch.response.StoreDetailsResponse;
import com.hul.launch.service.LaunchBasepacksService;
import com.hul.proco.controller.createpromo.CreatePromoService;

@Service
public class LaunchBasepacksServiceImpl implements LaunchBasepacksService {

	@Autowired
	LaunchBasePacksDao launchBacePacksDao;

	@Autowired
	public CreatePromoService createPromoService;

	@Autowired
	LaunchDao launchDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public List<String> getSalesCategory() {
		return launchBacePacksDao.getSalesCatData();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public List<TblLaunchBasebacks> getPsaCategory(String psaCat) {
		return launchBacePacksDao.getPsaCatData(psaCat);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public List<TblLaunchBasebacks> getBrandPsaCategory(String psaCat, String salesCat) {
		return launchBacePacksDao.getBrandOnPsaCatData(psaCat, salesCat);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public List<String> getBasepackCodeOnLaunchId(String launchId) {
		return launchBacePacksDao.getBasepackCodeOnLaunchId(launchId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String saveLaunchDetails(SaveLaunchMasterRequest tblLaunchMaster, String userId) {
		return launchBacePacksDao.saveLaunchDetails(tblLaunchMaster, userId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int saveLaunchBasepacks(SaveLaunchBasepacksListReq tblLaunchbasePacks, String userId) {
		return launchBacePacksDao.saveLaunchBasepacks(tblLaunchbasePacks, userId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String saveBasepackByUpload(List<Object> saveLaunchBasepack, String userId, String status, boolean isCreate,
			boolean isFromUi, String launchId) throws Exception {
		try {
			List<SaveLaunchBasepackRequest> listBasepack = new ArrayList<>();
			Iterator<Object> iterator = saveLaunchBasepack.iterator();
			SaveLaunchBasepacksListReq saveLaunchBasepacksListReq = new SaveLaunchBasepacksListReq();
			Map<String, String> basepackCode = new HashMap<>();
			Map<String, String> basepackDesc = new HashMap<>();
			while (iterator.hasNext()) {
				LaunchBaseplan obj = (LaunchBaseplan) iterator.next();
				SaveLaunchBasepackRequest saveLaunchBasepackRequest = new SaveLaunchBasepackRequest();
				saveLaunchBasepacksListReq.setLaunchId(launchId);
				saveLaunchBasepackRequest.setBrand(obj.getBRAND());
				saveLaunchBasepackRequest.setClassification(obj.getCLASSIFICATION());
				saveLaunchBasepackRequest.setPsaCategory(obj.getPSACATEGORY());
				if (basepackCode.containsKey(obj.getCODE())) {
					return "Basepack Code can not repeat";
				} else {
					basepackCode.put(obj.getCODE(), "");
				}
				saveLaunchBasepackRequest.setCode(obj.getCODE());

				if (basepackDesc.containsKey(obj.getDESCRIPTION())) {
					return "Basepack Description can not repeat";
				} else {
					basepackDesc.put(obj.getDESCRIPTION(), "");
				}
				saveLaunchBasepackRequest.setDescription(obj.getDESCRIPTION());
				saveLaunchBasepackRequest.setMrp(obj.getMRP());
				saveLaunchBasepackRequest.setTur(obj.getTUR());
				saveLaunchBasepackRequest.setGsv(obj.getGSV());
				saveLaunchBasepackRequest.setCldConfig(obj.getCLDCONFIG());
				saveLaunchBasepackRequest.setGrammage(obj.getGRAMMAGE());
				saveLaunchBasepackRequest.setSalesCategory(obj.getSALESCATEGORY());
				listBasepack.add(saveLaunchBasepackRequest);
			}

			saveLaunchBasepacksListReq.setListBasePacks(listBasepack);
			int result = saveLaunchBasepacks(saveLaunchBasepacksListReq, userId);
			if (result == 1) {
				return "SUCCESS_FILE";
			} else {
				return "ERROR";
			}
		} catch (Exception e) {
			return "ERROR : " + e.toString();
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public List<ArrayList<String>> getbasepackDump(ArrayList<String> headerDetail, String userId) {
		return launchBacePacksDao.getLaunchBasepackData(headerDetail, userId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public List<ArrayList<String>> getClusterDumpforCustomerStoreformat(ArrayList<String> headerDetail, String userId,
			DownloadLaunchClusterRequest downloadLaunchClusterRequest) {
		return launchBacePacksDao.getLaunchClusterDataforCustomerStoreFormat(headerDetail, userId,
				downloadLaunchClusterRequest);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public List<ArrayList<String>> getClusterDumpForStoreFormat(ArrayList<String> headerDetail, String userId,
			DownloadLaunchClusterRequest downloadLaunchClusterRequest) {
		return launchBacePacksDao.getLaunchClusterDataforStoreFormat(headerDetail, userId,
				downloadLaunchClusterRequest);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public StoreDetailsResponse getLaunchStores(String launchId) {
		StoreDetailsResponse storeDetailsResponse = new StoreDetailsResponse();
		try {
			TblLaunchMaster tblLaunchMaster = launchBacePacksDao.getStoreDetails(launchId);
			storeDetailsResponse.setLaunchId(launchId);
			storeDetailsResponse.setLaunchNature(tblLaunchMaster.getLaunchNature());
			storeDetailsResponse.setLaunchNature2(tblLaunchMaster.getLaunchNature2());
			Map<String, List<String>> customerChainL2 = null;
			List<ProcoCustResponse> customerChainL3 = new ArrayList<>();
			List<List<String>> liC2 = new ArrayList<>();
			if (tblLaunchMaster.getLaunchNature().equals("General")) {
				storeDetailsResponse
						.setStoreCount(launchBacePacksDao.storeCountByLaunchClass(tblLaunchMaster.getClassification()));
				storeDetailsResponse.setRegionCluster("ALL INDIA");
				storeDetailsResponse.setCustomerData("ALL CUSTOMERS");
			} else if (tblLaunchMaster.getLaunchNature().equals("Regional")) {
				String[] splittedString = tblLaunchMaster.getLaunchNature2().split(",");
				List<String> liClusterCode = new ArrayList<>();
				for (String string2 : splittedString) {
					if (null != string2) {
						if (string2.split(":").length > 1) {
							liClusterCode.add(string2.split(":")[1]);
						}
					}
				}

				String custString = createPromoService.getCustomerDataOnRegion(liClusterCode);
				List<String> listOfCust = Arrays.asList(custString.split(","));
				List<String> accountl1String = new ArrayList<>();
				List<String> accountl2String = new ArrayList<>();
				for (String customerStr : listOfCust) {
					if (null != customerStr) {
						String[] l1l2 = customerStr.split(":");
						accountl1String.add(l1l2[0]);
						if (l1l2.length > 1) {
							accountl2String.add(l1l2[1]);
						}
					}
				}
				storeDetailsResponse.setCustomerData(custString);
				String storeCount = getStoreCountByClass(liClusterCode, accountl1String, accountl2String,
						tblLaunchMaster.getClassification(), false);  //Sarin Changes - Q1Sprint Feb2021 Added last parameter
				storeDetailsResponse.setStoreCount(Integer.parseInt(storeCount));

				storeDetailsResponse.setRegionCluster(tblLaunchMaster.getLaunchNature2());
			} else if (tblLaunchMaster.getLaunchNature().equals("Format Specific")) {
				customerChainL2 = createPromoService.getL2WithClusterStore(tblLaunchMaster.getLaunchNature2());
				List<String> liC3 = new ArrayList<>();
				for (Entry<String, List<String>> entry : customerChainL2.entrySet()) {
					ProcoCustResponse procoCustResponse = new ProcoCustResponse();
					procoCustResponse.setCustomerL1(entry.getKey());
					procoCustResponse.setCustomerL2(entry.getValue());
					customerChainL3.add(procoCustResponse);
					liC3.add(entry.getKey().toString());
					entry.getValue().forEach(data -> {
						liC3.add(data.toString());
					});
					liC2.add(entry.getValue());
				}

				List<String> listOfL1 = new ArrayList<>();
				List<String> listOfL2 = new ArrayList<>();

				liC2.forEach(data -> {
					data.forEach(string -> {
						String[] l1l2 = string.split(":");
						listOfL1.add(l1l2[0]);

						if (l1l2.length > 1) {
							listOfL2.add(l1l2[1]);
						}
					});
				});
				String customerData[] = { "" };
				liC3.forEach(data -> {
					customerData[0] = customerData[0] + data + ",";
				});
				String regionData = createPromoService.getClusterOnCustomer(listOfL1);
				storeDetailsResponse.setRegionCluster(regionData);

				String[] splittedString = regionData.split(",");
				List<String> liClusterCode1 = new ArrayList<>();
				for (String string2 : splittedString) {
					if (null != string2) {
						liClusterCode1.add(string2.split(":")[1]);
					}
				}
				storeDetailsResponse.setCustomerData(customerData[0]);
				String storeCount = getStoreCountByClass(liClusterCode1, listOfL1, listOfL2,
						tblLaunchMaster.getClassification(), false); //Sarin Changes - Q1Sprint Feb2021 Added last parameter
				storeDetailsResponse.setStoreCount(Integer.parseInt(storeCount));
			} else if (tblLaunchMaster.getLaunchNature().equals("Town Specific")) {
				customerChainL2 = createPromoService.getL2WithClusterTown(tblLaunchMaster.getLaunchNature2());
				List<String> liC3 = new ArrayList<>();
				for (Entry<String, List<String>> entry : customerChainL2.entrySet()) {
					ProcoCustResponse procoCustResponse = new ProcoCustResponse();
					procoCustResponse.setCustomerL1(entry.getKey());
					procoCustResponse.setCustomerL2(entry.getValue());
					customerChainL3.add(procoCustResponse);
					liC3.add(entry.getKey().toString());
					entry.getValue().forEach(data -> {
						liC3.add(data.toString());
					});
					liC2.add(entry.getValue());
				}
				List<String> listOfL1 = new ArrayList<>();
				List<String> listOfL2 = new ArrayList<>();
				liC2.forEach(data -> {
					data.forEach(string -> {
						String[] l1l2 = string.split(":");
						listOfL1.add(l1l2[0]);

						if (l1l2.length > 1) {
							listOfL2.add(l1l2[1]);
						}
					});
				});
				String customerData[] = { "" };
				liC3.forEach(data -> {
					customerData[0] = customerData[0] + data + ",";
				});
				String regionData = createPromoService.getClusterOnCustomer(listOfL1);
				storeDetailsResponse.setRegionCluster(regionData);
				String[] splittedString = regionData.split(",");
				List<String> liClusterCode = new ArrayList<>();
				for (String string2 : splittedString) {
					if (null != string2) {
						liClusterCode.add(string2.split(":")[0]);
					}
				}
				storeDetailsResponse.setCustomerData(customerData[0]);
				String storeCount = getStoreCountByClass(liClusterCode, listOfL1, listOfL2,
						tblLaunchMaster.getClassification(), false);  //Sarin Changes - Q1Sprint Feb2021 Added last parameter
				storeDetailsResponse.setStoreCount(Integer.parseInt(storeCount));
			} else if (tblLaunchMaster.getLaunchNature().equals("Customer Specific")) {
				customerChainL2 = createPromoService.getCustomerChainL2WithCluster(tblLaunchMaster.getLaunchNature2());
				for (Entry<String, List<String>> entry : customerChainL2.entrySet()) {
					ProcoCustResponse procoCustResponse = new ProcoCustResponse();
					procoCustResponse.setCustomerL1(entry.getKey());
					procoCustResponse.setCustomerL2(entry.getValue());
					customerChainL3.add(procoCustResponse);
					liC2.add(entry.getValue());
				}
				List<String> listOfL1 = new ArrayList<>();

				Set<String> setStr = new HashSet<>();

				for (List<String> string2 : liC2) {
					for (String string3 : string2) {
						String[] accLi = string3.split(":");
						if (accLi.length == 1) {
							listOfL1.add(accLi[0]);
							setStr.add("");
						} else {
							listOfL1.add(accLi[0]);
							setStr.add(accLi[1]);
						}
					}
				}

				List<String> listOfL2 = new ArrayList<>();
				listOfL2.addAll(setStr);
				String regionData = createPromoService.getClusterOnCustomer(listOfL1);
				storeDetailsResponse.setRegionCluster(regionData);
				storeDetailsResponse.setCustomerData(tblLaunchMaster.getLaunchNature2());
				storeDetailsResponse.setStoreCount(launchBacePacksDao.storeCount(listOfL1, listOfL2));
			} else {
				storeDetailsResponse
						.setStoreCount(launchBacePacksDao.storeCountByLaunchClass(tblLaunchMaster.getClassification()));
				storeDetailsResponse.setRegionCluster("ALL INDIA");
				storeDetailsResponse.setCustomerData("ALL CUSTOMERS");
			}
			List<String> launchStores = launchBacePacksDao.getLaunchStores();
			storeDetailsResponse.setLaunchFormat(launchStores);
			storeDetailsResponse.setCustomerStoreFormat(getCustomerStoreFormat(launchStores));
			return storeDetailsResponse;
		} catch (Exception e) {
			e.printStackTrace();
			storeDetailsResponse.setError(e.toString());
			throw new ParameterNotFoundException(0, "");
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String getStoreCountByClass(List<String> liClusterCode, List<String> accountl1String,
			List<String> accountl2String, String classification
			, boolean isCustomStoreFormat) { //Sarin Changes - Q1Sprint Feb2021 - Include All StoreFormats based on Custom Store Selection
		return launchBacePacksDao.getStoreCountByClass(liClusterCode, accountl1String, accountl2String, classification, isCustomStoreFormat);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public List<String> getCustomerStoreFormat(List<String> launchStore) {
		return launchBacePacksDao.getCustomerStoreFormat(launchStore);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String getStoreCount(List<AccountRequest> account) {
		String storeCount;
		try {
			List<String> liString = new ArrayList<>();
			for (AccountRequest string : account) {
				liString.add(string.getAccount().split(":")[0]);
			}
			storeCount = launchBacePacksDao.getStoreCount(liString);
		} catch (Exception e) {
			e.printStackTrace();
			storeCount = e.toString();
		}
		return storeCount;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public List<String> getLaunchStores(List<String> accountList) {
		return launchBacePacksDao.getLaunchStores(accountList);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public List<String> getStoreFormat() {
		return launchBacePacksDao.getStoreFormat();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public List<String> getTownSpecificData() {
		return launchBacePacksDao.getTownSpecific();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int saveLaunchClustersAndAcc(SaveLaunchClustersRequest saveLaunchClustersRequest, String userId) {
		String accString = saveLaunchClustersRequest.getAccountString();
		String finalAccString = "";
		Set<String> accountCombo = new HashSet<>();
		if (!accString.equals("ALL CUSTOMERS")) {
			String[] accStringArr = saveLaunchClustersRequest.getAccountString().split(",");
			for (int i = 0; i < accStringArr.length; i++) {
				if (accStringArr[i].indexOf(':') != -1) {
					finalAccString = finalAccString + accStringArr[i] + ",";
					accountCombo.add(accStringArr[i]);
				}
			}
		} else {
			finalAccString = accString;
			accountCombo.add(accString);
		}
		String finalAccStringToPass = String.join(",", accountCombo);
		saveLaunchClustersRequest.setAccountString(finalAccStringToPass);
		return launchBacePacksDao.saveLaunchClustersAndAcc(saveLaunchClustersRequest, userId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String updateLaunchDetails(SaveLaunchMasterRequest tblLaunchMaster, String userId) {
		return launchBacePacksDao.updateLaunchMaster(tblLaunchMaster, userId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String saveClusterByUpload(List<Object> saveLaunchCluster, String userID, String string, boolean b,
			boolean c, String launchId) {
		int result = 0;
		String storeFormYes = "";

		String storeFormNo = "";
		try {
			Set<String> geoClusterYes = new HashSet<>();
			Set<String> geoClusterNo = new HashSet<>();
			Iterator<Object> iterator = saveLaunchCluster.iterator();
			boolean ifYesIsAvailabe = false;
			boolean ifNoIsAvailabe = false;
			List<String> listOfL1Yes = new ArrayList<>();
			List<String> listOfL1No = new ArrayList<>();
			List<String> listOfL2Yes = new ArrayList<>();
			List<String> listOfL2No = new ArrayList<>();
			Set<String> accClusComboUniqueYes = new HashSet<>();
			Set<String> accClusComboUniqueNo = new HashSet<>();
			List<String> storeForYes = new ArrayList<>();
			List<String> storeForNo = new ArrayList<>();
			while (iterator.hasNext()) {
				LaunchClusterDataStoreForm obj = (LaunchClusterDataStoreForm) iterator.next();
				if (obj.getLaunch_planned().equals("Yes")) {
					if (obj.getAccount_L1().equals("ALL CUSTOMERS")) {
						accClusComboUniqueYes.add("ALL CUSTOMERS");
					} else {
						accClusComboUniqueYes.add(obj.getAccount_L1() + ":" + obj.getAccount_L2());
					}

					listOfL1Yes.add(obj.getAccount_L1());
					listOfL2Yes.add(obj.getAccount_L2());
					geoClusterYes.add(obj.getCluster());
					if (!obj.getStore_Format().trim().equals("")) {
						storeFormYes = obj.getStore_Format();
						storeForYes.add(storeFormYes);
					}
					ifYesIsAvailabe = true;
				} else {
					if (obj.getAccount_L1().equals("ALL CUSTOMERS")) {
						accClusComboUniqueNo.add("ALL CUSTOMERS");
					} else {
						accClusComboUniqueNo.add(obj.getAccount_L1() + ":" + obj.getAccount_L2());
					}
					geoClusterNo.add(obj.getCluster());
					listOfL1No.add(obj.getAccount_L1());
					listOfL2No.add(obj.getAccount_L2());
					if (!obj.getStore_Format().trim().equals("")) {
						storeFormNo = obj.getStore_Format();
						storeForNo.add(storeFormNo);
					}
					ifNoIsAvailabe = true;
				}
			}

			Set<String> storesForYesUnique = new HashSet<>(storeForYes);
			List<String> finalStoresYes = new ArrayList<>();
			finalStoresYes.addAll(storesForYesUnique);

			String storeFormatYes = String.join("~", finalStoresYes);

			Set<String> storesForNoUnique = new HashSet<>(storeForNo);
			List<String> finalStoresNo = new ArrayList<>();
			finalStoresNo.addAll(storesForNoUnique);

			String storeFormatNo = String.join("~", finalStoresNo);

			List<String> geographyYes = new ArrayList<>();
			geographyYes.addAll(geoClusterYes);

			List<String> geographyNo = new ArrayList<>();
			geographyNo.addAll(geoClusterNo);
			String geoDataYes[] = { "" };
			if (geoClusterYes.size() != 0) {
				if (geoClusterYes.contains("ALL INDIA")) {
					geoDataYes[0] = "ALL INDIA";
				} else {
					geoClusterYes.forEach(data -> {
						geoDataYes[0] = geoDataYes[0] + data + ",";
					});
				}
			}

			String geoDataNo[] = { "" };
			if (geoClusterNo.size() != 0) {
				if (geoClusterNo.contains("ALL INDIA")) {
					geoDataNo[0] = "ALL INDIA";
				} else {
					geoClusterNo.forEach(data -> {
						geoDataNo[0] = geoDataNo[0] + data + ",";
					});
				}
			}
			String finalAccStringToPassYes = String.join(",", accClusComboUniqueYes);
			String finalAccStringToPassNo = String.join(",", accClusComboUniqueNo);
			LaunchDataResponse launchDataResponse = launchDao.getSpecificLaunchData(launchId);
			if (ifYesIsAvailabe) {
				SaveLaunchClustersRequest saveLaunchClustersRequest = new SaveLaunchClustersRequest();
				saveLaunchClustersRequest.setAccountString(finalAccStringToPassYes);
				saveLaunchClustersRequest.setClusterString(geoDataYes[0]);
				saveLaunchClustersRequest.setLaunchId(launchId);
				saveLaunchClustersRequest.setStoreFormat(storeFormYes);
				saveLaunchClustersRequest.setCustomerStoreFormat("");
				if (storeFormatYes.trim().equals("")) {
					saveLaunchClustersRequest.setTotalStoresToLaunch(launchBacePacksDao.getStoreCountByClass(
							geographyYes, listOfL1Yes, listOfL2Yes, launchDataResponse.getClassification(), false));  //Sarin Changes - Q1Sprint Feb2021 Added last parameter
				} else {
					saveLaunchClustersRequest
							.setTotalStoresToLaunch(launchBacePacksDao.getStoreCountOnStore(storeFormatYes, listOfL1Yes,
									listOfL2Yes, geographyYes, launchDataResponse.getClassification(), false));  //Sarin Changes - Q1Sprint Feb2021 Added last parameter
				}
				saveLaunchClustersRequest.setLaunchPlanned("Yes");
				result = saveLaunchClustersAndAcc(saveLaunchClustersRequest, userID);
			}

			if (ifNoIsAvailabe) {
				SaveLaunchClustersRequest saveLaunchClustersRequest2 = new SaveLaunchClustersRequest();
				saveLaunchClustersRequest2.setAccountString(finalAccStringToPassNo);
				saveLaunchClustersRequest2.setClusterString(geoDataNo[0]);
				saveLaunchClustersRequest2.setLaunchId(launchId);
				saveLaunchClustersRequest2.setStoreFormat(storeFormNo);
				saveLaunchClustersRequest2.setCustomerStoreFormat("");
				if (storeFormatNo.trim().equals("")) {
					saveLaunchClustersRequest2.setTotalStoresToLaunch(launchBacePacksDao.getStoreCountByClass(
							geographyNo, listOfL1No, listOfL2No, launchDataResponse.getClassification(), false));  //Sarin Changes - Q1Sprint Feb2021 Added last parameter
				} else {
					saveLaunchClustersRequest2
							.setTotalStoresToLaunch(launchBacePacksDao.getStoreCountOnStore(storeFormatNo, listOfL1No,
									listOfL2No, geographyNo, launchDataResponse.getClassification(), false));  //Sarin Changes - Q1Sprint Feb2021 Added last parameter
				}

				saveLaunchClustersRequest2.setLaunchPlanned("No");
				result = result + saveLaunchClustersAndAcc(saveLaunchClustersRequest2, userID);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "ERROR";
		}
		if (result >= 1) {
			return "SUCCESS_FILE";
		} else {
			return "ERROR";
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public List<LaunchBasePackResponse> getLaunchBasePackDetails(String basepackCode) {
		return launchBacePacksDao.getLaunchBasePackDetails(basepackCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public List<String> getBpClassification() {
		return launchBacePacksDao.getBpClassification();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public List<String> getLaunchStores(List<String> liClusterName, List<String> accountl1String,
			List<String> accountl2String, String classification
			, boolean isCustomStoreFormat) {  //Sarin Changes - Q1Sprint Feb2021 - Include All StoreFormats based on Custom Store Selection
		return launchBacePacksDao.getLaunchStores(liClusterName, accountl1String, accountl2String, classification, isCustomStoreFormat);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Object getCustomerStoreFormat(List<String> liClusterName, List<String> accountl1String,
			List<String> accountl2String, String classification
			, boolean isCustomStoreFormat) {  //Sarin Changes - Q1Sprint Feb2021 - Include All StoreFormats based on Custom Store Selection
		return launchBacePacksDao.getCustomerStoreFormat(liClusterName, accountl1String, accountl2String,
				classification, isCustomStoreFormat);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String getStoreCountOnCust(String custStoreFormat, List<String> accountl1String,
			List<String> accountl2String, List<String> liClusterName, String classification
			, boolean isCustomStoreFormat) {  //Sarin Changes - Q1Sprint Feb2021 - Include All StoreFormats based on Custom Store Selection
		return launchBacePacksDao.getStoreCountOnCust(custStoreFormat, accountl1String, accountl2String, liClusterName,
				classification, isCustomStoreFormat);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String getStoreCountOnStore(String storeFormat, List<String> accountl1String, List<String> accountl2String,
			List<String> liClusterName,String classification
			, boolean isCustomStoreFormat) {  //Sarin Changes - Q1Sprint Feb2021 - Include All StoreFormats based on Custom Store Selection
		return launchBacePacksDao.getStoreCountOnStore(storeFormat, accountl1String, accountl2String, liClusterName, classification, isCustomStoreFormat);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public List<ArrayList<String>> getKamInputDumpForLaunch(ArrayList<String> headerDetail, String userId) {
		return launchBacePacksDao.getKamInputDumpForLaunch(headerDetail, userId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String saveKamRequestByUpload(List<Object> saveLaunchCluster, String userID) {
		try {
			Iterator<Object> iterator = saveLaunchCluster.iterator();
			List<String> reqIdsForAccept = new ArrayList<>();
			List<String> remarksForAccept = new ArrayList<>();
			List<String> reqIdsForReject = new ArrayList<>();
			List<String> remarksForReject = new ArrayList<>();
			while (iterator.hasNext()) {
				UploadKamInputs obj = (UploadKamInputs) iterator.next();
				if (obj.getAction_To_Take().equals("Yes")) {
					reqIdsForAccept.add(obj.getReq_Id());
					remarksForAccept.add(obj.getTme_Remarks());
				} else if (obj.getAction_To_Take().equals("No")) {
					reqIdsForReject.add(obj.getReq_Id());
					remarksForReject.add(obj.getTme_Remarks());
				} else {
					throw new Exception("Invalid values for the column Action_To_Take");
				}
			}

			if (reqIdsForAccept.isEmpty() && reqIdsForReject.isEmpty()) {
				throw new Exception("No data to Approve/Reject");
			}
			if (!reqIdsForAccept.isEmpty()) {
				String reqIdsCommaSeparated = String.join(",", reqIdsForAccept);
				AcceptByTmeRequest acceptByTme = new AcceptByTmeRequest();
				acceptByTme.setReqIds(reqIdsCommaSeparated);
				acceptByTme.setAcceptRemark(remarksForAccept.get(0));
				launchDao.acceptKamInputs(acceptByTme, userID);
			}

			if (!reqIdsForReject.isEmpty()) {
				String reqIdsCommaSeparated = String.join(",", reqIdsForReject);
				RejectByTmeRequest rejectByTme = new RejectByTmeRequest();
				rejectByTme.setReqIds(reqIdsCommaSeparated);
				rejectByTme.setRejectRemark(remarksForReject.get(0));
				launchDao.rejectKamInputs(rejectByTme, userID);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "ERROR";
		}
		return "SUCCESS_FILE";
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String saveClusterByUploadForCluster(List<Object> saveLaunchCluster, String userID, String string, boolean b,
			boolean c, String launchId) {
		int result = 0;
		String custStoreFormYes = "";
		String custStoreFormNo = "";
		try {
			Set<String> geoClusterYes = new HashSet<>();
			Set<String> geoClusterNo = new HashSet<>();
			Iterator<Object> iterator = saveLaunchCluster.iterator();
			boolean ifYesIsAvailabe = false;
			boolean ifNoIsAvailabe = false;
			List<String> listOfL1Yes = new ArrayList<>();
			List<String> listOfL1No = new ArrayList<>();
			List<String> listOfL2Yes = new ArrayList<>();
			List<String> listOfL2No = new ArrayList<>();
			Set<String> accClusComboUniqueYes = new HashSet<>();
			Set<String> accClusComboUniqueNo = new HashSet<>();
			List<String> custStoreForYes = new ArrayList<>();
			List<String> custStoreForNo = new ArrayList<>();
			while (iterator.hasNext()) {
				LaunchClusterDataCustStoreForm obj = (LaunchClusterDataCustStoreForm) iterator.next();
				if (obj.getLaunch_planned().equals("Yes")) {
					if (obj.getAccount_L1().equals("ALL CUSTOMERS")) {
						accClusComboUniqueYes.add("ALL CUSTOMERS");
					} else {
						accClusComboUniqueYes.add(obj.getAccount_L1() + ":" + obj.getAccount_L2());
					}

					listOfL1Yes.add(obj.getAccount_L1());
					listOfL2Yes.add(obj.getAccount_L2());
					geoClusterYes.add(obj.getCluster());
					if (!obj.getCustomer_Store_Format().trim().equals("")) {
						custStoreFormYes = obj.getCustomer_Store_Format();
						custStoreForYes.add(custStoreFormYes);
					}
					ifYesIsAvailabe = true;
				} else {
					if (obj.getAccount_L1().equals("ALL CUSTOMERS")) {
						accClusComboUniqueNo.add("ALL CUSTOMERS");
					} else {
						accClusComboUniqueNo.add(obj.getAccount_L1() + ":" + obj.getAccount_L2());
					}
					geoClusterNo.add(obj.getCluster());
					listOfL1No.add(obj.getAccount_L1());
					listOfL2No.add(obj.getAccount_L2());
					if (!obj.getCustomer_Store_Format().trim().equals("")) {
						custStoreFormNo = obj.getCustomer_Store_Format();
						custStoreForNo.add(custStoreFormNo);
					}
					ifNoIsAvailabe = true;
				}
			}

			Set<String> storesForYesUnique = new HashSet<>(custStoreForYes);
			List<String> finalCustStoresYes = new ArrayList<>();
			finalCustStoresYes.addAll(storesForYesUnique);

			String custStoreFormatYes = String.join("~", finalCustStoresYes);

			Set<String> storesForNoUnique = new HashSet<>(custStoreForNo);
			List<String> finalStoresNo = new ArrayList<>();
			finalStoresNo.addAll(storesForNoUnique);

			String custStoreFormatNo = String.join("~", finalStoresNo);

			List<String> geographyYes = new ArrayList<>();
			geographyYes.addAll(geoClusterYes);

			List<String> geographyNo = new ArrayList<>();
			geographyNo.addAll(geoClusterNo);
			String geoDataYes[] = { "" };
			if (geoClusterYes.size() != 0) {
				if (geoClusterYes.contains("ALL INDIA")) {
					geoDataYes[0] = "ALL INDIA";
				} else {
					geoClusterYes.forEach(data -> {
						geoDataYes[0] = geoDataYes[0] + data + ",";
					});
				}
			}

			String geoDataNo[] = { "" };
			if (geoClusterNo.size() != 0) {
				if (geoClusterNo.contains("ALL INDIA")) {
					geoDataNo[0] = "ALL INDIA";
				} else {
					geoClusterNo.forEach(data -> {
						geoDataNo[0] = geoDataNo[0] + data + ",";
					});
				}
			}
			String finalAccStringToPassYes = String.join(",", accClusComboUniqueYes);
			String finalAccStringToPassNo = String.join(",", accClusComboUniqueNo);
			LaunchDataResponse launchDataResponse = launchDao.getSpecificLaunchData(launchId);
			if (ifYesIsAvailabe) {
				SaveLaunchClustersRequest saveLaunchClustersRequest = new SaveLaunchClustersRequest();
				saveLaunchClustersRequest.setAccountString(finalAccStringToPassYes);
				saveLaunchClustersRequest.setClusterString(geoDataYes[0]);
				saveLaunchClustersRequest.setLaunchId(launchId);
				saveLaunchClustersRequest.setStoreFormat("");
				saveLaunchClustersRequest.setCustomerStoreFormat(custStoreFormYes);
				if (custStoreFormatYes.trim().equals("")) {
					saveLaunchClustersRequest.setTotalStoresToLaunch(launchBacePacksDao.getStoreCountByClass(
							geographyYes, listOfL1Yes, listOfL2Yes, launchDataResponse.getClassification(), false));  //Sarin Changes - Q1Sprint Feb2021 Added last parameter
				} else {
					saveLaunchClustersRequest
							.setTotalStoresToLaunch(launchBacePacksDao.getStoreCountOnCust(custStoreFormatYes,
									listOfL1Yes, listOfL2Yes, geographyYes, launchDataResponse.getClassification(), false));  //Sarin Changes - Q1Sprint Feb2021 Added last parameter
				}
				saveLaunchClustersRequest.setLaunchPlanned("Yes");
				result = saveLaunchClustersAndAcc(saveLaunchClustersRequest, userID);
			}

			if (ifNoIsAvailabe) {
				SaveLaunchClustersRequest saveLaunchClustersRequest2 = new SaveLaunchClustersRequest();
				saveLaunchClustersRequest2.setAccountString(finalAccStringToPassNo);
				saveLaunchClustersRequest2.setClusterString(geoDataNo[0]);
				saveLaunchClustersRequest2.setLaunchId(launchId);
				saveLaunchClustersRequest2.setStoreFormat("");
				saveLaunchClustersRequest2.setCustomerStoreFormat(custStoreFormNo);
				if (custStoreFormatNo.trim().equals("")) {
					saveLaunchClustersRequest2.setTotalStoresToLaunch(launchBacePacksDao.getStoreCountByClass(
							geographyNo, listOfL1No, listOfL2No, launchDataResponse.getClassification(), false));  //Sarin Changes - Q1Sprint Feb2021 Added last parameter
				} else {
					saveLaunchClustersRequest2
							.setTotalStoresToLaunch(launchBacePacksDao.getStoreCountOnCust(custStoreFormatNo,
									listOfL1No, listOfL2No, geographyNo, launchDataResponse.getClassification(), false));  //Sarin Changes - Q1Sprint Feb2021 Added last parameter
				}

				saveLaunchClustersRequest2.setLaunchPlanned("No");
				result = result + saveLaunchClustersAndAcc(saveLaunchClustersRequest2, userID);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "ERROR";
		}
		if (result >= 1) {
			return "SUCCESS_FILE";
		} else {
			return "ERROR";
		}

	}

}