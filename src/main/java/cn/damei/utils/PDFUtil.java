package cn.damei.utils;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
public class PDFUtil {
	@SuppressWarnings({ "unused", "unchecked" })
	public static <T> String writePdf(T obj, Map<String, Object> map, String templFilePath, String targetPath) {
		File newPDFPath = new File(targetPath);
		PdfReader reader;
		FileOutputStream out;
		ByteArrayOutputStream bos;
		PdfStamper stamper;
		try {
			out = new FileOutputStream(newPDFPath);// 输出流
			reader = new PdfReader(templFilePath);// 读取pdf模板
			bos = new ByteArrayOutputStream();
			stamper = new PdfStamper(reader, bos);
			AcroFields form = stamper.getAcroFields();
			// 字体,使用本机的宋体
			// String font_cn = getChineseFont();
			// BaseFont bf = BaseFont.createFont(font_cn + ",1", BaseFont.IDENTITY_H,
			// BaseFont.NOT_EMBEDDED);// 注意这里有一个,1
			BaseFont bf = BaseFont.createFont("/simsun.ttc" + ",1", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);// 注意这里有一个,1
			// 处理实体字段
			HashMap<String, Object> fields = form.getFields();
			Set<String> sets = fields.keySet();
			Class<? extends Object> clazz = obj.getClass();
			BeanInfo bif = Introspector.getBeanInfo(clazz);
			PropertyDescriptor pds[] = bif.getPropertyDescriptors();
			for (PropertyDescriptor pd : pds) {
				String field = pd.getName();
				if (field.equals("class")) {
					continue;
				}
				if (sets.contains(field)) {
					form.setFieldProperty(field, "textfont", bf, null);
					PropertyDescriptor proDescriptor = new PropertyDescriptor(field, clazz);
					Method methodGet = proDescriptor.getReadMethod();
					Object value = methodGet.invoke(obj);
					if (null == value) {
						value = "";
					}
					if(field.equals("applyPersonName")) {
						form.setField(field, "a\nb\nc");
					}else {
						form.setField(field, String.valueOf(value));
					}
				}
			}
			// 非实体域字段处理
			Set<String> keySet = map.keySet();
			for (String field : keySet) {
				form.setFieldProperty(field, "textfont", bf, null);
				form.setField(field, String.valueOf(map.get(field)));
			}
			// 如果为false那么生成的PDF文件还能编辑，一定要设为true
			stamper.setFormFlattening(true);
			stamper.close();
			Document doc = new Document();
			PdfCopy copy = new PdfCopy(doc, out);
			doc.open();
			PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), 1);
			copy.addPage(importPage);
			doc.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newPDFPath.getAbsolutePath();
	}
	private static String getChineseFont() {
		// 宋体（对应css中的 属性 font-family: SimSun; ）
		String font = "C:/Windows/Fonts/simsun.ttc";
		// 判断系统类型，加载字体文件
		java.util.Properties prop = System.getProperties();
		String osName = prop.getProperty("os.name").toLowerCase();
		if (osName.indexOf("linux") > -1) {
			font = "/usr/share/fonts/simsun.ttc";
		}
		if (!new File(font).exists()) {
			throw new RuntimeException("simsun.ttc is existx" + font);
		}
		return font;
	}
	@SuppressWarnings("unused")
	private static <T> Object getProperty(T t, Class<T> cls, String fileName) {
		Object objx = null;
		try {
			PropertyDescriptor proDescriptor = new PropertyDescriptor(fileName, cls);
			Method methodGet = proDescriptor.getReadMethod();
			objx = methodGet.invoke(t);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		return objx;
	}
}
