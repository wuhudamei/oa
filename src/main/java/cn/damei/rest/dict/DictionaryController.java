package cn.damei.rest.dict;
import com.google.common.collect.Maps;
import cn.damei.common.BaseController;
import cn.damei.dto.StatusDto;
import cn.damei.dto.page.PageTable;
import cn.damei.dto.page.Pagination;
import cn.damei.dto.page.Sort;
import cn.damei.entity.dict.MdniDictionary;
import cn.damei.service.dict.MdniDictionaryService;
import cn.damei.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping(value = "/api/dict")
public class DictionaryController extends BaseController {
    @Autowired
    MdniDictionaryService mdniDictionaryService;
    @RequestMapping("/list")
    public Object list(@RequestParam(value = "keyword", required = false) String keyword,
                       @RequestParam(value = "searchType", required = false) Long searchType,
                       @RequestParam(value = "offset", defaultValue = "0") int offset,
                       @RequestParam(value = "limit", defaultValue = "20") int pageSize,
                       @RequestParam(value = "sortName", required=false) String orderColumn,
                       @RequestParam(value = "sortOrder", defaultValue = "DESC") String orderSort) {
        Map<String, Object> params = Maps.newHashMap();
        MapUtils.putNotNull(params, "keyword", keyword);
        MapUtils.putNotNull(params, "searchType", searchType);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.valueOf(orderSort), "sort"));
        PageTable<MdniDictionary> pageTable = mdniDictionaryService.searchScrollPage(params, new Pagination(offset, pageSize, sort));
        return StatusDto.buildSuccess(pageTable);
    }
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Object add(@RequestBody MdniDictionary mdniDictionary) {
        mdniDictionaryService.addOrUpdate(mdniDictionary);
        return StatusDto.buildSuccess("操作成功");
    }
    @RequestMapping("/delete/{id}")
    public Object delete(@PathVariable(value = "id") Long id) {
        mdniDictionaryService.deleteById(id);
        return StatusDto.buildSuccess("删除成功");
    }
    @RequestMapping("/getNode/{type}")
    public Object getNode(@PathVariable("type") String type) {
        return StatusDto.buildSuccess(mdniDictionaryService.getNode(Long.parseLong(type)));
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public StatusDto getById(@PathVariable(value = "id") Long id) {
        MdniDictionary dic = mdniDictionaryService.getById(id);
        if (dic == null) {
            return StatusDto.buildFailure("获取组织架构失败！");
        }
        return StatusDto.buildSuccess(dic);
    }
    @RequestMapping(value = "/getByType")
    public Object getByType(@RequestParam(value = "parentType", defaultValue = "0") int parentType, @RequestParam int type) {
        return StatusDto.buildSuccess(mdniDictionaryService.getByType(parentType, type));
    }
    @RequestMapping(value = "/getDictsByType")
    public Object getByType(@RequestParam int type) {
        return StatusDto.buildSuccess(mdniDictionaryService.getByType(null, type));
    }
    @RequestMapping(value = "/getByTypeFilter")
    public Object getByTypeFilter(@RequestParam(value = "parentType", defaultValue = "0") int parentType, @RequestParam int type) {
        List<MdniDictionary> mdniDictionarybyType = mdniDictionaryService.getByType(parentType, type);
		List<MdniDictionary> filterDictionary = mdniDictionaryService.filterDictionary(mdniDictionarybyType);
		return StatusDto.buildSuccess(filterDictionary);
    }
    @RequestMapping(value = "/getDictsByTypeFilter")
    public Object getByTypeFilter(@RequestParam int type) {
        return StatusDto.buildSuccess(mdniDictionaryService.filterDictionary(mdniDictionaryService.getByType(null, type)));
    }
}