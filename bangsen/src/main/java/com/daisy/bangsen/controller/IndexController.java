package com.daisy.bangsen.controller;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.system.SystemUtil;
import com.daisy.bangsen.dao.RespectPlanDao;
import com.daisy.bangsen.dao.UserDao;
import com.daisy.bangsen.entity.auth.User;
import com.daisy.bangsen.entity.producement.RespectPlan;
import com.daisy.bangsen.service.IndexService;
import com.daisy.bangsen.util.DownLoadUtil;
import com.daisy.bangsen.util.RespBean;
import com.daisy.bangsen.util.enums.ExportTypesEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("index")
@CrossOrigin
public class IndexController {

    @Autowired
    IndexService indexService;
    @Autowired
    RespectPlanDao respectPlanDao;
    @Autowired
    UserDao userDao;


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

    @RequestMapping(value = "/upload" , method = RequestMethod.POST)
    public RespBean upload( String type,HttpServletRequest request) {
        RespBean respBean=new RespBean();
        try {
            String web_root;// 绝对路径
            if (SystemUtil.getOsInfo().isLinux()){
                web_root = "/home/bangsen/import/";
            }else{
                web_root = "D:\\home\\bangsen\\import\\";
            }

            File exisfolder = new File(web_root);//检查文件夹是否存在 ，不存在则新建
            if (!exisfolder.exists() || !exisfolder.isDirectory()) {
                exisfolder.mkdirs();
            }else{
                //检查上传文件是否存在，若存在则覆盖，不存在则新增
                FileUtil.del(web_root);
                exisfolder.mkdirs();
            }
            MultipartHttpServletRequest multipartRequest =
                    WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class);
            MultipartFile file = multipartRequest.getFile("file");
            if (file==null){
                respBean.setStatus(201);
                respBean.setMsg("附件为空!");
                return respBean;
            }
            String originalFilename = file.getOriginalFilename();
            String fileName = RandomUtil.randomString(10) + "_" + originalFilename;//获取文件名
            if (!FileUtil.extName(fileName).equals("xls") &&
                    !FileUtil.extName(fileName).equals("xlsx")
            ) {
                respBean.setStatus(201);
                respBean.setMsg("表格格式错误");
                return respBean;
            }
            String filePath = web_root + File.separatorChar + fileName;//拼装文件存储路径
            File src_imgFile = new File(filePath);
            //存入磁盘
            file.transferTo(src_imgFile);
            //存数据库
            if (FileUtil.exist(src_imgFile)) {
                ExcelReader reader ;
                if ("attendance".equals(type)){
                    //读取考勤信息
                    reader  = ExcelUtil.getReader(src_imgFile,2);
                    List<List<Object>> readAll = reader.read();
                    System.out.println();
                }else{
                    reader  = ExcelUtil.getReader(src_imgFile);
                }
                List<Map<String, Object>> readAll = reader.readAll();
                for (Map<String, Object> map : readAll) {
                    if ("userinfo".equals(type)) {
                        User user = new User();
                        if (map.get("性别").toString().equals("男")) {
                            user.setSex(1);
                        } else {
                            user.setSex(0);
                        }
                        user.setName(map.get("姓名").toString());
                        user.setEducation(map.get("学历").toString());
                        user.setMajor(map.get("专业").toString());
                        user.setPhone(map.get("电话").toString());
                        user.setEmail(map.get("邮箱").toString());
                        user.setInterviewtime(map.get("入职时间").toString());
                        user.setIshire(map.get("是否聘用").toString());
                        user.setRoleid("1");
                        user.setId(System.currentTimeMillis());
                        userDao.insert(user);
                    }else if("caigou".equals(type)){

                    }
                }
            }
            respBean.setStatus(200);
            respBean.setMsg("导入成功！");
            return respBean;
        } catch (Exception e) {
            e.printStackTrace();
            respBean.setStatus(201);
            respBean.setMsg("导入失败，系统错误！");
            return respBean;
        }
    }

    public static void main(String[] args) {
        ExcelReader reader = ExcelUtil.getReader("C:\\Users\\Daisy\\Downloads\\报表列表详情.xlsx");
        List<List<Object>> readAll = reader.read();
        System.out.println("111");
    }

}
