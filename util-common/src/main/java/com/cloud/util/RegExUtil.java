package com.cloud.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 正则表达式工具类
 *
 * @author zhuz
 * @date 2020/7/31
 */
public class RegExUtil {

    public RegExUtil() {
    }

    private static final Logger logger = LoggerFactory.getLogger(RegExUtil.class);

    /**
     * 判断数字类型
     *
     * @param str
     * @return {@link Boolean}
     * @author zhuz
     * @date 2020/7/31
     */
    public static Boolean isNumbers(String str) {
        logger.info("isNumbers" + str);
        Boolean strResult = str.matches("-?[0-9]+.?[0-9]*");
        return strResult;
    }

    /**
     * 判断邮件
     *
     * @param str
     * @return {@link Boolean}
     * @author zhuz
     * @date 2020/7/31
     */
    public static Boolean isEmail(String str) {
        logger.info("isEmail" + str);
        Boolean strResult = str.matches("^[a-z0-9A-Z]+[- | a-z0-9A-Z . _]+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-z]{2,}$");
        return strResult;
    }

}
