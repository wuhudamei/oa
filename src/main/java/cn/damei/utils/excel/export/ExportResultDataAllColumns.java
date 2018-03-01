package cn.damei.utils.excel.export;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
public class ExportResultDataAllColumns implements ExportResultData {
	public Map<String, Object> doExport(ResultSet rest, int rowNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ResultSetMetaData metaData = rest.getMetaData();
			int columnCount = metaData.getColumnCount();
			for (int i = 1; i <= columnCount; i++) {
				String columnName = metaData.getColumnLabel(i);
				Object object = rest.getObject(columnName);
				map.put(columnName, object);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}
}
