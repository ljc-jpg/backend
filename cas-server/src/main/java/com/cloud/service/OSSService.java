package com.cloud.service;

import com.aliyun.oss.OSSClient;
import com.cloud.model.EmailContent;
import com.cloud.utils.UploadResult;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.URLDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.UUID;


/**
 * @author: zhuzheng
 * @date: 2020/1/24
 * @version: 1.0
 * @since: JDK 1.7
 */
@Service
public class OSSService {

    private static final Logger log = LoggerFactory.getLogger(OSSService.class);

    @Value("${aliyun.accessKeyId}")
    public String ALIYUN_ACCESS_KEY_ID;

    @Value("${aliyun.accessKeySecret}")
    private String ALIYUN_ACCESS_KEY_SECRET;

    @Value("${aliyun.endpoint}")
    private String ALIYUN_OSS_ENDPOINT;

    @Value("${aliyun.cname}")
    private String ALIYUN_OSS_ENDPOINT_CNAME;

    @Value("${aliyun.bucketName}")
    private String ALIYUN_OSS_BUCKET_NAME;

    @Value("${aliyun.root}")
    private String ALIYUN_OSS_DIR_CONSOLE_ROOT;

    @Value("${mail.host}")
    private String HOST;

    @Value("${mail.port}")
    private String PORT;

    @Value("${mail.user}")
    private String USER;

    @Value("${mail.password}")
    private String PASSWORD;

    /**
     * 单个文件上传
     *
     * @param originalFileName 原始文件名
     * @param relative         相对路径
     * @param inputStream      文件流
     * @return UploadResult
     */
    public UploadResult uploadSingle(String originalFileName, String relative, InputStream inputStream) {
        UploadResult uploadResult = new UploadResult();
        //创建客户端
        OSSClient ossClient = new OSSClient(ALIYUN_OSS_ENDPOINT, ALIYUN_ACCESS_KEY_ID, ALIYUN_ACCESS_KEY_SECRET);
        try {
            // 文件扩展名
            String fileExtName = FilenameUtils.getExtension(originalFileName);
            // 新文件名
            String fileSavedName = UUID.randomUUID().toString() + "." + fileExtName;
            //执行文件保存
//            ossClient.putObject(ALIYUN_OSS_BUCKET_NAME, ALIYUN_OSS_DIR_CONSOLE_ROOT + relative + fileSavedName, inputStream);
            //构造返回
            uploadResult.setOriginalFileName(originalFileName);
            uploadResult.setFileExtName(fileExtName);
            uploadResult.setFileSavedName(fileSavedName);
            uploadResult.setFileSavedPath(relative + fileSavedName);
            uploadResult.setFilePreviewPathFull(ALIYUN_OSS_ENDPOINT_CNAME + "/" + ALIYUN_OSS_DIR_CONSOLE_ROOT + relative + fileSavedName);
            uploadResult.setReturnCode(UploadResult.RETURN_CODE_SUCC);
            log.debug(originalFileName + "   " + ALIYUN_OSS_ENDPOINT_CNAME + "/" + ALIYUN_OSS_DIR_CONSOLE_ROOT + relative + fileSavedName);
            return uploadResult;
        } catch (Exception oe) {
            log.error("Error Message," + oe);
        } finally {
//            ossClient.shutdown();
        }
        return uploadResult;
    }

    /**
     * @Author zhuz
     * @Description 添加图片的方式 将整个图片包含到邮件内容中
     * @Date 9:47 2020/5/26
     * @Param [addressee（邮件地址）, content（邮件内容）, subject邮件主题）, files（邮件附件文件）, picture（邮件图片）]
     **/
    public boolean sendEmail(String addressee, List<EmailContent> list, String subject, String[] files) {
        log.error("收件人邮箱:" + addressee);
        // 创建Properties 类用于记录邮箱的一些属性
        Properties props = new Properties();
        // 表示SMTP发送邮件，必须进行身份验证
        props.put("mail.smtp.auth", "true");
        //此处填写SMTP服务器
        props.put("mail.smtp.host", HOST);
        //端口号，QQ邮箱端口587
        props.put("mail.smtp.port", PORT);
        // 此处填写，写信人的账号
        props.put("mail.smtp.user", USER);
        // 此处填写16位STMP口令
        props.put("mail.smtp.password", PASSWORD);
        // 构建授权信息，发件人邮件用户名、授权码 用于进行SMTP进行身份验证
        Authenticator authenticator = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                // 用户名、密码
                String userName = props.getProperty("mail.smtp.user");
                String password = props.getProperty("mail.smtp.password");
                return new PasswordAuthentication(userName, password);
            }
        };
        // 使用环境属性和授权信息，创建邮件会话
        Session mailSession = Session.getInstance(props, authenticator);
        mailSession.setDebug(true);
        // 创建邮件消息
        MimeMessage message = new MimeMessage(mailSession);
        // 设置发件人
        InternetAddress from = null;
        try {
            from = new InternetAddress(props.getProperty("mail.smtp.user"));
        } catch (AddressException e) {
            log.error("设置发件邮箱错误:" + e);
            throw new RuntimeException("设置发件邮箱错误:" + e);
        }
        // 创建混合节点
        MimeMultipart multipart = new MimeMultipart();

        try {
            message.setFrom(from);
            // 设置收件人的邮箱
            InternetAddress to = new InternetAddress(addressee);
            message.setRecipient(Message.RecipientType.TO, to);
            // 设置邮件标题
            message.setSubject(subject);
            String content = "";

            for (int i = 0; i < list.size(); i++) {
                try {
                    EmailContent emailContent = list.get(i);
                    if ("1".equals(emailContent.getType())) {
                        content = content + emailContent.getContent();
                    } else {
                        //创建图片节点
                        MimeBodyPart image = new MimeBodyPart();
                        URLDataSource url = new URLDataSource(new URL(emailContent.getContent()));
                        DataHandler dataHandler = new DataHandler(url);
                        //将图片添加至结点
                        image.setDataHandler(dataHandler);
                        //为 图片"节点"设置一个唯一编号
                        image.setContentID("pic" + i);
                        multipart.addBodyPart(image);
                        content = content + "<img src='cid:pic" + i + "'/>";
                    }
                } catch (Exception e) {
                    log.error("设置邮件图片错误:" + e);
                    throw new RuntimeException("设置邮件图片错误:" + e);
                }
            }

            // 创建文本节点
            MimeBodyPart text = new MimeBodyPart();
            text.setContent(content, "text/html;charset=UTF-8");
            multipart.addBodyPart(text);

            // 循环创建附件节点
            if (!ArrayUtils.isEmpty(files)) {
                for (int j = 0; j < files.length; j++) {
                    try {
                        MimeBodyPart attachment = new MimeBodyPart();
                        URLDataSource url = new URLDataSource(new URL(files[j]));
                        DataHandler dataHandler = new DataHandler(url);
                        attachment.setDataHandler(dataHandler);
                        // 设置附件的文件名（需要编码）
                        attachment.setFileName(MimeUtility.encodeText(dataHandler.getName()));
                        multipart.addBodyPart(attachment);
                    } catch (Exception e) {
                        log.error("设置附件文件名错误:" + e);
                        throw new RuntimeException("设置附件文件名错误:" + e);
                    }
                }
            }

            //混合节点存入邮件消息
            message.setContent(multipart);
            Transport.send(message);
        } catch (MessagingException e) {
            log.error("发送邮件报错:" + e);
            throw new RuntimeException("发送邮件报错:" + e);
        }
        return true;
    }
}
