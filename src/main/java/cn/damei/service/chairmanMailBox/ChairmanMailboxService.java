package cn.damei.service.chairmanMailBox;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.damei.common.PropertyHolder;
import cn.damei.common.service.CrudService;
import cn.damei.entity.chairmanMailbox.ChairmanMailbox;
import cn.damei.enumeration.UploadCategory;
import cn.damei.repository.chairmanMailbox.ChairmanMailboxDao;
import cn.damei.service.upload.UploadService;
import cn.damei.shiro.ShiroUser;
import cn.damei.utils.WebUtils;
import cn.damei.utils.weixin.WXUtil;
import java.util.Date;
@Service
public class ChairmanMailboxService extends CrudService<ChairmanMailboxDao,ChairmanMailbox> {
    @Autowired
    private ChairmanMailboxDao chairmanMaiboxDao;
    @Autowired
    private UploadService uploadService ;
    @Transactional(rollbackFor = Exception.class)
    public boolean insertChairmanMailbox(ChairmanMailbox chairmanMailbox) {
        try{
            ShiroUser loggedUser = WebUtils.getLoggedUser();
            chairmanMailbox.setReadStatus(false);
            chairmanMailbox.setCreateUser(loggedUser.getId());
            chairmanMailbox.setCompanyId(loggedUser.getCompanyId());
            chairmanMailbox.setDepartmentId(loggedUser.getDepartmentId());
            chairmanMailbox.setCreateTime(new Date());
            chairmanMailbox.setImportantDegree(1);
            if(StringUtils.isNotBlank(chairmanMailbox.getPictureUrls())){
                String localPicUrls = "";
                String[] picArr = chairmanMailbox.getPictureUrls().split("&");
                for(int i = 0; i < picArr.length; i++){
                    String localPicUrl = WXUtil.downloadMedia(picArr[i],
                            uploadService.saveFilePath(UploadCategory.CHAIRMAN_MAIBOX,""), "jpg");
                    localPicUrl = PropertyHolder.getUploadBaseUrl() + localPicUrl.substring(localPicUrl.indexOf(UploadCategory.CHAIRMAN_MAIBOX.getPath()));
                    if(i < picArr.length - 1){
                        localPicUrls += localPicUrl + "&";
                    }else{
                        localPicUrls += localPicUrl;
                    }
                }
                chairmanMailbox.setPictureUrls(localPicUrls);
            }
            String voiceUrl = chairmanMailbox.getVoiceUrl();
            if(StringUtils.isNotBlank(voiceUrl)){
                voiceUrl = WXUtil.downloadMedia(voiceUrl,
                        uploadService.saveFilePath(UploadCategory.CHAIRMAN_MAIBOX, ""), "amr");
                String voiceMp3Url = voiceUrl.replace(".amr", ".mp3");
                if(WXUtil.changeToMp3(voiceUrl, voiceMp3Url)){
                    voiceUrl = voiceMp3Url;
                }
                voiceUrl = PropertyHolder.getUploadBaseUrl() + voiceUrl.substring(
                        voiceUrl.indexOf(UploadCategory.CHAIRMAN_MAIBOX.getPath()));
                chairmanMailbox.setVoiceUrl(voiceUrl);
            }
            System.out.println(chairmanMailbox);
            int count = chairmanMaiboxDao.insert(chairmanMailbox);
            if(count <= 0){
                return false;
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
