package com.cloud.util;

import java.io.Serializable;

/**
 * @author zhuz
 * @date 2020/8/5
 */
public class UploadResult extends ResultVo implements Serializable {
    /**
     * 原始文件名
     */
    private String originalFileName;

    /**
     * 文件扩展名
     */
    private String fileExtName;

    /**
     * 文件存储名
     */
    private String fileSavedName;

    /**
     * 文件存储相对路径
     */
    private String fileSavedPath;

    /**
     * 文件预览完整路径
     */
    private String filePreviewPathFull;

    public UploadResult() {
        super();
    }

    public UploadResult(byte returnCode, String msg) {
        super(returnCode, msg);
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getFileExtName() {
        return fileExtName;
    }

    public void setFileExtName(String fileExtName) {
        this.fileExtName = fileExtName;
    }

    public String getFileSavedName() {
        return fileSavedName;
    }

    public void setFileSavedName(String fileSavedName) {
        this.fileSavedName = fileSavedName;
    }

    public String getFileSavedPath() {
        return fileSavedPath;
    }

    public void setFileSavedPath(String fileSavedPath) {
        this.fileSavedPath = fileSavedPath;
    }

    public String getFilePreviewPathFull() {
        return filePreviewPathFull;
    }

    public void setFilePreviewPathFull(String filePreviewPathFull) {
        this.filePreviewPathFull = filePreviewPathFull;
    }
}
