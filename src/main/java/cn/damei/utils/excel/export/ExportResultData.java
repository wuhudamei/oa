package cn.damei.utils.excel.export;
import java.sql.ResultSet;
import java.util.Map;
public interface ExportResultData {
	Map<String, Object> doExport(ResultSet rest, int rowNum);
}
