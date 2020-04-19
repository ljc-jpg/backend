package com.cloud.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class RegExUtil {

    private static final Logger log = LoggerFactory.getLogger(RegExUtil.class);

    /**
     * @Description: 判断数字类型
     * @author zhu zheng
     */
    public static Boolean isNumbers(String str) {
        log.debug("isNumbers", str);
        Boolean strResult = str.matches("-?[0-9]+.?[0-9]*");
        return strResult;
    }

    public static Boolean isEmail(String str) {
        log.debug("isEmail", str);
        Boolean strResult = str.matches("^[a-z0-9A-Z]+[- | a-z0-9A-Z . _]+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-z]{2,}$");
        return strResult;
    }

}
