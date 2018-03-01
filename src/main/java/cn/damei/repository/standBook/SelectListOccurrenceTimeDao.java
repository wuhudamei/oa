package cn.damei.repository.standBook;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.standBook.DesignTime;
@Repository
public interface SelectListOccurrenceTimeDao extends CrudDao<DesignTime> {
    DesignTime findOccurrenceTime(String orderno);
}
