package com.cloud.service;

import com.cloud.dao.ArticleMapper;
import com.cloud.model.Article;
import com.cloud.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author zhuz
 * @Date 2020/12/14
 */
@Service
public class ArticleService {

    private static final Logger logger = LoggerFactory.getLogger(ArticleService.class);

    @Resource
    private ArticleMapper articleMapper;

    public List<Article> articleByUser(Article article) {
        List<Article> list = articleMapper.select(article);
        return list;
    }

}
