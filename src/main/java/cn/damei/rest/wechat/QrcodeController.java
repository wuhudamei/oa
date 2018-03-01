package cn.damei.rest.wechat;
import com.rocoinfo.weixin.api.OAuthApi;
import com.rocoinfo.weixin.api.QrcodeApi;
import cn.damei.Constants;
import cn.damei.common.BaseController;
import cn.damei.common.PropertyHolder;
import cn.damei.dto.StatusDto;
import cn.damei.dto.page.PageTable;
import cn.damei.dto.page.Pagination;
import cn.damei.entity.stylist.Stylist;
import cn.damei.entity.wechat.Qrcode;
import cn.damei.enumeration.wechat.QrcodeType;
import cn.damei.service.wechat.QrcodeService;
import cn.damei.utils.MapUtils;
import cn.damei.utils.QrcodeUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping(value = "/wx/qrcode")
public class QrcodeController extends BaseController {
	@Autowired
	private QrcodeService qrcodeService;
	@RequestMapping(method = RequestMethod.POST)
	public Object create(@RequestBody Qrcode qrcode) {
		if (qrcode.getType() == null){
			return StatusDto.buildFailure("创建的二维码类型不能为空");
		}
		if (QrcodeType.QR_SCENE.equals(qrcode.getType()) && qrcode.getSceneId() == null){
			return StatusDto.buildFailure("创建临时二维码时，整型场景值id不能为空");
		}
		if (QrcodeType.QR_LIMIT_SCENE.equals(qrcode.getType()) && qrcode.getSceneId() == null){
			return StatusDto.buildFailure("创建永久整型二维码时，整型场景值id不能为空");
		}
		if (QrcodeType.QR_LIMIT_STR_SCENE.equals(qrcode.getType()) && qrcode.getSceneStr() == null){
			return StatusDto.buildFailure("创建永久字符串型二维码时，字符串型场景值id不能为空");
		}
		Map<String,Object> retMessageMap = this.qrcodeService.create(qrcode);
		if ((Long)retMessageMap.get(Constants.QRCODE_OPERATION_CODE) == 1) {
			return StatusDto.buildSuccess(retMessageMap.get(Constants.QRCODE_OPERATION_MESSAGE));
		}
		return StatusDto.buildFailure(retMessageMap.get(Constants.QRCODE_OPERATION_CODE) + "," +retMessageMap.get(Constants.QRCODE_OPERATION_MESSAGE));
	}
	@RequestMapping(value = "findQrCodeByCondition", method = RequestMethod.GET)
	public Object findQrCodeByCondition(@RequestParam(required = false) String keyword,
			@RequestParam(required = false, defaultValue = "0") int offset,
			@RequestParam(required = false, defaultValue = "20") int limit) {
		Map<String, Object> params = new HashMap<>();
		MapUtils.putNotNull(params, "keyword", keyword);
		PageTable<Qrcode> qrcodeList = qrcodeService.searchScrollPage(params, new Pagination(offset, limit));
		return StatusDto.buildSuccess(qrcodeList);
	}
	@Deprecated
	@RequestMapping(value = "/{type}")
	public Object get(@PathVariable QrcodeType type, Long sceneId, String sceneStr) {
		Qrcode qrcode = null;
		if (QrcodeType.QR_SCENE.equals(type) && sceneId != null) {
			qrcode = this.qrcodeService.getTemporary(sceneId);
		}
		if (QrcodeType.QR_LIMIT_SCENE.equals(type) && sceneId != null) {
			qrcode = this.qrcodeService.getPermanentByLong(sceneId);
		}
		if (QrcodeType.QR_LIMIT_STR_SCENE.equals(type) && StringUtils.isNotBlank(sceneStr)) {
			qrcode = this.qrcodeService.getPermanentByStr(sceneStr);
		}
		if (qrcode == null || StringUtils.isBlank(qrcode.getTicket())) {
			return StatusDto.buildFailure("查询不到指定的二维码");
		}
		return StatusDto.buildSuccess("Success", QrcodeApi.showQrcodeUrl(qrcode.getTicket()));
	}
	@RequestMapping(method = RequestMethod.GET)
	public Object get(String uuid, HttpServletResponse response) {
		OutputStream os = null;
		// 输出为jpeg图片
		response.setContentType("image/jpeg");
		// 禁止浏览器缓存
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setIntHeader("Expires", -1);
		try {
			os = response.getOutputStream();
			String redirectUrl = PropertyHolder.getBaseurl() + "/wx/scan";
			String content = OAuthApi.buildUrl(redirectUrl, uuid, OAuthApi.Type.BASE);
			QrcodeUtils.generateQRCode(content, os);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(os);
		}
		return null;
	}
}
