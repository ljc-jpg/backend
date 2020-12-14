package com.cloud.controller;

import com.cloud.model.Article;
import com.cloud.model.User;
import com.cloud.service.ArticleService;
import com.cloud.util.ResultVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static com.cloud.util.ResultVo.RETURN_CODE_ERR;

/**
 * @author zhuz
 * @date 2020/12/7
 */
@RestController
@RequestMapping("/article")
public class ArticleController {


    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @Resource
    private ArticleService articleService;

    @GetMapping("/articleByUser")
    public ResultVo<Article> articleByUser(Article article) {
        ResultVo resultVo = new ResultVo();
        try {
            List<Article> list  = articleService.articleByUser(article);
            resultVo.setData(list);
        } catch (Exception e) {
            logger.error("articleByUser:", e);
            resultVo.setMsg("articleByUser:" + e);
            resultVo.setCode(RETURN_CODE_ERR);
        }
        return resultVo;
    }


}
