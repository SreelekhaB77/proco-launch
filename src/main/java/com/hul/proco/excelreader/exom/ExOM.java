/*
 * code https://github.com/jittagornp/excel-object-mapping
 */
package com.hul.proco.excelreader.exom;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hul.proco.excelreader.exom.util.EachFieldCallback;
import com.hul.proco.excelreader.exom.util.ReflectionUtils;

/** @author redcrow */
public class ExOM {
	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(ExOM.class);
	private final File excelFile;
	@SuppressWarnings("rawtypes")
	private Class clazz;
	boolean isWrong = false;
	int index = 0;
	boolean isValidate = true;

	private ExOM(File excelFile) {
		this.excelFile = excelFile;
	}

	public static ExOM mapFromExcel(File excelFile) {
		return new ExOM(excelFile);
	}

	@SuppressWarnings("rawtypes")
	public ExOM to(Class clazz) {
		this.clazz = clazz;
		return this;
	}

	private String getValueByName(String name, Row row, Map<String, Integer> cells, boolean isSpecialCharAllowed) {
		if (cells.get(name) == null) {
			return null;
		}
		Cell cell = row.getCell(cells.get(name));
		String cellValue = getCellValue(cell);
		if (isSpecialCharAllowed) {
			return cellValue;
		}
		if (cellValue.equals("")) {
			return "";
		}
		if (cellValue != null && !cellValue.equals("")) {
			String patternToMatch = "[\\\\!\"#$%*,:;<=>?\\[\\]^{|}~]+";
			Pattern p = Pattern.compile(patternToMatch);
			Matcher m = p.matcher(cellValue);
			boolean matches = m.find();
			// String pattern = "([{\^-=$!|]})?*+.";
			// boolean matches = cellValue.matches(pattern);
			// System.out.println(cellValue + " : " + matches);
			if (!matches) {
				return cellValue;
			} else {
				return "SCNA";
			}
		}
		return cellValue;
	}

	private void mapName2Index(String name, Row row, Map<String, Integer> cells) {
		int index = findIndexCellByName(name, row);
		if (index != -1) {
			cells.put(name, index);
		}
	}

	private void readExcelHeader(final Row row, final Map<String, Integer> cells) throws Throwable {
		ReflectionUtils.eachFields(clazz, new EachFieldCallback() {
			@Override
			public void each(Field field, String name) throws Throwable {
				mapName2Index(name, row, cells);
			}
		});
	}

