package com.daisy.myblog.service;

import com.daisy.myblog.entity.Article;

import java.util.List;

public interface ArticleService {
    int addNewArticle(Article article);

    int addTagsToArticle(String[] dynamicTags, Long aid);

    String stripHtml(String content);

    List<Article> getArticleByState(Integer state, Integer page, Integer count, String keywords);

    int getArticleCountByState(Integer state, Long uid, String keywords);

    int updateArticleState(Long[] aids, Integer state);

    Article getArticleById(Long aid);

    void pvStatisticsPerDay();

}
