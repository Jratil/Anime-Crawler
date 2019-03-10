package top.ratil.animecrawler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.ratil.animecrawler.api.Constant.ConstantInfo;
import top.ratil.animecrawler.api.ResultRest;
import top.ratil.animecrawler.service.PicUrlService;

import java.time.LocalDate;

@RestController
public class SaveController {

    @Autowired
    PicUrlService picUrlService;

    @RequestMapping({"/api/addGallery/{verify}/{date}", "/api/addGallery/{verify}"})
    public Object addGallery(@PathVariable("verify") String verify,
                             @PathVariable(value = "date", required = false) String date) {

        if (ConstantInfo.VERIFY.equals(verify)) {
            return ResultRest.fail("验证出错!");
        }

        LocalDate spiderDate = null;
        if (date != null && date.matches("^\\d{4}-\\d{1,2}-\\d{1,2}")) {
            String[] dates = date.split("-");
            spiderDate = LocalDate.of(Integer.parseInt(dates[0]), Integer.parseInt(dates[1]), Integer.parseInt(dates[2]));
        }

        int num = picUrlService.inset2Sql(spiderDate);
        return num > 0
                ? ResultRest.success("Added " + num + " gallery!")
                : ResultRest.fail("Adding gallery failed, the date of " + date + " hadn't galleries" +
                " or these galleries already exist in sql");
    }
}
