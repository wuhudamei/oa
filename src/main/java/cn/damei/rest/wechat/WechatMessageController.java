package cn.damei.rest.wechat;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.google.common.collect.Maps;
import com.mysql.jdbc.Connection;
import cn.damei.dto.StatusDto;
import cn.damei.dto.page.PageTable;
import cn.damei.dto.page.Pagination;
import cn.damei.entity.wechat.WechatMessageLog;
import cn.damei.service.wechat.WechatMessageLogService;
import cn.damei.utils.MapUtils;
@RestController
@RequestMapping(value = "/wx/message")
public class WechatMessageController {
	@Resource
	private NamedParameterJdbcTemplate namedParameterJdbcTemplateOa;
	@Resource
	private WechatMessageLogService wechatMessageLogService;
	@Resource
	private DruidDataSource dataSource;
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Object list(@RequestParam(required = false) String keyword, @RequestParam(defaultValue = "0") int offset,
			@RequestParam(defaultValue = "20") int limit) {
		Map<String, Object> params = Maps.newHashMap();
		MapUtils.putNotNull(params, "keyword", keyword);
		PageTable page = this.wechatMessageLogService.searchScrollPage(params, new Pagination(offset, limit));
		return StatusDto.buildSuccess(page);
	}
	@RequestMapping(value = "/save", method = RequestMethod.GET)
	public void save() {
		WechatMessageLog wml = new WechatMessageLog();
		wml.setToUsers("11111");
		String sql = "insert into wechat_message_log(to_users) values(:toUsers)";
		int update = namedParameterJdbcTemplateOa.update(sql, new BeanPropertySqlParameterSource(wml));
		List<Map<String, Object>> queryForList = namedParameterJdbcTemplateOa
				.queryForList("select * from wechat_message_log", new MapSqlParameterSource());
		for (Map<String, Object> map : queryForList) {
			System.out.println(map);
		}
		try {
			Connection connection = (Connection) dataSource.getConnection();
			Statement stat = connection.createStatement();
			boolean execute = stat.execute("INSERT INTO wechat_message_log(to_users) VALUES('0000000000')");
			System.out.println("execute:\t" + execute);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(queryForList.size());
	}
}
