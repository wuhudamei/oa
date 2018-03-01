package cn.damei.service.noticeboard;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import cn.damei.common.service.CrudService;
import cn.damei.dto.page.PageTable;
import cn.damei.dto.page.Pagination;
import cn.damei.entity.noticeboard.NoticeBoard;
import cn.damei.repository.noticeboard.NoticeBoardDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
@Service
public class NoticeBoardService extends CrudService<NoticeBoardDao, NoticeBoard> {
    @Autowired
    NoticeBoardDao noticeBoardDao;
    public List<String> findFamilyCodeByOrgId(List<Long> orgIdList) {
        return noticeBoardDao.findFamilyCodeByOrgId(orgIdList);
    }
    public List<String> findFamilyCOdeByUserId(Long userId) {
        return noticeBoardDao.findFamilyCOdeByUserId(userId);
    }
    public List<NoticeBoard> findAllNotice(Map<String, Object> params) {
        return noticeBoardDao.findAllNotice(params);
    }
}
