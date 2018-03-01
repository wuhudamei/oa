package cn.damei.service.standBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.damei.common.service.CrudService;
import cn.damei.datasource.DynamicDataSourceHolder;
import cn.damei.entity.standBook.AreaLevelInfo;
import cn.damei.repository.standBook.QualityCheckStandBookDao;
import cn.damei.utils.JsonUtils;
import java.util.List;
import java.util.Map;
@Service
public class QualityCheckStandBookService extends CrudService<QualityCheckStandBookDao, AreaLevelInfo> {
    @Autowired
    private QualityCheckStandBookDao qualityCheckStandBookDao;
    public List<AreaLevelInfo> findAreaLevelInfoByOrderNo(String plan,String orderNumber) {
        DynamicDataSourceHolder.setDataSource("dataSourceMdniXC");
        List<AreaLevelInfo> areaLevelInfoList = this.qualityCheckStandBookDao.findAreaLevelInfoByOrderNo(orderNumber);
        try {
            Map<String, Object> areaLevelInfoMap = JsonUtils.fromJsonAsMap(plan, String.class, Object.class);
            if("1".equals(areaLevelInfoMap.get("code"))){
                Map<String, Object> orderFloorMap = (Map<String, Object>) areaLevelInfoMap.get("orderFloor");
                if(!orderFloorMap.isEmpty() && areaLevelInfoList != null && areaLevelInfoList.size()>0){
                    for(AreaLevelInfo areaLevelInfo:areaLevelInfoList){
                        areaLevelInfo.setFloorSettleArea(Double.parseDouble(orderFloorMap.get("floorSettleArea").toString()));
                        areaLevelInfo.setFloorTileBudgetArea(Double.parseDouble(orderFloorMap.get("floorTileBudgetArea").toString()));
                        areaLevelInfo.setFloorTileSettleArea(Double.parseDouble(orderFloorMap.get("floorTileSettleArea").toString()));
                    }
                }
            }
            return areaLevelInfoList;
        }catch(Exception e){
            e.printStackTrace();
            return areaLevelInfoList;
        }finally {
            DynamicDataSourceHolder.clearDataSource();//清除此数据源
        }
    }
}