package cn.damei.repository.stylist;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.stylist.Contract;
@Repository
public interface ContractDao extends CrudDao<Contract> {
    String USER_ID = "userId";
    String SEARCH_MONTH = "searchMonth";
    String STATUS = "status";
    String KEYWORD = "keyword";
}