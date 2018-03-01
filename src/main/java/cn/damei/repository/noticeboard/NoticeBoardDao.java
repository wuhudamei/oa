package cn.damei.repository.noticeboard;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.noticeboard.NoticeBoard;
import java.util.List;
import java.util.Map;
@Repository
public interface NoticeBoardDao extends CrudDao<NoticeBoard> {
    List<String> findFamilyCodeByOrgId(List<Long> orgIdList);
    List<String> findFamilyCOdeByUserId(Long userId);
    List<NoticeBoard> findAllNotice(Map<String, Object> params);
}
