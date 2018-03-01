package cn.damei.repository.standBook;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.standBook.InstallBaseChangeStandBook;
import java.util.List;
@Repository
public interface InstallBaseChangeStandBookDao extends CrudDao<InstallBaseChangeStandBook> {
    List<InstallBaseChangeStandBook> findInstallBaseChange(@Param("orderno") String orderno);
}
