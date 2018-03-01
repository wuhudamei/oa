package cn.damei.utils.weixin;
import com.rocoinfo.weixin.config.ParamManager;
import com.rocoinfo.weixin.token.TokenManager;
import cn.damei.common.PropertyHolder;
import it.sauronsoftware.jave.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
public interface WXUtil {
	static Logger logger = LoggerFactory.getLogger(WXUtil.class);
	// 微信认证url
	static String OAUTH_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxAppid&redirect_uri=http%3A%2F%2FauthUrl%2Foauth%2Fmenu%2Fcode%3Fappid%3DauthAppid%26redirect_url%3Dhttp%3A%2F%2FdomainName%2FoauthCallBack&response_type=code&scope=snsapi_base&state=stateUrl#wechat_redirect";
	//微信公众平台多媒体下载地址
	//static String WX_MEDIA_DOWMLOAD_URL = "https://qyapi.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
	//static String WX_MEDIA_DOWMLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
	public static String getOauthUrl(String url) {
		// wxAppid: 微信公众号appid
		return OAUTH_URL.replaceAll("wxAppid", ParamManager.getAppid())
				// 认证中心url
				.replaceAll("authUrl", PropertyHolder.getOauthCenterUrl().replaceAll("http://", ""))
				// authAppid oa系统认证中心的appid
				.replaceAll("authAppid", PropertyHolder.getOauthCenterAppid())
				// domainName 认证通过后的跳转域名(当前项目域名)
				.replaceAll("domainName", PropertyHolder.getBaseurl().replaceAll("http://", ""))
				// state 需要访问的连接名(不含系统域名)
				.replaceAll("stateUrl", url);
	}
	public static String downloadMedia(String mediaId, String savePath, String fileExtName) {
		String filePath = null;
		//替换 access_token 和 mediaId
		String requestUrl = PropertyHolder.getWxMediaDowmloadUrl().replace("ACCESS_TOKEN",  TokenManager.getAccessToken())
				.replace("MEDIA_ID", mediaId);
		if (!savePath.endsWith("/")) {
			savePath += "/";
		}
		HttpURLConnection conn = null;
		BufferedInputStream bis = null;
		FileOutputStream fos = null;
		try {
			// 新建url
			URL url = new URL(requestUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setRequestMethod("GET");
			// 根据内容类型获取扩展名
			String fileExt = WXUtil.getFileEndWitsh(conn.getHeaderField("Content-Type"));
			// 如果扩展名为空,就将传过来的 赋值上去
			if (StringUtils.isBlank(fileExt)) {
				fileExt = fileExtName;
			}
			// 拼接新文件路径--使用纳秒作为文件名
			filePath = savePath + System.nanoTime() + fileExt;
			bis = new BufferedInputStream(conn.getInputStream());
			fos = new FileOutputStream(new File(filePath));
			byte[] buf = new byte[8096];
			int size = 0;
			while ((size = bis.read(buf)) != -1) {
				fos.write(buf, 0, size);
			}
			logger.info("从微信服务器下载媒体文件成功，filePath=" + filePath);
		} catch (Exception e) {
			filePath = null;
			logger.error("从微信服务器下载媒体文件失败：%s", e);
		} finally {
			try {
				if(fos != null){
					fos.close();
				}
				if(bis != null) {
					bis.close();
				}
				if(conn != null) {
					conn.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return filePath;
	}
	public static String getFileEndWitsh(String headerField) {
		MimeTypes allTypes = MimeTypes.getDefaultMimeTypes();
		MimeType jpeg = null;
		try {
			jpeg = allTypes.forName(headerField);
		} catch (MimeTypeException e) {
			e.printStackTrace();
		}
		return (jpeg == null ? "" : jpeg.getExtension());
	}
	public static boolean changeToMp3(String sourcePath, String targetPath) {
		boolean result = false;
		File source = new File(sourcePath);
		File target = new File(targetPath);
		AudioAttributes audio = new AudioAttributes();
		Encoder encoder = new Encoder();
		audio.setCodec("libmp3lame");
		EncodingAttributes attrs = new EncodingAttributes();
		attrs.setFormat("mp3");
		attrs.setAudioAttributes(audio);
		try {
			encoder.encode(source, target, attrs);
			result = true;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InputFormatException e) {
			e.printStackTrace();
		} catch (EncoderException e) {
			//it.sauronsoftware.jave.EncoderException: video:0kB audio:3kB subtitle:0kB other streams:0kB global headers:0kB muxing overhead: 8.841463%
			//报此异常 但仍能转换成功
			result = true;
			//e.printStackTrace();
		}
		return result;
	}
}
