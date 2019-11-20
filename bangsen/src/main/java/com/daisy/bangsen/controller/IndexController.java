package com.daisy.bangsen.controller;


import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.BigExcelWriter;
import com.daisy.bangsen.dao.RespectPlanDao;
import com.daisy.bangsen.entity.producement.RespectPlan;
import com.daisy.bangsen.service.IndexService;
import com.daisy.bangsen.util.DownLoadUtil;
import com.daisy.bangsen.util.RespBean;
import com.daisy.bangsen.util.enums.ExportTypesEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("index")
@CrossOrigin
public class IndexController {

    @Autowired
    IndexService indexService;
    @Autowired
    RespectPlanDao respectPlanDao;


    @RequestMapping("top")
   public RespBean top() {
        return indexService.top();
    }

    @RequestMapping("monthly")
   public RespBean monthly() {
        return indexService.monthly();
    }

    @RequestMapping("dept")
    public RespBean dept() {
        return indexService.dept();
    }

    @RequestMapping("dutyrecord")
   public RespBean dutyrecord() {
        return indexService.dutyrecord();
    }

    @RequestMapping(value ="updateplan", method = RequestMethod.POST)
    public boolean updateplan(@RequestBody String postData) {
        RespectPlan respectPlan= JSONUtil.toBean(postData,RespectPlan.class);
        int i= respectPlanDao.updateById(respectPlan);
        return i>0?true:false;
    }

    @RequestMapping(value = "/export" , method = RequestMethod.GET)
    public ResponseEntity export(String type) {
        if (StringUtils.isNotBlank(type)) {
            HashMap datalist = indexService.selectDataList(type);
            List<?> data = (List<?>) datalist.get("data");
            String tittle = datalist.get("tittle").toString();
            ExportTypesEnum exportTypesEnum = Enum.valueOf(ExportTypesEnum.class, type);
            List<?> HeadRows = exportTypesEnum.getTypeHead();
            String localPath = "/home/bangsen/export/" + tittle + ".xlsx";
            if (FileUtil.exist(localPath)) {
                FileUtil.del(localPath);
            }
            BigExcelWriter writer = new BigExcelWriter(localPath);
            int count = HeadRows.size();
            writer.merge(count - 1, tittle);
            for (int i = 0; i < count; i++) {
                writer.setColumnWidth(i, 30);
            }
            writer.writeHeadRow(HeadRows);
            writer.write(data);
            // 关闭writer，释放内存
            writer.close();
            ResponseEntity re = DownLoadUtil.downloadFile(tittle + ".xlsx", "/home/bangsen/export/");
            return re;
        } else {
            return null;
        }
    }

}
