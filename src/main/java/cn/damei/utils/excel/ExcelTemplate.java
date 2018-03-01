package cn.damei.utils.excel;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import java.io.*;
import java.util.*;
public final class ExcelTemplate {
	public final static String DATA_LINE = "datas";
	public final static String DEFAULT_STYLE = "defaultStyles";
	public final static String STYLE = "styles";
	public final static String SER_NUM = "sernums";
	private Workbook wb;
	private Sheet sheet;
	private int dataInitColIndex;
	private int dataInitRowIndex;
	private int curColIndex;
	private int curRowIndex;
	private Row curRow;
	private int lastRowIndex;
	private CellStyle defaultStyle;
	private float defaultRowHeight;
	private Map<Integer, CellStyle> colStyleMap;
	private int serColIndex;
	//1、读取相应的模板文档
	public ExcelTemplate readTemplateByClasspath(String clsPath) {
		try {
			wb = WorkbookFactory.create(getClass().getClassLoader().getResourceAsStream(clsPath));
			initTemplate();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
			throw new RuntimeException("读取模板格式有错，！请检查");
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("读取模板不存在！请检查");
		}
		return this;
	}
	public void writeToFile(String filepath) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(filepath);
			wb.write(fos);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("写入的文件不存在");
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("写入数据失败:" + e.getMessage());
		} finally {
			try {
				if (fos != null)
					fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public void wirteToStream(OutputStream os) {
		try {
			wb.write(os);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("写入流失败:" + e.getMessage());
		}
	}
	public ExcelTemplate readTemplateByPath(String fileAbsolutePath) {
		try {
			File file = new File(fileAbsolutePath);
			wb = WorkbookFactory.create(file);
			initTemplate();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
			throw new RuntimeException("读取模板格式有错，！请检查");
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("读取模板不存在！请检查");
		}
		return this;
	}
	public void createCell(String value) {
		Cell c = curRow.createCell(curColIndex);
		setCellStyle(c);
		c.setCellType(HSSFCell.CELL_TYPE_STRING);
		c.setCellValue(value);
		curColIndex++;
	}
	public void createCell(int value) {
		Cell c = curRow.createCell(curColIndex);
		setCellStyle(c);
		c.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		c.setCellValue(value);
		curColIndex++;
	}
	public void createCell(Date value) {
		Cell c = curRow.createCell(curColIndex);
		setCellStyle(c);
		c.setCellValue(value);
		curColIndex++;
	}
	public void createCell(double value) {
		Cell c = curRow.createCell(curColIndex);
		setCellStyle(c);
		c.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		c.setCellValue(value);
		curColIndex++;
	}
	public void createCell(boolean value) {
		Cell c = curRow.createCell(curColIndex);
		setCellStyle(c);
		c.setCellType(HSSFCell.CELL_TYPE_BOOLEAN);
		c.setCellValue(value);
		curColIndex++;
	}
	public void createCell(Calendar value) {
		Cell c = curRow.createCell(curColIndex);
		setCellStyle(c);
		c.setCellValue(value);
		curColIndex++;
	}
	private void setCellStyle(Cell c) {
		if (colStyleMap.containsKey(curColIndex)) {
			c.setCellStyle(colStyleMap.get(curColIndex));
		} else {
			c.setCellStyle(defaultStyle);
		}
	}
	public void createNewRow() {
		if (lastRowIndex > curRowIndex && curRowIndex != dataInitRowIndex) {
			sheet.shiftRows(curRowIndex, lastRowIndex, 1, true, true);
			lastRowIndex++;
		}
		curRow = sheet.createRow(curRowIndex);
		curRow.setHeightInPoints(defaultRowHeight);
		curRowIndex++;
		curColIndex = dataInitColIndex;
	}
	public void insertSerFinally() {
		int seq = 1;
		Row row = null;
		Cell c = null;
		for (int i = dataInitRowIndex; i < curRowIndex; i++) {
			row = sheet.getRow(i);
			c = row.createCell(serColIndex);
			setCellStyle(c);
			c.setCellValue(seq++);
		}
	}
	public void replaceFinalData(Map<String, String> datas) {
		if (datas != null) {
			Properties props = new Properties();
			props.putAll(datas);
			replaceFinalData(props);
		}
	}
	public void replaceFinalData(Properties prop) {
		if (prop != null) {
			for (Row row : sheet) {
				for (Cell c : row) {
					if (c.getCellType() != Cell.CELL_TYPE_STRING)
						continue;
					String str = c.getStringCellValue().trim();
					if (str.startsWith("#")) {
						String replacekey = str.substring(1);
						if (prop.containsKey(replacekey)) {
							c.setCellValue(prop.getProperty(replacekey));
						}
					}
				}
			}
		}
	}
	private void initTemplate() {
		sheet = wb.getSheetAt(0);
		initConfigData();
		lastRowIndex = sheet.getLastRowNum();
	}
	private void initConfigData() {
		boolean findData = false;
		boolean findSer = false;
		for (Row row : sheet) {
			if (findData)
				break;
			for (Cell c : row) {
				if (c.getCellType() != Cell.CELL_TYPE_STRING)
					continue;
				String str = c.getStringCellValue().trim();
				if (str.equals(SER_NUM)) {
					serColIndex = c.getColumnIndex();
					findSer = true;
				} else if (str.equals(DATA_LINE)) {
					dataInitColIndex = c.getColumnIndex();
					dataInitRowIndex = row.getRowNum();
					curColIndex = dataInitColIndex;
					curRowIndex = dataInitRowIndex;
					defaultStyle = c.getCellStyle();
					defaultRowHeight = row.getHeightInPoints();
					findData = true;
					initStyles();
					break;
				}
			}
		}
		if (!findSer) {
			initSer();
		}
	}
	private void initSer() {
		for (Row row : sheet) {
			for (Cell c : row) {
				if (c.getCellType() != Cell.CELL_TYPE_STRING)
					continue;
				String str = c.getStringCellValue().trim();
				if (str.equals(SER_NUM)) {
					serColIndex = c.getColumnIndex();
				}
			}
		}
	}
	private void initStyles() {
		colStyleMap = new HashMap<Integer, CellStyle>();
		for (Row row : sheet) {
			for (Cell c : row) {
				if (c.getCellType() != Cell.CELL_TYPE_STRING)
					continue;
				String str = c.getStringCellValue().trim();
				if (str.equals(DEFAULT_STYLE)) {
					defaultStyle = c.getCellStyle();
				} else if (str.equals(STYLE)) {
					colStyleMap.put(c.getColumnIndex(), c.getCellStyle());
				}
			}
		}
	}
}
