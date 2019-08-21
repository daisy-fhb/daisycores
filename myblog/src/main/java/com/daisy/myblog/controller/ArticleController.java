package com.daisy.myblog.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.system.SystemUtil;
import com.daisy.myblog.entity.Article;
import com.daisy.myblog.service.ArticleService;
import com.daisy.myblog.util.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by daisy.
 */
@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleController {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    @Autowired
    ArticleService articleService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public RespBean addNewArticle(Article article) {
        int result = articleService.addNewArticle(article);
        if (result == 1) {
            return new RespBean(true,"success", article.getId() + "");
        } else {
            return new RespBean(false,"error", article.getState() == 0 ? "文章保存失败!" : "文章发表失败!");
        }
    }

    /**
     * 上传图片
     *
     * @return 返回值为图片的地址
     */
    @RequestMapping(value = "/uploadimg", method = RequestMethod.POST)
    public RespBean uploadImg(HttpServletRequest req, MultipartFile image) {
        StringBuffer url = new StringBuffer();
        String filePath = "/blogimg/" + sdf.format(new Date());
        String imgFolderPath = req.getServletContext().getRealPath(filePath);
        String SystemFolderPath ;
        if(SystemUtil.getOsInfo().isWindows()){
            SystemFolderPath="D:\\blogs";
            url.append(req.getScheme())
                    .append("://")
                    .append(req.getServerName())
                    .append(":")
                    .append(req.getServerPort())
                    .append(req.getContextPath())
                    .append(filePath);
        }else{
            SystemFolderPath="/usr/sanxing/front/blog/dist/imgs";
            url.append("http://blog.daisyfhb.cn/imgs/");
        }
        File imgFolder = new File(imgFolderPath);
        if (!imgFolder.exists()) {
            imgFolder.mkdirs();
        }

        String imgName = UUID.randomUUID() + "_" + image.getOriginalFilename().replaceAll(" ", "");
        try {
            if (SystemUtil.getOsInfo().isWindows()){
                image.transferTo(new File(imgFolder, imgName));
                FileUtil.copyFile(new File(imgFolder,imgName),new File(SystemFolderPath));
            }else {
                image.transferTo(new File(SystemFolderPath, imgName));
            }
            url.append("/").append(imgName);
            return new RespBean(true,"success", url.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new RespBean(false,"error", "上传失败!");
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Map<String, Object> getArticleByState(@RequestParam(value = "state", defaultValue = "-1") Integer state, @RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "count", defaultValue = "6") Integer count, String keywords) {
        int totalCount = articleService.getArticleCountByState(state, 1L,keywords);
        List<Article> articles = articleService.getArticleByState(state, page, count,keywords);
        Map<String, Object> map = new HashMap<>();
        map.put("totalCount", totalCount);
        map.put("articles", articles);
        return map;
    }

    @RequestMapping(value = "/{aid}", method = RequestMethod.GET)
    public Article getArticleById(@PathVariable Long aid) {
        return articleService.getArticleById(aid);
    }

    @RequestMapping(value = "/dustbin", method = RequestMethod.PUT)
    public RespBean updateArticleState(Long[] aids, Integer state) {
        if (articleService.updateArticleState(aids, state) == aids.length) {
            return new RespBean(true,"success", "删除成功!");
        }
        return new RespBean(false,"error", "删除失败!");
    }

}
