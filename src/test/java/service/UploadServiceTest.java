package service;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import cn.damei.service.upload.UploadService;
public class UploadServiceTest extends SpringTestCase {
    @Autowired
    private UploadService uploadService;
    @Test
    public void testGetRealPath() {
        String path = "/static-content/contract/2017/04/18/34.pdf";
        System.out.println(uploadService.getRelatePath(path));
    }
    @Test
    public void testDeleteFile() {
        String path = "/static-content/tmp/contract/2017/04/18/34.pdf";
        String relatePath = this.uploadService.getRelatePath(path);
        System.out.println(this.uploadService.delete(relatePath));
    }
}
