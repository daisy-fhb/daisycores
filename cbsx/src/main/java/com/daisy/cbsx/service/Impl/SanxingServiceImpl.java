package com.daisy.cbsx.service.Impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.img.Img;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.setting.dialect.Props;
import cn.hutool.system.SystemUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.daisy.cbsx.dao.SanxingDao;
import com.daisy.cbsx.entity.Info;
import com.daisy.cbsx.service.SanxingService;
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
public class SanxingServiceImpl implements SanxingService {

    @Autowired
    SanxingDao sanxingDao;

    @Override
    public String upload(HttpServletRequest request) {
        try {
            String web_root;// 绝对路径
            Snowflake snowflake = IdUtil.createSnowflake(1, 1);
            if (SystemUtil.getOsInfo().isLinux()) {
                web_root = "/usr/sanxing/front/sx/dist/sx_imgs/";
            } else {
                web_root = "E:\\迅雷下载\\" + "sx_imgs/";
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
            if (!FileUtil.extName(fileName).equals(ImgUtil.IMAGE_TYPE_JPG) &&
                    !FileUtil.extName(fileName).equals(ImgUtil.IMAGE_TYPE_JPEG) &&
                    !FileUtil.extName(fileName).equals(ImgUtil.IMAGE_TYPE_BMP) &&
                    !FileUtil.extName(fileName).equals(ImgUtil.IMAGE_TYPE_PNG) &&
                    !FileUtil.extName(fileName).equals(ImgUtil.IMAGE_TYPE_GIF)
            ) {
                return " 图片格式错误，请将原图截图后上传截图即可 ";
            }
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
            Info info = new Info();
            info.setId(String.valueOf(snowflake.nextIdStr()));
            info.setModel(model);
            info.setType(type);
            info.setUptime(DateUtil.now());
            info.setFilename(fileName);
            sanxingDao.save(info);
            return "上传成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "上传失败 " + e.getMessage();
        }
    }

    @Override
    public String query(String postData, HttpServletRequest request) {
        JSONObject re = new JSONObject();
        int counts;
        try {
            String web_root = "";// 绝对路径
            if (SystemUtil.getOsInfo().isLinux()) {
                web_root = "/usr/sanxing/front/sx/dist/sx_imgs/";
            } else if (SystemUtil.getOsInfo().isWindows()) {
                web_root = "E:\\迅雷下载\\" + "sx_imgs";
            }
            JSONObject post=JSONUtil.parseObj(postData);
            String realpath = request.getSession().getServletContext().getRealPath("");
            String realData = post.getStr("serial");
            String page = post.getStr("curPage");
            Page pageBean = new Page<Info>(Integer.parseInt(page), 5);
            HashMap param = new HashMap();
            param.put("type", realData);
            counts=sanxingDao.selectByMap(param).size();
            param.put("page", pageBean);
            List<HashMap> tmp = sanxingDao.querybyPage(param);
            JSONArray jsonArray = new JSONArray();
            Props props = new Props("application.properties");
            String current_ip = props.getProperty("current_ip");
            if (tmp != null && !tmp.isEmpty()) {
                for (HashMap e : tmp) {
                    //存入应用服务器
                    if (FileUtil.exist(web_root + File.separatorChar + e.get("filename"))) {
                        FileUtil.copy(web_root + File.separatorChar + e.get("filename"), realpath, true);
                        JSONObject jsonObject = JSONUtil.parseObj(e);
                        jsonObject.put("url", "http://" + current_ip + "/sx_imgs/" + e.get("filename"));
                        jsonArray.add(jsonObject);
                    }
                }
                re.put("msg", jsonArray);
            }
            re.put("flag", true);
            re.put("counts",counts);
        } catch (Exception e) {
            e.printStackTrace();
            re.put("msg", "查询失败 " + e.getMessage());
        }
        return re.toString();
    }

    @Override
    public String delete(String postData, HttpServletRequest request) {
        JSONObject re = new JSONObject();
        try {
            String web_root = "";// 绝对路径
            if (SystemUtil.getOsInfo().isLinux()) {
                web_root = "/usr/sanxing/front/sx/dist/sx_imgs/";
            } else if (SystemUtil.getOsInfo().isWindows()) {
                web_root = "E:\\迅雷下载\\" + "sx_imgs/";
            }

            HashMap param = new HashMap();
            param.put("id", JSONUtil.parseObj(postData).getStr("mid"));
            List<Info> tmp = sanxingDao.selectByMap(param);
            if (tmp != null && !tmp.isEmpty()) {
                for (Info e : tmp) {
                    int ree = sanxingDao.delete(e.getId());
                    if (ree > 0) {
                        re.put("msg", "删除成功");
                    }
                    boolean exist = FileUtil.exist(web_root + File.separatorChar + e.getFilename());
                    if (exist) {
                        FileUtil.del(web_root + File.separatorChar + e.getFilename());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            re.put("msg", "删除失败 " + e.getMessage());
        }
        re.put("flag", true);
        return re.toString();
    }
}