	private Object readExcelContent(final Row row, final Map<String, Integer> cells, final boolean isSpecialCharAllowed, final ArrayList<Integer> columnsToValidate) throws Throwable {
		final Object instance = clazz.newInstance();
		isWrong = false;
		index = 0;
		ReflectionUtils.eachFields(clazz, new EachFieldCallback() {
			@Override
			public void each(Field field, String name) throws Throwable {
				try {
					index++;
					isValidate = true;
					if (columnsToValidate != null && columnsToValidate.size() > 0) {
						if (columnsToValidate.contains(index)) {
							isValidate = false;
						}
					}
					String valueByName = getValueByName(name, row, cells, isValidate);
					if (valueByName != null && valueByName.equals("SCNA")) {
						isWrong = true;
					} else {
						ReflectionUtils.setValueOnField(instance, field, valueByName);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		if (isWrong)
			return null;
		return instance;
	}

	private boolean isVersion2003(File file) {
		return file.getName().endsWith(".xls");
	}

	private Workbook createWorkbook(InputStream inputStream) throws IOException {
		if (isVersion2003(excelFile)) {
			return new HSSFWorkbook(inputStream);
		} else { // 2007+
			return new XSSFWorkbook(inputStream);
		}
	}

	/*@SuppressWarnings("unchecked")
	public <T> Map<String, List<T>> map(int columnLength,
			boolean isSpecialCharAllowed, ArrayList<String> templateList)
			throws Throwable {
		InputStream inputStream = null;
		List<T> items = new LinkedList<>();
		List<T> errorList = new LinkedList<>();
		Map<String, List<T>> map = new HashMap<String, List<T>>();
		boolean isSequenceCorrect = false;
		boolean isFileBlank = false;

		try {
			Iterator<Row> rowIterator;
			inputStream = new FileInputStream(excelFile);
			Workbook workbook = createWorkbook(inputStream);
			int numberOfSheets = workbook.getNumberOfSheets();

			for (int index = 0; index < numberOfSheets; index++) {
				Sheet sheet = workbook.getSheetAt(index);
				rowIterator = sheet.iterator();

				int lastRowNum = sheet.getLastRowNum();
				if (lastRowNum == 0) {
					isFileBlank = true;
					errorList.add((T) "File does not contains any data.");
					break;

				}

				Map<String, Integer> nameIndexMap = new HashMap<>();
				while (rowIterator.hasNext()) {
					Row row = rowIterator.next();
					if (row.getRowNum() == 0) {
						int noOfColumns = row.getPhysicalNumberOfCells();

						Iterator<Cell> iterator = row.iterator();

						while (iterator.hasNext()) {

							Cell next = iterator.next();
							String headerName = next.getStringCellValue();
							// System.out.println("headerName: " + headerName);
							int columnIndex = next.getColumnIndex();
							// System.out.println("columnIndex: " +
							// columnIndex);

							if (templateList != null && templateList.size() > 0) {
								try {

									if (columnIndex <= templateList.size()) {
										String string = templateList
												.get(columnIndex);
										if (!headerName
												.equalsIgnoreCase(string)) {
											errorList
													.add((T) "Invalid column sequence. Please correct it and try again");
											isSequenceCorrect = true;
											break;
										}
									} else {
										errorList
												.add((T) "Invalid column sequence. Please correct it and try again");
										isSequenceCorrect = true;
										break;
									}

								} catch (Exception e) {
									e.printStackTrace();
									errorList
											.add((T) "Invalid column sequence. Please correct it and try again");
									isSequenceCorrect = true;
									break;
								}
							}

						}

						if (isSequenceCorrect)
							break;

						if (columnLength == noOfColumns) {
							readExcelHeader(row, nameIndexMap);

						} else {
							errorList
									.add((T) "Files contains invalid columns . Please correct it and try again.");
							break;
						}
					} else {
						try {
							Object readExcelContent = readExcelContent(row,
									nameIndexMap, isSpecialCharAllowed);
							if (readExcelContent != null) {
								items.add((T) readExcelContent);
							} else {
								errorList
										.add((T) "File contains invalid cell value . Please check for special character");
								break;
							}

						} catch (Exception e) {
							e.printStackTrace();
							errorList
									.add((T) "Something went wrong . Please try again.");
							break;

						}
					}
				}

				if (errorList != null && errorList.size() > 0) {
					map.put("ERROR", errorList);
					return map;
				}

				if (items != null && items.size() > 0) {
					map.put("DATA", items);
				}

			}
			if (isFileBlank) {
				if (errorList != null && errorList.size() > 0) {
					map.put("ERROR", errorList);
					return map;
				}
			}
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}

		return map;
	}*/
	@SuppressWarnings("unchecked")
	public <T> Map<String, List<T>> map(int columnLength, boolean isSpecialCharAllowed, ArrayList<String> templateList, ArrayList<Integer> columnArray) throws Throwable {
		InputStream inputStream = null;
		List<T> items = new LinkedList<>();
		List<T> errorList = new LinkedList<>();
		Map<String, List<T>> map = new HashMap<String, List<T>>();
		boolean isSequenceCorrect = false;
		boolean isFileBlank = false;
		try {
			Iterator<Row> rowIterator;
			inputStream = new FileInputStream(excelFile);
			Workbook workbook = createWorkbook(inputStream);
			int numberOfSheets = workbook.getNumberOfSheets();
			for (int index = 0; index < numberOfSheets; index++) {
				Sheet sheet = workbook.getSheetAt(index);
				rowIterator = sheet.iterator();
				int lastRowNum = sheet.getLastRowNum();
				if (lastRowNum == 0) {
					isFileBlank = true;
					errorList.add((T) "File does not contains any data.");
					break;
				}
				Map<String, Integer> nameIndexMap = new HashMap<>();
				while (rowIterator.hasNext()) {
					Row row = rowIterator.next();
					if (row.getRowNum() == 0) {
						int noOfColumns = row.getPhysicalNumberOfCells();
						Iterator<Cell> iterator = row.iterator();
						while (iterator.hasNext()) {
							Cell next = iterator.next();
							String headerName = next.getStringCellValue();
							// System.out.println("headerName: " + headerName);
							int columnIndex = next.getColumnIndex();
							// System.out.println("columnIndex: " +
							// columnIndex);
							if (templateList != null && templateList.size() > 0) {
								try {
									if (columnIndex <= templateList.size()) {
										String string = templateList.get(columnIndex);
										if (!headerName.equalsIgnoreCase(string)) {
											errorList.add((T) "Invalid column sequence. Please correct it and try again");
											isSequenceCorrect = true;
											break;
										}
									} else {
										errorList.add((T) "Invalid column sequence. Please correct it and try again");
										isSequenceCorrect = true;
										break;
									}
								} catch (Exception e) {
									e.printStackTrace();
									errorList.add((T) "Invalid column sequence. Please correct it and try again");
									isSequenceCorrect = true;
									break;
								}
							}
						}
						if (isSequenceCorrect)
							break;
						if (columnLength == noOfColumns) {
							readExcelHeader(row, nameIndexMap);
						} else {
							errorList.add((T) "Files contains invalid columns . Please correct it and try again.");
							break;
						}
					} else {
						try {
							Object readExcelContent = readExcelContent(row, nameIndexMap, isSpecialCharAllowed, columnArray);
							if (readExcelContent != null) {
								items.add((T) readExcelContent);
							} else {
								errorList.add((T) "File contains invalid cell value . Please check for special character");
								break;
							}
						} catch (Exception e) {
							e.printStackTrace();
							errorList.add((T) "Something went wrong . Please try again.");
							break;
						}
					}
				}
				if (errorList != null && errorList.size() > 0) {
					map.put("ERROR", errorList);
					return map;
				}
				if (items != null && items.size() > 0) {
					map.put("DATA", items);
				}
			}
			if (isFileBlank) {
				if (errorList != null && errorList.size() > 0) {
					map.put("ERROR", errorList);
					return map;
				}
			}
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	public <T> Map<String, List<T>> map(int columnLength, boolean isSpecialCharAllowed, ArrayList<Integer> columnsToValidate) throws Throwable {
		InputStream inputStream = null;
		List<T> items = new LinkedList<>();
		List<T> errorList = new LinkedList<>();
		Map<String, List<T>> map = new HashMap<String, List<T>>();
		boolean isFileBlank = false;
		try {
			Iterator<Row> rowIterator;
			inputStream = new FileInputStream(excelFile);
			Workbook workbook = createWorkbook(inputStream);
			//int numberOfSheets = workbook.getNumberOfSheets();
			//for (int index = 0; index < numberOfSheets; index++) {
				Sheet sheet = workbook.getSheetAt(index);
				rowIterator = sheet.iterator();
				int lastRowNum = sheet.getLastRowNum();
				if (lastRowNum == 0) {
					isFileBlank = true;
					errorList.add((T) "File does not contains any data.");
					//break;
				}
				Map<String, Integer> nameIndexMap = new HashMap<>();
				while (rowIterator.hasNext()) {
					Row row = rowIterator.next();
					if (row.getRowNum() == 0) {
						int noOfColumns = row.getPhysicalNumberOfCells();
						if (columnLength == noOfColumns) {
							readExcelHeader(row, nameIndexMap);
						} else {
							errorList.add((T) "Files contains invalid columns . Please correct it and try again.");
							break;
						}
					} else {
						try {
							Object readExcelContent = readExcelContent(row, nameIndexMap, isSpecialCharAllowed, columnsToValidate);
							if (readExcelContent != null) {
								items.add((T) readExcelContent);
							} else {
								errorList.add((T) "File contains invalid cell value . Please check for special character");
								break;
							}
						} catch (Exception e) {
							e.printStackTrace();
							errorList.add((T) "Something went wrong . Please try again.");
							break;
						}
					}
				}
				if (errorList != null && errorList.size() > 0) {
					map.put("ERROR", errorList);
					return map;
				}
				if (items != null && items.size() > 0) {
					map.put("DATA", items);
				}
			//}
			if (isFileBlank) {
				if (errorList != null && errorList.size() > 0) {
					map.put("ERROR", errorList);
					return map;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
		return map;
	}

	private int findIndexCellByName(String name, Row row) {
		Iterator<Cell> iterator = row.cellIterator();
		while (iterator.hasNext()) {
			Cell cell = iterator.next();
			if (getCellValue(cell).trim().equalsIgnoreCase(name)) {
				return cell.getColumnIndex();
			}
		}
		return -1;
	}

	private String getCellValue(Cell cell) {
		if (cell == null) {
			return "";
		}
		String value = "";
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BOOLEAN:
			value += String.valueOf(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_NUMERIC:
			value += new BigDecimal(cell.getNumericCellValue()).toString();
			break;
		case Cell.CELL_TYPE_STRING:
			value += cell.getStringCellValue();
			break;
		}
		return value;
	}
}
