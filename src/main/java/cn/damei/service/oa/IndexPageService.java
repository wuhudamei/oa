package cn.damei.service.oa;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import cn.damei.dto.page.PageTable;
import cn.damei.dto.page.Pagination;
import cn.damei.entity.oa.InsideMessage;
import cn.damei.entity.oa.InsideMessageTarget;
import cn.damei.entity.oa.InsideMessageWithStatus;
import cn.damei.repository.oa.InsideMessageDao;
import cn.damei.repository.oa.InsideMessageTargetDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
@Service
public class IndexPageService{
    @Autowired
    InsideMessageDao insideMessageDao;
    @Autowired
    InsideMessageTargetDao insideMessageTargetDao;
    private static final String PAGE_SORT = "sort";
    public PageTable mesSearchPageForIndexPage(Map<String, Object> params, Pagination page) {
        params.put(PAGE_SORT, page.getSort());
        PageHelper.offsetPage(page.getOffset(), page.getLimit());
        Page<InsideMessageWithStatus> result = (Page<InsideMessageWithStatus>) insideMessageDao.searchForList(params);
        return new PageTable<InsideMessageWithStatus>(result.getResult(), result.getTotal());
    }
    public List<InsideMessage> indexPageMsg(Long userId){
        return insideMessageDao.mineMesForIndex(userId);
    }
    public void changeMsgStatus(InsideMessageTarget entity){
        insideMessageTargetDao.changeStatus(entity);
    }
}