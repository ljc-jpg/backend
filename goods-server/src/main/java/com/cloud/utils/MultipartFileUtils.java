package com.cloud.utils;

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

public class MultipartFileUtils {

    private static final Logger log = LoggerFactory.getLogger(MultipartFileUtils.class);

    public static CommonsMultipartFile getMultipartFile(File file, String fileType, String fileName) {
        if ((null == file) || (null == fileType) || (StringUtils.isEmpty(fileName))) {
            return null;
        }
        log.error("***fileName***:" + fileName);
        FileItemFactory factory = new DiskFileItemFactory(16, null);
        FileItem item = factory.createItem(fileType, "multipart/form-data", true, fileName);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        try {
            FileInputStream fis = new FileInputStream(file);
            OutputStream os = item.getOutputStream();
            while ((bytesRead = fis.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            fis.close();
        } catch (IOException e) {
            log.error("***getMultipartFile***:" + e);
        }
        return new CommonsMultipartFile(item);
    }

    /**
     * 根据文件名称删除指定目录下的文件
     *
     * @param path
     * @param fileName
     * @return
     */
    public static boolean deleteFile(String path, String fileName) {
        log.error("***********path*******:" + path, "****fileName****:" + fileName);
        if (StringUtils.isEmpty(path)) {
            return false;
        }
        if (StringUtils.isEmpty(fileName)) {
            return false;
        }
        File directory = new File(path);
        File[] files = directory.listFiles();//把目录directory下的所有文件放在数组files
        if (files.length == 0) {
            return false;
        }
        for (File file : files) {
            if (file.getName().equals(fileName)) { //若文件名与待删除文件名相同，则删除文件
                file.delete();
                return true;
            }
        }
        return false;
    }
//      输入流转字符串
//      StringWriter writer = new StringWriter();
//      IOUtils.copy(new FileInputStream(path), writer, StandardCharsets.UTF_8.name());
//      String str = writer.toString();

//      字符串转输入流
//      InputStream targetStream = IOUtils.toInputStream(fileInputStream, StandardCharsets.UTF_8.name());
}
