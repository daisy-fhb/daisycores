package com.daisy.myblog.service.Impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.daisy.myblog.dao.ArticleMapper;
import com.daisy.myblog.dao.TagsMapper;
import com.daisy.myblog.entity.Article;
import com.daisy.myblog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by daisy.
 */
@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    TagsMapper tagsMapper;

    @Override
    public int addNewArticle(Article article) {
        //处理文章摘要
        if (article.getSummary() == null || "".equals(article.getSummary())) {
            //直接截取
            String stripHtml = stripHtml(article.getHtmlContent());
            article.setSummary(stripHtml.substring(0, stripHtml.length() > 50 ? 50 : stripHtml.length()));
        }
        if (article.getId() == -1) {
            //添加操作
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            if (article.getState() == 1) {
                //设置发表日期
                article.setPublishDate(timestamp);
            }
            article.setEditTime(timestamp);
            //设置当前用户
            article.setUid(1L);
            article.setId(IdUtil.createSnowflake(1,1).nextId());
            article.setPageView(0);
            int i = articleMapper.addNewArticle(article);
            //打标签
            String[] dynamicTags = article.getDynamicTags();
            if (dynamicTags != null && dynamicTags.length > 0) {
                int tags = addTagsToArticle(dynamicTags, article.getId());
                if (tags == -1) {
                    return tags;
                }
            }
            return i;
        } else {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            if (article.getState() == 1) {
                //设置发表日期
                article.setPublishDate(timestamp);
            }
            //更新
            article.setEditTime(new Timestamp(System.currentTimeMillis()));
            int i = articleMapper.updateArticle(article);
            //修改标签
            String[] dynamicTags = article.getDynamicTags();
            if (dynamicTags != null && dynamicTags.length > 0) {
                int tags = addTagsToArticle(dynamicTags, article.getId());
                if (tags == -1) {
                    return tags;
                }
            }
            return i;
        }
    }
    @Override
    public int addTagsToArticle(String[] dynamicTags, Long aid) {
        //1.删除该文章目前所有的标签
        tagsMapper.deleteTagsByAid(aid);
        //2.将上传上来的标签全部存入数据库
        tagsMapper.saveTags(dynamicTags);
        //3.查询这些标签的id
        List<Long> tIds = tagsMapper.getTagsIdByTagName(dynamicTags);
        //4.重新给文章设置标签
        int i = tagsMapper.saveTags2ArticleTags(tIds, aid);
        return i == dynamicTags.length ? i : -1;
    }
    @Override
    public String stripHtml(String content) {
        content = content.replaceAll("<p .*?>", "");
        content = content.replaceAll("<br\\s*/?>", "");
        content = content.replaceAll("\\<.*?>", "");
        return content;
    }
    @Override
    public List<Article> getArticleByState(Integer state, Integer page, Integer count,String keywords) {
        int start = (page - 1) * count;
        Long uid =1L;
        return articleMapper.getArticleByState(state, start, count, uid,keywords);
    }
    @Override
    public int getArticleCountByState(Integer state, Long uid,String keywords) {
        return articleMapper.getArticleCountByState(state, uid,keywords);
    }
    @Override
    public int updateArticleState(Long[] aids, Integer state) {
        return articleMapper.deleteArticleById(aids);
//        if (state == 2) {
//            return articleMapper.deleteArticleById(aids);
//        } else {
//            return articleMapper.updateArticleState(aids, 2);//放入到回收站中
//        }
    }
    @Override
    public Article getArticleById(Long aid) {
        Article article = articleMapper.getArticleById(aid);
        List<String> tags=articleMapper.getTagsByaid(aid);
        String []tagg=new String[tags.size()];
        for (int i=0;i<tags.size();i++){
           tagg[i]=tags.get(i);
        }
        article.setDynamicTags(tagg);
        articleMapper.pvIncrement(aid);
        return article;
    }
    @Override
    public void pvStatisticsPerDay() {
        articleMapper.pvStatisticsPerDay();
    }
}
