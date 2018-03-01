package cn.damei.service.upload;
import com.google.common.io.Files;
import cn.damei.common.PropertyHolder;
import cn.damei.common.service.ServiceException;
import cn.damei.enumeration.UploadCategory;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
@Service
public class UploadService {
	@Value("${upload.dir}")
	private String uploadDir;
	@Value("${upload.tmp.dir}")
	private String tmpDir;
	@Value("${upload.base.url}")
	private String uploadBaseUrl;
	@Value("${upload.max.file.size}")
	private long maxUploadBytes;
	public static final String REQUEST_KEY = "request";
	public static final String USER_ID = "userid";
	private static final SimpleDateFormat DATE_PATH_SDF = new SimpleDateFormat("yyyy/MM/dd");
	// 匹配 src="tmp/xxx"
	// 有点复杂，[^\\1]不匹配1，所以写成([^\\1]|1)
	private static final Pattern imgSrcPattern = Pattern.compile(" src=(\"|')(([^\\\\1]|1)+?)\\1");
	public static ThreadLocal<Map<String, Object>> uploadThreadLocal = new ThreadLocal<Map<String, Object>>();
	protected static List<String> XH_EDITOR_ALLOWED_TYPE = Arrays.asList("jpg", "jpeg", "gif", "png");
	private Logger logger = LoggerFactory.getLogger(UploadService.class);
	// 文件上传 基 目录
	@Autowired
	private SequenceService sequenceService;
	public String upload(MultipartFile file, UploadCategory uploadCategory) {
		validateUploadFile(file, uploadCategory);
		InputStream input;
		try {
			input = file.getInputStream();
		} catch (IOException e) {
			logger.debug("读取上传文件输入流发生错误", e);
			throw new ServiceException("读取上传文件发生错误");
		}
		String ext = FilenameUtils.getExtension(file.getOriginalFilename());
		return saveTmp(input, ext, uploadCategory.getPath());
	}
	public String upload(InputStream stream, UploadCategory uploadCategory, String ext) {
		return saveTmp(stream, ext, uploadCategory.getPath());
	}
	private String saveTmp(InputStream input, String ext, String categoryPath) {
		Assert.state(StringUtils.isNotBlank(ext));
		Assert.notNull(input);
		String path = tmpDir + categoryPath + "/" + DateFormatUtils.format(new Date(), "yyyy/MM/dd") + "/"
				+ sequenceService.getNextVal(SequenceService.SequenceTable.UPLOAD) + "." + ext;
		this.save(path, input);
		return path;
	}
	private void save(String path, InputStream input) {
		Assert.state(StringUtils.isNotBlank(uploadDir));
		Assert.state(StringUtils.isNotBlank(path));
		BufferedInputStream bufInput = null;
		BufferedOutputStream bufOut = null;
		try {
			File dest = new File(uploadDir, path);
			if (!dest.getParentFile().exists()) {
				if (!dest.getParentFile().mkdirs()) {
					throw new ServiceException("创建目录失败 " + dest.getParentFile().getAbsolutePath());
				}
			}
			bufInput = new BufferedInputStream(input);
			bufOut = new BufferedOutputStream(new FileOutputStream(dest));
			IOUtils.copy(bufInput, bufOut);
		} catch (IOException e) {
			logger.warn("保存文件出错", e);
			throw new ServiceException("保存文件出错");
		} finally {
			IOUtils.closeQuietly(bufOut);
			IOUtils.closeQuietly(bufInput);
		}
	}
	public void validateUploadFile(MultipartFile file, UploadCategory uploadCategory) {
		if (file.isEmpty()) {
			throw new ServiceException("上传文件不能为空");
		}
		if (StringUtils.isBlank(file.getOriginalFilename())) {
			throw new ServiceException("上传文件名不能为空");
		}
		String ext = FilenameUtils.getExtension(file.getOriginalFilename());
		if (StringUtils.isBlank(ext)) {
			throw new ServiceException("上传文件扩展名不能为空");
		}
		if (file.getSize() > maxUploadBytes) {
			throw new ServiceException("上传文件不能大于 " + FileUtils.byteCountToDisplaySize(maxUploadBytes));
		}
	}
	public String submitPath(String tmpPath) {
		// 由于文件上传成功后加了前缀,详见：application.properties:image.base.url,去掉前缀 /static-content/
		tmpPath = this.removePrefix(tmpPath);
		if (!tmpPath.startsWith(tmpDir)) {
			return PropertyHolder.getUploadBaseUrl() + tmpPath;
		}
		File tmpFile = new File(uploadDir, tmpPath);
		// 去掉文件路径的tmp/，得到文件存放的目标路径
		final String destPath = tmpPath.substring(tmpDir.length());
		File destFile = new File(uploadDir, destPath);
		if (!tmpFile.exists() && destFile.exists()) {
			// 当系统保存表单，已将一部分图片移至持久目录，处理后面事务发生错误，有可能符合业务规则或其他原因，事务回滚，
			// 用户修改表单后，再次提交，就会tmpFile不存在destFile已存在的情况。这种情况应该返回destPath。
			// 实际例子：添加商品时，填写多个sku，如果第1个sku之后的某个sku编码已存在，就会出现这种情况。
			return destPath;
		}
		if (!destFile.getParentFile().exists()) {
			if (!destFile.getParentFile().mkdirs()) {
				throw new ServiceException("创建目录失败" + destFile.getParentFile().getAbsolutePath());
			}
		}
		try {
			FileUtils.moveFile(tmpFile, destFile);
		} catch (IOException e) {
			logger.warn("移动文件失败", e);
			throw new ServiceException("移动文件失败");
		}
		// 路径拼上用于访问的前缀
		return PropertyHolder.getUploadBaseUrl() + destPath;
	}
	public String saveFilePath(UploadCategory uploadCategory, String fileName) {
		String path = uploadCategory.getPath();// 分类
		// String allPath = "uploads" + File.separator + path;
		File file = null;
		String datePath = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
		String allPath = uploadDir + File.separator + path + File.separator + datePath + File.separator;
		try {
			if (!StringUtils.isEmpty(fileName)) {
				file = new File(allPath, fileName);
				Files.createParentDirs(file);
			} else {
				file = new File(allPath);
				file.mkdirs();
			}
		} catch (IOException e) {
			throw new ServiceException("创建目录失败" + datePath);
		}
		return file == null ? "" : file.getAbsolutePath();
	}
	private String removePrefix(String url) {
		if (!url.startsWith(uploadBaseUrl)) {
			return url;
		}
		return url.substring(uploadBaseUrl.length());
	}
	public boolean delete(String saveRelatePath) {
		if (StringUtils.isBlank(saveRelatePath)) {
			return false;
		}
		char first = saveRelatePath.charAt(0);
		if (first == '/' || first == '.') {
			return false;
		}
		File file = new File(uploadDir, saveRelatePath);
		if (file.exists()) {
			return file.delete();
		}
		return false;
	}
	public String getRelatePath(String path) {
		if (StringUtils.isBlank(path))
			return StringUtils.EMPTY;
		if (path.startsWith(PropertyHolder.getUploadBaseUrl())) {
			int i = path.indexOf(PropertyHolder.getUploadBaseUrl());
			return path.substring(i + PropertyHolder.getUploadBaseUrl().length());
		}
		if (path.startsWith("/")) {
			return path.substring(1);
		}
		return path;
	}
}