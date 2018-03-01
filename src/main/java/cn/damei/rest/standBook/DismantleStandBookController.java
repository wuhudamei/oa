package cn.damei.rest.standBook;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import cn.damei.dto.StatusDto;
import cn.damei.entity.standBook.DismantleStandBook;
import cn.damei.service.standBook.DismantleStandBookService;
@RestController
@RequestMapping("/api/dismantleStandBook")
public class DismantleStandBookController {
    @Autowired
    private DismantleStandBookService dismantleStandBookService;
    @RequestMapping("/findDismantle")
    public Object findDismantle(@RequestParam String orderno){
        List<DismantleStandBook> additemStandBookList = this.dismantleStandBookService.findDismantle(orderno);
        return StatusDto.buildSuccess(additemStandBookList);
    }
}
