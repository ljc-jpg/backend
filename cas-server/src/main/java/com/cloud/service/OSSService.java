package com.cloud.service;

import com.aliyun.oss.OSSClient;
import com.cloud.utils.UploadResult;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.InputStream;
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
     * @param addressee 收件人邮箱  content邮件内容 subject 邮件主题
     * @Description:
     * @author zhu zheng
     * @date 2020/3/2
     */
    public boolean sendEmail(String addressee, String content, String subject) {
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
        // 构建授权信息，发件人邮件用户名、授权码 用于进行SMTP进行身份验证  //vpxpiufkxhcjbfig
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
        InternetAddress form = null;
        try {
            form = new InternetAddress(props.getProperty("mail.smtp.user"));
        } catch (AddressException e) {
            log.error("第一步报错:" + e);
            return false;
        }

        try {
            message.setFrom(form);
            // 设置收件人的邮箱
            InternetAddress to = new InternetAddress(addressee);
            message.setRecipient(Message.RecipientType.TO, to);
            // 设置邮件标题
            message.setSubject(subject);


            //创建图片节点  添加图片的方式是将 图片唯一编号包含到邮件内容中
//            MimeBodyPart image = new MimeBodyPart();
//            //读取本地文件
//            DataHandler dataHandler = new DataHandler(new FileDataSource("src/lib/love.jpg"));
//            //将图片添加至结点
//            image.setDataHandler(dataHandler);
//            //为 图片"节点"设置一个唯一编号
//            image.setContentID("pic");
            // 创建文本"节点"
            MimeBodyPart text = new MimeBodyPart();
            // 这里添加图片的方式是将整个图片包含到邮件内容中
            text.setContent(content, "text/html;charset=UTF-8");
//            text.setContent(
//                    "<a href='" + content + "'>点击我查看工资单</a>" //+  "<img src='cid:pic'/>"
//                    , "text/html;charset=UTF-8"
//            );

            // 创建附件结点1
//            MimeBodyPart attachment  = new MimeBodyPart();
//            // 读取本地文件
//            DataHandler dataHandler1 = new DataHandler(new FileDataSource("src/com/funyoo/mail/test/MailTest.java"));
//            // 将文件添加至结点
//            attachment.setDataHandler(dataHandler1);
//            // 设置附件的文件名（需要编码）
//            attachment.setFileName(MimeUtility.encodeText(dataHandler1.getName()));

            // 创建附件结点2
//            MimeBodyPart jar  = new MimeBodyPart();
//            // 读取本地文件
//            DataHandler dataHandler2 = new DataHandler(new FileDataSource("src/lib/mail-1.4.7.jar"));
//            // 将文件添加至结点
//            jar.setDataHandler(dataHandler2);
//            // 设置附件的文件名（需要编码）
//            jar.setFileName(MimeUtility.encodeText(dataHandler2.getName()));

            // 创建混合节点  将图片节点 文件结点 附件结点 加入
            MimeMultipart multipart = new MimeMultipart();
//            multipart.addBodyPart(image);
            multipart.addBodyPart(text);
//            multipart.addBodyPart(attachment);
//            multipart.addBodyPart(jar);

            // 将混合节点加入邮件中
            message.setContent(multipart);
            // 发送邮件
            Transport.send(message);
        } catch (MessagingException e) {
            log.error("第二步报错:" + e);
            return false;
        }
        return true;
    }

}
