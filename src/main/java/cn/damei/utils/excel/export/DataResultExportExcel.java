package cn.damei.utils.excel.export;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
public class DataResultExportExcel implements DataResult {
	private String[] title = {};
	private String[] columns = {};
	private boolean isZip = true;
	private int batchSize = 100;
	private Sheet sheet = null;
	private Workbook wb = null;
	private File outFile;
	private ExportResultData rdata;
	private Map<String, Integer> cellType = null;
	public boolean isZip() {
		return isZip;
	}
	public void setZip(boolean isZip) {
		this.isZip = isZip;
	}
	public void crateData(Sheet sh, int rowNum, String[] data) {
		Row row = sh.createRow(rowNum);
		for (int i = 0; i < data.length; i++) {
			String value = data[i];
			Cell cell = row.createCell(i);
			cell.setCellValue(value);
		}
	}
	public void crateTitle(Sheet sh, String[] title) {
		crateData(sh, 0, title);
	}
	public void crateContext(Sheet sh, int rowNum, String[] title) {
		crateData(sh, rowNum, title);
	}
	private void createContext(String[] columns, int rowNum, Sheet sheet, Map<String, Object> exportResult) {
		Row row = sheet.createRow(rowNum);
		for (int i = 0; i < columns.length; i++) {
			String cellName = columns[i];
			Object object = exportResult.get(cellName);
			String value = "";
			if (null != object) {
				value = object.toString();
			}
			Cell cell = row.createCell(i);
			if (null != this.cellType) {
				Integer cType = this.cellType.get(cellName);
				int intValue = 100;
				if (null != cType) {
					intValue = cType.intValue();
				}
				switch (intValue) {
				case Cell.CELL_TYPE_NUMERIC:
					cell.setCellType(cType);
					cell.setCellValue(Integer.parseInt(value));
					break;
				default:
					cell.setCellType(Cell.CELL_TYPE_STRING);
					cell.setCellValue(value);
					break;
				}
			} else {
				cell.setCellValue(value);
			}
		}
	}
	public DataResultExportExcel(int batchSize, String[] title, String[] columns, ExportResultData rdata) {
		this.title = title;
		this.columns = columns;
		this.batchSize = batchSize;
		this.wb = new SXSSFWorkbook(batchSize);
		this.sheet = wb.createSheet();
		this.rdata = rdata;
		// Sheet sheet = wb.createSheet();
		// crateTitle(sheet, title);
	}
	public DataResultExportExcel(int batchSize, String[] title, String[] columns, File outFile,
			ExportResultData rdata) {
		this.outFile = outFile;
		this.title = title;
		this.columns = columns;
		this.batchSize = batchSize;
		this.wb = new SXSSFWorkbook(batchSize);
		this.sheet = wb.createSheet();
		this.rdata = rdata;
		// Sheet sheet = wb.createSheet();
		// crateTitle(sheet, title);
	}
	public DataResultExportExcel(int batchSize, String[] title, String[] columns, File outFile,
			Map<String, Integer> cellType, ExportResultData rdata) {
		this.outFile = outFile;
		this.title = title;
		this.columns = columns;
		this.batchSize = batchSize;
		this.wb = new SXSSFWorkbook(batchSize);
		this.sheet = wb.createSheet();
		this.rdata = rdata;
		this.cellType = cellType;
	}
	public Object before() {
		crateTitle(sheet, title);
		return null;
	}
	public Map<String, Object> exportToExecl(ResultSet rest, ExportResultData rdata, int rowNum) {
		Map<String, Object> exportResult = rdata.doExport(rest, rowNum);
		createContext(columns, rowNum, sheet, exportResult);
		return exportResult;
	}
	public Map<String, Object> doExport(ResultSet rest, int rowNum) {
		Map<String, Object> exportResult = exportToExecl(rest, rdata, rowNum);
		return exportResult;
	}
	public Object after(int countRow) {
		try {
			FileOutputStream out = new FileOutputStream(outFile);
			wb.write(out);
			if (isZip) {
				String sourcePath = outFile.getAbsolutePath();
				String fileName = outFile.getName();
				int indexOfPoint = fileName.lastIndexOf(".");
				String zipFileName = fileName.substring(0, indexOfPoint);
				//FileToZip.fileToZip(sourcePath, outFile.getParent(), zipFileName, true);
			}
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public String[] getTitle() {
		return title;
	}
	public void setTitle(String[] title) {
		this.title = title;
	}
	public String[] getColumns() {
		return columns;
	}
	public void setColumns(String[] columns) {
		this.columns = columns;
	}
	public int getBatchSize() {
		return batchSize;
	}
	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}
	public Sheet getSheet() {
		return sheet;
	}
	public void setSheet(Sheet sheet) {
		this.sheet = sheet;
	}
	public Workbook getWb() {
		return wb;
	}
	public void setWb(Workbook wb) {
		this.wb = wb;
	}
	public File getOutFile() {
		return outFile;
	}
	public void setOutFile(File outFile) {
		this.outFile = outFile;
	}
	public ExportResultData getRdata() {
		return rdata;
	}
	public void setRdata(ExportResultData rdata) {
		this.rdata = rdata;
	}
}
