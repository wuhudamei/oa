package cn.damei.entity.completionSiteData;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.damei.entity.IdEntity;
import java.math.BigDecimal;
import java.util.Date;
public class ContractInformation extends IdEntity {
    private String projectCode;
    private String customerName;
    private String customerPhone;
    private String projectAddress;
    private String designerName;
    private String designerPhone;
    private String managerName;
    private String managerPhone;
    private String inspectorName;
    private String inspectorPhone;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date contractStartDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date contractFinishDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date actualStartDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date actualFinishDate;
    private BigDecimal contractTotalAmount;
    private BigDecimal alterationTotalAmount;
    private BigDecimal changeTotalAmount;
    private BigDecimal paidTotalAmount;
    private String storeName;
    private String projectMode;
    private String dataSources;
    private Integer createUserId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
    private Integer comSiteCount;
    private Integer januaryCount;
    private Integer februaryCount;
    private Integer marchCount;
    private Integer aprilCount;
    private Integer mayCount;
    private Integer juneCount;
    private Integer julyCount;
    private Integer augustCount;
    private Integer septemberCount;
    private Integer octoberCount;
    private Integer novemberCount;
    private Integer decemberCount;
    public Integer getJanuaryCount() {
        return januaryCount;
    }
    public void setJanuaryCount(Integer januaryCount) {
        this.januaryCount = januaryCount;
    }
    public Integer getFebruaryCount() {
        return februaryCount;
    }
    public void setFebruaryCount(Integer februaryCount) {
        this.februaryCount = februaryCount;
    }
    public Integer getMarchCount() {
        return marchCount;
    }
    public void setMarchCount(Integer marchCount) {
        this.marchCount = marchCount;
    }
    public Integer getAprilCount() {
        return aprilCount;
    }
    public void setAprilCount(Integer aprilCount) {
        this.aprilCount = aprilCount;
    }
    public Integer getMayCount() {
        return mayCount;
    }
    public void setMayCount(Integer mayCount) {
        this.mayCount = mayCount;
    }
    public Integer getJuneCount() {
        return juneCount;
    }
    public void setJuneCount(Integer juneCount) {
        this.juneCount = juneCount;
    }
    public Integer getJulyCount() {
        return julyCount;
    }
    public void setJulyCount(Integer julyCount) {
        this.julyCount = julyCount;
    }
    public Integer getAugustCount() {
        return augustCount;
    }
    public void setAugustCount(Integer augustCount) {
        this.augustCount = augustCount;
    }
    public Integer getSeptemberCount() {
        return septemberCount;
    }
    public void setSeptemberCount(Integer septemberCount) {
        this.septemberCount = septemberCount;
    }
    public Integer getOctoberCount() {
        return octoberCount;
    }
    public void setOctoberCount(Integer octoberCount) {
        this.octoberCount = octoberCount;
    }
    public Integer getNovemberCount() {
        return novemberCount;
    }
    public void setNovemberCount(Integer novemberCount) {
        this.novemberCount = novemberCount;
    }
    public Integer getDecemberCount() {
        return decemberCount;
    }
    public void setDecemberCount(Integer decemberCount) {
        this.decemberCount = decemberCount;
    }
    public Integer getComSiteCount() {
        return comSiteCount;
    }
    public void setComSiteCount(Integer comSiteCount) {
        this.comSiteCount = comSiteCount;
    }
    public String getProjectCode() {
        return projectCode;
    }
    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getCustomerPhone() {
        return customerPhone;
    }
    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }
    public String getProjectAddress() {
        return projectAddress;
    }
    public void setProjectAddress(String projectAddress) {
        this.projectAddress = projectAddress;
    }
    public String getDesignerName() {
        return designerName;
    }
    public void setDesignerName(String designerName) {
        this.designerName = designerName;
    }
    public String getDesignerPhone() {
        return designerPhone;
    }
    public void setDesignerPhone(String designerPhone) {
        this.designerPhone = designerPhone;
    }
    public String getManagerName() {
        return managerName;
    }
    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }
    public String getManagerPhone() {
        return managerPhone;
    }
    public void setManagerPhone(String managerPhone) {
        this.managerPhone = managerPhone;
    }
    public String getInspectorName() {
        return inspectorName;
    }
    public void setInspectorName(String inspectorName) {
        this.inspectorName = inspectorName;
    }
    public String getInspectorPhone() {
        return inspectorPhone;
    }
    public void setInspectorPhone(String inspectorPhone) {
        this.inspectorPhone = inspectorPhone;
    }
    public Date getContractStartDate() {
        return contractStartDate;
    }
    public void setContractStartDate(Date contractStartDate) {
        this.contractStartDate = contractStartDate;
    }
    public Date getContractFinishDate() {
        return contractFinishDate;
    }
    public void setContractFinishDate(Date contractFinishDate) {
        this.contractFinishDate = contractFinishDate;
    }
    public Date getActualStartDate() {
        return actualStartDate;
    }
    public void setActualStartDate(Date actualStartDate) {
        this.actualStartDate = actualStartDate;
    }
    public Date getActualFinishDate() {
        return actualFinishDate;
    }
    public void setActualFinishDate(Date actualFinishDate) {
        this.actualFinishDate = actualFinishDate;
    }
    public BigDecimal getContractTotalAmount() {
        return contractTotalAmount;
    }
    public void setContractTotalAmount(BigDecimal contractTotalAmount) {
        this.contractTotalAmount = contractTotalAmount;
    }
    public BigDecimal getAlterationTotalAmount() {
        return alterationTotalAmount;
    }
    public void setAlterationTotalAmount(BigDecimal alterationTotalAmount) {
        this.alterationTotalAmount = alterationTotalAmount;
    }
    public BigDecimal getChangeTotalAmount() {
        return changeTotalAmount;
    }
    public void setChangeTotalAmount(BigDecimal changeTotalAmount) {
        this.changeTotalAmount = changeTotalAmount;
    }
    public BigDecimal getPaidTotalAmount() {
        return paidTotalAmount;
    }
    public void setPaidTotalAmount(BigDecimal paidTotalAmount) {
        this.paidTotalAmount = paidTotalAmount;
    }
    public String getStoreName() {
        return storeName;
    }
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
    public String getProjectMode() {
        return projectMode;
    }
    public void setProjectMode(String projectMode) {
        this.projectMode = projectMode;
    }
    public String getDataSources() {
        return dataSources;
    }
    public void setDataSources(String dataSources) {
        this.dataSources = dataSources;
    }
    public Integer getCreateUserId() {
        return createUserId;
    }
    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
