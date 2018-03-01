package cn.damei.common;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;
import javax.net.ssl.*;
import javax.servlet.ServletContext;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
@Component
@Lazy(false)
public class PropertyHolder implements ServletContextAware, ApplicationContextAware {
    public static ApplicationContext appCtx;
    private static ServletContext servletContext;
    private static String uploadBaseUrl;
    private static String uploadDir;
    private static String baseurl;
    private static String oldCrmBaseUrl;
    private static String repotSignAppId;
    private static String repotSignSecret;
    private static int socketIOPort;
    private static String oauthCenterUrl;
    private static String oauthCenterAppid;
    private static String oauthCenterSecret;
    private static String standBookPlanUrl;
    private static String installBaseUrl;
    private static String installBasePmUrl;
    private static String projectUrl;
    private static String qualityCheckUrl;
    private static String repeatQualityCheckUrl;
    private static String principalInstallUrl;
    private static String reviewUrl;
    private static String supplyUrl;
    private static String customerManagementUrl;
    private static String completionSiteDataUrl;
    private static String completionSiteAllDataUrl;
    private static String areaLevelInfoUrl;
    private static String commonlyApproveId;
    private static String commonlyApplicantId;
    private static String crmTaskTemplateMessage;
    private static String crmCommonTemplateMessage;
    private static String oaWorkOrderStageTemplateMessage;
    private static String reCallTemplateMessage;
    private static String attendanceRecordsTemplateMessage;
    private static String copyRemindTemplateMessage;
    private static String noticeOfLeaveTemplateMessage;
    private static String wxMediaDowmloadUrl;
    private static String saleUrl;
    private static String saleMd5;
    private static String workerMd5;
    static {
        disableSslVerification();
    }
    public static ServletContext getServletContext() {
        return servletContext;
    }
    public void setServletContext(ServletContext ctx) {
        PropertyHolder.servletContext = ctx;
        ctx.setAttribute("ctx", ctx.getContextPath());
    }
    public static String getUploadBaseUrl() {
        return PropertyHolder.uploadBaseUrl;
    }
    @Value("${upload.base.url}")
    public void setUploadBaseUrl(String uploadBaseUrl) {
        PropertyHolder.uploadBaseUrl = uploadBaseUrl;
    }
    public static String getUploadDir() {
        return PropertyHolder.uploadDir;
    }
    @Value("${upload.dir}")
    public void setUploadDir(String uploadDir) {
        PropertyHolder.uploadDir = uploadDir;
    }
    public static String getBaseurl() {
        return PropertyHolder.baseurl;
    }
    @Value("${base.url}")
    public void setBaseurl(String baseurl) {
        PropertyHolder.baseurl = baseurl;
    }
    public static int getSocketIOPort() {
        return PropertyHolder.socketIOPort;
    }
    @Value("${socket.io.port}")
    public void setSocketIOPort(int socketIOPort) {
        PropertyHolder.socketIOPort = socketIOPort;
    }
    public static String getRepotSignAppId() {
        return repotSignAppId;
    }
    @Value("${report.sign.appid}")
    public void setRepotSignAppId(String repotSignAppId) {
        PropertyHolder.repotSignAppId = repotSignAppId;
    }
    public static String getRepotSignSecret() {
        return repotSignSecret;
    }
    @Value("${report.sign.secret}")
    public void setRepotSignSecret(String repotSignSecret) {
        PropertyHolder.repotSignSecret = repotSignSecret;
    }
    public static String getOldCrmBaseUrl() {
        return oldCrmBaseUrl;
    }
    @Value("${oldCrm.base.url}")
    public void setOldCrmBaseUrl(String oldCrmBaseUrl) {
        PropertyHolder.oldCrmBaseUrl = oldCrmBaseUrl;
    }
    public static String getOauthCenterUrl() {
        return PropertyHolder.oauthCenterUrl;
    }
    @Value("${oauth.center.url}")
    public void setOauthCenterUrl(String oauthCenterUrl) {
        PropertyHolder.oauthCenterUrl = oauthCenterUrl;
    }
    public static String getOauthCenterAppid() {
        return PropertyHolder.oauthCenterAppid;
    }
    @Value("${oauth.center.appid}")
    public void setOauthCenterAppid(String oauthCenterAppid) {
        PropertyHolder.oauthCenterAppid = oauthCenterAppid;
    }
    public static String getOauthCenterSecret() {
        return PropertyHolder.oauthCenterSecret;
    }
    @Value("${oauth.center.secret}")
    public void setOauthCenterSecret(String oauthCenterSecret) {
        PropertyHolder.oauthCenterSecret = oauthCenterSecret;
    }
    public static String getStandBookPlanUrl() {
        return standBookPlanUrl;
    }
    @Value("${standBook.plan.url}")
    public void setStandBookPlanUrl(String standBookPlanUrl) {
        PropertyHolder.standBookPlanUrl = standBookPlanUrl;
    }
    public static String getInstallBaseUrl() {
        return installBaseUrl;
    }
    @Value("${standBook.installBase.url}")
    public void setInstallBaseUrl(String installBaseUrl) {
        PropertyHolder.installBaseUrl = installBaseUrl;
    }
    public static String getInstallBasePmUrl() {
        return installBasePmUrl;
    }
    @Value("${standBook.installBasePm.url}")
    public void setInstallBasePmUrl(String installBasePmUrl) {
        PropertyHolder.installBasePmUrl = installBasePmUrl;
    }
    public static String getProjectUrl() {
        return projectUrl;
    }
    @Value("${standBook.project.url}")
    public void setProjectUrl(String projectUrl) {
        PropertyHolder.projectUrl = projectUrl;
    }
    public static String getQualityCheckUrl() {
        return qualityCheckUrl;
    }
    @Value("${standBook.qualityCheck.url}")
    public void setQualityCheckUrl(String qualityCheckUrl) {
        PropertyHolder.qualityCheckUrl = qualityCheckUrl;
    }
    public static String getRepeatQualityCheckUrl() {
        return repeatQualityCheckUrl;
    }
    @Value("${standBook.repeatQualityCheck.url}")
    public void setRepeatQualityCheckUrl(String repeatQualityCheckUrl) {
        PropertyHolder.repeatQualityCheckUrl = repeatQualityCheckUrl;
    }
    public static String getPrincipalInstallUrl() {
        return principalInstallUrl;
    }
    @Value("${standBook.principalInstall.url}")
    public void setPrincipalInstallUrl(String principalInstallUrl) {
        PropertyHolder.principalInstallUrl = principalInstallUrl;
    }
    public static String getReviewUrl() {
        return reviewUrl;
    }
    @Value("${standBook.review.url}")
    public void setReviewUrl(String reviewUrl) {
        PropertyHolder.reviewUrl = reviewUrl;
    }
    public static String getSupplyUrl() {
        return supplyUrl;
    }
    @Value("${standBook.supply.url}")
    public void setSupplyUrl(String supplyUrl) {
        PropertyHolder.supplyUrl = supplyUrl;
    }
    public static String getCustomerManagementUrl() {
        return customerManagementUrl;
    }
    @Value("${standBook.customerManagement.url}")
    public void setCustomerManagementUrl(String customerManagementUrl) {
        PropertyHolder.customerManagementUrl = customerManagementUrl;
    }
    public static String getCompletionSiteDataUrl() {
        return completionSiteDataUrl;
    }
    @Value("${completionSiteData.orderByActualEndDate.url}")
    public void setCompletionSiteDataUrl(String completionSiteDataUrl) {
        PropertyHolder.completionSiteDataUrl = completionSiteDataUrl;
    }
    public static String getCompletionSiteAllDataUrl() {
        return completionSiteAllDataUrl;
    }
    @Value("${completionSiteData.findOrder.url}")
    public void setCompletionSiteAllDataUrl(String completionSiteAllDataUrl) {
        PropertyHolder.completionSiteAllDataUrl = completionSiteAllDataUrl;
    }
    public static String getAreaLevelInfoUrl() {
        return areaLevelInfoUrl;
    }
    @Value("${standBook.areaLevelInfo.url}")
    public void setAreaLevelInfoUrl(String areaLevelInfoUrl) {
        PropertyHolder.areaLevelInfoUrl = areaLevelInfoUrl;
    }
    public static String getCommonlyApproveId() {
        return PropertyHolder.commonlyApproveId;
    }
    @Value("${commonly_approve_id}")
    public void setCommonlyApproveId(String commonlyApproveId) {
        PropertyHolder.commonlyApproveId = commonlyApproveId;
    }
    public static String getCommonlyApplicantId() {
        return PropertyHolder.commonlyApplicantId;
    }
    @Value("${commonly_applicant_id}")
    public void setCommonlyApplicantId(String commonlyApplicantId) {
        PropertyHolder.commonlyApplicantId = commonlyApplicantId;
    }
    public static String getCrmTaskTemplateMessage() {
        return PropertyHolder.crmTaskTemplateMessage;
    }
    @Value("${crm_task_template_message}")
    public void setCrmTaskTemplateMessage(String crmTaskTemplateMessage) {
        PropertyHolder.crmTaskTemplateMessage = crmTaskTemplateMessage;
    }
    public static String getCrmCommonTemplateMessage() {
        return PropertyHolder.crmCommonTemplateMessage;
    }
    @Value("${crm_common_template_message}")
    public void setCrmCommonTemplateMessage(String crmCommonTemplateMessage) {
        PropertyHolder.crmCommonTemplateMessage = crmCommonTemplateMessage;
    }
    public static String getOaWorkOrderStageTemplateMessage() {
        return oaWorkOrderStageTemplateMessage;
    }
    @Value("${oa_workOrder_stage_template_message}")
    public void setOaWorkOrderStageTemplateMessage(String oaWorkOrderStageTemplateMessage) {
        PropertyHolder.oaWorkOrderStageTemplateMessage = oaWorkOrderStageTemplateMessage;
    }
    public static String getReCallTemplateMessage() {
        return reCallTemplateMessage;
    }
    @Value("${re_call_template_message}")
    public void setReCallTemplateMessage(String reCallTemplateMessage) {
        PropertyHolder.reCallTemplateMessage = reCallTemplateMessage;
    }
    public static String getAttendanceRecordsTemplateMessage() {
        return attendanceRecordsTemplateMessage;
    }
    @Value("${attendance_records_template_message}")
    public void setAttendanceRecordsTemplateMessage(String attendanceRecordsTemplateMessage) {
        PropertyHolder.attendanceRecordsTemplateMessage = attendanceRecordsTemplateMessage;
    }
    public static String getCopyRemindTemplateMessage() {
        return copyRemindTemplateMessage;
    }
    @Value("${copy_remind_template_message}")
    public void setCopyRemindTemplateMessage(String copyRemindTemplateMessage) {
        PropertyHolder.copyRemindTemplateMessage = copyRemindTemplateMessage;
    }
    public static String getWxMediaDowmloadUrl() {
        return wxMediaDowmloadUrl;
    }
    @Value("${wx_media_dowmload_url}")
    public void setWxMediaDowmloadUrl(String wxMediaDowmloadUrl) {
        PropertyHolder.wxMediaDowmloadUrl = wxMediaDowmloadUrl;
    }
    public static String getSaleUrl() {
        return saleUrl;
    }
    @Value("${sale.url}")
    public  void setSaleUrl(String saleUrl) {
        PropertyHolder.saleUrl = saleUrl;
    }
    public static String getSaleMd5() {
        return saleMd5;
    }
    @Value("${sale.md5}")
    public  void setSaleMd5(String saleMd5) {
        PropertyHolder.saleMd5 = saleMd5;
    }
    public static String getWorkerMd5() {
        return workerMd5;
    }
    @Value("${worker.md5}")
    public  void setWorkerMd5(String workerMd5) {
        PropertyHolder.workerMd5 = workerMd5;
    }
    public static String getNoticeOfLeaveTemplateMessage() {
        return noticeOfLeaveTemplateMessage;
    }
    @Value("${notice_of_leave_template_message}")
    public void setNoticeOfLeaveTemplateMessage(String noticeOfLeaveTemplateMessage) {
        PropertyHolder.noticeOfLeaveTemplateMessage = noticeOfLeaveTemplateMessage;
    }
    private static void disableSslVerification() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        appCtx = applicationContext;
    }
}