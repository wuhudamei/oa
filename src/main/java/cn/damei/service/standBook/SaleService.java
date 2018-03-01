package cn.damei.service.standBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.damei.common.service.CrudService;
import cn.damei.datasource.DynamicDataSourceHolder;
import cn.damei.entity.employee.Employee;
import cn.damei.entity.standBook.Sale;
import cn.damei.repository.employee.EmployeeDao;
import cn.damei.repository.standBook.SaleDao;
import java.util.*;
@Service
public class SaleService extends CrudService<SaleDao, Sale> {
    @Autowired
    private SaleDao saleDao;
    @Autowired
    private EmployeeDao employeeDao;
    public Sale findOrderDetail(String orderno){
        Sale sale = this.saleBuild(orderno);
        String orgCode = "";
        List<Employee> employeeMobile = employeeDao.findOrgCodeByMobil(sale.getServiceMobile());
        if(employeeMobile != null && employeeMobile.size() > 0){
            for(int i = 0 ;i < employeeMobile.size(); i++){
                orgCode = employeeMobile.get(0).getOrgCode();
            }
        }else{
            List<Employee> employeeName = employeeDao.findOrgCodeByName(sale.getServiceName());
            for(int i = 0 ;i < employeeName.size(); i++){
                orgCode = employeeName.get(0).getOrgCode();
            }
        }
        sale.setOrgCode(orgCode);
        return sale;
    }
    private Sale saleBuild(String orderno)  {
        DynamicDataSourceHolder.setDataSource("dataSourceMdni");
        try {
            Sale saleOrd = this.saleDao.findOrderCustomerByOrderNo(orderno);
            Sale salePay = this.saleDao.findPaymentByOrderNo(orderno);
            Sale salePlace = this.saleDao.findPlaceByOrderNo(orderno);
            saleOrd.setImprestAmount(salePay.getImprestAmount());
            saleOrd.setIsImprestAmount(salePay.getIsImprestAmount());
            saleOrd.setBudgetAmount(salePlace.getBudgetAmount());
            return saleOrd;
        }catch(Exception e){
            return null;
        }finally {
            DynamicDataSourceHolder.clearDataSource();//清除此数据源
        }
    }
}