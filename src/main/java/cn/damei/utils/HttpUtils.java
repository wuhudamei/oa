package cn.damei.utils;
import com.google.common.collect.Lists;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
public final class HttpUtils {
    private static final String DEFAULT_ENCODING = "utf-8";
    private static final String JSON_TYPE = "application/json";
    private static final String XML_TYPE = "text/xml";
    private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);
    public static String get(String url, final NameValuePair... parameters) {
        if (StringUtils.isBlank(url)) {
            throw new IllegalArgumentException("get请求地址不能为空");
        } else {
            String respTxt = StringUtils.EMPTY;
            HttpClient httpClient = createHttpClientInstance();
            try {
                if (null != parameters && parameters.length > 0) {
                    String encodedParams = encodeParameters(parameters);
                    if (-1 == url.indexOf("?")) {
                        url += "?" + encodedParams;
                    } else {
                        url += "&" + encodedParams;
                    }
                }
                HttpGet getMethod = new HttpGet(url);
                RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(4000).setConnectTimeout(2000).build();
                getMethod.setConfig(requestConfig);
                HttpResponse resp = httpClient.execute(getMethod);
                if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    respTxt = EntityUtils.toString(resp.getEntity(), DEFAULT_ENCODING);
                }
            } catch (Exception e) {
                logger.error("httpGet请求异常：{},错误详细是：{}", url, e.getMessage());
            } finally {
                HttpClientUtils.closeQuietly(httpClient);
            }
            return respTxt;
        }
    }
    public static String postJson(String postUrl, String json) {
        HttpClient client = createHttpClientInstance();
        HttpPost post = new HttpPost(postUrl);
        String respResult = null;
        try {
            StringEntity stringEntity = new StringEntity(json, DEFAULT_ENCODING);
            stringEntity.setContentEncoding(DEFAULT_ENCODING);
            stringEntity.setContentType(JSON_TYPE);
            post.setEntity(stringEntity);
            HttpResponse resp = client.execute(post);
            if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = resp.getEntity();
                respResult = EntityUtils.toString(entity, DEFAULT_ENCODING);
            }
        } catch (Exception ex) {
            logger.error("httpPost json异常：{},错误详细是：{}", postUrl, ex.getMessage());
        } finally {
            HttpClientUtils.closeQuietly(client);
        }
        return respResult;
    }
    public static String postXml(String url, String xml, String encoding) {
        HttpClient client = createHttpClientInstance();
        String respResult = null;
        try {
            HttpPost post = new HttpPost(url);
            StringEntity stringEntity = new StringEntity(xml, encoding);
            stringEntity.setContentEncoding(encoding);
            stringEntity.setContentType(XML_TYPE);
            post.setEntity(stringEntity);
            HttpResponse resp = client.execute(post);
            if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = resp.getEntity();
                respResult = EntityUtils.toString(entity, encoding);
            }
        } catch (Exception ex) {
            logger.error("httpPost xml：{},错误详细是：{}", url, ex.getMessage());
        } finally {
            HttpClientUtils.closeQuietly(client);
        }
        return respResult;
    }
    public static String postXmlWithSSL(String url, String xml, InputStream stream, String password) {
        CloseableHttpClient client = null;
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(stream, password.toCharArray());
            SSLContext sslcontext = SSLContexts.custom()
                    .loadKeyMaterial(keyStore, password.toCharArray())
                    .build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext,
                    new String[]{"TLSv1"},
                    null,
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            client = HttpClients.custom()
                    .setSSLSocketFactory(sslsf)
                    .build();
        } catch (Exception e) {
            logger.error("构建SSL的http请求失败,错误详细是：{}", e.getMessage());
        }
        String respResult = null;
        if (client == null) {
            return respResult;
        }
        try {
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000)
                    .setConnectTimeout(2000).build();
            HttpPost post = new HttpPost(url);
            StringEntity stringEntity = new StringEntity(xml, DEFAULT_ENCODING);
            stringEntity.setContentEncoding(DEFAULT_ENCODING);
            stringEntity.setContentType(XML_TYPE);
            post.setEntity(stringEntity);
            post.setConfig(requestConfig);
            HttpResponse resp = client.execute(post);
            if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = resp.getEntity();
                respResult = EntityUtils.toString(entity, DEFAULT_ENCODING);
            }
        } catch (Exception ex) {
            logger.error("httpPost xml：{},错误详细是：{}", url, ex.getMessage());
        } finally {
            HttpClientUtils.closeQuietly(client);
        }
        return respResult;
    }
    public static String post(String url, final NameValuePair... parameters) {
        HttpClient client = createHttpClientInstance();
        String respResult = null;
        try {
            HttpPost httPost = new HttpPost(url);
            if (ArrayUtils.isNotEmpty(parameters)) {
                List<NameValuePair> params = Lists.newArrayList(parameters);
                httPost.setEntity(new UrlEncodedFormEntity(params, DEFAULT_ENCODING));
            }
            HttpResponse resp = client.execute(httPost);
            if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = resp.getEntity();
                respResult = EntityUtils.toString(entity);
            }
        } catch (Exception ex) {
            logger.error("httpPost请求发生异常：地址：{},错误详细是：{}", url, ex.getMessage());
        } finally {
            HttpClientUtils.closeQuietly(client);
        }
        return respResult;
    }
    public static String post(String url, Map<String, String> map) {
        HttpClient client = createHttpClientInstance();
        String respResult = null;
        try {
            HttpPost httPost = new HttpPost(url);
            List<NameValuePair> params = new ArrayList<>();
            if (MapUtils.isNotEmpty(map)) {
                Set<String> keys = map.keySet();
                for (String key : keys) {
                    params.add(new BasicNameValuePair(key, map.get(key)));
                }
            }
            httPost.setEntity(new UrlEncodedFormEntity(params, DEFAULT_ENCODING));
            HttpResponse resp = client.execute(httPost);
            if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = resp.getEntity();
                respResult = EntityUtils.toString(entity);
            }
        } catch (Exception ex) {
            logger.error("httpPost请求发生异常：地址：{},错误详细是：{}", url, ex.getMessage());
        } finally {
            HttpClientUtils.closeQuietly(client);
        }
        return respResult;
    }
    private static String encodeParameters(final NameValuePair[] nameValues) {
        if (nameValues == null || nameValues.length == 0) {
            return StringUtils.EMPTY;
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < nameValues.length; i++) {
            NameValuePair nameValue = nameValues[i];
            if (i == 0) {
                buffer.append(nameValue.getName() + "=" + nameValue.getValue());
            } else {
                buffer.append("&" + nameValue.getName() + "=" + nameValue.getValue());
            }
        }
        return buffer.toString();
    }
    public static String encodeFilename(String filename, HttpServletRequest request) {
        String agent = request.getHeader("USER-AGENT");
        if (agent == null) {
            return filename;
        }
        try {
            if (-1 != agent.indexOf("MSIE")) {
                String newFileName = URLEncoder.encode(filename, DEFAULT_ENCODING);
                newFileName = StringUtils.replace(newFileName, "+", "%20");
                if (newFileName.length() > 150) {
                    newFileName = new String(filename.getBytes(DEFAULT_ENCODING), "ISO8859-1");
                    newFileName = StringUtils.replace(newFileName, " ", "%20");
                }
                return newFileName;
            }
            if (-1 != agent.indexOf("Mozilla")) {
                return "=?UTF-8?B?" + (new String(Base64.encodeBase64(filename.getBytes("UTF-8")))) + "?=";
            }
            return filename;
        } catch (Exception ex) {
            return filename;
        }
    }
    public static HttpClient createHttpClientInstance() {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        RequestConfig.Builder reqConfigBuilder = RequestConfig.custom();
        reqConfigBuilder.setSocketTimeout(1000*30);
        reqConfigBuilder.setConnectTimeout(1000*30);
        reqConfigBuilder.setConnectionRequestTimeout(1000*30);
        RequestConfig reqConfig = reqConfigBuilder.build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(SSLContexts.createDefault(),
                SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        httpClientBuilder.setSSLSocketFactory(sslsf);
        httpClientBuilder.setDefaultRequestConfig(reqConfig);
        return httpClientBuilder.build();
    }
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String constant = "x-requested-with";
        String xRequestedWith = request.getHeader(constant);
        return StringUtils.isNotBlank(xRequestedWith) && StringUtils.equalsIgnoreCase(xRequestedWith, "XMLHttpRequest");
    }
}