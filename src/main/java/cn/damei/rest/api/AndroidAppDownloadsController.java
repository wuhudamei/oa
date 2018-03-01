package cn.damei.rest.api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import cn.damei.common.BaseController;
import cn.damei.dto.StatusDto;
import cn.damei.entity.proprietorappdownloadrecord.ProprietorAppDownloadRecord;
import cn.damei.service.proprietorappdownloadrecord.ProprietorAppDownloadRecordService;
import java.util.Date;
@RestController
@RequestMapping(value = "/api/interface/appDownloads")
public class AndroidAppDownloadsController extends BaseController {
    @Autowired
    private ProprietorAppDownloadRecordService proprietorAppDownloadRecordService;
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public Object save() {
        try{
            ProprietorAppDownloadRecord proprietorAppDownloadRecord = new ProprietorAppDownloadRecord();
            proprietorAppDownloadRecord.setDownloadTime(new Date());
            this.proprietorAppDownloadRecordService.insert(proprietorAppDownloadRecord);
            return StatusDto.buildSuccess();
        }catch (Exception e){
            e.printStackTrace();
            return StatusDto.buildFailure("操作失败");
        }
    }
}
