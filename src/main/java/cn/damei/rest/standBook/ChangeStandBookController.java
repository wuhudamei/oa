package cn.damei.rest.standBook;
import com.google.common.collect.Maps;
import cn.damei.common.BaseController;
import cn.damei.dto.StatusDto;
import cn.damei.entity.standBook.ChangeStandBook;
import cn.damei.entity.standBook.InstallBaseChangeStandBook;
import cn.damei.service.standBook.ChangeStandBookService;
import cn.damei.service.standBook.InstallBaseChangeStandBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/changeStandBook")
public class ChangeStandBookController extends BaseController {
    @Autowired
    private ChangeStandBookService changeStandBookService;
    @Autowired
    private InstallBaseChangeStandBookService installBaseChangeStandBookService;
    @RequestMapping("/change")
    public Object findChange(@RequestParam String orderno) {
        String changeNo = null;
        String createUser = "";
        Date changeDate = null;
        List<ChangeStandBook> tempList = new ArrayList<>();
        Map<String, Object> temp = Maps.newHashMap();
        List<Map<String, Object>> changeList = new ArrayList<>();
        List<ChangeStandBook> list = this.changeStandBookService.findChange(orderno);
        if (list != null && list.size() > 0) {
            for (ChangeStandBook changeStandBook : list) {
                if (changeNo == null) {
                    tempList.add(changeStandBook);
                    changeNo = changeStandBook.getChangeNo();
                    createUser = changeStandBook.getRealName();
                    changeDate = changeStandBook.getCreateTime();
                } else {
                    if (changeNo.equals(changeStandBook.getChangeNo())) {//当变更号一致
                        tempList.add(changeStandBook);
                    } else {
                        temp.put("changeNo",changeNo);
                        temp.put("createUser", createUser);
                        temp.put("changeDate", changeDate);
                        temp.put("changeStandBookList", tempList);
                        changeList.add(temp);
                        temp = null;
                        temp = Maps.newHashMap();
                        tempList = null;
                        tempList = new ArrayList<>();
                        tempList.add(changeStandBook);
                        changeNo = changeStandBook.getChangeNo();
                        createUser = changeStandBook.getRealName();
                        changeDate = changeStandBook.getCreateTime();
                    }
                }
            }
            temp.put("changeNo",changeNo);
            temp.put("createUser", createUser);
            temp.put("changeDate", changeDate);
            temp.put("changeStandBookList", tempList);
            changeList.add(temp);
        }
        return StatusDto.buildSuccess(changeList);
    }
    @RequestMapping("/installBaseChange")
    public Object findInstallBaseChange(@RequestParam String orderno) {
        String changeNo = null;
        Date changeApplyDate = null;
        List<InstallBaseChangeStandBook> tempList = new ArrayList<>();
        Map<String, Object> temp = Maps.newHashMap();
        List<Map<String, Object>> installBaseChangeList = new ArrayList<>();
        List<InstallBaseChangeStandBook> list = this.installBaseChangeStandBookService.findInstallBaseChange(orderno);
        if (list != null && list.size() > 0) {
            for (InstallBaseChangeStandBook change : list) {
                if (changeNo == null) {
                    tempList.add(change);
                    changeNo = change.getConstructionChangeNo();
                    changeApplyDate = change.getChangeApplyDate();
                } else {
                    if (changeNo.equals(change.getConstructionChangeNo())) {//当变更号一致
                        tempList.add(change);
                    } else {
                        temp.put("constructionChangeNo",changeNo);
                        temp.put("changeApplyDate",changeApplyDate);
                        temp.put("installBaseChangeStandBookList", tempList);
                        installBaseChangeList.add(temp);
                        temp = null;
                        temp = Maps.newHashMap();
                        tempList = null;
                        tempList = new ArrayList<>();
                        tempList.add(change);
                        changeNo = change.getConstructionChangeNo();
                    }
                }
            }
            temp.put("constructionChangeNo",changeNo);
            temp.put("changeApplyDate",changeApplyDate);
            temp.put("installBaseChangeStandBookList", tempList);
            installBaseChangeList.add(temp);
        }
        return StatusDto.buildSuccess(installBaseChangeList);
    }
    @RequestMapping("/findInstallBaseChangeData")
    public Object findInstallBaseChangeData(@RequestParam String orderno){
        List<InstallBaseChangeStandBook> list = this.installBaseChangeStandBookService.findInstallBaseChange(orderno);
        return StatusDto.buildSuccess(list);
    }
}
