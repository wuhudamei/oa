package cn.damei.enumeration;
import org.apache.commons.lang3.StringUtils;
public enum UploadCategory {
	CONTRACT("contract"), UEDITOR("ueditor"), NOTICEBOARD("noticeboard"), EMPLOYEE_PHOTO("employee"), CHAIRMAN_MAIBOX(
			"chairmanMaibox"), APPLY_ATTACHMENT("attachment"), PDF("pdf"), EXCLE("xls");
	// 文件保存的目录
	private String path;
	UploadCategory(String path) {
		this.path = path;
	}
	public static UploadCategory parsePathToCategory(String path) {
		for (UploadCategory category : UploadCategory.values()) {
			if (StringUtils.equalsIgnoreCase(path, category.getPath())) {
				return category;
			}
		}
		return null;
	}
	public String getPath() {
		return path;
	}
}
