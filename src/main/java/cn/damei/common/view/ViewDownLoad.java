package cn.damei.common.view;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.View;
public class ViewDownLoad implements View {
	private final File dfile;
	private final String contentType;
	public ViewDownLoad(File dfile, String contentType) {
		this.dfile = dfile;
		this.contentType = contentType;
	}
	@Override
	public String getContentType() {
		return this.contentType;
	}
	@SuppressWarnings("rawtypes")
	@Override
	public void render(Map model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fileName = dfile.getName();// 避免下载时文件名乱码
		fileName = new String(fileName.getBytes(), "ISO-8859-1");
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
		response.setContentType("application/octet-stream");
		FileInputStream fis = null;
		// BufferedInputStream bis = null;
		fis = new FileInputStream(dfile.getAbsoluteFile());
		// bis = new BufferedInputStream(fis);
		OutputStream out = response.getOutputStream();
		// 创建缓冲区
		try {
			byte buffer[] = new byte[1024];
			int len = 0;
			// 循环将输入流中的内容读取到缓冲区当中
			while ((len = fis.read(buffer)) > 0) {
				// 输出缓冲区的内容到浏览器，实现文件下载
				out.write(buffer, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭文件输入流
			if (fis != null) {
				fis.close();
			}
			// 关闭输出流
			if (out != null) {
				out.close();
			}
		}
	}
}
