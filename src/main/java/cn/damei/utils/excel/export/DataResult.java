package cn.damei.utils.excel.export;
import java.sql.ResultSet;
import java.util.Map;
public interface DataResult {
	Object before();
	Map<String, Object> doExport(ResultSet rest, int rowNum);
	Object after(int countRow);
}
