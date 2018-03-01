package cn.damei.repository.wechat;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.wechat.Qrcode;
import cn.damei.enumeration.wechat.QrcodeType;
@Repository
public interface QrcodeDao extends CrudDao<Qrcode> {
    Qrcode getTemporary(@Param("sceneId") Long sceneId);
    Qrcode getPermanentByLong(@Param("sceneId") Long sceneId);
    Qrcode getPermanentByStr(@Param("sceneStr")String sceneStr);
    Qrcode getByTypeAndSceneIdAndStr(@Param("type") QrcodeType type, @Param("sceneId") Long sceneId, @Param("sceneStr") String sceneStr);
}