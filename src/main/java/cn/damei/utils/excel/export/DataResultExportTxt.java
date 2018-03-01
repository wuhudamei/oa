package cn.damei.utils.excel.export;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.ResultSet;
import java.util.Map;
public class DataResultExportTxt implements DataResult {
	private ExportResultData rdata;
	private String[] columns = {};
	private String[] title = {};
	private File outFile;
	private String spiltStr = " ";
	private String titleDesc = "";
	private Boolean isExistTitle=false;//标题是否存在
	public File getOutFile() {
		return outFile;
	}
	public void setOutFile(File outFile) {
		this.outFile = outFile;
	}
	public Map<String, Object> exportToTxt(ResultSet rest, ExportResultData rdata, int rowNum) {
		Map<String, Object> exportResult = rdata.doExport(rest, rowNum);
		createContext(columns, rowNum, exportResult);
		return exportResult;
	}
	public void createContext(String[] columns, int rowNum, Map<String, Object> exportResult) {
		for (String col : columns) {
			Object object = exportResult.get(col);
			String context = String.valueOf(object);
			toF(context + "" + spiltStr);
		}
		toF("\r\n");
	}
	public Map<String, Object> doExport(ResultSet rest, int rowNum) {
		Map<String, Object> exportResult = exportToTxt(rest, rdata, rowNum);
		return exportResult;
	}
	public Object before() {
		if(!isExistTitle){//标题不存在，添加标题
			if (null != titleDesc && !"".equals(titleDesc)) {
				toF(titleDesc + " " + spiltStr);
			}
			if (this.title.length > 0) {
				for (String t : title) {
					toF(t + " " + spiltStr);
				}
			}
			toF("\r\n");
			isExistTitle=true;//标题已存在，不再追加
		}
		return null;
	}
	public Object after(int countRow) {
		if (countRow == 1) {
			toF("");
		}
		return null;
	}
	public DataResultExportTxt(String[] columns, File outFile, ExportResultData rdata) {
		this.columns = columns;
		this.outFile = outFile;
		this.rdata = rdata;
	}
	public DataResultExportTxt(String[] title, String[] columns, File outFile, ExportResultData rdata) {
		this.title = title;
		this.columns = columns;
		this.outFile = outFile;
		this.rdata = rdata;
	}
	private void toF(String context) {
		BufferedWriter out = null;
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(this.outFile, true);
			out = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
			out.write(context);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != out) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public ExportResultData getRdata() {
		return rdata;
	}
	public void setRdata(ExportResultData rdata) {
		this.rdata = rdata;
	}
	public String[] getColumns() {
		return columns;
	}
	public void setColumns(String[] columns) {
		this.columns = columns;
	}
	public String getSpiltStr() {
		return spiltStr;
	}
	public void setSpiltStr(String spiltStr) {
		this.spiltStr = spiltStr;
	}
	public String getTitleDesc() {
		return titleDesc;
	}
	public void setTitleDesc(String titleDesc) {
		this.titleDesc = titleDesc;
	}
}
