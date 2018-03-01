package cn.damei.service.wechat.tag;
import com.google.common.collect.Lists;
import com.rocoinfo.weixin.api.TagApi;
import com.rocoinfo.weixin.model.ApiResult;
import cn.damei.common.service.CrudService;
import cn.damei.dto.StatusDto;
import cn.damei.entity.wechat.tag.Tag;
import cn.damei.entity.wechat.tag.TagEmployee;
import cn.damei.repository.wechat.tag.TagDao;
import cn.damei.rest.wechat.MessageController;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;
@Service
public class TagService extends CrudService<TagDao, Tag> {
	private static Logger logger = LoggerFactory.getLogger(TagService.class);//日志
    @Autowired
    private TagEmployeeService tagEmployeeService;
    @Transactional
    public boolean create(Tag wechatTag) {
        if (wechatTag == null || StringUtils.isEmpty(wechatTag.getName())) {
            return false;
        }
        Long oid = this.remoteCreate(wechatTag.getName());
        if (oid != null) {
            wechatTag.setOid(oid);
            return this.insert(wechatTag) > 0;
        }
        return false;
    }
    @Transactional
    public boolean edit(Tag wechatTag) {
        if (wechatTag != null) {
            boolean b = this.remoteUpdate(wechatTag.getOid(), wechatTag.getName());
            if (b) {
                return this.update(wechatTag) > 0;
            }
        }
        return false;
    }
    @Transactional
    public boolean delete(Long id) {
        Long oid = Optional.ofNullable(this.getById(id)).map(Tag::getOid).orElse(null);
        boolean b = this.remoteDelete(oid);
        if (b) {
            return this.deleteById(id) > 0;
        }
        return false;
    }
    public boolean untag(Long id) {
        if (id == null) {
            return false;
        }
        TagEmployee tagEmployee = this.tagEmployeeService.getById(id);
        if (tagEmployee == null) {
            return false;
        }
        Long tagOid = Optional.ofNullable(tagEmployee).map(TagEmployee::getTag).map(Tag::getOid).orElse(null);
        boolean b = this.remoteUntag(tagOid, tagEmployee.getOpenid());
        if (b) {
            return this.tagEmployeeService.deleteById(id) > 0;
        }
        return false;
    }
    public StatusDto tagEmployees(Long tagId, List<Long> empIds) {
        if (tagId == null) {
            return StatusDto.buildFailure("标签id为null");
        }
        Tag tag = this.getById(tagId);
        if (tag == null) {
            return StatusDto.buildFailure("查询不到标签信息");
        }
        List<TagEmployee> allTagged = this.tagEmployeeService.buildPojoFromEmpIds(empIds, tag);
        List<TagEmployee> hasTagged = this.tagEmployeeService.findAllByTagId(tagId);
        StatusDto cancelTagRes = this.processCancelTagEmployee(allTagged, hasTagged, tag);
        if (!cancelTagRes.isSuccess()) {
            return cancelTagRes;
        }
        StatusDto newTagRes = this.processNewTagEmployee(allTagged, hasTagged, tag);
        if (!newTagRes.isSuccess()) {
            return newTagRes;
        }
        return StatusDto.buildSuccess();
    }
    public Tag getTagByOid(Long oid){
    	return this.entityDao.getTagByOid(oid);
    }
    private StatusDto processCancelTagEmployee(List<TagEmployee> allTagged, List<TagEmployee> hasTagged, Tag tag) {
        List<String> allOpenids = allTagged.stream().map(TagEmployee::getOpenid).collect(Collectors.toList());
        List<TagEmployee> needCancel = hasTagged.stream()
                .filter((o) -> !allOpenids.contains(o.getOpenid()))
                .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(needCancel)) {
            List<String> cancelOpenids = needCancel.stream().map(TagEmployee::getOpenid).collect(Collectors.toList());
            ApiResult res = TagApi.batchUntag(tag.getOid(), cancelOpenids);
            if (res.isSuccess()) {
                this.tagEmployeeService.deleteByOpenids(cancelOpenids);
            } else {
                return StatusDto.buildFailure(res.getErrorMsg());
            }
        }
        return StatusDto.buildSuccess();
    }
    private StatusDto processNewTagEmployee(List<TagEmployee> allTagged, List<TagEmployee> hasTagged, Tag tag) {
        List<String> taggedOpenids = hasTagged.stream().map(TagEmployee::getOpenid).collect(Collectors.toList());
        List<TagEmployee> needTag = allTagged.stream()
                .filter((o) -> !taggedOpenids.contains(o.getOpenid()))
                .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(needTag)) {
            List<String> needTagOpenids = needTag.stream()
                    .map(TagEmployee::getOpenid)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            ApiResult result = TagApi.batchTag(tag.getOid(), needTagOpenids);
            if (result.isSuccess()) {
                this.tagEmployeeService.batchInsert(needTag);
            } else {
                return StatusDto.buildFailure(result.getErrorMsg());
            }
        }
        return StatusDto.buildSuccess();
    }
    private Long remoteCreate(String name) {
        if (StringUtils.isNotEmpty(name)) {
            ApiResult result = TagApi.create(name);
            if (result.isSuccess()) {
                Object tag = Optional.ofNullable(result.fromJsonAsMap()).map((t) -> t.get("tag")).orElse(new HashMap<String, Object>());
                if (tag instanceof Map) {
                    Map<String, Object> tagMap = (Map<String, Object>) tag;
                    return Optional.ofNullable(tagMap.get("id")).map((t) -> (Integer) t).map((t) -> (t.longValue())).orElse(null);
                }
            }
        }
        return null;
    }
    private boolean remoteUpdate(Long oid, String name) {
        if (StringUtils.isNotEmpty(name) && oid != null) {
            return TagApi.edit(oid, name).isSuccess();
        }
        return false;
    }
    private boolean remoteDelete(Long oid) {
        if (oid != null) {
            return TagApi.delete(oid).isSuccess();
        }
        return false;
    }
    private boolean remoteUntag(Long oid, String openid) {
        if (oid != null && StringUtils.isNoneBlank(openid)) {
            return TagApi.batchUntag(oid, Lists.newArrayList(openid)).isSuccess();
        }
        return false;
    }
}