package com.cloud.util;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author zhuz
 */
public class MultipartFileUtils {

    private static Logger logger = LoggerFactory.getLogger(MultipartFileUtils.class);

    private MultipartFileUtils() {
    }

    public static CommonsMultipartFile getMultipartFile(File file, String fileType, String fileName) {
        if ((null == file) || (null == fileType) || (StringUtils.isEmpty(fileName))) {
            return null;
        }
        FileItemFactory factory = new DiskFileItemFactory(16, null);
        FileItem item = factory.createItem(fileType, "multipart/form-data", true, fileName);
        int bytesRead;
        byte[] buffer = new byte[Integer.parseInt(ActiveEnum.MB_EVENT.getValue())];
        try (FileInputStream fis = new FileInputStream(file); OutputStream os = item.getOutputStream()) {
            while ((bytesRead = fis.read(buffer, ActiveEnum.ZERO_EVENT.getKey(), Integer.parseInt(ActiveEnum.MB_EVENT.getValue()))) != -1) {
                os.write(buffer, ActiveEnum.ZERO_EVENT.getKey(), bytesRead);
            }
        } catch (IOException e) {
            logger.error("getMultipartFile错误:" + e);
        }
        return new CommonsMultipartFile(item);
    }

    /**
     * 根据文件名称删除指定目录下的文件
     *
     * @param path
     * @param fileName
     * @return {@link boolean}
     * @author zhuz
     * @date 2020/7/31
     */
    public static boolean deleteFile(String path, String fileName) {
        if (StringUtils.isEmpty(path)) {
            return false;
        }
        if (StringUtils.isEmpty(fileName)) {
            return false;
        }
        File directory = new File(path);
        //把目录directory下的所有文件放在数组files
        File[] files = directory.listFiles();
        if (files.length == ActiveEnum.ZERO_EVENT.getKey()) {
            return false;
        }
        for (File file : files) {
            //若文件名与待删除文件名相同，则删除文件
            if (file.getName().equals(fileName)) {
                file.delete();
                return true;
            }
        }
        return false;
    }

}
