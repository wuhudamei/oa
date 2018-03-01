package cn.damei.rest.report;
import com.google.common.collect.Maps;
import cn.damei.Constants;
import cn.damei.common.BaseController;
import cn.damei.common.PropertyHolder;
import cn.damei.dto.StatusBootTableDto;
import cn.damei.dto.StatusDto;
import cn.damei.utils.HttpUtils;
import cn.damei.utils.JsonUtils;
import cn.damei.utils.SignUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/report/nissin")
public class ReportNissinController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(ReportNissinController.class);
    @RequestMapping(value = "/group", method = RequestMethod.GET)
    public Object groupReport( @RequestParam(value = "date") String date,
                               @RequestParam(value = "companyId",defaultValue = "98") String companyId,
                                @RequestParam(value = "isDep",required = false) Boolean isDep) {
        String defaultValue="98";
        if(isDep!=null&&isDep && defaultValue.equals(companyId)){
            List<Object> objects = new ArrayList<Object>();
            PageImpl page = new PageImpl(objects);
            return StatusBootTableDto.buildDataSuccessStatusDto(page);
        }else {
            String[] parameterArr = new String[]{"date=" + date, "companyId=" + companyId};
            String key = SignUtil.getKey(parameterArr, PropertyHolder.getSaleMd5());
            HashMap<String, String> params = Maps.newHashMap();
            params.put("key", key);
            params.put("date", date);
            params.put("companyId", companyId);
            String result = null;
            try {
                result = HttpUtils.post(PropertyHolder.getSaleUrl() + Constants.SALE_NISSIN_REPORT_URL, params);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("远程调用日清报表接口发生错误");
            }
            return getResult(result);
        }
    }
    @RequestMapping(value = "/depList", method = RequestMethod.GET)
    public Object depReport(@RequestParam(value = "companyId",defaultValue = "98") String companyId) {
        String[] parameterArr = new String[]{"companyId=" + companyId};
        String key = SignUtil.getKey(parameterArr, PropertyHolder.getSaleMd5());
        HashMap<String, String> params= Maps.newHashMap();
        params.put("key",key);
        params.put("companyId",companyId);
        String result=null;
        try {
            result = HttpUtils.post(PropertyHolder.getSaleUrl()+ Constants.SALE_DEP_URL,params);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("远程调用日清报表接口发生错误");
        }
        if(StringUtils.isNotBlank(result)){
            Map<String, Object> stringObjectMap = JsonUtils.fromJsonAsMap(result, String.class, Object.class);
            String code = (String)stringObjectMap.get("code");
            String successCode="1";
            if(code!=null&& successCode.equals(code)){
                Object obj = stringObjectMap.get("data");
                List<Map<String, Object>> map=new ArrayList<Map<String, Object>>();
                if(obj!=null&&obj instanceof List ){
                    map=(List<Map<String, Object>>)obj;
                }
                return StatusDto.buildSuccess(map);
            }else{
                return StatusDto.buildFailure("远程调用失败");
            }
        }else{
            return StatusDto.buildFailure("远程调用失败");
        }
    }
    private  Object getResult(String result){
        if(StringUtils.isNotBlank(result)){
            Map<String, Object> stringObjectMap = JsonUtils.fromJsonAsMap(result, String.class, Object.class);
            String code = (String)stringObjectMap.get("code");
            String successCode="1";
            if(code!=null&& successCode.equals(code)){
                Object obj = stringObjectMap.get("data");
                List<Map<String, Object>> map=new ArrayList<Map<String, Object>>();
                if(obj!=null&&obj instanceof List ){
                    map=(List<Map<String, Object>>)obj;
                }
                PageImpl page = new PageImpl(map);
                return StatusBootTableDto.buildDataSuccessStatusDto(page);
            }else{
                return StatusDto.buildFailure("远程调用失败");
            }
        }else{
            return StatusDto.buildFailure("远程调用失败");
        }
    }
  }