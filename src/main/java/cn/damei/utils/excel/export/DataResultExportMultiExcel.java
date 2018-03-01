package cn.damei.utils.excel.export;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
public class DataResultExportMultiExcel implements DataResult {
	private String[] title = {};
	private String[] columns = {};
	private boolean isZip = true;
	private int batchSize = 100;
	private Sheet sheet = null;
	private Workbook wb = null;
	private File outFile;
	private ExportResultData rdata;
	private int fileMaxRow = 1000000;
	private List<File> createFiles = null;
	private int currentRowIndex = 0;
	private Map<String, Integer> cellType = null;
	private int multiDsSize = 1;
	private int afterPost = 0;// 上个数据源的偏移量
	private int currentDataSourceIndex = 0;
	public int getMultiDsSize() {
		return multiDsSize;
	}
	public void setMultiDsSize(int multiDsSize) {
		this.multiDsSize = multiDsSize;
	}
	public Map<String, Integer> getCellType() {
		return cellType;
	}
	public void setCellType(Map<String, Integer> cellType) {
		this.cellType = cellType;
	}
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
	public void createTitle(Sheet sh, String[] title) {
		crateData(sh, 0, title);
	}
	public void crateContext(Sheet sh, int rowNum, String[] title) {
		crateData(sh, rowNum, title);
	}
	private void createContext(String[] columns, int rowIndex, Map<String, Object> exportResult) {
		if (rowIndex == 0) {
			crateData(this.sheet, 0, this.title);
		}
		int tartRowIndex = rowIndex + 1;
		int size = exportResult.size();
		if (size == 0)
			return;
		Row row = this.sheet.createRow(tartRowIndex);
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
				System.out.println(value);
				cell.setCellValue(value);
			}
		}
	}
	public DataResultExportMultiExcel(int batchSize, String[] title, String[] columns, File outFile,
			ExportResultData rdata) {
		this.outFile = outFile;
		this.title = title;
		this.columns = columns;
		this.batchSize = batchSize;
		this.wb = new SXSSFWorkbook(batchSize);
		this.sheet = wb.createSheet();
		this.rdata = rdata;
		this.createFiles = new ArrayList<File>();
	}
	public DataResultExportMultiExcel(int batchSize, String[] title, String[] columns, File outFile,
			Map<String, Integer> cellType, ExportResultData rdata) {
		this.outFile = outFile;
		this.title = title;
		this.columns = columns;
		this.batchSize = batchSize;
		this.wb = new SXSSFWorkbook(batchSize);
		this.sheet = wb.createSheet();
		this.rdata = rdata;
		this.createFiles = new ArrayList<File>();
		this.cellType = cellType;
	}
	public Object before() {
		currentDataSourceIndex++;
		// this.wb = new SXSSFWorkbook(batchSize);
		// this.sheet = wb.createSheet();
		// currentRowIndex = 0;
		return null;
	}
	public Map<String, Object> exportToExecl(ResultSet rest, ExportResultData rdata, int rowNum) {
		Map<String, Object> exportResult = rdata.doExport(rest, rowNum);
		createContext(this.columns, currentRowIndex, exportResult);
		++currentRowIndex;
		if ((rowNum + afterPost) % fileMaxRow == 0) {
			createExcelFile(rowNum);
			afterPost = 0;
			currentRowIndex = 0;
			this.wb = new SXSSFWorkbook(batchSize);
			this.sheet = wb.createSheet();
			this.rdata = rdata;
		}
		return exportResult;
	}
	private void createExcelFile(int rowNum) {
		// String saveFileName = UUID.randomUUID().toString() + ".xlsx";
		// int fileStart = (int) (rowNum / fileMaxRow);
		// String fileName = this.getOutFile().getParent() + File.separator +
		// getFileName() + File.separator
		// + String.format("%02d", fileStart) + "_" +
		// this.getOutFile().getName();
		// String fileName = this.getOutFile().getParent() + File.separator +
		// getFileName() + File.separator + String.format("%02d", fileStart) +
		// "_" + saveFileName;
		String fileName = this.getOutFile().getParent() + File.separator + getFileName() + File.separator
				+ String.format("%03d", createFiles.size()) + "_" + this.getOutFile().getName();
		File zipFile = new File(fileName);
		if (!zipFile.exists()) {
			zipFile.getParentFile().mkdirs();
		}
		try {
			FileOutputStream out = new FileOutputStream(fileName);
			wb.write(out);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.createFiles.add(new File(fileName));
	}
	public Map<String, Object> doExport(ResultSet rest, int rowNum) {
		Map<String, Object> exportResult = exportToExecl(rest, rdata, rowNum);
		return exportResult;
	}
	public void createEmptyFile() {
		createContext(this.columns, 0, new HashMap<String, Object>());
		createExcelFile(0);
	}
	public Object after(int countRow) {
		afterPost = currentRowIndex;
		if (currentDataSourceIndex == multiDsSize) {
			// 已经是最后个数据源了，可以生成文件了
			if (1 == countRow) {
				if (createFiles.size() == 0) {
					createEmptyFile();
				}
			} else {
				afterPost = 0;
				createExcelFile(0);
			}
			String fileName = outFile.getName();
			int indexOfPoint = fileName.lastIndexOf(".");
			String zipFileName = fileName.substring(0, indexOfPoint);
			String soruceFileDic = outFile.getParent() + File.separator + zipFileName;
			if (this.createFiles.size() > 0) {
			//	ZipUtil.zip(soruceFileDic, soruceFileDic + ".zip");
			}
			new File(soruceFileDic).delete();
		}
		// if (currentRowIndex > 0 && currentRowIndex < fileMaxRow) {
		// 数据量小于最大行
		// createExcelFile(0);
		// }
		// String fileName = outFile.getName();
		// int indexOfPoint = fileName.lastIndexOf(".");
		// String zipFileName = fileName.substring(0, indexOfPoint);
		// String soruceFileDic = outFile.getParent() + File.separator +
		// zipFileName;
		//
		//
		//
		//
		return null;
	}
	public String getFileName() {
		String fileName = outFile.getName();
		int indexOfPoint = fileName.lastIndexOf(".");
		String efileName = fileName.substring(0, indexOfPoint);
		return efileName;
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
	public int getFileMaxRow() {
		return fileMaxRow;
	}
	public void setFileMaxRow(int fileMaxRow) {
		this.fileMaxRow = fileMaxRow;
	}
	public static void deleteAllFilesOfDir(File path) {
		if (!path.exists())
			return;
		if (path.isFile()) {
			boolean delete = path.delete();
			System.out.println("delete:\t" + delete + "\t" + path);
			return;
		}
		File[] files = path.listFiles();
		if (null != files) {
			for (int i = 0; i < files.length; i++) {
				deleteAllFilesOfDir(files[i]);
			}
		}
		path.delete();
	}
}
