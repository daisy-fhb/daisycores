package com.daisy.temple.service.Impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.img.Img;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.system.SystemUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.daisy.temple.dao.TestDao;
import com.daisy.temple.entity.TestInfo;
import com.daisy.temple.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.File;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class TestServiceImpl implements TestService {

    @Autowired
    TestDao testDao;

    @Override
    public JSONObject upload(HttpServletRequest request) {
        JSONObject result=JSONUtil.createObj();
        try {
            String web_root;// 绝对路径
            Snowflake snowflake = IdUtil.createSnowflake(1, 1);
            if (SystemUtil.getOsInfo().isLinux()) {
                web_root = "/usr/sanxing/sx_imgs";
            } else {
                web_root = "D:\\" + "sx_imgs";
            }
            File exisfolder = new File(web_root);//检查文件夹是否存在 ，不存在则新建
            if (!exisfolder.exists() || !exisfolder.isDirectory()) {
                exisfolder.mkdirs();
            }
            MultipartHttpServletRequest multipartRequest =
                    WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class);
            MultipartFile file = multipartRequest.getFile("upImgs");
            String originalFilename = file.getOriginalFilename();
            String fileName = snowflake.nextIdStr() + "_" + originalFilename;//获取文件名
            if (    !FileUtil.extName(fileName).equals(ImgUtil.IMAGE_TYPE_JPG) &&
                    !FileUtil.extName(fileName).equals(ImgUtil.IMAGE_TYPE_JPEG) &&
                    !FileUtil.extName(fileName).equals(ImgUtil.IMAGE_TYPE_BMP) &&
                    !FileUtil.extName(fileName).equals(ImgUtil.IMAGE_TYPE_PNG) &&
                    !FileUtil.extName(fileName).equals(ImgUtil.IMAGE_TYPE_GIF)
            ) {
                result.put("flag",false);
                result.put("msg"," 图片格式错误，请将原图截图后上传截图即可 ");
            }else{
            String filePath = web_root + File.separatorChar + fileName;//拼装文件存储路径
            //检查上传文件是否存在，若存在则覆盖，不存在则新增
            File existcheck = new File(web_root + File.separatorChar + fileName);
            if (existcheck.exists() && existcheck.isFile()) {
                existcheck.delete();
            }

            File src_imgFile = new File(filePath);
            //存入磁盘
            file.transferTo(src_imgFile);
            if ((FileUtil.size(src_imgFile) / 1024 > 1200)) {
                Img img = Img.from(src_imgFile);
                img.scale(0.8f);
                img.write(src_imgFile);
            }
            //存数据库
            String model = request.getParameter("model");
            String type = request.getParameter("type");
            result.put("flag",true);
            result.put("msg","上传成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("flag",false);
            result.put("msg"," 上传失败 "+e.getMessage());
        }
        return result;
    }

    @Override
    public JSONObject save(String postData) {
        JSONObject result=JSONUtil.createObj();
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        try {
            TestInfo testInfo= JSONUtil.toBean(postData, TestInfo.class);
            testInfo.setId(snowflake.nextIdStr());
            testInfo.setUptime(DateUtil.now());
            int re=testDao.insert(testInfo);
            if (re>0){
                result.put("flag",true);
            }else{
                result.put("flag",false);
                result.put("msg","添加失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            result.put("flag",false);
            result.put("msg",e.getMessage());
        }
        return result;
    }

    @Override
    public JSONObject delete(String id) {
        JSONObject result=JSONUtil.createObj();
        try {
            int re=testDao.deleteById(JSONUtil.parseObj(id).getStr("id"));
            if (re>0){
                result.put("flag",true);
            }else{
                result.put("flag",false);
                result.put("msg","删除失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            result.put("flag",false);
            result.put("msg",e.getMessage());
        }
        return result;
    }

    @Override
    public JSONObject update(String postData) {
        JSONObject result=JSONUtil.createObj();
        try {
            TestInfo testInfo= JSONUtil.toBean(postData,TestInfo.class);
            int re=testDao.updateById(testInfo);
            if (re>0){
                result.put("flag",true);
            }else{
                result.put("flag",false);
                result.put("msg","更新失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            result.put("flag",false);
            result.put("msg",e.getMessage());
        }
        return result;
    }

    @Override
    public JSONObject query(String postData) {
        JSONObject result=JSONUtil.createObj();
        try {
            JSONObject param=JSONUtil.parseObj(postData);
            HashMap paraMap=new HashMap<>();
            if (param.containsKey("type")){
                paraMap.put("type",param.get("type"));
            }
            if (param.containsKey("model")){
                paraMap.put("model",param.get("model"));
            }
            List<TestInfo> re=testDao.selectByMap(paraMap);
            if (re!=null&&re.size()>0){
                result.put("flag",true);
                result.put("msg",re);
            }
        }catch (Exception e){
            e.printStackTrace();
            result.put("flag",false);
            result.put("msg",e.getMessage());
        }
        return result;
    }

    @Override
    public JSONObject querybymutitable(String postData) {
        JSONObject result=JSONUtil.createObj();
        try {
            JSONObject param=JSONUtil.parseObj(postData);
            HashMap paraMap=new HashMap<>();
            if (param.containsKey("model")){
                paraMap.put("model",param.get("model"));
            }
            if (param.containsKey("type")){
                paraMap.put("type",param.get("type"));
            }
            Page pageBean = new Page<TestInfo>(1, 2);
            paraMap.put("page",pageBean);
            List<HashMap> re=testDao.QueryByMutiTable(paraMap);
            if (re!=null&&re.size()>0){
                result.put("flag",true);
                result.put("msg",re);
            }
        }catch (Exception e){
            e.printStackTrace();
            result.put("flag",false);
            result.put("msg",e.getMessage());
        }
        return result;
    }

}
