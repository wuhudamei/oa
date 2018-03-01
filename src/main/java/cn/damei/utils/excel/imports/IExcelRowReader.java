package cn.damei.utils.excel.imports;
import java.util.List;
public interface IExcelRowReader {
	void getRows(int sheetIndex, int curRow, List<String> rowlist);
	void lastBatchSave();
}
