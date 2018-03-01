package cn.damei.utils;
import java.io.IOException;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
public class HttpUtil {
	// http请求工具代理对象
	private static final OkHttpClient httpClient;
	static {
		httpClient = new OkHttpClient();
	}
	public static String post(String url, String appid, String secret, String code, boolean socpe) throws IOException {
		FormBody body = new FormBody.Builder().add("appid", appid).add("secret", secret).add("code", code)
				.add("scope", "true").build();
		Request request = new Request.Builder().url(url).post(body).build();
		Response response = httpClient.newCall(request).execute();
		if (response.isSuccessful()) {
			return response.body().string();
		} else {
			throw new IOException("Unexpected code " + response);
		}
	}
	public static String post(String url, String appid, String secret, String jobNo) throws IOException {
		FormBody body = new FormBody.Builder().add("appid", appid).add("secret", secret).add("username", jobNo).build();
		Request request = new Request.Builder().url(url).post(body).build();
		Response response = httpClient.newCall(request).execute();
		if (response.isSuccessful()) {
			return response.body().string();
		} else {
			throw new IOException("Unexpected code " + response);
		}
	}
	public static String post(String url, String data, String type) {
		Request request = null;
		if ("json".equals(type)) {
			MediaType CONTENT_TYPE_FORM = MediaType.parse("application/json;charset=utf-8");
			RequestBody body = RequestBody.create(CONTENT_TYPE_FORM, data);
			request = new Request.Builder().url(url).post(body).build();
		} else {
			FormBody body = new FormBody.Builder().add("orderno", data).build();
			request = new Request.Builder().url(url).post(body).build();
		}
		try {
			Response response = httpClient.newCall(request).execute();
			if (!response.isSuccessful()) {
				throw new RuntimeException("Unexpected code" + response);
			}
			return response.body().string();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
