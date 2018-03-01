package cn.damei.rest.completionSiteData;
import com.google.common.collect.Maps;
import cn.damei.common.BaseController;
import cn.damei.dto.StatusDto;
import cn.damei.dto.page.PageTable;
import cn.damei.dto.page.Pagination;
import cn.damei.service.completionSiteData.CompletionSiteDataService;
import cn.damei.utils.MapUtils;
import cn.damei.utils.excel.imports.ExcelRowReaderDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;
@RestController
@RequestMapping("/api/completionSiteData")
public class CompletionSiteDataController extends BaseController {
    @Autowired
    private CompletionSiteDataService completionSiteDataService;
    @RequestMapping(value = "/list")
    public Object list(@RequestParam(required = false) String keyword) {
        Map<String, Object> params = Maps.newHashMap();
        MapUtils.putNotNull(params, "keyword", keyword);
        return StatusDto.buildSuccess(completionSiteDataService.getCompletionSiteData(params));
    }
    @RequestMapping(value = "/listDetails")
    public Object listDetails(@RequestParam(required = false) String keyword,
                              @RequestParam(required = false) String storeName,
                              @RequestParam(value = "offset", defaultValue = "0") int offset,
                              @RequestParam(value = "limit", defaultValue = "10") int limit) {
        Map<String, Object> params = Maps.newHashMap();
        MapUtils.putNotNull(params, "keyword", keyword);
        MapUtils.putNotNull(params, "storeName", storeName);
        PageTable pageTable = completionSiteDataService.searchScrollPage(params, new Pagination(offset, limit));
        return StatusDto.buildSuccess(pageTable);
    }
    @RequestMapping(value = "/importFile", method = RequestMethod.POST)
    public Object importFile(@RequestParam("file") MultipartFile file) {
        String msg = completionSiteDataService.importFile(file);
        if(msg != null && msg.length() > 0){
            return StatusDto.buildFailure(msg);
        }else{
            return StatusDto.buildSuccess("导入成功");
        }
    }
    @RequestMapping(value = "/orderInsert", method = RequestMethod.GET)
    public Object orderInsert() {
        int i = completionSiteDataService.orderInsert();
        if(i > 0){
            return StatusDto.buildSuccess("同步成功");
        }else{
            return StatusDto.buildFailure("同步失败");
        }
    }
}
