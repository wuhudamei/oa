package service;
import com.rocoinfo.weixin.api.TagApi;
import com.rocoinfo.weixin.model.ApiResult;
import org.junit.Test;
public class TagServiceTest extends SpringTestCase {
    @Test
    public void testGetAllTags() {
        ApiResult result = TagApi.getAll();
        System.out.println(result.getRawJson());
    }
}
