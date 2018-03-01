package cn.damei.utils.excel;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.rocoinfo.weixin.util.StringUtils;
public class UploadExcel {
	private static final Logger logger = LoggerFactory.getLogger(UploadExcel.class);
	private static final String columnNam = "columnNam";// 读取列别名
	private static final String importIdx = "importIdx";// 导入的列索引
	public static List<Object> readExcel(Class clas, InputStream fileStream, String readName) throws Exception {
		Map<String, String[]> uploadConfigMap = analyData(readName);
		List<Object> list = new ArrayList<Object>();
		String[] column = uploadConfigMap.get(columnNam);// 模板配置的列
		Integer startRow = Integer.parseInt(uploadConfigMap.get(importIdx)[0]);
		Object tmp = null;
		try {
			Workbook workbook = WorkbookFactory.create(fileStream);
			Sheet sheet = workbook.getSheetAt(0);
			int totalRow = getTotalRow(sheet);
			if (totalRow > 0) {
				for (int i = 1; i <= totalRow; i++) {
					int columnIndex = 1;
					Row row = sheet.getRow((short) i);
					tmp = clas.newInstance();
					if (row == null) {
						throw new Exception("导入的Excel中存在空行!");
					}
					for (int j = startRow; j <= column.length; j++) {
						Cell cell = row.getCell((short) j);
						if (cell != null) {
							if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
								String tmpVal = String.valueOf(cell.getRichStringCellValue());
								//
								readExcela(tmp, column[columnIndex - 1], tmpVal);
								columnIndex++;
							}
							if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
								if (HSSFDateUtil.isCellDateFormatted(cell)) {
									double d = cell.getNumericCellValue();
									Date date = HSSFDateUtil.getJavaDate(d);
									readExcela(tmp, column[columnIndex - 1],
											new SimpleDateFormat("yyyy-MM-dd").format(date));
									columnIndex++;
								} else {
									readExcela(tmp, column[columnIndex - 1],
											String.valueOf(cell.getNumericCellValue()));
									columnIndex++;
								}
							}
						}
					}
					list.add(tmp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return list;
	}
	private static int getTotalRow(Sheet hsf) {
		if (hsf.getLastRowNum() > 0) {
			return hsf.getLastRowNum();
		} else {
			return 0;
		}
	}
	private static Map<String, String> getMethodMap(Class<?> classe, String simMethod) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(simMethod, getSetMethod(classe, simMethod));
		return map;
	}
	private static String getSetMethod(Class<?> classed, String simMethod) {
		StringBuffer methodAndParam = new StringBuffer();
		try {
			Method[] allSetMethod = classed.getMethods();
			for (Method everSetMethod : allSetMethod) {
				String methodNam = everSetMethod.getName();
				if (methodNam.indexOf("set") != -1) {
					if (methodNam.toLowerCase().indexOf(simMethod) != -1) {
						Type[] type = everSetMethod.getGenericParameterTypes();
						for (Type paramType : type) {
							String simParamType = paramType.toString().substring(paramType.toString().indexOf("j"),
									paramType.toString().length());
							methodAndParam.append(methodNam + "," + simParamType);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return methodAndParam.toString();
	}
	private static void readExcela(Object obj, String simMeth, String value) {
		try {
			Class<?> classed = Class.forName(obj.getClass().getName());
			Map<String, String> map = getMethodMap(classed, simMeth);
			String methodAndType = map.get(simMeth);
			String[] tmpVal = methodAndType.split(",");
			Method method1 = classed.getMethod(tmpVal[0], new Class[] { Class.forName(tmpVal[1]) });
			if ("java.lang.String".equals(tmpVal[1])) {
				method1.invoke(obj, value);
			} else if ("java.lang.Integer".equals(tmpVal[1])) {
				method1.invoke(obj, (int) Double.parseDouble(value));
			} else if ("java.lang.Double".equals(tmpVal[1])) {
				method1.invoke(obj, Double.valueOf(value));
			} else if ("java.lang.Float".equals(tmpVal[1])) {
				method1.invoke(obj, (float) Double.parseDouble(value));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static Properties loadProperty() {
		Properties prop = new Properties();
		try {
			InputStream is = UploadExcel.class.getResourceAsStream("/uploadConfig.properties");
			prop.load(is);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prop;
	}
	private static Map<String, String[]> analyData(String param) throws Exception {
		Map<String, String[]> map = new HashMap<String, String[]>();
		Properties properties = loadProperty();
		String retColumnNam = properties.getProperty(param + "-" + columnNam);
		String retImportIdx = properties.getProperty(param + "-" + importIdx);
		if (StringUtils.isBlank(retColumnNam) || StringUtils.isBlank(retImportIdx)) {
			throw new Exception("配置文件错误!");
		}
		try {
			Integer.parseInt(retImportIdx);
		} catch (Exception e) {
			throw new Exception("【importIdx】必须为数字!");
		}
		if (Integer.parseInt(retImportIdx) <= 0) {
			throw new Exception("【importIdx】必须为大于零的正数!");
		}
		String[] tmpColumnNam = retColumnNam.split(",");
		String[] tmpImportIdx = retImportIdx.split(",");
		map.put(columnNam, tmpColumnNam);
		map.put(importIdx, tmpImportIdx);
		return map;
	}
}
