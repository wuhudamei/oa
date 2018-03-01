package cn.damei.rest;
import com.google.common.collect.Maps;
import cn.damei.common.service.ServiceException;
import cn.damei.dto.StatusDto;
import cn.damei.enumeration.UploadCategory;
import cn.damei.service.upload.UploadService;
import cn.damei.utils.MapUtils;
import cn.damei.utils.WebUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import java.util.Map;
@RestController
@RequestMapping(value = "/api/upload")
public class UploadController {
    @Autowired
    private UploadService uploadService;
    @RequestMapping(method = RequestMethod.POST)
    public Object upload(@RequestParam(value = "file") CommonsMultipartFile file, @RequestParam UploadCategory category) {
        try {
            String saveTmpPath = uploadService.upload(file, category);
            Map<String, String> data = Maps.newHashMap();
            data.put("fileName", file.getOriginalFilename());
            data.put("path", WebUtils.getUploadFilePath(saveTmpPath));
            data.put("fullPath", WebUtils.getFullUploadFilePath(saveTmpPath));
            return StatusDto.buildSuccess(data);
        } catch (Exception e) {
            e.printStackTrace();
            String msg = "上传文件失败";
            if (e instanceof ServiceException) {
                msg = e.getMessage();
            }
            return StatusDto.buildFailure(msg);
        }
    }
    @RequestMapping(method = RequestMethod.DELETE)
    public Object delete(@RequestParam String path) {
        if (StringUtils.isEmpty(path))
            return StatusDto.buildFailure("文件路径不能为空！");
        String relatePath = this.uploadService.getRelatePath(path);
        if (this.uploadService.delete(relatePath))
            return StatusDto.buildSuccess();
        return StatusDto.buildFailure("删除失败");
    }
}
