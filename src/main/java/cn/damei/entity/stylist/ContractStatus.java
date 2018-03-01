package cn.damei.entity.stylist;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.damei.entity.IdEntity;
import cn.damei.enumeration.stylist.Status;
import java.io.Serializable;
import java.util.Date;
public class ContractStatus extends IdEntity implements Serializable {
    private static final long serialVersionUID = 335080832433224338L;
    private Long contractId;
    private Status status;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+08:00")
    private Date statusTime;
    public ContractStatus(){}
    public ContractStatus(Long contractId, Status status, Date statusTime) {
        this.contractId = contractId;
        this.status = status;
        this.statusTime = statusTime;
    }
    public Long getContractId() {
        return contractId;
    }
    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }
    public Date getStatusTime() {
        return statusTime;
    }
    public void setStatusTime(Date statusTime) {
        this.statusTime = statusTime;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
}