package cn.damei.utils.excel.imports;
import java.io.PrintStream;
import java.util.List;
public class ExcelRowReaderPrint implements IExcelRowReader {
	private PrintStream print = System.out;
	@Override
	public void getRows(int sheetIndex, int curRow, List<String> rowlist) {
		print.print(curRow + " ");
		for (int i = 0; i < rowlist.size(); i++) {
			print.print("".equals(rowlist.get(i)) ? "*" : rowlist.get(i) + " ");
		}
		print.println();
	}
	@Override
	public void lastBatchSave() {
	}
}
