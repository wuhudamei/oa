package cn.damei.datasource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
public class DynamicDataSource extends AbstractRoutingDataSource{
	private static Logger logger = LoggerFactory.getLogger(DynamicDataSource.class);//日志
	@Override
	protected Object determineCurrentLookupKey() {
		String dataSource = DynamicDataSourceHolder.getDataSource();
		logger.info("当前数据源 = " + dataSource);
	  return dataSource;
	}
}
