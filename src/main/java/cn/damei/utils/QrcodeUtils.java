package cn.damei.utils;
import com.google.common.collect.Maps;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
public class QrcodeUtils {
    private QrcodeUtils() {
    }
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 300;
    private static final String DEFAULT_FORMAT = "png";
    private static final Integer DEFAULT_MARGIN = 0;
    private static final ErrorCorrectionLevel DEFAULT_ECL = ErrorCorrectionLevel.L;
    private static final String DEFAULT_FILE_DIR = "qrcode";
    public static String generateQRCode(String content) {
        return generateQRCode(content, DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_FORMAT, DEFAULT_MARGIN, DEFAULT_ECL, null);
    }
    public static String generateQRCode(String content, OutputStream os) {
        return generateQRCode(content, DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_FORMAT, DEFAULT_MARGIN, DEFAULT_ECL, os);
    }
    public static String generateQRCode(String content, String format) {
        return generateQRCode(content, DEFAULT_WIDTH, DEFAULT_HEIGHT, format, DEFAULT_MARGIN, DEFAULT_ECL, null);
    }
    public static String generateQRCode(String content, int width, int height) {
        return generateQRCode(content, width, height, DEFAULT_FORMAT, DEFAULT_MARGIN, DEFAULT_ECL, null);
    }
    public static String generateQRCode(String content, int width, int height, String format) {
        return generateQRCode(content, width, height, format, DEFAULT_MARGIN, DEFAULT_ECL, null);
    }
    public static String generateQRCode(String content, int width, int height, String format, Integer margin, ErrorCorrectionLevel ecl, OutputStream os) {
        Map<EncodeHintType, Object> hint = Maps.newConcurrentMap();
        hint.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hint.put(EncodeHintType.ERROR_CORRECTION, ecl);
        hint.put(EncodeHintType.MARGIN, margin);
        String extension = format;
        if (StringUtils.isBlank(extension)) {
            extension = DEFAULT_FORMAT;
        }
        try {
            String filePath = getOrGenerateFilePath(extension);
            if (StringUtils.isNoneBlank(filePath)) {
                BitMatrix matrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hint);
                if (os == null) {
                    os = new FileOutputStream(new File(filePath));
                }
                MatrixToImageWriter.writeToStream(matrix, format, os);
                return filePath;
            }
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(os);
        }
        return null;
    }
    private static String getOrGenerateFilePath(String extension) {
        return "/";
    }
    private static String generateFileName(String extension) {
        return System.currentTimeMillis() + RandomStringUtils.random(6) + "." + extension;
    }
}
