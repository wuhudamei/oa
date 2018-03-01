package cn.damei.web.servlet;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springside.modules.web.Servlets;
import cn.damei.common.PropertyHolder;
import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;
@SuppressWarnings("all")
public class StaticContentServlet extends HttpServlet {
    private static final long serialVersionUID = -1855617048198368534L;
    private static final String[] GZIP_MIME_TYPES = {"text/html", "application/xhtml+xml", "text/plain", "text/css",
            "text/javascript", "application/x-javascript", "application/json"};
    private static final int GZIP_MINI_LENGTH = 512;
    private MimetypesFileTypeMap mimetypesFileTypeMap;
    private ApplicationContext applicationContext;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String contentPath = request.getPathInfo();
        if (StringUtils.isBlank(contentPath) || "/".equals(contentPath)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "contentPath parameter is required.");
            return;
        }
        ContentInfo contentInfo = getContentInfo(contentPath);
        if (!contentInfo.file.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "file not found.");
            return;
        }
        if (!Servlets.checkIfModifiedSince(request, response, contentInfo.lastModified)
                || !Servlets.checkIfNoneMatchEtag(request, response, contentInfo.etag)) {
            return;
        }
        Servlets.setExpiresHeader(response, Servlets.ONE_YEAR_SECONDS);
        Servlets.setLastModifiedHeader(response, contentInfo.lastModified);
        Servlets.setEtag(response, contentInfo.etag);
        response.setContentType(contentInfo.mimeType);
        if (request.getParameter("download") != null) {
            Servlets.setFileDownloadHeader(response, contentInfo.fileName);
        }
        OutputStream output;
        if (checkAccetptGzip(request) && contentInfo.needGzip) {
            output = buildGzipOutputStream(response);
        } else {
            response.setContentLength(contentInfo.length);
            output = response.getOutputStream();
        }
        FileUtils.copyFile(contentInfo.file, output);
        output.flush();
    }
    private static boolean checkAccetptGzip(HttpServletRequest request) {
        String acceptEncoding = request.getHeader("Accept-Encoding");
        return StringUtils.contains(acceptEncoding, "gzip");
    }
    private OutputStream buildGzipOutputStream(HttpServletResponse response) throws IOException {
        response.setHeader("Content-Encoding", "gzip");
        response.setHeader("Vary", "Accept-Encoding");
        return new GZIPOutputStream(response.getOutputStream());
    }
    @Override
    public void init() throws ServletException {
        applicationContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        mimetypesFileTypeMap = new MimetypesFileTypeMap();
        mimetypesFileTypeMap.addMimeTypes("text/css css");
    }
    private ContentInfo getContentInfo(String contentPath) {
        ContentInfo contentInfo = new ContentInfo();
        String realFilePath = PropertyHolder.getUploadDir() + contentPath;
        File file = new File(realFilePath);
        contentInfo.file = file;
        contentInfo.contentPath = contentPath;
        contentInfo.fileName = file.getName();
        contentInfo.length = (int) file.length();
        contentInfo.lastModified = file.lastModified();
        contentInfo.etag = "W/\"" + contentInfo.lastModified + "\"";
        contentInfo.mimeType = mimetypesFileTypeMap.getContentType(contentInfo.fileName);
        if ((contentInfo.length >= GZIP_MINI_LENGTH) && ArrayUtils.contains(GZIP_MIME_TYPES, contentInfo.mimeType)) {
            contentInfo.needGzip = true;
        } else {
            contentInfo.needGzip = false;
        }
        return contentInfo;
    }
    static class ContentInfo {
        protected String contentPath;
        protected File file;
        protected String fileName;
        protected int length;
        protected String mimeType;
        protected long lastModified;
        protected String etag;
        protected boolean needGzip;
    }
}