package cn.damei.service.upload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.damei.repository.upload.SequenceDao;
@Service
public class SequenceService {
    @Autowired
    private SequenceDao sequenceDao;
    public Integer getNextVal(SequenceTable seqTab) {
        sequenceDao.next(seqTab);
        return sequenceDao.getCurVal(seqTab);
    }
    public enum SequenceTable {
        UPLOAD, DICT_PLAN(4), ADV_TYPE(4), CENT_RULE(4), INSTORE_CODE(6), COMPANY_CODE(6);
        private int fixWidth;
        SequenceTable() {
        }
        SequenceTable(int width) {
            this.fixWidth = width;
        }
        public int getFixWidth() {
            return this.fixWidth;
        }
    }
}
