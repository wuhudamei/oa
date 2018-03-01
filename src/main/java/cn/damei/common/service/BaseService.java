package cn.damei.common.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@SuppressWarnings("all")
public abstract class BaseService<T> implements IBaseService<T> {
	protected Logger logger = LoggerFactory.getLogger(getClass());
}