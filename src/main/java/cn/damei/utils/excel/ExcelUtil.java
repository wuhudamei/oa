package cn.damei.utils.excel;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cn.damei.utils.*;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springside.modules.utils.Collections3;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;
public final class ExcelUtil {
	private static String[] DATE_PATTERNS = {"yyyy-MM-dd", "MM/dd/yyyy", "yyyy/MM/dd","yyyyMMdd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm"};
	private static ExcelUtil util = new ExcelUtil();
	private ExcelUtil() {
	}
	public static ExcelUtil getInstance() {
		return util;
	}
	public <E> List<E> readExcel2ObjsByFile(File file, Class<E> clz) {
		Workbook wb = null;
		try {
			wb = WorkbookFactory.create(file);
			return handleExcel2Objs(wb, clz, 0);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}
	private <E> ExcelTemplate handleObj2Excel(final String excelTemplateFile, List<E> exportDataList, Class<E> clz, boolean isClassPath) {
		ExcelTemplate template = new ExcelTemplate();
		if (isClassPath) {
			template.readTemplateByClasspath(excelTemplateFile);
		} else {
			template.readTemplateByPath(excelTemplateFile);
		}
		List<ExcelHeader> headers = getHeaderList(clz);
		//输出标题
		template.createNewRow();
		for (ExcelHeader eh : headers) {
			template.createCell(eh.getTitle());
		}
		//写数据到Excel
		for (E rowData : exportDataList) {
			template.createNewRow();
			for (ExcelHeader eh : headers) {
				createCellAndWriteValue(template, rowData, eh);
			}
		}
		return template;
	}
	private <E> void createCellAndWriteValue(ExcelTemplate template, E rowData, ExcelHeader excelHeader) {
		Object propValue = ReflectionUtils.getFieldValue(rowData, excelHeader.getPropertyName());
		if (propValue == null) {
			propValue = StringUtils.EMPTY;
		}
		if (propValue instanceof Date) {
			template.createCell((Date) propValue);
		} else if (propValue instanceof Double) {
			template.createCell((Double) propValue);
		} else if (propValue instanceof Integer) {
			template.createCell((Integer) propValue);
		} else if (propValue instanceof Boolean) {
			template.createCell((Boolean) propValue);
		} else {
			template.createCell(propValue.toString());
		}
	}
	public <E> void exportObj2ExcelByTemplate(Map<String, String> variableExtras, String excelTemplatePath, OutputStream os, List<E> exportDataList, Class<E> clz, boolean isClasspath) {
		ExcelTemplate et = handleObj2Excel(excelTemplatePath, exportDataList, clz, isClasspath);
		et.replaceFinalData(variableExtras);
		et.wirteToStream(os);
	}
	public <E> void exportObj2ExcelByTemplate(Map<String, String> variableExtras, String excelTemplatePath, String outPath, List<E> exportDataList, Class<E> clz, boolean isClasspath) {
		ExcelTemplate et = handleObj2Excel(excelTemplatePath, exportDataList, clz, isClasspath);
		et.replaceFinalData(variableExtras);
		et.writeToFile(outPath);
	}
	public <E> void exportObj2ExcelByTemplate(Properties variableExtras, String excelTemplatePath, OutputStream os, List<E> exportDataList, Class<E> clz, boolean isClasspath) {
		ExcelTemplate et = handleObj2Excel(excelTemplatePath, exportDataList, clz, isClasspath);
		et.replaceFinalData(variableExtras);
		et.wirteToStream(os);
	}
	public <E> void exportObj2ExcelByTemplate(Properties variableExtras, String excelTemplatePath, String outFile, List<E> exportDataList, Class<E> clz, boolean isClasspath) {
		ExcelTemplate et = handleObj2Excel(excelTemplatePath, exportDataList, clz, isClasspath);
		et.replaceFinalData(variableExtras);
		et.writeToFile(outFile);
	}
	private <E> Workbook handleObj2Excel(List<E> exportDataList, Class<E> clz, boolean isXssf) {
		Workbook wb = null;
		if (isXssf) {
			wb = new XSSFWorkbook();
		} else {
			wb = new HSSFWorkbook();
		}
		Sheet sheet = wb.createSheet();
		Row row = sheet.createRow(0);
		List<ExcelHeader> headers = getHeaderList(clz);
		//写标题
		for (int i = 0; i < headers.size(); i++) {
			row.createCell(i).setCellValue(headers.get(i).getTitle());
		}
		//写数据
		Object obj = null;
		for (int rowIdx = 0; rowIdx < exportDataList.size(); rowIdx++) {
			row = sheet.createRow(rowIdx + 1);
			obj = exportDataList.get(rowIdx);
			for (int colIdx = 0; colIdx < headers.size(); colIdx++) {
				Cell cell = row.createCell(colIdx);
				Object propValue = ReflectionUtils.getFieldValue(obj, headers.get(colIdx).getPropertyName());
				if (propValue == null) {
					propValue = StringUtils.EMPTY;
				}
				if (propValue instanceof Date) {
					cell.setCellValue((Date) propValue);
				} else if (propValue instanceof Double) {
					cell.setCellValue((Double) propValue);
				} else if (propValue instanceof Integer) {
					cell.setCellValue((Integer) propValue);
				} else if (propValue instanceof Boolean) {
					cell.setCellValue((Boolean) propValue);
				} else {
					cell.setCellValue(propValue.toString());
				}
			}
		}
		return wb;
	}
	public <E> void exportObj2Excel(String outFileAbsolutePath, List<E> exportDataList, Class<E> clz, boolean isXssf) {
		Workbook wb = handleObj2Excel(exportDataList, clz, isXssf);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(outFileAbsolutePath);
			wb.write(fos);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(fos);
			closeWorkbookQuietly(wb);
		}
	}
	public <E> Workbook exportObj2Excel(OutputStream os, List<E> exportDataList, Class<E> clz, boolean isXssf) {
		Workbook wb = null;
		try {
			wb = handleObj2Excel(exportDataList, clz, isXssf);
			wb.write(os);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeWorkbookQuietly(wb);
		}
		return null;
	}
	public <E> List<E> readExcel2ObjsByClasspath(String clsPath, Class<E> clz, int titleRowIndex) {
		Workbook wb = null;
		try {
			wb = WorkbookFactory.create(getClass().getResourceAsStream(clsPath));
			return handleExcel2Objs(wb, clz, titleRowIndex);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeWorkbookQuietly(wb);
		}
		return null;
	}
	public <E> List<E> readExcel2ObjsByStream(InputStream inputStream, Class<E> clz) {
		Workbook wb = null;
		try {
			wb = WorkbookFactory.create(inputStream);
			return handleExcel2Objs(wb, clz, 0);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeWorkbookQuietly(wb);
		}
		return Collections.emptyList();
	}
	public static void closeWorkbookQuietly(Workbook wb) {
		if (wb != null) {
			try {
				wb.close();
			} catch (Exception e) {
			}
		}
	}
	public <E> List<E> readExcel2ObjsByPath(String path, Class<E> clz, int titleRowIndex) {
		Workbook wb = null;
		try {
			wb = WorkbookFactory.create(new File(path));
			return handleExcel2Objs(wb, clz, titleRowIndex);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeWorkbookQuietly(wb);
		}
		return null;
	}
	public <E> List<E> readExcel2ObjsByClasspath(String clsPath, Class<E> clz) {
		return this.readExcel2ObjsByClasspath(clsPath, clz, 0);
	}
	public <E> List<E> readExcel2ObjsByPath(String path, Class<E> clz) {
		return this.readExcel2ObjsByPath(path, clz, 0);
	}
	private Object getCellValue(Cell cell) {
		Object cellValue = null;
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BLANK:
			cellValue = "";
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			cellValue = cell.getBooleanCellValue();
			break;
		case Cell.CELL_TYPE_NUMERIC:
			// 日期
			if (DateUtil.isCellDateFormatted(cell)) {
				cellValue = cell.getDateCellValue();
			} else {
				//其他数字类型也按照字符串处理，因为浮点数可能用科学计数法表示
				cellValue = ArithUtils.formatMoney(cell.getNumericCellValue());
			}
			break;
		case Cell.CELL_TYPE_STRING:
			cellValue = StringUtils.trimToEmpty(cell.getStringCellValue());
			break;
		default:
			cellValue = StringUtils.trimToEmpty(cell.getStringCellValue());
			break;
		}
		return cellValue;
	}
	public <E> List<E> handleExcel2ObjsOfSheet(Sheet sheet, Class<E> clz, int titleRowIndex) {
		List<E> entityList = null;
		Row row = sheet.getRow(titleRowIndex);
		entityList = new ArrayList<E>();
		Map<Integer, String> colIndexPropertyMap = getHeaderMap(row, clz);
		if (colIndexPropertyMap == null || colIndexPropertyMap.isEmpty())
			throw new RuntimeException("要读取的Excel的格式不正确，检查是否设定了合适的行");
		for (int dataRowIdx = titleRowIndex + 1; dataRowIdx <= sheet.getLastRowNum(); dataRowIdx++) {
			row = sheet.getRow(dataRowIdx);
			Cell firstCol = row.getCell(0);
			if (firstCol == null) {
				//如果第一列没有值 则判定为空行，空行不处理
				continue;
			}
			Object firstCellValue = this.getCellValue(firstCol);
			if (firstCellValue == null || (firstCellValue instanceof String && StringUtils.isEmpty(firstCellValue.toString()))) {
				//如果第一列没有值 则判定为空行，空行不处理
				continue;
			}
			try {
				E obj = clz.newInstance();
				for (Cell cell : row) {
					Object cellValue = this.getCellValue(cell);
					if (cellValue != null) {
						Object castedCellValue = null;
						int colIndx = cell.getColumnIndex();
						String fieldName = colIndexPropertyMap.get(colIndx);
						Field field = ReflectionUtils.getAccessibleField(obj, fieldName);
						Class type = field.getType();
						if (type.equals(Integer.class)) {
							castedCellValue = Integer.valueOf(cellValue.toString());
						} else if (type.equals(Long.class)) {
							castedCellValue = Long.valueOf(cellValue.toString());
						} else if (type.equals(Double.class)) {
							String number = cellValue.toString();
							if (number.endsWith("%")) {
								castedCellValue = Double.valueOf(ArithUtils.percentToDouble(number));
							} else {
								castedCellValue = NumberUtils.toDouble(number);
							}
						} else if (type.equals(BigDecimal.class)) {
							String number = cellValue.toString();
							if (number.endsWith("%")) {
								castedCellValue = new BigDecimal(ArithUtils.percentToDouble(number));
							} else {
								castedCellValue = new BigDecimal(number);
							}
						} else if (type.equals(Boolean.class)) {
							castedCellValue = cellValue.getClass().equals(Boolean.class) ? cellValue : Boolean.parseBoolean(cellValue.toString());
						} else if (type.equals(Date.class)) {
							castedCellValue = cellValue.getClass().equals(Date.class) ? cellValue : DateUtils.parseDate(cellValue.toString(), DATE_PATTERNS);
						} else if (type.isEnum()) {
							String enumString = cellValue.toString();
							if (StringUtils.isNotBlank(enumString)) {
								castedCellValue = Enum.valueOf(type, enumString);
							}
						} else if (type.equals(String.class)) {
							castedCellValue = cellValue.toString();
						} else {
							castedCellValue = cellValue;
						}
						field.set(obj, castedCellValue);
					}
				}
				entityList.add(obj);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return entityList;
	}
	private <E> List<E> handleExcel2Objs(Workbook wb, Class<E> clz, int titleRowIndex) {
		Sheet sheet = wb.getSheetAt(0);
		return handleExcel2ObjsOfSheet(sheet, clz, titleRowIndex);
	}
	private <E> List<ExcelHeader> getHeaderList(Class<E> clz) {
		List<ExcelHeader> headers = new ArrayList<ExcelHeader>();
		//在 Java Bean 上进行内省，了解其所有属性、公开的方法和事件。
		try {
			BeanInfo info = Introspector.getBeanInfo(clz);
			PropertyDescriptor[] proertyDescritors = info.getPropertyDescriptors();
			for (PropertyDescriptor pd : proertyDescritors) {
				String propertyName = pd.getName();
				Method method = pd.getReadMethod();
				if (method.isAnnotationPresent(ExcelTitle.class)) {
					ExcelTitle excelTitle = method.getAnnotation(ExcelTitle.class);
					headers.add(new ExcelHeader(excelTitle.title(), excelTitle.order(), propertyName));
				}
			}
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		Collections.sort(headers);
		return headers;
	}
	private <E> Map<Integer, String> getHeaderMap(Row titleRow, Class<E> clz) {
		List<ExcelHeader> headers = getHeaderList(clz);
		Map<Integer, String> maps = new HashMap<Integer, String>();
		for (Cell cell : titleRow) {
			String title = cell.getStringCellValue();
			for (ExcelHeader eh : headers) {
				if (eh.getTitle().equals(title.trim())) {
					maps.put(cell.getColumnIndex(), eh.getPropertyName());
					break;
				}
			}
		}
		return maps;
	}
	public <E> Workbook exportObj2ExcelWithTitleAndFields(List<E> exportDataList, Class<E> clz, boolean isXssf, List<String> titles, List<String> fields) {
		return handleObj2ExcelWithTitleAndFields(exportDataList, clz, isXssf, titles, fields);
	}
	private <E> Workbook handleObj2ExcelWithTitleAndFields(List<E> exportDataList, Class<E> clz, boolean isXssf, List<String> titles, List<String> fields) {
		Workbook wb = null;
		if (isXssf) {
			wb = new XSSFWorkbook();
		} else {
			wb = new HSSFWorkbook();
		}
		Sheet sheet = wb.createSheet();
		Row row = sheet.createRow(0);
		//写标题
		int length = titles.size();
		for (int i = 0; i < length; i++) {
			row.createCell(i).setCellValue(titles.get(i));
		}
		//写数据
		Object obj = null;
		for (int rowIdx = 0; rowIdx < exportDataList.size(); rowIdx++) {
			row = sheet.createRow(rowIdx + 1);
			obj = exportDataList.get(rowIdx);
			for (int colIdx = 0; colIdx < length; colIdx++) {
				Cell cell = row.createCell(colIdx);
				String fieldName = fields.get(colIdx);
				Object propValue = null;
				try {
					//支持bean.bean.colName
					propValue = BeanUtilsBean.getInstance().getPropertyUtils().getProperty(obj, fieldName);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				if (propValue == null) {
					propValue = StringUtils.EMPTY;
				}
				if (propValue instanceof Date) {
					cell.setCellValue(cn.damei.utils.excel.DateUtil.formatDate((Date) propValue));
				} else if (propValue instanceof Double) {
					cell.setCellValue((Double) propValue);
				} else if (propValue instanceof Integer) {
					cell.setCellValue((Integer) propValue);
				} else if (propValue instanceof Boolean) {
					cell.setCellValue((Boolean) propValue);
				} else {
					cell.setCellValue(propValue.toString());
				}
			}
		}
		return wb;
	}
	//导出基金标准净值
	public static <E> Workbook exportFundStandNetValue(List<E> exportDataList, boolean isXssf, Class<E> clz, Map<String, String> fundCodeNameMap, String fundCodeField, String dateField, String valueField) throws Exception {
		Workbook wb = null;
		if (isXssf) {
			wb = new XSSFWorkbook();
		} else {
			wb = new HSSFWorkbook();
		}
		//如果导入数据为空,直接返回空excel
		if (Collections3.isEmpty(exportDataList))
			return wb;
		//Date fundCode Netvalue
		Map<String, BigDecimal> innerStandNetvalueMap = null;
		Map<String, Map<String, BigDecimal>> standNetvalueMap = Maps.newHashMap();
		String dateKey = null;
		Object propValue = null;
		//组装数据
		for (Object obj : exportDataList) {
			propValue = BeanUtilsBean.getInstance().getPropertyUtils().getProperty(obj, dateField);
			dateKey = (String) propValue;
			if (standNetvalueMap.containsKey(dateKey)) {
				innerStandNetvalueMap = standNetvalueMap.get(dateKey);
			} else {
				innerStandNetvalueMap = Maps.newHashMap();
			}
			propValue = BeanUtilsBean.getInstance().getPropertyUtils().getProperty(obj, valueField);
			innerStandNetvalueMap.put(BeanUtilsBean.getInstance().getPropertyUtils().getProperty(obj, fundCodeField).toString(), propValue == null ? null : (BigDecimal) propValue);
			standNetvalueMap.put(dateKey, innerStandNetvalueMap);
		}
		Sheet sheet = wb.createSheet();
		//创建标题行
		Row row = sheet.createRow(0);
		int length = fundCodeNameMap.size();
		List<String> fundCodes = new ArrayList<String>(fundCodeNameMap.keySet());
		Collections.sort(fundCodes);
		String fundCode = null;
		//添加基金名称行
		Row fundNameRow = sheet.createRow(1);
		fundNameRow.createCell(0).setCellValue("");
		row.createCell(0).setCellValue("");
		for (int i = 0; i < length; i++) {
			fundCode = fundCodes.get(i);
			row.createCell(i + 1).setCellValue(fundCode);
			fundNameRow.createCell(i + 1).setCellValue(fundCodeNameMap.get(fundCode));
		}
		//创建内容
		if (Collections3.isEmpty(standNetvalueMap))
			return wb;
		//日期排序 降序
		List<String> dateKeys = Lists.newArrayList(standNetvalueMap.keySet());
		Collections.sort(dateKeys);
		int contentLen = dateKeys.size();
		BigDecimal value = null;
		for (int rowIdx = 0; rowIdx < contentLen; rowIdx++) {
			dateKey = dateKeys.get(rowIdx);
			innerStandNetvalueMap = standNetvalueMap.get(dateKey);
			//创建新行
			row = sheet.createRow(rowIdx + 2);
			//设置日期
			row.createCell(0).setCellValue(dateKey);
			for (int codeIdx = 0; codeIdx < length; codeIdx++) {
				value = innerStandNetvalueMap.get(fundCodes.get(codeIdx));
				if (value == null) {
					row.createCell(codeIdx + 1).setCellValue("");
				} else {
					row.createCell(codeIdx + 1).setCellValue(value.doubleValue());
				}
			}
		}
		return wb;
	}
}