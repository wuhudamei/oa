package cn.damei.service.oa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.damei.common.service.CrudService;
import cn.damei.entity.oa.ApplySequence;
import cn.damei.enumeration.WfApplyTypeEnum;
import cn.damei.repository.oa.ApplySequenceDao;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
@Service
public class ApplySequenceService extends CrudService<ApplySequenceDao,ApplySequence> {
    @Autowired
    ApplySequenceDao sequenceDao;
    private Lock lock = new ReentrantLock();
    private SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
    public  String getSequence( WfApplyTypeEnum applyType ){
        String result = "";
        try{
            lock.lock();
            ApplySequence applySequence = new ApplySequence();
            String applyDate = sdf.format( new Date() );
            applySequence.setApplyDate( applyDate );
            applySequence.setApplyType( applyType.name());
            Integer sequenceVal = sequenceDao.getSequence(applySequence);
            if( sequenceVal != null ){
                sequenceDao.updateSequence(applySequence);
                sequenceVal = sequenceVal + 1;
            }else{
                sequenceVal = 1;
                applySequence.setCurrentSequence(sequenceVal);
                sequenceDao.insert(applySequence);
            }
            result = applyDate.replaceAll("-","") + convertType( applyType.name() ) + accumulationNum(sequenceVal);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return result;
    }
    private String convertType(String  applyType){
        if( applyType.equals( WfApplyTypeEnum.BUSINESS.name() ) ){
            return "01";
        }else if( applyType.equals( WfApplyTypeEnum.LEAVE.name() ) ){
            return "02";
        }else if( applyType.equals( WfApplyTypeEnum.BUDGET.name() ) ){
            return "03";
        }else if( applyType.equals( WfApplyTypeEnum.EXPENSE.name() ) ){
            return "04";
        }else if( applyType.equals( WfApplyTypeEnum.PURCHASE.name() ) ){
            return "05";
        }else if( applyType.equals( WfApplyTypeEnum.SIGNATURE.name() ) ){
            return "06";
        }{
            return "00";
        }
    }
    private String accumulationNum(int s) {
        s = s == 1000 ? 1 : s;
        String reslut = s > 10 ? (s > 100 ? s + "" : "0" + s) : "00" + s;
        return reslut;
    }
}