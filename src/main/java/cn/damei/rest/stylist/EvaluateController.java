package cn.damei.rest.stylist;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import cn.damei.common.BaseController;
import cn.damei.dto.StatusDto;
import cn.damei.dto.page.PageTable;
import cn.damei.dto.page.Pagination;
import cn.damei.entity.stylist.Evaluate;
import cn.damei.entity.stylist.Stylist;
import cn.damei.service.stylist.EvaluateService;
import cn.damei.service.stylist.StylistService;
import cn.damei.shiro.ShiroUser;
import cn.damei.utils.MapUtils;
import cn.damei.utils.WebUtils;
import cn.damei.utils.excel.UploadExcel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/evaluate")
public class EvaluateController extends BaseController {
    @Autowired
    private EvaluateService evaluateService;
    @Autowired
    private StylistService stylistService;
    @RequestMapping(method = RequestMethod.GET)
    public Object findEvaluate(@RequestParam(required = false) String keyword,
                               @RequestParam(required = false) String evaluateMonth,
                               @RequestParam(required = false, defaultValue = "0") int offset,
                               @RequestParam(required = false, defaultValue = "20") int limit) {
        Map<String, Object> params = new HashMap<>();
        MapUtils.putNotNull(params, "keyword", keyword);
        MapUtils.putNotNull(params, "evaluateMonth", evaluateMonth);
        PageTable<Stylist> page = evaluateService.searchScrollPage(params, new Pagination(offset, limit));
        return StatusDto.buildSuccess(page);
    }
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Object insert(@RequestBody Evaluate evaluate) {
        ShiroUser logged = WebUtils.getLoggedUser();
        try {
            int insertNum = evaluateService.insert(evaluate, logged);
            if (insertNum > 0) {
                return StatusDto.buildSuccess("添加考核信息成功！");
            } else {
                return StatusDto.buildFailure("此月份考核信息已经存在！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return StatusDto.buildFailure("考核信息添加失败！" + e.getMessage());
        }
    }
    @RequestMapping(value = "/{id}/del", method = RequestMethod.GET)
    public Object delete(@PathVariable Long id) {
        int delNum = evaluateService.deleteById(id);
        if (delNum > 0) {
            return StatusDto.buildSuccess();
        }
        return StatusDto.buildFailure();
    }
    @RequestMapping(value = "/importFile", method = RequestMethod.POST)
    public Object importFile(@RequestParam("file") MultipartFile file, @RequestParam(value = "month") String month) {
        if (!file.isEmpty()) {
            List<Object> importEvaluateList = null;
            try {
                importEvaluateList = UploadExcel.readExcel(Evaluate.class, file.getInputStream(), "eavluate");
            } catch (Exception e) {
                return StatusDto.buildFailure("上传Excel失败," + e.getMessage());
            }
            if (CollectionUtils.isNotEmpty(importEvaluateList)) {
                ShiroUser logged = WebUtils.getLoggedUser();//登录用户
                List<Evaluate> insertEvaluateList = new ArrayList<Evaluate>();
                Map<String, String> stylistMap = anlayStylistToMap(stylistService.findAll());
                Map<String, String> paramMap = new HashMap<String, String>();
                paramMap.put("evaluateMonth", month);
                Map<String, String> evaluateList = anlayEvaluateToMap(evaluateService.getEvaluateByCondition(paramMap));
                importEvaluateList.forEach(importEvaluate -> {
                    Evaluate tmpEvaluate = (Evaluate) importEvaluate;
                    String stylistStr = stylistMap.get(tmpEvaluate.getJobNo() + "-" + tmpEvaluate.getMobile());
                    String evaluateStr = evaluateList.get(tmpEvaluate.getJobNo() + "-" + tmpEvaluate.getMobile());
                    if (stylistStr != null && evaluateStr == null) {
                        tmpEvaluate.setEvaluateMonth(month);
                        insertEvaluateList.add(tmpEvaluate);
                    }
                });
                if (CollectionUtils.isNotEmpty(insertEvaluateList)) {
                    for (Evaluate evaluate : insertEvaluateList) {
                        if (evaluate.getScore() == null || evaluate.getScore() <= 0 || evaluate.getScore() > 100) {
                            return StatusDto.buildFailure("工号为" + evaluate.getJobNo() + ",手机号为：" + evaluate.getMobile() + "的设计师得分不合法！");
                        }
                    }
                    int importNum = evaluateService.batchInsert(insertEvaluateList, logged);
                    return StatusDto.buildSuccess("导入成功,本次导入【" + importNum + "】条数据!");
                } else {
                    return StatusDto.buildFailure("无对应设计师数据或对应月份数据已经存在!");
                }
            } else {
                return StatusDto.buildFailure("导入的Excel无数据行!");
            }
        }
        return StatusDto.buildSuccess();
    }
    private Map<String, String> anlayStylistToMap(List<Stylist> stylistList) {
        Map<String, String> stylistMap = new HashMap<String, String>();
        if (CollectionUtils.isNotEmpty(stylistList)) {
            stylistList.forEach(stylist -> {
                String tmpKey = stylist.getJobNo() + "-" + stylist.getMobile();
                if (stylistMap.get(tmpKey) == null) {
                    stylistMap.put(tmpKey, stylist.getName());
                }
            });
        }
        return stylistMap;
    }
    private Map<String, String> anlayEvaluateToMap(List<Evaluate> evaluateList) {
        Map<String, String> evaluateMap = new HashMap<String, String>();
        if (CollectionUtils.isNotEmpty(evaluateList)) {
            evaluateList.forEach(evaluate -> {
                String tmpKey = evaluate.getJobNo() + "-" + evaluate.getMobile();
                if (evaluateMap.get(tmpKey) == null) {
                    evaluateMap.put(tmpKey, evaluate.getScore().toString());
                }
            });
        }
        return evaluateMap;
    }
}
