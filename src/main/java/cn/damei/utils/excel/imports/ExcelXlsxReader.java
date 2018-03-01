package cn.damei.utils.excel.imports;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
public class ExcelXlsxReader extends DefaultHandler {
	enum xssfDataType {
		BOOL, ERROR, FORMULA, INLINESTR, SSTINDEX, NUMBER,
	}
	private boolean vIsOpen;
	private StringBuffer value = new StringBuffer();
	private int thisColumn = -1;
	private int lastColumnNumber = -1;
	private xssfDataType nextDataType = xssfDataType.NUMBER;
	private short formatIndex;
	private String formatString;
	private DataFormatter formatter = new DataFormatter();
	private List<String> rowList = new ArrayList<String>();
	private int curRow = 0;
	private int sheetIndex = 0;
	private int minColumnCount = 3;
	private StylesTable stylesTable;
	private ReadOnlySharedStringsTable sharedStringsTable;
	private IExcelRowReader rowReader;
	private String emptyCellFillStr = " ";
	public String getEmptyCellFillStr() {
		return emptyCellFillStr;
	}
	public void setEmptyCellFillStr(String emptyCellFillStr) {
		this.emptyCellFillStr = emptyCellFillStr;
	}
	public int getMinColumnCount() {
		return minColumnCount;
	}
	public void setMinColumnCount(int minColumnCount) {
		this.minColumnCount = minColumnCount;
	}
	public StylesTable getStylesTable() {
		return stylesTable;
	}
	public void setStylesTable(StylesTable stylesTable) {
		this.stylesTable = stylesTable;
	}
	public ReadOnlySharedStringsTable getSharedStringsTable() {
		return sharedStringsTable;
	}
	public void setSharedStringsTable(ReadOnlySharedStringsTable sharedStringsTable) {
		this.sharedStringsTable = sharedStringsTable;
	}
	public void setRowReader(IExcelRowReader rowReader) {
		this.rowReader = rowReader;
	}
	public ExcelXlsxReader() {
	}
	public void process(String filename) {
		try {
			OPCPackage pkg = OPCPackage.open(filename);
			ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(pkg);
			XSSFReader xssfReader = new XSSFReader(pkg);
			StylesTable styles = xssfReader.getStylesTable();
			XSSFReader.SheetIterator iter = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
			if (!iter.hasNext()) {
				throw new IllegalStateException("document is not sheet!");
			}
			InputStream sheetInputStream = iter.next();
			InputSource sheetSource = new InputSource(sheetInputStream);
			SAXParserFactory saxFactory = SAXParserFactory.newInstance();
			SAXParser saxParser = saxFactory.newSAXParser();
			XMLReader sheetParser = saxParser.getXMLReader();
			setRowReader(this.rowReader);
			setStylesTable(styles);
			setSharedStringsTable(strings);
			setMinColumnCount(this.minColumnCount);
			sheetParser.setContentHandler(this);
			sheetParser.parse(sheetSource);
			sheetInputStream.close();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (OpenXML4JException e) {
			e.printStackTrace();
		} catch (FactoryConfigurationError e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
	public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
		if ("inlineStr".equals(name) || "v".equals(name)) {
			vIsOpen = true;
			value.setLength(0);
		} else if ("c".equals(name)) {
			String r = attributes.getValue("r");
			int firstDigit = -1;
			for (int c = 0; c < r.length(); ++c) {
				if (Character.isDigit(r.charAt(c))) {
					firstDigit = c;
					break;
				}
			}
			// 我0开始计算坐标
			thisColumn = nameToColumn(r.substring(0, firstDigit));
			this.nextDataType = xssfDataType.NUMBER;
			this.formatIndex = -1;
			this.formatString = null;
			String cellType = attributes.getValue("t");
			String cellStyleStr = attributes.getValue("s");
			if ("b".equals(cellType))
				nextDataType = xssfDataType.BOOL;
			else if ("e".equals(cellType))
				nextDataType = xssfDataType.ERROR;
			else if ("inlineStr".equals(cellType))
				nextDataType = xssfDataType.INLINESTR;
			else if ("s".equals(cellType))
				nextDataType = xssfDataType.SSTINDEX;
			else if ("str".equals(cellType))
				nextDataType = xssfDataType.FORMULA;
			else if (cellStyleStr != null) {
				int styleIndex = Integer.parseInt(cellStyleStr);
				XSSFCellStyle style = stylesTable.getStyleAt(styleIndex);
				this.formatIndex = style.getDataFormat();
				this.formatString = style.getDataFormatString();
				if (this.formatString == null)
					this.formatString = BuiltinFormats.getBuiltinFormat(this.formatIndex);
			}
		}
	}
	private int nameToColumn(String name) {
		int column = -1;
		for (int i = 0; i < name.length(); ++i) {
			int c = name.charAt(i);
			column = (column + 1) * 26 + c - 'A';
		}
		return column;
	}
	public void endElement(String uri, String localName, String name) throws SAXException {
		String thisStr = null;
		if ("v".equals(name)) {
			switch (nextDataType) {
			case BOOL:
				char first = value.charAt(0);
				thisStr = first == '0' ? "FALSE" : "TRUE";
				break;
			case ERROR:
				thisStr = "\"ERROR:" + value.toString() + '"';
				break;
			case FORMULA:
				thisStr = value.toString();
				break;
			case INLINESTR:
				XSSFRichTextString rtsi = new XSSFRichTextString(value.toString());
				thisStr = rtsi.toString();
				break;
			case SSTINDEX:
				String sstIndex = value.toString();
				try {
					int idx = Integer.parseInt(sstIndex);
					XSSFRichTextString rtss = new XSSFRichTextString(sharedStringsTable.getEntryAt(idx));
					thisStr = rtss.toString();
				} catch (NumberFormatException ex) {
				}
				break;
			case NUMBER:
				String n = value.toString();
				if (this.formatString != null) {
					thisStr = formatter.formatRawCellContents(Double.parseDouble(n), this.formatIndex,
							this.formatString);
				} else {
					thisStr = n;
				}
				break;
			default:
				thisStr = "(TODO: Unexpected type: " + nextDataType + ")";
				break;
			}
			if (lastColumnNumber == -1) {
				lastColumnNumber = 0;
			}
			for (int i = lastColumnNumber; i < thisColumn; ++i) {
				if (rowList.size() < thisColumn) {
					rowList.add(this.emptyCellFillStr);
				}
			}
			rowList.add(thisStr);
			if (thisColumn > -1) {
				lastColumnNumber = thisColumn;
			}
		} else if ("row".equals(name)) {
			if (lastColumnNumber == -1) {
				lastColumnNumber = 0;
			}
			for (int i = lastColumnNumber; i < (this.minColumnCount) - 1; i++) {
				rowList.add(this.emptyCellFillStr);
			}
			if (curRow == 0) {
				this.minColumnCount = rowList.size();
			}
			rowReader.getRows(sheetIndex, curRow, rowList);
			rowList.clear();
			curRow++;
			lastColumnNumber = -1;
		}
	}
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (vIsOpen)
			value.append(ch, start, length);
	}
	public static void main(String[] args) throws Exception {
		String[] columns = new String[] { "a", "b" };
		String tableName = "test-001";
		ExcelRowReaderDB iReader = new ExcelRowReaderDB(null,tableName, columns, 20) {
			@Override
			public boolean checkRow(int sheetIndex, int curRow, List<String> rowlist) {
				if (curRow == 0) {
					return false;
				}
				String join = StringUtils.join(rowlist, ',');
				System.out.println(
						"sheetIndex: " + sheetIndex + " | curRow: " + curRow + " | " + rowlist.size() + " | " + join);
				return true;
			}
			@Override
			public int saveDB(String sql, List<Object[]> setters) {
				return 0;
			}
		};
		ExcelXlsxReader reader = new ExcelXlsxReader();
		reader.setRowReader(iReader);
		// reader.setEmptyCellFillStr("*");
		// reader.setMinColumnCount(4);//最小列数
		reader.process("D:/ask/channelWhiteList (6) - 副本.xlsx");
	}
}
