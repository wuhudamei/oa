package cn.damei.repository.upload;
import org.springframework.stereotype.Repository;
import cn.damei.service.upload.SequenceService.SequenceTable;
@Repository
public interface SequenceDao {
    public Integer getCurVal(SequenceTable seqTab);
    public void next(SequenceTable seqTab);
}
