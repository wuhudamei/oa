package cn.damei.utils.excel.imports;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
public class ExcelReaderUtil {
	// excel2003扩展名
	public static final String EXCEL03_EXTENSION = ".xls";
	// excel2007扩展名
	public static final String EXCEL07_EXTENSION = ".xlsx";
	public static void readExcel(IExcelRowReader reader, String fileName) throws Exception {
		// 处理excel2003文件
		if (fileName.endsWith(EXCEL03_EXTENSION)) {
			ExcelXlsReader exceXls = new ExcelXlsReader();
			exceXls.setRowReader(reader);
			exceXls.process(fileName);
			// 处理excel2007文件
		} else if (fileName.endsWith(EXCEL07_EXTENSION)) {
			ExcelXlsxReader exceXlsx = new ExcelXlsxReader();
			exceXlsx.setRowReader(reader);
			exceXlsx.process(fileName);
			// ExcelXlsxReader exceXlsx = new ExcelXlsxReader();
			// exceXlsx.setRowReader(reader);
			//
			// exceXlsx.processOneSheet(fileName, 1);
		} else {
			throw new Exception("文件格式错误，fileName的扩展名只能是xls或xlsx。");
		}
		reader.lastBatchSave();// 保存最后一页
	}
	public static void main(String[] args) throws Exception {
		// IExcelRowReader rowReader = new ExcelRowReaderPrint();
		// ExcelReaderUtil.readExcel(rowReader, "D:\\ask\\2003.xls");
		// ExcelReaderUtil.readExcel(rowReader, "D:\\ask\\ttt.xlsx");
		// ExcelRowReaderDB readerDB = new ExcelRowReaderMySql(columns);
		// ExcelReaderUtil.readExcel(readerDB, "D:\\ask\\2003.xls");
		String[] columns = new String[] { "a", "b" };
		String tableName = "test-001";
		ExcelRowReaderDB readerDBx = new ExcelRowReaderDB(null,tableName, columns, 20) {
			@Override
			public boolean checkRow(int sheetIndex, int curRow, List<String> rowlist) {
				if (curRow == 0) {
					return false;
				}
				String join = StringUtils.join(rowlist, ',');
				System.out.println(join);
				return true;
			}
			@Override
			public int saveDB(String sql, List<Object[]> setters) {
				return 0;
			}
		};
		// ExcelReaderUtil.readExcel(readerDBx, "D:\\ask\\2003.xls");
//		ExcelReaderUtil.readExcel(readerDBx, "D:\\ask\\2010.xlsx");
		ExcelReaderUtil.readExcel(readerDBx, "D:\\ask\\channelWhiteList (6) - 副本.xlsx");
	}
}
