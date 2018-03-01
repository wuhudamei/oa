package cn.damei.service.process;
import static java.util.stream.Collectors.groupingBy;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import cn.damei.common.service.CrudService;
import cn.damei.dto.process.ProcessEntityTreeDto;
import cn.damei.entity.oa.WfProcessParam;
import cn.damei.entity.process.ProcessEntity;
import cn.damei.enumeration.WfNatureEnum;
import cn.damei.repository.process.ProcessDao;
import cn.damei.service.oa.WfProcessService;
import net.sf.jsqlparser.statement.select.Join;
@SuppressWarnings("all")
@Service
public class ProcessService extends CrudService<ProcessDao, ProcessEntity> {
	@Autowired
	private ProcessDao processDao;
	@Autowired
	private WfProcessService wfProcessService;
	public boolean checkOrg(ProcessEntity permission) {
		String wfType = permission.getWfType().name();
		String wfNature = permission.getWfNature().name();
		String applyOrg = permission.getApplyOrg();
		String[] orgs = applyOrg.substring(1, applyOrg.length() - 1).split(",");
		for (String org : orgs) {
			if (this.entityDao.checkOrgRepeat(permission.getWfType().name(), permission.getWfNature().name(),
					permission.getId(), "," + org + ",") > 0) {
				return true;
			}
		}
		return false;
	}
	public ProcessEntityTreeDto createOrUpdate(ProcessEntity processEntity) {
		if (processEntity != null) {
			// 流程类型 (行政类,人事类等,枚举)
			if (processEntity.getWfType() == null) {
				processEntity.setWfType(null);
			}
			// 审批类型 (会签,一般)
			if (processEntity.getApprovalType() == null) {
				processEntity.setApprovalType(null);
			}
			if (processEntity.getId() != null) {
				this.entityDao.update(processEntity);
			} else {
				this.entityDao.insert(processEntity);
				// 查出父的全路径和当前拼接,得到当前的全路径
				ProcessEntity parentNode = this.entityDao.getById(processEntity.getPid());
				String allPath = "";
				if (null == parentNode.getAllPath()) {
					allPath = "/" + processEntity.getId();
				} else {
					allPath = parentNode.getAllPath() + "/" + processEntity.getId();
				}
				processEntity.setAllPath(allPath);
				this.entityDao.updateAllPathById(allPath, processEntity.getId());
			}
			return buildProcessTree(processEntity, Boolean.FALSE);
		}
		return null;
	}
	public ProcessEntityTreeDto findProcessTree() {
		List<ProcessEntity> processList = this.entityDao.findAll();
		if (CollectionUtils.isNotEmpty(processList)) {
			Map<Long, List<ProcessEntity>> partProcessMap = processList.stream()
					.collect(groupingBy(ProcessEntity::getPid));
			List<ProcessEntity> roots = partProcessMap.get(0L);
			// 保证根节点有且仅有一个
			if (CollectionUtils.isNotEmpty(roots) && roots.size() == 1) {
				// 将ProcessEntity对象构建成ProcessEntityTree对象
				ProcessEntityTreeDto rootTree = buildProcessTree(roots.get(0), Boolean.TRUE);
				buildChildren(partProcessMap, rootTree);
				return rootTree;
			}
		}
		return null;
	}
	public ProcessEntityTreeDto findProcessTreeByParentId(Long pId, String wfNature) {
		List<ProcessEntity> processList = this.entityDao.findAll();
		if (CollectionUtils.isNotEmpty(processList)) {
			Map<Long, List<ProcessEntity>> partProcessMap = processList.stream()
					.collect(groupingBy(ProcessEntity::getPid));
			List<ProcessEntity> roots = partProcessMap.get(pId);
			if (CollectionUtils.isNotEmpty(roots)) {
				ProcessEntity processEntity = roots.size() == 1 ? roots.get(0)
						: (wfNature.equals(roots.get(0).getWfNature().name()) ? roots.get(0) : roots.get(1));
				// 将ProcessEntity对象构建成ProcessEntityTree对象
				ProcessEntityTreeDto rootTree = buildProcessTree(processEntity, Boolean.TRUE);
				buildChildren(partProcessMap, rootTree);
				return rootTree;
			}
		}
		return null;
	}
	public int findWfByTypeAndOrg(String type, String wfNature, Long org) {
		return this.entityDao.findWfByTypeAndOrg(type, wfNature, org);
	}
	private ProcessEntityTreeDto buildProcessTree(ProcessEntity processEntity, Boolean opened) {
		ProcessEntityTreeDto processTreeDto = new ProcessEntityTreeDto();
		if (processEntity != null) {
			processTreeDto.setId(processEntity.getId()).setPid(processEntity.getPid()).setSort(processEntity.getSeq())
					.setText(processEntity.getNodeTitle()).setParentWfType(processEntity.getWfType())
					.setParentWfNature(processEntity.getWfNature()).setOpened(opened);
		}
		return processTreeDto;
	}
	private void buildChildren(Map<Long, List<ProcessEntity>> partProcessMap, ProcessEntityTreeDto processTreeDto) {
		List<ProcessEntity> processList = partProcessMap.get(processTreeDto.getId());
		if (CollectionUtils.isNotEmpty(processList)) {
			for (ProcessEntity processEntity : processList) {
				ProcessEntityTreeDto subProcessTreeDto = buildProcessTree(processEntity, Boolean.FALSE);
				buildChildren(partProcessMap, subProcessTreeDto);
				processTreeDto.pushChildren(subProcessTreeDto);
			}
		}
	}
	public List<ProcessEntity> query(WfProcessParam wfProcessParam, String type) {
		List<ProcessEntity> listapprovalTemplate = Lists.newArrayList();
		Map<String, Object> paramMap = Maps.newHashMap();
		paramMap.put("wfType", wfProcessParam.getApplyType());
		paramMap.put("wfNature", type);
		paramMap.put("applyOrg", wfProcessParam.getOrgId());
		ProcessEntity processEntity = processDao.findTemplateAllPath(paramMap);
		if (null == processEntity) {
			return listapprovalTemplate;
		}
		String allPath = processEntity.getAllPath();
		if (StringUtils.isEmpty(allPath)) {
			return listapprovalTemplate;
		} else {
			List<String> splitToList = Splitter.on("/").splitToList(allPath.substring(1));
			// 只有根节点
			if (splitToList.size() == 1) {
				return listapprovalTemplate;
			}
			List<String> subList = splitToList.subList(1, splitToList.size());
			if (subList.size() == 0) {
				return listapprovalTemplate;
			}
			String ids = Joiner.on(",").join(subList);
			// String rootNode = subList.get(0);
			paramMap.put("ids", ids);
			// 找到目标树全路径
			List<ProcessEntity> processEntityList = processDao.findTemplateByConditionByDayOrMoney(paramMap);
			// 执行侧也按金额和天确定
			return wfProcessService.findTargetWfTreeByApprovalTemplate(wfProcessParam, processEntityList);
			//
			// if (type.endsWith(WfNatureEnum.APPROVAL.name())) {
			//
			// } else if (type.endsWith(WfNatureEnum.EXECUTE.name())) {
			// return processEntityList;
			// } else {
			// return listapprovalTemplate;
			// }
		}
	}
	public String disposeCcUser(List<ProcessEntity> approvalList) {
		String ccUser = "";
		Set<String> ccUserSet = Sets.newHashSet();
		for (ProcessEntity processEntity : approvalList) {
			String ccUserProcess = processEntity.getCcUser();
			if (StringUtils.isEmpty(ccUserProcess)) {
				continue;
			}
			List<String> splitToList = Splitter.on(",").splitToList(ccUserProcess.toString());
			ccUserSet.addAll(splitToList);
		}
		// 处理抄送人
		ccUser = Joiner.on(",").skipNulls().join(ccUserSet);
		if (StringUtils.isNotEmpty(ccUser)) {
			ccUser = "," + ccUser;
		}
		return ccUser;
	}
	public List<ProcessEntity> disposeApprovalAndExecute(WfProcessParam wfProcessParam) {
		List<ProcessEntity> approvalList = query(wfProcessParam, WfNatureEnum.APPROVAL.name());
		List<ProcessEntity> executeList = query(wfProcessParam, WfNatureEnum.EXECUTE.name());
		List<ProcessEntity> allList = Lists.newArrayList();
		// 合并审批和执行
		allList.addAll(approvalList);
		allList.addAll(executeList);
		return allList;
	}
}