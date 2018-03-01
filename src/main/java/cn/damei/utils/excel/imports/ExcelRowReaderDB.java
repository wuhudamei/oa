package cn.damei.utils.excel.imports;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
public abstract class ExcelRowReaderDB implements IExcelRowReader {
	private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	private String[] columns = null;
	private int saveBatch = 1000;
	private String tableName;
	private boolean showPrint = false;
	private PrintStream print = System.out;
	private int targetColLength = 0;
	private String emtyFillStr = " ";
	private boolean isIgnore = true;
	private String execSql;
	private List<Object[]> setters = null;
	private Long succInsertLength = 0l;
	private Long repeatedInsertLength = 0l;
	private Long rowCount = 0l;
	private Integer[] errMsgColumn;// 错误列
	private boolean errMsg;
	private StringBuilder errMsgStr = new StringBuilder();
	private List<String> headList = new ArrayList<String>();
	public List<Object[]> getSetters() {
		return setters;
	}
	private JdbcTemplate jdbcTemplate;
	public void setSetters(List<Object[]> setters) {
		this.setters = setters;
	}
	public List<String> fillColumn(List<String> rowlist) {
		List<String> list = new ArrayList<String>();
		int targetColumnLength = this.targetColLength;
		int headColumnLength = this.columns.length;
		if (targetColumnLength == 0) {
			targetColumnLength = headColumnLength;
		}
		int rowSize = rowlist.size();
		for (int i = 0; i < targetColumnLength; i++) {
			if (i < rowSize) {
				String cellValue = rowlist.get(i);
				list.add(cellValue);
			} else {
				list.add(this.emtyFillStr);
			}
		}
		return list;
	}
	public int saveDB(String sql, List<Object[]> setters) {
		int[] ints = this.jdbcTemplate.batchUpdate(sql, setters);
		int count = 0;
		for (int i = 0; i < ints.length; i++) {
			if (ints[i] == 1) {
				count++;
			} else {
				Object[] objects = setters.get(i);
				for (int j = 0; j < this.getErrMsgColumn().length; j++) {
					Object object = objects[this.getErrMsgColumn()[j]];
					errMsgStr.append(object);
					if (j < this.getErrMsgColumn().length - 1) {
						errMsgStr.append(" | ");
					}
				}
				errMsgStr.append(",");
			}
		}
		return count;
	}
	public boolean isShowPrint() {
		return showPrint;
	}
	public void setShowPrint(boolean showPrint) {
		this.showPrint = showPrint;
	}
	public List<Map<String, Object>> getList() {
		return list;
	}
	public void setList(List<Map<String, Object>> list) {
		this.list = list;
	}
	public String getEmtyFillStr() {
		return emtyFillStr;
	}
	public void setEmtyFillStr(String emtyFillStr) {
		this.emtyFillStr = emtyFillStr;
	}
	public abstract boolean checkRow(int sheetIndex, int curRow, List<String> rowlist);
	public ExcelRowReaderDB(JdbcTemplate jdbcTemplate, String tableName, String[] columns, int saveBatch) {
		this.tableName = tableName;
		this.columns = columns;
		this.saveBatch = saveBatch;
		this.jdbcTemplate = jdbcTemplate;
	}
	public void showRow(int curRow, List<String> rowlist) {
		print.print(curRow + " ");
		for (int i = 0; i < rowlist.size(); i++) {
			print.print("".equals(rowlist.get(i)) ? "*" : rowlist.get(i) + " ");
		}
		print.println();
	}
	@Override
	public void getRows(int sheetIndex, int curRow, List<String> rowlist) {
		this.rowCount++;
		if (curRow == 0) {
			int firstRowColumnLength = rowlist.size();
			this.targetColLength = firstRowColumnLength;
		}
		List<String> targetColLengthList = fillColumn(rowlist);
		if (curRow == 0) {
			generExecSql();
			if (this.targetColLength == 0) {
				this.targetColLength = rowlist.size();
			}
		}
		if (this.showPrint) {
			showRow(curRow, targetColLengthList);
		}
		// 把行号拿出来
		if (curRow == 0) {
			headList.addAll(rowlist);
		}
		if (checkRow(sheetIndex, curRow, targetColLengthList)) {
			Map<String, Object> rowMap = new HashMap<String, Object>();
			for (int i = 0; i < this.columns.length; i++) {
				String columnName = this.columns[i];
				String columnValue = targetColLengthList.get(i);
				rowMap.put(columnName, columnValue);
			}
			list.add(rowMap);
			generatorBatchSetters(list);
			if (list.size() % this.saveBatch == 0) {
				int saveLength = saveDB(this.execSql, this.setters);
				succInsertLength += saveLength;
				repeatedInsertLength += (this.setters.size() - succInsertLength);
				list.clear();
			}
		}
	}
	private StringBuffer generExecSql() {
		StringBuffer buffSql = new StringBuffer();
		buffSql.append("insert ");
		if (isIgnore) {
			buffSql.append(" IGNORE ");
		}
		buffSql.append(" into " + this.tableName);
		buffSql.append("(");
		buffSql.append(StringUtils.join(columns, ","));
		buffSql.append(")");
		buffSql.append(" values(");
		// 处理？号参数
		String[] setts = new String[this.columns.length];
		List<String> asList = Arrays.asList(setts);
		Collections.fill(asList, "?");
		String str = StringUtils.join(setts, ",");
		buffSql.append(str);
		buffSql.append(")");
		setExecSql(buffSql.toString());
		return buffSql;
	}
	public void generatorBatchSetters(List<Map<String, Object>> list) {
		List<Object[]> params = new ArrayList<Object[]>();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);
			List<Object> values = new ArrayList<Object>();
			for (int j = 0; j < this.columns.length; j++) {
				String colMapKey = this.columns[j];
				Object object = map.get(colMapKey);
				values.add(object);
			}
			Object[] array = values.toArray();
			params.add(array);
		}
		setSetters(params);
	}
	public void lastBatchSave() {
		if (this.execSql.trim().length() > 0 && null != this.setters && this.setters.size() > 0
				&& this.setters.size() < saveBatch) {
			int saveLength = saveDB(this.execSql, this.setters);
			succInsertLength += saveLength;
			repeatedInsertLength += (this.setters.size() - succInsertLength);
		}
	}
	public boolean isIgnore() {
		return isIgnore;
	}
	public void setIgnore(boolean isIgnore) {
		this.isIgnore = isIgnore;
	}
	public String getExecSql() {
		return execSql;
	}
	public void setExecSql(String execSql) {
		this.execSql = execSql;
	}
	public Long getSuccInsertLength() {
		return succInsertLength;
	}
	public void setSuccInsertLength(Long succInsertLength) {
		this.succInsertLength = succInsertLength;
	}
	public Long getRepeatedInsertLength() {
		return repeatedInsertLength;
	}
	public void setRepeatedInsertLength(Long repeatedInsertLength) {
		this.repeatedInsertLength = repeatedInsertLength;
	}
	public Long getRowCount() {
		return rowCount;
	}
	public void setRowCount(Long rowCount) {
		this.rowCount = rowCount;
	}
	public Integer[] getErrMsgColumn() {
		return errMsgColumn;
	}
	public void setErrMsgColumn(Integer[] errMsgColumn) {
		this.errMsgColumn = errMsgColumn;
	}
	public boolean isErrMsg() {
		return errMsg;
	}
	public void setErrMsg(boolean errMsg) {
		this.errMsg = errMsg;
	}
	public StringBuilder getErrMsgStr() {
		return errMsgStr;
	}
	public void setErrMsgStr(StringBuilder errMsgStr) {
		this.errMsgStr = errMsgStr;
	}
}