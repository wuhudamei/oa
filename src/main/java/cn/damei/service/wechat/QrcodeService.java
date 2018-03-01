package cn.damei.service.wechat;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.rocoinfo.weixin.api.QrcodeApi;
import com.rocoinfo.weixin.model.ApiResult;
import cn.damei.Constants;
import cn.damei.common.service.CrudService;
import cn.damei.entity.wechat.Qrcode;
import cn.damei.enumeration.wechat.QrcodeType;
import cn.damei.repository.wechat.QrcodeDao;
import cn.damei.rest.wechat.CommonMethod;
@Service
public class QrcodeService extends CrudService<QrcodeDao, Qrcode> {
    private static final Long DEFAULT_EXPIRE_SECONDS = 1800L;
    @Transactional
    public Map<String,Object> create(Qrcode qrcode) {
        ApiResult res = null;
        if (QrcodeType.QR_SCENE.equals(qrcode.getType())) {
            if (qrcode.getExpireSeconds() == null)
                qrcode.setExpireSeconds(DEFAULT_EXPIRE_SECONDS);
            res = QrcodeApi.createTemporary(qrcode.getExpireSeconds(), qrcode.getSceneId());
        }
        if (QrcodeType.QR_LIMIT_SCENE.equals(qrcode.getType())) {
            res = QrcodeApi.createPermanent(qrcode.getSceneId());
        }
        if (QrcodeType.QR_LIMIT_STR_SCENE.equals(qrcode.getType())) {
            res = QrcodeApi.createPermanent(qrcode.getSceneStr());
        }
        if (!res.isSuccess()){
        	return CommonMethod.retMessage(res.getErrorCode().longValue(),res.getErrorMsg());
        }
        qrcode.setTicket((String) res.get("ticket"));
        qrcode.setUrl((String) res.get("url"));
        qrcode.setGenerated(true);
        Qrcode q = this.getByTypeAndSceneIdAndStr(qrcode.getType(), qrcode.getSceneId(), qrcode.getSceneStr());
        if (q != null) {
            qrcode.setId(q.getId());
        	Integer updateNum = this.update(qrcode);
            return CommonMethod.retMessage(updateNum.longValue(),"更新成功!");
        }
        Integer insertNum = this.insert(qrcode);
        return CommonMethod.retMessage(insertNum.longValue(),"操作成功!");
    }
    private Qrcode getByTypeAndSceneIdAndStr(QrcodeType type, Long sceneId, String sceneStr) {
        return this.entityDao.getByTypeAndSceneIdAndStr(type, sceneId, sceneStr);
    }
    public Qrcode getTemporary(Long sceneId) {
        if (sceneId != null)
            return this.entityDao.getTemporary(sceneId);
        return null;
    }
    public Qrcode getPermanentByLong(Long sceneId) {
        if (sceneId != null)
            return this.entityDao.getPermanentByLong(sceneId);
        return null;
    }
    public Qrcode getPermanentByStr(String sceneStr) {
        if (StringUtils.isNotBlank(sceneStr))
            return this.entityDao.getPermanentByStr(sceneStr);
        return null;
    }
}
